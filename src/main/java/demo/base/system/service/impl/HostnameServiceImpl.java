package demo.base.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.base.system.mapper.HostnameMapper;
import demo.base.system.pojo.po.Hostname;
import demo.base.system.pojo.po.HostnameExample;
import demo.base.system.pojo.result.HostnameType;
import demo.base.system.service.HostnameService;
import demo.common.service.CommonService;

@Scope("singleton")
@Service
public class HostnameServiceImpl extends CommonService implements HostnameService {

	@Autowired
	private SystemOptionService systemOptionService;
	@Autowired
	private HostnameMapper hostnameMapper;
	
	private Map<HostnameType, Hostname> hostnameMap = new HashMap<>();

	@Override
	public List<Hostname> findHostnames() {
		HostnameExample example = new HostnameExample();
		example.createCriteria().andIsdeleteEqualTo(false);
		return hostnameMapper.selectByExample(example);
	}

	@Override
	public String findHostNameFromRequst(HttpServletRequest request) {
		log.error("get server name: " + request.getServerName());
		return request.getServerName();

//		String r = "from getServerName: " + request.getServerName();
//		String url = request.getServerName();
//		Pattern p = Pattern.compile("(?!:http://)(www\\.[0-9a-zA-Z_]+\\.[a-z]{1,8})(?!:/.*)");
//		Matcher m = p.matcher(url);
//		if (m.find()) {
//			r = r + " from pattern: " + m.group(0);
//		}
//
//		return r;
	}

	@Override
	public HostnameType findHostnameType(HttpServletRequest request) {
		if(systemOptionService.isDev()) {
			return HostnameType.zhang3;
		}
		return HostnameType.getTypeCustom(findHostNameFromRequst(request));
	}

	@Override
	public String findMainHostname() {
		Hostname po = hostnameMapper.selectByPrimaryKey(5);
		if (po == null) {
			return null;
		} else {
			return po.getHostname();
		}
	}

	@Override
	public boolean isMainHostname(HttpServletRequest request) {
		String hostname = request.getServerName();
		if (StringUtils.isBlank(hostname)) {
			return false;
		}
		return HostnameType.zhang3.equals(HostnameType.getTypeCustom(hostname));
	}
	
	@Override
	public Hostname findHostname(HostnameType type) {
		if(hostnameMap.containsKey(type)) {
			return hostnameMap.get(type);
		}
		Hostname po = hostnameMapper.selectByPrimaryKey(type.getCode());
		hostnameMap.put(type, po);
		return po;
	}
}
