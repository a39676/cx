package demo.joy.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import demo.common.service.CommonService;

public class JoyCommonService extends CommonService {
	
	@Autowired
	protected RedisTemplate<String, Object> redisTemplate;

}
