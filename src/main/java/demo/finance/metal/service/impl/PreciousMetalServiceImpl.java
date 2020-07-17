package demo.finance.metal.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.baseCommon.pojo.result.CommonResultCX;
import demo.finance.metal.mapper.MetalPriceNoticeMapper;
import demo.finance.metal.pojo.dto.InsertNewMetalPriceNoticeSettingDTO;
import demo.finance.metal.pojo.po.MetalPrice;
import demo.finance.metal.pojo.po.MetalPriceExample;
import demo.finance.metal.pojo.po.MetalPriceExample.Criteria;
import demo.finance.metal.pojo.po.MetalPriceNotice;
import demo.finance.metal.service.PreciousMetalService;
import demo.tool.pojo.type.MailType;
import demo.tool.service.MailService;
import demo.tool.service.ValidRegexToolService;
import precious_metal.pojo.dto.PreciousMetailPriceDTO;
import precious_metal.pojo.type.MetalType;
import tool.pojo.type.UtilOfWeightType;

@Service
public class PreciousMetalServiceImpl extends PreciousMetalCommonService implements PreciousMetalService {

	@Autowired
	private MailService mailService;
	@Autowired
	private ValidRegexToolService validRegexToolService;
	
	@Autowired
	private MetalPriceNoticeMapper metalPriceNoticeMapper;
	
	@Override
	public CommonResult reciveMetalPrice(PreciousMetailPriceDTO dto) {
		CommonResult result = new CommonResult();
		
		MetalPrice tmpPO = null;
		int insertCount = 0;
		if(dto.getPrice() == null || dto.getWeightUtilType() == null) {
			return result;
		}
		
		UtilOfWeightType weightType = UtilOfWeightType.getType(dto.getWeightUtilType());
		if(weightType == null) {
			return result;
		}
		
		MetalType metalType = MetalType.getType(dto.getMetalType());
		if(metalType == null) {
			return result;
		}
		
		tmpPO = new MetalPrice();
		tmpPO.setId(snowFlake.getNextId());
		tmpPO.setMetalType(dto.getMetalType());
		tmpPO.setPrice(new BigDecimal(dto.getPrice()));
		tmpPO.setWeightType(dto.getWeightUtilType());
		tmpPO.setCreateTime(LocalDateTime.now());
		
		insertCount += metalPriceMapper.insertSelective(tmpPO);
		
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
}
