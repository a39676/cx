package demo.tool.taobao.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.service.CommonService;
import demo.tool.taobao.mapper.TaobaoProductSourceMapper;
import demo.tool.taobao.pojo.dto.TaobaoProductSourceAddDTO;
import demo.tool.taobao.pojo.dto.TaobaoProductSourceSearchDTO;
import demo.tool.taobao.pojo.dto.TaobaoProductSourceUpdateDTO;
import demo.tool.taobao.pojo.po.TaobaoProductSource;
import demo.tool.taobao.pojo.po.TaobaoProductSourceExample;
import demo.tool.taobao.pojo.po.TaobaoProductSourceExample.Criteria;
import demo.tool.taobao.service.TaobaoProductSourceService;
import demo.tool.textMessageForward.telegram.service.TelegramService;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.type.TelegramBotType;

@Service
public class TaobaoProductSourceServiceImpl extends CommonService implements TaobaoProductSourceService {

	@Autowired
	private TaobaoProductSourceOptionService optionService;
	@Autowired
	private TaobaoProductSourceMapper mapper;
	@Autowired
	private TelegramService telegramService;

	@Override
	public ModelAndView taobaoProductSource() {
		ModelAndView view = new ModelAndView("toolJSP/taobaoProductSource/taobaoProductSource");
		view.addObject("title", "TaobaoProduct");
		return view;
	}

	@Override
	public CommonResult insert(TaobaoProductSourceAddDTO dto) {
		CommonResult r = new CommonResult();
		TaobaoProductSource po = new TaobaoProductSource();
		po.setId(snowFlake.getNextId());
		po.setCommodityId(dto.getCommodityId().longValue());
		po.setSourceId(dto.getSourceId().longValue());
		po.setCommodityName(dto.getCommodityName());
		po.setCommodityNameZhTw(dto.getCommodityNameZhTw());
		po.setCommodityNameEn(dto.getCommodityNameEn());
		po.setCommodityImgName(getImageNameFromLink(dto.getCommodityImgName()));
		po.setIncludePostage(dto.getIncludePostage());
		po.setIsAvailable(true);
		po.setRemark(dto.getRemark());
		try {
			int i = mapper.insertSelective(po);
			if (i > 0) {
				r.setMessage("Insert " + dto.getCommodityName() + ", done");
				r.setIsSuccess();
			} else {
				r.setMessage("Insert failed");
			}
		} catch (Exception e) {
			r.setMessage(e.getLocalizedMessage());
		}
		return r;
	}

	@Override
	public CommonResult update(TaobaoProductSourceUpdateDTO dto) {
		CommonResult r = new CommonResult();
		Long id = null;
		try {
			id = Long.parseLong(dto.getIdStr());
		} catch (Exception e) {
			r.setMessage("ID error");
			return r;
		}
		if (dto.getIncludePostage() == null && dto.getIsAvailable() == null) {
			r.setMessage("Null param");
			return r;
		}
		TaobaoProductSource po = mapper.selectByPrimaryKey(id);
		if (po == null) {
			r.setMessage("Null data");
			return r;
		}
		if (dto.getIsAvailable() != null) {
			po.setIsAvailable(dto.getIsAvailable());
		}
		if (dto.getIncludePostage() != null) {
			po.setIncludePostage(dto.getIncludePostage());
		}
		mapper.updateByPrimaryKeySelective(po);
		r.setMessage("Updated, " + po.getCommodityName());
		r.setIsSuccess();
		return r;
	}

	private String getImageNameFromLink(String linkStr) {
		int end = -1;
		int jpgIndex = linkStr.indexOf("jpg");
		if (jpgIndex > 0) {
			end = jpgIndex;
		}

		int pngIndex = linkStr.indexOf("png");
		if (pngIndex > 0 && end < 0) {
			end = pngIndex;
		}

		int bmpIndex = linkStr.indexOf("bmp");
		if (bmpIndex > 0 && end < 0) {
			end = bmpIndex;
		}

		String startStr = "O1CN";
		int start = linkStr.indexOf(startStr);

		return String.valueOf(linkStr.subSequence(start, end + 3));
	}

	@SuppressWarnings("unused")
	private Long getSourceIdFromLink(String linkStr) {
		String startStr = "offer/";
		String endStr = ".html";
		int start = linkStr.indexOf(startStr) + endStr.length() + 1;
		int end = linkStr.indexOf(endStr);
		return Long.parseLong(String.valueOf(linkStr.subSequence(start, end)));
	}

	@Override
	public ModelAndView search(TaobaoProductSourceSearchDTO dto) {
		ModelAndView v = new ModelAndView("toolJSP/taobaoProductSource/taobaoProductList");
		TaobaoProductSourceExample example = new TaobaoProductSourceExample();
		Criteria criteria = example.createCriteria().andIsDeleteEqualTo(false);
		if (StringUtils.isNotBlank(dto.getCommodityIdListStr())) {
			String[] idStrArray = dto.getCommodityIdListStr().replaceAll(" ", "").split(",");
			List<Long> idList = new ArrayList<>();
			for (String idStr : idStrArray) {
				try {
					idList.add(Long.parseLong(idStr));
				} catch (Exception e) {
				}
			}
			if (idList.size() > 0) {
				criteria.andCommodityIdIn(idList);
			}
		}
		if (StringUtils.isNotBlank(dto.getSourceIdIdListStr())) {
			String[] idStrArray = dto.getSourceIdIdListStr().replaceAll(" ", "").split(",");
			List<Long> idList = new ArrayList<>();
			for (String idStr : idStrArray) {
				try {
					idList.add(Long.parseLong(idStr));
				} catch (Exception e) {
				}
			}
			if (idList.size() > 0) {
				criteria.andSourceIdIn(idList);
			}
		}
		if (StringUtils.isNotBlank(dto.getCommodityName())) {
			criteria.andCommodityNameLike("%" + dto.getCommodityName() + "%");
		}
		if (StringUtils.isNotBlank(dto.getCommodityNameZhTw())) {
			criteria.andCommodityNameZhTwLike("%" + dto.getCommodityNameZhTw() + "%");
		}
		if (StringUtils.isNotBlank(dto.getCommodityNameEn())) {
			criteria.andCommodityNameEnLike("%" + dto.getCommodityNameEn() + "%");
		}
		if (StringUtils.isNotBlank(dto.getRemark())) {
			criteria.andRemarkLike("%" + dto.getRemark() + "%");
		}
		List<TaobaoProductSource> list = mapper.selectByExample(example);
		if (list == null) {
			return v;
		}
		v.addObject("productList", list);
		return v;
	}

	@Override
	public List<TaobaoProductSource> getHotSaleList() {
		List<Long> hotSaleIdList = optionService.getHotSaleIdList();
		TaobaoProductSourceExample example = new TaobaoProductSourceExample();
		example.createCriteria().andIdIn(hotSaleIdList).andIsAvailableEqualTo(true).andIsDeleteEqualTo(false);
		List<TaobaoProductSource> list = mapper.selectByExample(example);
		return list;
	}

	@Override
	public List<TaobaoProductSource> getNewProductList() {
		LocalDateTime lastUpdateTime = optionService.getNewProductIdListLastUpdateTime();
		LocalDateTime now = LocalDateTime.now();
		if (lastUpdateTime == null
				|| lastUpdateTime.plusMinutes(optionService.getNewProductIdListRefreshGapInMinute()).isBefore(now)) {
			TaobaoProductSourceExample example = new TaobaoProductSourceExample();
			example.createCriteria().andIsAvailableEqualTo(true).andIsDeleteEqualTo(false);
			// 2025/10/28 基于目前数据结构(链接ID:来源ID 1:1), 当某一链接对应多个来源时, 再去重会造成列表缩小, 所以 limit
			// 数值需要相对放大, 暂取15
			example.setOrderByClause(" id desc limit 15");
			List<TaobaoProductSource> list = mapper.selectByExample(example);
			Set<Long> commodityIdSet = new HashSet<>();
			List<Long> newProductIdList = new ArrayList<>();
			for (int i = 0; commodityIdSet.size() < optionService.getMinNewProductIdListSize(); i++) {
				TaobaoProductSource product = list.get(i);
				if (commodityIdSet.contains(product.getCommodityId())) {
					continue;
				}
				commodityIdSet.add(list.get(i).getCommodityId());
				newProductIdList.add(product.getId());
			}
			optionService.getNewProductIdList().clear();
			optionService.getNewProductIdList().addAll(newProductIdList);
			optionService.setNewProductIdListLastUpdateTime(now);
		}
		List<Long> newProductIdList = optionService.getNewProductIdList();
		TaobaoProductSourceExample example = new TaobaoProductSourceExample();
		example.createCriteria().andIdIn(newProductIdList).andIsAvailableEqualTo(true);
		List<TaobaoProductSource> list = mapper.selectByExample(example);
		return list;
	}

	@Override
	public List<TaobaoProductSource> getRandomProductList(Integer size) {
		List<Long> randomProductIdList = new ArrayList<>();
		List<TaobaoProductSource> list = new ArrayList<>();
		TaobaoProductSourceExample example = new TaobaoProductSourceExample();
		LocalDateTime lastUpdateTime = optionService.getRandomProductIdListLastUpdateTime();
		LocalDateTime now = LocalDateTime.now();
		if (lastUpdateTime != null
				&& lastUpdateTime.plusMinutes(optionService.getRandomProductIdListRefreshGapInMinute()).isAfter(now)) {
			randomProductIdList = optionService.getRandomProductIdList();
			example = new TaobaoProductSourceExample();
			example.createCriteria().andIdIn(randomProductIdList).andIsAvailableEqualTo(true).andIsDeleteEqualTo(false);
			list = mapper.selectByExample(example);
			return list;
		}
		example = new TaobaoProductSourceExample();
		example.createCriteria().andIsAvailableEqualTo(true).andIsDeleteEqualTo(false);
		example.setOrderByClause(" id desc limit 200");
		list = mapper.selectByExample(example);
		List<Long> randomIdList = new ArrayList<>();
		for (int i = 0; i < optionService.getMinNewProductIdListSize(); i++) {
			int randomIndex = ThreadLocalRandom.current().nextInt(0, list.size() - 1);
			randomIdList.add(list.remove(randomIndex).getId());
		}
		optionService.setRandomProductIdList(randomIdList);
		example = new TaobaoProductSourceExample();
		example.createCriteria().andIdIn(randomIdList);
		list = mapper.selectByExample(example);
		optionService.setRandomProductIdListLastUpdateTime(now);
		return list;
	}

	@Override
	public void whenLinkClick(String commodityId) {
		String msg = "Click link, commodity ID: " + commodityId;
		try {
			Long commodityIdNum = Long.parseLong(commodityId);
			TaobaoProductSourceExample example = new TaobaoProductSourceExample();
			example.createCriteria().andCommodityIdEqualTo(commodityIdNum);
			List<TaobaoProductSource> poList = mapper.selectByExample(example);
			TaobaoProductSource po = poList.get(0);
			msg += ", name: " + po.getCommodityName() + ", include postage: " + po.getIncludePostage()
					+ ", is available: " + po.getIsAvailable();
		} catch (Exception e) {
		}
		telegramService.sendMessageByChatRecordId(TelegramBotType.CX_MESSAGE, msg, TelegramStaticChatID.MY_ID);
	}

}
