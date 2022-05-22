package demo.joy.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import demo.base.system.service.impl.SystemOptionService;
import demo.base.user.service.UserDetailService;
import demo.common.service.CommonService;

public class JoyCommonService extends CommonService {
	
	@Autowired
	protected SystemOptionService systemOptionService;
	@Autowired
	protected RedisTemplate<String, Object> redisTemplate;
	@Autowired
	protected UserDetailService userDetailService;

}
