package demo.base.system.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import demo.base.system.pojo.bo.SystemConstant;
import demo.common.service.CommonService;

@Service
public class RedisConnectService extends CommonService {
	
	public String getValByName(String constantName) {
		if(StringUtils.isBlank(constantName)) {
			log.error("constant name was empty");
			return "";
		}
		
		if(redisTemplate.hasKey(constantName)) {
			return String.valueOf(redisTemplate.opsForValue().get(constantName));
		} else {
			return "";
		}
	}

	public void setValByName(String cosntantName, String constantValue) {
		redisTemplate.opsForValue().set(cosntantName, constantValue);
	}
	
	public void setValByName(String cosntantName, String constantValue, Long validTime, TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(cosntantName, constantValue, validTime, timeUnit);
	}
	
	public void setValByName(String cosntantName, String constantValue, int validTime, TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(cosntantName, constantValue, validTime, timeUnit);
	}
	
	public void setValByName(SystemConstant systemConstant) {
		redisTemplate.opsForValue().set(systemConstant.getConstantName(), systemConstant.getConstantValue());
	}
	
	public void setValsByName(List<SystemConstant> systemConstants) {
		Map<String, String> values = systemConstants.stream().collect(Collectors.toMap(SystemConstant::getConstantName, SystemConstant::getConstantValue));
		redisTemplate.opsForValue().multiSet(values);
	}
	
	public void deleteValByName(String constantName) {
		redisTemplate.delete(constantName);
	}
	
	public Boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}
	
	/**
	 * 保留作为用例
	 */
//	private void setRoleListFromDBToRedis() {
//		List<Roles> roleList = getRoleListFromDB();
//		
//		JSONArray ja = JSONArray.fromObject(roleList);
//		constantService.setValByName(SystemConstantStore.roleList, ja.toString());
//	}
//	
//	public List<Roles> getRoleListFromDB() {
//		
//		List<Roles> roleList = roleMapper.getRoleList();
//		
//		if(roleList == null || roleList.isEmpty()) {
//			roleList = new ArrayList<Roles>();
//		}
//		
//		return roleList;
//		
//	}
//	
//	public List<Roles> getRoleListFromRedis(boolean refresh) {
//		if(refresh) {
//			setRoleListFromDBToRedis();
//		}
//		
//		String roleListStr = constantService.getValByName(SystemConstantStore.roleList);
//		if(StringUtils.isBlank(roleListStr)) {
//			return new ArrayList<Roles>();
//		}
//		
//		JSONArray ja = JSONArray.fromObject(roleListStr);
//		if(ja.size() < 1) {
//			return new ArrayList<Roles>();
//		}
//		
//		Gson g = new Gson();
//		Roles r = null;
//		List<Roles> roleList = new ArrayList<Roles>();
//		for(int i = 0; i < ja.size(); i++) {
//			r = g.fromJson(ja.getString(i), Roles.class);
//			roleList.add(r);
//		}
//		
//		return roleList;
//	}
}
