package demo.base.system.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class RedisSetConnectService extends RedisConnectCommonService {
	
	public String pop(String redisKey) {
		List<String> resultList = pop(redisKey, 1);
		if(resultList.isEmpty()) {
			return ""; 
		}
		return String.valueOf(resultList.get(0));
	}
	
	public List<String> pop(String redisKey, long count) {
		if(!redisTemplate.hasKey(redisKey)) {
			return new ArrayList<>();
		}
		
		List<Object> objList = redisTemplate.opsForSet().pop(redisKey, count);
		List<String> result = new ArrayList<>();
		for(Object obj : objList) {
			result.add(String.valueOf(obj));
		}
		return result;
	}

	public List<String> members(String redisKey) {
		if(!redisTemplate.hasKey(redisKey)) {
			return new ArrayList<>();
		}
		Set<Object> objList = redisTemplate.opsForSet().members(redisKey);
		List<String> result = new ArrayList<>();
		for(Object obj : objList) {
			result.add(String.valueOf(obj));
		}
		return result;
	}
	
	public void add(String redisKey, String value) {
		redisTemplate.opsForSet().add(redisKey, value);
	}
	
	public void add(String redisKey, String value, LocalDateTime expiredTime) {
		redisTemplate.opsForSet().add(redisKey, value);
		redisTemplate.expireAt(redisKey, dateHandler.localDateTimeToDate(expiredTime));
	}
	
	public boolean isMember(String redisKey, String value) {
		return redisTemplate.opsForSet().isMember(redisKey, value);
	}
	
	public void add(String redisKey, List<String> values) {
		redisTemplate.opsForSet().add(redisKey, values);
	}

}
