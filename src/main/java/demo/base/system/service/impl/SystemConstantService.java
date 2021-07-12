package demo.base.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.system.mapper.SystemConstantMapper;
import demo.base.system.pojo.bo.SystemConstant;
import demo.common.service.CommonService;

@Service
public class SystemConstantService extends CommonService {
	
	@Autowired
	private SystemConstantMapper systemConstantMapper;
	
	public String getSysValByName(String constantName) {
		String val = redisConnectService.getValByName(constantName);
		
		if(!val.equals("")) {
			return val;
		} else {
			SystemConstant tmpConstant = systemConstantMapper.getValByName(constantName);
			if(tmpConstant == null || StringUtils.isBlank(tmpConstant.getConstantValue())) {
				log.error("can NOT get constant: " + constantName + " from database");
				return "";
			}
			redisTemplate.opsForValue().set(tmpConstant.getConstantName(), tmpConstant.getConstantValue());
			log.error("refresh " + constantName + ", ---> " + tmpConstant.getConstantValue());
			return tmpConstant.getConstantValue();
		}
	}
	
	public String getSysValByName(String constantName, boolean refreshFlag) {
		if(refreshFlag) {
			redisTemplate.delete(constantName);
		}
		return getSysValByName(constantName);
	}
	
	public HashMap<String, String> getSysValsByName(List<String> constantNames, boolean refreshFlag) {
		if(refreshFlag) {
			redisTemplate.delete(constantNames);
			return getSysValsByName(constantNames);
		} else {
			List<String> realConstantNames = constantNames.stream().filter(name -> !redisTemplate.hasKey(name)).collect(Collectors.toList());
			return getSysValsByName(realConstantNames);
		}
		
	}

	public HashMap<String, String> getSysValsByName(List<String> constantNames) {
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

	
}
