package demo.tool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import demo.base.system.service.HostnameService;
import demo.base.system.service.impl.RedisHashConnectService;
import demo.base.system.service.impl.SystemOptionService;
import demo.common.service.CommonService;

public abstract class ToolCommonService extends CommonService {

	@Autowired
	protected SystemOptionService systemConstantService;
	@Autowired
	protected HostnameService hostnameService;
	@Autowired
	protected RedisHashConnectService redisHashConnectService;
	@Autowired
	protected ToolConstantService toolConstantService;
}
