package demo.base.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.system.mapper.SystemConstantMapper;
import demo.base.system.pojo.bo.SystemConstant;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.baseCommon.service.CommonService;

@Service
public class SystemConstantService extends CommonService {

	@Autowired
	private SystemConstantMapper systemConstantMapper;
	
	public String getValByName(String constantName) {
		if(StringUtils.isBlank(constantName)) {
			return "";
		}
		
		if(redisTemplate.hasKey(constantName)) {
			return String.valueOf(redisTemplate.opsForValue().get(constantName));
		} else {
			SystemConstant tmpConstant = systemConstantMapper.getValByName(constantName);
			if(tmpConstant == null || StringUtils.isBlank(tmpConstant.getConstantValue())) {
				return "";
			}
			redisTemplate.opsForValue().set(tmpConstant.getConstantName(), tmpConstant.getConstantValue());
			return tmpConstant.getConstantValue();
		}
	}
	
	public String getValByName(String constantName, boolean refreshFlag) {
		if(refreshFlag) {
			redisTemplate.delete(constantName);
		}
		return getValByName(constantName);
	}
	
	public HashMap<String, String> getValsByName(List<String> constantNames, boolean refreshFlag) {
		if(refreshFlag) {
			redisTemplate.delete(constantNames);
			return getValsByName(constantNames);
		} else {
			List<String> realConstantNames = constantNames.stream().filter(name -> !redisTemplate.hasKey(name)).collect(Collectors.toList());
			return getValsByName(realConstantNames);
		}
		
	}

	public HashMap<String, String> getValsByName(List<String> constantNames) {
		if(constantNames == null || constantNames.isEmpty()) {
			return new HashMap<String, String>();
		}
		List<SystemConstant> queryResult = systemConstantMapper.getValsByName(constantNames);

		
		HashMap<String, String> result = new HashMap<String, String>();
		
		if(queryResult != null && queryResult.size() > 0) {
			queryResult.stream().forEach(tmpConstant -> {
				result.put(tmpConstant.getConstantName(), tmpConstant.getConstantValue());
			});
		}
		redisTemplate.opsForValue().multiSet(result);
		
		return result;
	}

	public void setValByName(String cosntantName, String constantValue) {
		redisTemplate.opsForValue().set(cosntantName, constantValue);
	}
	
	public void setValByName(String cosntantName, String constantValue, Long validTime, TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(cosntantName, constantValue, validTime, timeUnit);
	}
	
	public void setValByName(SystemConstant systemConstant) {
		redisTemplate.opsForValue().set(systemConstant.getConstantName(), systemConstant.getConstantValue());
	}
	
	public void setValsByName(List<SystemConstant> systemConstants) {
		Map<String, String> values = systemConstants.stream().collect(Collectors.toMap(SystemConstant::getConstantName, SystemConstant::getConstantValue));
		redisTemplate.opsForValue().multiSet(values);
	}
	
	public List<List<Character>> getCustomKey() {
		String sourceK = constantService.getValByName(SystemConstantStore.customKeys);
		List<List<Character>> customKey = strToCustomKey(sourceK);
		
		if(!detectCustomKeyCorrect(customKey)) {
			customKey = getCustomKeysFromDatabase();
		} else {
			return customKey;
		}
		
		if(!detectCustomKeyCorrect(customKey)) {
			return null;
		}
		return customKey;
	}
	
	private List<List<Character>> getCustomKeysFromDatabase() {
		List<String> constantNames = new ArrayList<String>();
		constantNames.add(SystemConstantStore.ckey0);
		constantNames.add(SystemConstantStore.ckey1);
		constantNames.add(SystemConstantStore.ckey2);
		constantNames.add(SystemConstantStore.ckey3);
		constantNames.add(SystemConstantStore.ckey4);
		constantNames.add(SystemConstantStore.ckey5);
		constantNames.add(SystemConstantStore.ckey6);
		constantNames.add(SystemConstantStore.ckey7);
		constantNames.add(SystemConstantStore.ckey8);
		constantNames.add(SystemConstantStore.ckey9);
		
		List<SystemConstant> constants = systemConstantMapper.getValsByName(constantNames);
		List<Character> tmpKey = null;
		List<List<Character>> keys = new ArrayList<List<Character>>();
		
		char[] keyCharAry = null;
		for(SystemConstant sc : constants) {
			tmpKey = new ArrayList<Character>();
			keyCharAry = sc.getConstantValue().replaceAll("[^0-9A-Za-z_]", "").toCharArray();
			for(int i = 0; i < keyCharAry.length; i++) {
				tmpKey.add(keyCharAry[i]);
			}
			keys.add(tmpKey);
		}
		
		return keys;
	}
	
	private boolean detectCustomKeyCorrect(List<List<Character>> keys) {
		if(keys.size() != 10) {
			return false;
		}
		for(List<Character> k : keys) {
			if(k.size() != 10) {
				return false;
			}
		}
		
		return true;
	}
	
	private List<List<Character>> strToCustomKey(String str) {
		List<Character> tmpCL = null;
		List<List<Character>> customKey = new ArrayList<List<Character>>();
		
		char[] keyCharAry = str.replaceAll("[^0-9A-Za-z_]", "").toCharArray();
		
		for(int i = 0; i < keyCharAry.length; i++) {
			if(i % 10 == 0) {
				tmpCL = new ArrayList<Character>();
				customKey.add(tmpCL);
			}
			tmpCL.add(keyCharAry[i]);
		}
		
		return customKey;
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
