package demo.joy.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import demo.base.system.service.impl.SystemConstantService;
import demo.common.service.CommonService;

public class JoyCommonService extends CommonService {
	
	@Autowired
	protected SystemConstantService systemConstantService;
	@Autowired
	protected RedisTemplate<String, Object> redisTemplate;

}
