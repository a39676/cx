package demo.base.system.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import net.sf.json.JSONObject;
import tool.pojo.bo.IpRecordBO;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class RedisOriginalConnectService extends RedisConnectCommonService {
	
	public String getValByName(String constantName) {
		if(StringUtils.isBlank(constantName)) {
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
	
	public void deleteValByName(String constantName) {
		redisTemplate.delete(constantName);
	}
	
	public CommonResult refreshRedisValueFromFile(String filePath) {
		CommonResult result = new CommonResult();
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

	public Set<String> findKeys(String pattern) {
		return redisTemplate.keys(pattern);
	}

	public List<String> getValuesByKeys(List<String> keys) {
		List<Object> values = redisTemplate.opsForValue().multiGet(keys);
		List<String> result = new ArrayList<>();
		for(Object value : values) {
			result.add(String.valueOf(value));
		}
		return result;
	}
}
