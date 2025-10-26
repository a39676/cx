package demo.tool.taobao.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.service.CommonService;
import demo.tool.taobao.mapper.TaobaoProductSourceMapper;
import demo.tool.taobao.pojo.dto.TaobaoProductSourceAddDTO;
import demo.tool.taobao.pojo.dto.TaobaoProductSourceSearchDTO;
import demo.tool.taobao.pojo.po.TaobaoProductSource;
import demo.tool.taobao.pojo.po.TaobaoProductSourceExample;
import demo.tool.taobao.pojo.po.TaobaoProductSourceExample.Criteria;
import demo.tool.taobao.service.TaobaoProductSourceService;

@Service
public class TaobaoProductSourceServiceImpl extends CommonService implements TaobaoProductSourceService {

	@Autowired
	private TaobaoProductSourceOptionService optionService;
	@Autowired
	private TaobaoProductSourceMapper mapper;

	@Override
	public ModelAndView taobaoProductSource() {
		ModelAndView view = new ModelAndView("toolJSP/taobaoProductSource/taobaoProductSource");
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
		po.setCommodityImgName(dto.getCommodityImgName());
		po.setIncludePostage(dto.getIncludePostage());
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
	public ModelAndView search(TaobaoProductSourceSearchDTO dto) {
		ModelAndView v = new ModelAndView("toolJSP/taobaoProductSource/taobaoProductList");
		TaobaoProductSourceExample example = new TaobaoProductSourceExample();
		Criteria criteria = example.createCriteria();
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
		example.createCriteria().andCommodityIdIn(hotSaleIdList);
		List<TaobaoProductSource> list = mapper.selectByExample(example);
		// 因数据表中储存方式(一个tb链接对应多个货源时, 有多条数据, 所以需要"去重")
		Set<Long> commodityIdSet = new HashSet<>();
		for (int i = 0; i < list.size(); i++) {
			TaobaoProductSource po = list.get(i);
			if (commodityIdSet.contains(po.getCommodityId())) {
				list.remove(i);
				continue;
			}
			commodityIdSet.add(po.getCommodityId());
		}
		return list;
	}

	@Override
	public List<TaobaoProductSource> getNewProductList() {
		List<Long> newProductIdList = optionService.getNewProductIdList();
		TaobaoProductSourceExample example = new TaobaoProductSourceExample();
		example.createCriteria().andCommodityIdIn(newProductIdList);
		List<TaobaoProductSource> list = mapper.selectByExample(example);
		// 因数据表中储存方式(一个tb链接对应多个货源时, 有多条数据, 所以需要"去重")
		Set<Long> commodityIdSet = new HashSet<>();
		for (int i = 0; i < list.size(); i++) {
			TaobaoProductSource po = list.get(i);
			if (commodityIdSet.contains(po.getCommodityId())) {
				list.remove(i);
				continue;
			}
			commodityIdSet.add(po.getCommodityId());
		}
		return list;
	}
}
