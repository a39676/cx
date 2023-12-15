package demo.common.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import demo.base.system.service.HostnameService;
import demo.base.system.service.impl.RedisHashConnectService;
import demo.base.system.service.impl.SystemOptionService;
import demo.tool.service.impl.ToolOptionService;
import jakarta.servlet.http.HttpServletRequest;

public abstract class ToolCommonService extends CommonService {

	@Autowired
	protected SystemOptionService systemOptionService;
	@Autowired
	protected HostnameService hostnameService;
	@Autowired
	protected RedisHashConnectService redisHashConnectService;
	@Autowired
	protected ToolOptionService toolConstantService;

	protected String findHostnameFromRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request.getServerName();
	}
}
