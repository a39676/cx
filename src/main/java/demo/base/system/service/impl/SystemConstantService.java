package demo.base.system.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import demo.base.system.mapper.SystemConstantMapper;
import demo.base.system.pojo.bo.SystemConstant;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.system.pojo.constant.DebugStatusConstant;
import demo.common.pojo.type.ResultTypeCX;
import demo.common.service.CommonService;
import toolPack.ioHandle.FileUtilCustom;
import toolPack.numericHandel.NumericUtilCustom;

@Scope("singleton")
@Service
public class SystemConstantService extends CommonService {
	
	@Autowired
	private NumericUtilCustom numberUtil;
	@Autowired
	private FileUtilCustom ioUtil;
	@Autowired
	private SystemConstantMapper systemConstantMapper;
	@Autowired
	protected RedisTemplate<String, Object> redisTemplate;
	
	private String aesKey = null;
	private String aesInitVector = null;
	private String envName = null;
	private String emailD = null;
	private String emailC = null;
	private Integer maxAttempts = null;
	private String normalWebSiteTitle = null;
	private String normalSubheading = null;
	private Boolean isJobing = null;
	private Boolean isDebuging = null;
	private String fakeFTPHome = null;
	private String homepageAnnouncementStr = null;
	
	public String getAESKey() {
		if(StringUtils.isBlank(aesKey)) {
			aesKey = getSysValByName(SystemConstantStore.AES_KEY);
		}
		return aesKey;
	}
	
	public String getAesInitVector() {
		if(StringUtils.isBlank(aesInitVector)) {
			aesInitVector = getSysValByName(SystemConstantStore.AES_INIT_VECTOR);
		}
		return aesInitVector;
	}
	
	public String getEnvName() {
		if(StringUtils.isBlank(envName)) {
			envName = getSysValByName(SystemConstantStore.ENV_NAME);
		}
		return envName;
	}
	
	public String getEnvNameRefresh() {
		envName = getSysValByName(SystemConstantStore.ENV_NAME);
		return envName;
	}
	
	
	public String getEmailD() {
		if(StringUtils.isBlank(emailD)) {
			emailD = getSysValByName(SystemConstantStore.EMAIL_D);
		}
		return emailD;
	}
	
	public String getEmailC() {
		if(StringUtils.isBlank(emailC)) {
			emailC = getSysValByName(SystemConstantStore.EMAIL_C);
		}
		return emailC;
	}
	
	public Integer getMaxAttempts() {
		String maxAttemptsStr = getSysValByName(SystemConstantStore.MAX_ATTEMPTS);
		if(StringUtils.isBlank(maxAttemptsStr) || !numberUtil.matchInteger(maxAttemptsStr)) {
			throw new BadCredentialsException(ResultTypeCX.serviceError.getName());
		}
		maxAttempts = Integer.parseInt(maxAttemptsStr);
		return maxAttempts;
	}
	
	public String getNormalWebSiteTitle() {
		if(StringUtils.isBlank(normalWebSiteTitle)) {
			normalWebSiteTitle = getSysValByName(SystemConstantStore.normalWebSiteTitle);
		}
		return normalWebSiteTitle;
	}
	
	public String getNormalSubheading() {
		if(StringUtils.isBlank(normalSubheading)) {
			normalSubheading = getSysValByName(SystemConstantStore.normalSubheading);
		}
		return normalSubheading;
	}
	
	public boolean getIsJobing() {
		if(isJobing == null) {
			isJobing = "1".equals(getSysValByName(SystemConstantStore.JOBING));
		}
		return isJobing;
	}
	
	public boolean getIsDebuging() {
		if(isDebuging == null) {
			isDebuging = DebugStatusConstant.debuging.equals(getSysValByName(SystemConstantStore.debugStatus));
		}
		return isDebuging;
	}
	
	public String getFakeFTPHome() {
		if(StringUtils.isBlank(fakeFTPHome)) {
			fakeFTPHome = getSysValByName(SystemConstantStore.FAKE_FTP_HOME);
		}
		return fakeFTPHome;
	}
	
	public String getHomepageAnnouncement() {
		if(StringUtils.isBlank(homepageAnnouncementStr)) {
			String homepageAnnouncementFilePathStr = getSysValByName(SystemConstantStore.homepageAnnouncementFilePath);
			if(StringUtils.isBlank(homepageAnnouncementFilePathStr)) {
				return "";
			}
			
			try {
				File f = new File(homepageAnnouncementFilePathStr);
				if(f.exists()) {
					homepageAnnouncementStr = ioUtil.getStringFromFile(homepageAnnouncementFilePathStr);
				}
			} catch (Exception e) {
			}
		}
		return homepageAnnouncementStr;
	}
	
	public void setTempHomepageAnnouncement(String homepageAnnouncementInput) {
		homepageAnnouncementStr = homepageAnnouncementInput;
	}
	
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
