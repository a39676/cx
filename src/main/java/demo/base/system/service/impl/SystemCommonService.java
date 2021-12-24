package demo.base.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import demo.base.system.service.HostnameService;
import demo.common.service.CommonService;
import demo.tool.other.service.VisitDataService;

public abstract class SystemCommonService extends CommonService{

	@Autowired
	protected VisitDataService visitDataService;
	@Autowired
	protected SystemConstantService systemConstantService;
	@Autowired
	protected HostnameService hostnameService;
	@Autowired
	protected RedisOriginalConnectService redisOriginalConnectService;
}
