package demo.tool.taobao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.service.CommonService;
import demo.tool.taobao.mapper.TaobaoProductSourceMapper;
import demo.tool.taobao.pojo.dto.AddTaobaoProductSourceDTO;
import demo.tool.taobao.pojo.po.TaobaoProductSource;
import demo.tool.taobao.pojo.po.TaobaoProductSourceExample;
import demo.tool.taobao.pojo.po.TaobaoProductSourceExample.Criteria;
import demo.tool.taobao.service.TaobaoProductSourceService;

@Service
public class TaobaoProductSourceServiceImpl extends CommonService implements TaobaoProductSourceService {

	@Autowired
	private TaobaoProductSourceMapper mapper;
	
	@Override
	public ModelAndView taobaoProductSource() {
		ModelAndView view = new ModelAndView("toolJSP/taobaoProductSource/taobaoProductSource");
		return view;
	}

	@Override
	public CommonResult insert(AddTaobaoProductSourceDTO dto) {
		CommonResult r = new CommonResult();
		TaobaoProductSource po = new TaobaoProductSource();
		po.setId(snowFlake.getNextId());
		po.setCommodityId(dto.getCommodityId().longValue());
		po.setSourceId(dto.getSourceId().longValue());
		po.setCommodityName(dto.getCommodityName());
		po.setRemark(dto.getRemark());
		try {
			int i = mapper.insertSelective(po);
			if (i > 0) {
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
	public List<TaobaoProductSource> search(AddTaobaoProductSourceDTO dto) {
		TaobaoProductSourceExample example = new TaobaoProductSourceExample();
		Criteria criteria = example.createCriteria();
		if (dto.getCommodityId() != null) {
			criteria.andCommodityIdEqualTo(dto.getCommodityId().longValue());
		}
		if (dto.getSourceId() != null) {
			criteria.andSourceIdEqualTo(dto.getSourceId().longValue());
		}
		if (StringUtils.isNotBlank(dto.getCommodityName())) {
			criteria.andCommodityNameLike("%" + dto.getCommodityName() + "%");
		}
		if (StringUtils.isNotBlank(dto.getRemark())) {
			criteria.andRemarkLike("%" + dto.getRemark() + "%");
		}
		List<TaobaoProductSource> list = mapper.selectByExample(example);
		if (list == null) {
			list = new ArrayList<>();
		}
		return list;
	}
}
