package demo.base.system.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.system.mapper.HostnameMapper;
import demo.base.system.pojo.po.Hostname;
import demo.base.system.pojo.po.HostnameExample;
import demo.base.system.pojo.result.HostnameType;
import demo.base.system.service.HostnameService;
import demo.baseCommon.service.CommonService;

@Service
public class HostnameServiceImpl extends CommonService implements HostnameService {

	@Autowired
	private HostnameMapper hostnameMapper;
	
	@Override
	public List<Hostname> findHonstnames() {
		HostnameExample example = new HostnameExample();
		example.createCriteria().andIsdeleteEqualTo(false);
		return hostnameMapper.selectByExample(example);
	}
	
	@Override
	public HostnameType findHostname(HttpServletRequest request) {
		return HostnameType.getTypeCustom(findHostNameFromRequst(request));
	}
}
