package demo.base.system.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import demo.base.system.pojo.bo.SystemConstant;
import demo.common.pojo.result.CommonResultCX;
import demo.common.service.CommonService;
import net.sf.json.JSONObject;
import tool.pojo.bo.IpRecordBO;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class RedisConnectService extends CommonService {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
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
	
	public CommonResultCX refreshRedisValueFromFile(String filePath) {
		CommonResultCX result = new CommonResultCX();
		try {
			if (StringUtils.isBlank(filePath)) {
				result.failWithMessage("path error");
				return result;
			}

			File file = new File(filePath);
			if (!file.exists()) {
				result.failWithMessage("file not exists");
				return result;
			}

			FileUtilCustom ioUtil = new FileUtilCustom();
			String fileStr = ioUtil.getStringFromFile(filePath);
			JSONObject json = JSONObject.fromObject(fileStr);
			@SuppressWarnings("rawtypes")
			Set keys = json.keySet();
			String tmpKey = null;
			String tmpValue = null;
			for (Object key : keys) {
				tmpKey = String.valueOf(key);
				tmpValue = json.getString(tmpKey);
				if (StringUtils.isNotBlank(tmpKey)) {
					if (redisTemplate.hasKey(tmpKey)) {
						result.addMessage("refresh key:" + tmpKey + " , set: " + tmpValue + "\n");
					} else {
						result.addMessage("add key:" + tmpKey + " , set: " + tmpValue + "\n");
					}
					setValByName(tmpKey, tmpValue);
				} else {
					result.addMessage("detect an empty key, has value: " + tmpValue + "\n");
				}
			}

			result.setIsSuccess();
			return result;
		} catch (Exception e) {
			result.failWithMessage(e.getMessage());
			return result;
		}
	}

	public void insertFunctionalModuleVisitData(HttpServletRequest request, String redisKeyPrefix) {
		insertFunctionalModuleVisitData(request, redisKeyPrefix, 30, TimeUnit.MINUTES);
	}

	public void insertFunctionalModuleVisitData(HttpServletRequest request, String redisKeyPrefix, long timeout,
			TimeUnit unit) {
		IpRecordBO record = getIp(request);

		String key = buildRedisKeyPrefix(record, redisKeyPrefix) + "_" + snowFlake.getNextId();
		redisTemplate.opsForValue().set(key, "", timeout, unit);
	}

	public int checkFunctionalModuleVisitData(HttpServletRequest request, String redisKeyPrefix) {
		IpRecordBO record = getIp(request);

		String keyPrefix = buildRedisKeyPrefix(record, redisKeyPrefix) + "*";
		Set<String> keys = redisTemplate.keys(keyPrefix);

		return keys.size();
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
