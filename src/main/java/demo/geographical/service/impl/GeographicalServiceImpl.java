package demo.geographical.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;
import demo.geographical.mapper.GeographicalAreaMapper;
import demo.geographical.pojo.po.GeographicalArea;
import demo.geographical.pojo.po.example.GeographicalAreaExample;
import demo.geographical.pojo.po.example.GeographicalAreaExample.Criteria;
import demo.geographical.service.GeographicalService;

@Service
public class GeographicalServiceImpl extends CommonService implements GeographicalService {

	@Autowired
	private GeographicalAreaMapper geoMapper;

	@Override
	public GeographicalArea findGeographical(Long geographicalId) {
		if (geographicalId == null) {
			return null;
		}
		GeographicalAreaExample example = new GeographicalAreaExample();
		Criteria c = example.createCriteria();
		c.andIdEqualTo(geographicalId);
		List<GeographicalArea> g = geoMapper.selectByExample(example);

		if (g == null || g.size() < 1) {
			return new GeographicalArea();
		}
		return g.get(0);
	}

	@Override
	public GeographicalArea findGeographical(String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		name = name.replaceAll("省", "");
		name = name.replaceAll("市", "");
		GeographicalAreaExample example = new GeographicalAreaExample();
		example.createCriteria().andAreaNameEqualTo(name);
		List<GeographicalArea> list = geoMapper.selectByExample(example);

		if (list == null || list.size() < 1) {
			return null;
		}
		return list.get(0);
	}
}
