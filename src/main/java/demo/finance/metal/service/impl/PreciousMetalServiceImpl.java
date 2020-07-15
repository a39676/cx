package demo.finance.metal.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.baseCommon.pojo.result.CommonResultCX;
import demo.baseCommon.service.CommonService;
import demo.finance.metal.mapper.MetalPrice10minuteMapper;
import demo.finance.metal.mapper.MetalPrice1dayMapper;
import demo.finance.metal.mapper.MetalPrice1monthMapper;
import demo.finance.metal.mapper.MetalPrice30minuteMapper;
import demo.finance.metal.mapper.MetalPrice5minuteMapper;
import demo.finance.metal.mapper.MetalPrice60minuteMapper;
import demo.finance.metal.mapper.MetalPriceMapper;
import demo.finance.metal.mapper.MetalPriceNoticeMapper;
import demo.finance.metal.pojo.constant.MetalConstant;
import demo.finance.metal.pojo.dto.InsertNewMetalPriceNoticeSettingDTO;
import demo.finance.metal.pojo.po.MetalPrice;
import demo.finance.metal.pojo.po.MetalPrice10minute;
import demo.finance.metal.pojo.po.MetalPrice5minute;
import demo.finance.metal.pojo.po.MetalPrice5minuteExample;
import demo.finance.metal.pojo.po.MetalPriceExample;
import demo.finance.metal.pojo.po.MetalPriceExample.Criteria;
import demo.finance.metal.pojo.po.MetalPriceNotice;
import demo.finance.metal.service.PreciousMetalService;
import demo.tool.pojo.type.MailType;
import demo.tool.service.MailService;
import demo.tool.service.ValidRegexToolService;
import precious_metal.pojo.dto.PreciousMetailPriceDTO;
import precious_metal.pojo.dto.TransmissionPreciousMetalPriceDTO;
import precious_metal.pojo.type.MetalType;
import tool.pojo.type.UtilOfWeightType;

@Service
public class PreciousMetalServiceImpl extends CommonService implements PreciousMetalService {

	@Autowired
	private MailService mailService;
	@Autowired
	private ValidRegexToolService validRegexToolService;
	
	@Autowired
	private MetalPriceMapper metalPriceMapper;
	@Autowired
	private MetalPriceNoticeMapper metalPriceNoticeMapper;
	@Autowired
	private MetalPrice5minuteMapper metalPrice5minuteMapper;
	@Autowired
	private MetalPrice10minuteMapper metalPrice10minuteMapper;
	@Autowired
	private MetalPrice30minuteMapper metalPrice30minuteMapper;
	@Autowired
	private MetalPrice60minuteMapper metalPrice60minuteMapper;
	@Autowired
	private MetalPrice1dayMapper metalPrice1dayMapper;
	@Autowired
	private MetalPrice1monthMapper metalPrice1monthMapper;
	
	@Override
	public CommonResult reciveMetalPrice(TransmissionPreciousMetalPriceDTO dto) {
		CommonResult result = new CommonResult();
		List<PreciousMetailPriceDTO> list = dto.getPriceList();
		
		MetalPrice tmpPO = null;
		int insertCount = 0;
		for(PreciousMetailPriceDTO d : list) {
			if(d.getPrice() == null || d.getWeightUtilType() == null) {
				continue;
			}
			
			UtilOfWeightType weightType = UtilOfWeightType.getType(d.getWeightUtilType());
			if(weightType == null) {
				continue;
			}
			
			MetalType metalType = MetalType.getType(d.getMetalType());
			if(metalType == null) {
				continue;
			}
			
			tmpPO = new MetalPrice();
			tmpPO.setId(snowFlake.getNextId());
			tmpPO.setMetalType(d.getMetalType());
			tmpPO.setPrice(new BigDecimal(d.getPrice()));
			tmpPO.setWeightType(d.getWeightUtilType());
			tmpPO.setCreateTime(LocalDateTime.now());
			
			insertCount += metalPriceMapper.insertSelective(tmpPO);
		}
		
		if(insertCount > 0) {
			result.setIsSuccess();
		}
		
		return result;
	}
	
	@Override
	public void noticeHandler() {
		List<MetalPriceNotice> noticeList = metalPriceNoticeMapper.selectValidNoticeSetting(LocalDateTime.now());
		if(noticeList == null || noticeList.isEmpty()) {
			return;
		}
		
		for(MetalPriceNotice notice: noticeList) {
			subNoticeHandler(notice);
		}
		
	}
	
	private void subNoticeHandler(MetalPriceNotice noticeSetting) {
		MetalType metalType = MetalType.getType(noticeSetting.getMetalType());
		if(metalType == null) {
			return;
		}
		
		MetalPriceExample example = new MetalPriceExample();
		Criteria criteria = example.createCriteria();
		criteria.andMetalTypeEqualTo(metalType.getCode());
		example.setOrderByClause("create_time desc");
		RowBounds r = new RowBounds(0, 1);
		
		List<MetalPrice> lastPOList = metalPriceMapper.selectByExampleWithRowbounds(example, r);
		if(lastPOList == null || lastPOList.isEmpty()) {
			return;
		}
		
		MetalPrice lastPO = lastPOList.get(0);
		if(lastPO == null) {
			return;
		}
		
		boolean noticeFlag = false;
		
		if(noticeSetting.getMaxGramPrice() != null) {
			BigDecimal kgMaxPrice = noticeSetting.getMaxGramPrice().multiply(new BigDecimal(1000));
			BigDecimal lastPrice = lastPO.getPrice();
			if(UtilOfWeightType.gram.getCode().equals(lastPO.getWeightType())) {
				lastPrice = lastPrice.multiply(new BigDecimal(1000));
			}
			
			if(lastPrice.compareTo(kgMaxPrice) >= 1) {
				noticeFlag = true;
				
			}
			
		} else if(noticeSetting.getMinGramPrice() != null) {
			BigDecimal kgMinPrice = noticeSetting.getMinGramPrice().multiply(new BigDecimal(1000));
			BigDecimal lastPrice = lastPO.getPrice();
			if(UtilOfWeightType.gram.getCode().equals(lastPO.getWeightType())) {
				lastPrice = lastPrice.multiply(new BigDecimal(1000));
			}
			
			if(lastPrice.compareTo(kgMinPrice) <= -1) {
				noticeFlag = true;
			}
		}
		
		if(noticeFlag) {
			if(!validRegexToolService.validEmail(noticeSetting.getEmail())) {
				return;
			}
			
			String content = metalType.getName() + " price had reach " + lastPO.getPrice().divide(new BigDecimal(1000));
			if(!"dev".equals(constantService.getValByName("envName"))) {
				mailService.sendSimpleMail(noticeSetting.getEmail(), "价格提示", content, null, MailType.preciousMetalsNotice);
			}
			
			noticeSetting.setNoticeTime(LocalDateTime.now());
			noticeSetting.setIsDelete(true);
			metalPriceNoticeMapper.updateByPrimaryKeySelective(noticeSetting);
		}
	}

	@Override
	public CommonResultCX insertNewMetalPriceNoticeSetting(InsertNewMetalPriceNoticeSettingDTO dto) {
		CommonResultCX r = new CommonResultCX();
		
		MetalPriceNotice newPO = new MetalPriceNotice();
		MetalType metalType = MetalType.getType(dto.getMetalType());
		if(metalType == null) {
			r.failWithMessage("error param");
			return r;
		}
		newPO.setMetalType(metalType.getCode());
		
		if(!validRegexToolService.validEmail(dto.getEmail())) {
			r.failWithMessage("error email");
			return r;
		}
		newPO.setEmail(dto.getEmail());
		
		if(dto.getMaxGramPrice() == null && dto.getMinGramPrice() == null) {
			r.failWithMessage("please set max / min price");
			return r;
		}
		if(dto.getMaxGramPrice() != null) {
			newPO.setMaxGramPrice(dto.getMaxGramPrice());
		}
		if(dto.getMinGramPrice() != null) {
			newPO.setMinGramPrice(dto.getMinGramPrice());
		}
		
		if(dto.getValidTime() == null) {
			newPO.setValidTime(LocalDateTime.now().plusMonths(6));
		} else if(!dateHandler.isDateValid(dto.getValidTime())) {
			r.failWithMessage("please select a valid date");
			return r;
		} 
		
		LocalDateTime validDate = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getValidTime());
		if(validDate.isBefore(LocalDateTime.now())) {
			r.failWithMessage("please select a valid date");
			return r;
		}
		newPO.setValidTime(validDate);
		
		newPO.setId(snowFlake.getNextId());
		newPO.setCreateTime(LocalDateTime.now());
		int count = metalPriceNoticeMapper.insertSelective(newPO);
		
		if(count > 0) {
			r.successWithMessage("insert notice setting: " + dto.toString());
		}
		return r;
	}

	@Override
	public ModelAndView insertNewMetalPriceNoticeSetting() {
		ModelAndView view = new ModelAndView("finance/metal/insertNewMetalPriceNoticeSetting");
		view.addObject("metalType", MetalType.values());
		return view;
	}

	public CommonResultCX dataUpdate(TransmissionPreciousMetalPriceDTO priceDTO) {
		/*
		 * TODO
		 * 2020-07-15
		 * 接收到 价格信息之后, 依次放入 缓存 / X分钟 .../......
		 */
		
		/**
		 * TODO
		 * 所有 "汇总"交易数据 需要增加交易时间字段!
		 * 应该实时更新对应所有汇总字段  
		 * 不是定时任务!!!
		 * 
		 * 价格捕获的时候, 应该传送数据中的交易时间, 而非采用服务器时间
		 */
		
		/*
		 * TODO
		 * 需要另外增设 删除 过期数据的定时任务
		 */
		
		CommonResultCX r = new CommonResultCX();
		
		List<PreciousMetailPriceDTO> sudDTOList = priceDTO.getPriceList();
		for(PreciousMetailPriceDTO dto : sudDTOList) {
			update5MinuteDate(dto);
		}
		
		return r;
	}
	
	private void update5MinuteDate(PreciousMetailPriceDTO priceDTO) {
		LocalDateTime transactionDateTime = null;
		try {
			transactionDateTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(priceDTO.getTransactionDateTime());
		} catch (Exception e) {
			transactionDateTime = LocalDateTime.now();
		}
		
		BigDecimal dtoPrice = new BigDecimal(priceDTO.getPrice());
		
		LocalDateTime nextStepDateTime = nextStepTime(transactionDateTime, 5);
		LocalDateTime stepStartDateTime = nextStepDateTime.minusMinutes(5);
		
		MetalPrice5minute po = null;
		
		MetalPrice5minuteExample example = new MetalPrice5minuteExample();
		example.createCriteria()
		.andIsDeleteEqualTo(false)
		.andStartTimeGreaterThanOrEqualTo(stepStartDateTime)
		.andMetalTypeEqualTo(priceDTO.getMetalType());
		example.setOrderByClause("start_time");
		List<MetalPrice5minute> poList = metalPrice5minuteMapper.selectByExample(example);
		if(poList == null || poList.isEmpty()) {
			po = new MetalPrice5minute();
			po.setId(snowFlake.getNextId());
			po.setMetalType(priceDTO.getMetalType());

			po.setStartTime(transactionDateTime);
			po.setEndTime(transactionDateTime);
			
			po.setStartPrice(dtoPrice);
			po.setEndPrice(dtoPrice);
			po.setHighPrice(dtoPrice);
			po.setLowPrice(dtoPrice);
			
			metalPrice5minuteMapper.insertSelective(po);
			
		} else {
			po = poList.get(0);
			po.setEndTime(transactionDateTime);
			po.setEndPrice(dtoPrice);
			
			if(po.getHighPrice().compareTo(dtoPrice) == -1) {
				po.setHighPrice(dtoPrice);
			} else if (po.getLowPrice().compareTo(dtoPrice) == 1) {
				po.setLowPrice(dtoPrice);
			}
			
			metalPrice5minuteMapper.updateByPrimaryKeySelective(po);
		}
		
		
	}
	
	private LocalDateTime nextStepTime(LocalDateTime time, int minuteStepLong) {
		int currentMinute = time.getMinute();
		int addMinute = 1;
		while((currentMinute + addMinute) % minuteStepLong != 0) {
			addMinute += 1;
		}
		return time.plusMinutes(addMinute).withSecond(0).withNano(0);
	}
	
}
