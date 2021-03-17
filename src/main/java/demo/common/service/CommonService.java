package demo.common.service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.system.service.IpRecordService;
import demo.base.system.service.impl.SystemConstantService;
import demo.common.pojo.result.CommonResultCX;
import demo.common.pojo.type.ResultTypeCX;
import demo.config.costom_component.BaseUtilCustom;
import demo.config.costom_component.EncryptUtil;
import demo.config.costom_component.SnowFlake;
import demo.tool.service.VisitDataService;
import net.sf.json.JSONObject;
import tool.pojo.bo.IpRecordBO;
import toolPack.dateTimeHandle.DateHandler;
import toolPack.dateTimeHandle.LocalDateTimeHandler;
import toolPack.ioHandle.FileUtilCustom;
import toolPack.numericHandel.NumericUtilCustom;

public abstract class CommonService {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	protected EncryptUtil encryptUtil;

	@Autowired
	protected SnowFlake snowFlake;
	@Autowired
	protected NumericUtilCustom numberUtil;
	@Autowired
	protected VisitDataService visitDataService;
	@Autowired
	protected LocalDateTimeHandler localDateTimeHandler;
	@Autowired
	protected DateHandler dateHandler;
	@Autowired
	protected SystemConstantService constantService;
	@Autowired
	protected BaseUtilCustom baseUtilCustom;
	@Autowired
	protected IpRecordService ipRecordService;

	@Autowired
	protected RedisTemplate<String, Object> redisTemplate;

	protected static final LocalDateTime BLOG_ARTICLE_START_TIME = LocalDateTime.of(2020, 5, 1, 0, 0, 0);

	protected String createDateDescription(Date inputDate) {
		if (inputDate == null) {
			return "";
		}
		Long oneHourLong = 1000L * 60 * 60;
		Long timeDiff = System.currentTimeMillis() - inputDate.getTime();
		if (timeDiff < (oneHourLong / 2)) {
			return "a moment...";
		} else if (timeDiff <= oneHourLong) {
			return "not long ago";
		} else if (timeDiff <= (oneHourLong * 12)) {
			return String.valueOf(timeDiff / oneHourLong) + " hours ago";
		} else if (timeDiff <= (oneHourLong * 24)) {
			return "today";
		} else if (timeDiff <= (oneHourLong * 24 * 3)) {
			return String.valueOf(timeDiff / (oneHourLong * 24)) + " days ago";
		} else if (timeDiff <= (oneHourLong * 24 * 7)) {
			return "within a week";
		} else if (timeDiff <= (oneHourLong * 24 * 31)) {
			return "within a month";
		} else {
			return "long long ago...";
		}
	}

	protected CommonResultCX nullParam() {
		CommonResultCX result = new CommonResultCX();
		result.fillWithResult(ResultTypeCX.nullParam);
		return result;
	}

	protected CommonResultCX errorParam() {
		CommonResultCX result = new CommonResultCX();
		result.fillWithResult(ResultTypeCX.errorParam);
		return result;
	}

	protected CommonResultCX serviceError() {
		CommonResultCX result = new CommonResultCX();
		result.fillWithResult(ResultTypeCX.serviceError);
		return result;
	}

	protected CommonResult normalSuccess() {
		CommonResult result = new CommonResult();
		result.normalSuccess();
		return result;
	}

	protected CommonResult notLogin() {
		CommonResult result = new CommonResult();
		result.failWithMessage("请登录后操作");
		return result;
	}

	protected boolean isWindows() {
		String os = System.getProperty("os.name");
		if (os != null) {
			if (os.toLowerCase().contains("windows")) {
				return true;
			}
		}
		return false;
	}

	protected boolean isLinux() {
		String os = System.getProperty("os.name");
		if (os != null) {
			if (os.toLowerCase().contains("linux")) {
				return true;
			}
		}
		return false;
	}

	protected String getSuffixName(String str) {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		return str.substring(str.lastIndexOf("."));
	}

	protected String pathChangeByDetectOS(String oldPath) {
		if (isWindows()) {
			return oldPath.replaceAll("/", "\\\\");
		} else {
			return oldPath.replaceAll("\\\\", "/");
		}
	}

	protected String findHostNameFromRequst(HttpServletRequest request) {
		if ("dev".equals(constantService.getSysValByName("envName"))) {
			return "easy";
		}
		return request.getServerName();
//		if("dev".equals(constantService.getValByName("envName"))) {
//			return request.getServerName();
//		} else {
//			String url = request.getServerName();
//			Pattern p = Pattern.compile("(?!:http://)(www\\.[0-9a-zA-Z_]+\\.[a-z]{1,8})(?!:/.*)");
//			Matcher m = p.matcher(url);
//			if(m.find()) {
//				return m.group(0);
//			} else {
//				return "";
//			}
//		}
	}

	protected String testFindHostNameFromRequst(HttpServletRequest request) {
		String r = "from getServerName: " + request.getServerName();
		String url = request.getServerName();
		Pattern p = Pattern.compile("(?!:http://)(www\\.[0-9a-zA-Z_]+\\.[a-z]{1,8})(?!:/.*)");
		Matcher m = p.matcher(url);
		if (m.find()) {
			r = r + " from pattern: " + m.group(0);
		}

		return r;
	}

	public String encryptId(Long id) {
		List<String> encryptIdList = encryptId(Arrays.asList(id));
		if (encryptIdList == null || encryptIdList.isEmpty()) {
			return null;
		} else {
			return encryptIdList.get(0);
		}
	}

	public List<String> encryptId(List<Long> idList) {
		if (idList == null || idList.isEmpty()) {
			return null;
		}

		String keys = constantService.getSysValByName(SystemConstantStore.aesKey);
		if (StringUtils.isBlank(keys)) {
			keys = constantService.getSysValByName(SystemConstantStore.aesKey, true);
			if (StringUtils.isBlank(keys)) {
				return null;
			}
		}

		String initVector = constantService.getSysValByName(SystemConstantStore.aesInitVector);
		if (StringUtils.isBlank(initVector)) {
			initVector = constantService.getSysValByName(SystemConstantStore.aesKey, true);
			if (StringUtils.isBlank(initVector)) {
				return null;
			}
		}

		List<String> encryptResult = new ArrayList<String>();
		try {
			for (Long id : idList) {
				encryptResult.add(encryptUtil.aesEncrypt(keys, initVector, String.valueOf(id)));
			}
		} catch (Exception e) {

		}
		return encryptResult;
	}

	public Long decryptPrivateKey(String inputPk) {
		List<Long> idList = decryptPrivateKey(Arrays.asList(inputPk));
		if (idList == null || idList.isEmpty()) {
			return null;
		} else {
			return idList.get(0);
		}
	}

	public List<Long> decryptPrivateKey(List<String> inputPkList) {
		if (inputPkList == null || inputPkList.isEmpty()) {
			return null;
		}

		String keys = constantService.getSysValByName(SystemConstantStore.aesKey);
		if (StringUtils.isBlank(keys)) {
			keys = constantService.getSysValByName(SystemConstantStore.aesKey, true);
			if (StringUtils.isBlank(keys)) {
				return null;
			}
		}

		String initVector = constantService.getSysValByName(SystemConstantStore.aesInitVector);
		if (StringUtils.isBlank(initVector)) {
			initVector = constantService.getSysValByName(SystemConstantStore.aesInitVector, true);
			if (StringUtils.isBlank(initVector)) {
				return null;
			}
		}

		Long id = null;
		List<Long> idList = new ArrayList<Long>();
		for (String pk : inputPkList) {
			try {
				id = Long.parseLong(encryptUtil.aesDecrypt(keys, initVector, pk));
				idList.add(id);
			} catch (Exception e) {
				idList.add(null);
			}
		}
		return idList;
	}

	protected boolean isBigUser() {
		return baseUtilCustom.hasSuperAdminRole();
	}

	protected IpRecordBO getIp(HttpServletRequest request) {
		IpRecordBO record = new IpRecordBO();
		record.setRemoteAddr(request.getRemoteAddr());
		record.setForwardAddr(request.getHeader("X-FORWARDED-FOR"));

		return record;
	}

	protected CommonResultCX refreshRedisValueFromFile(String filePath) {
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
					constantService.setValByName(tmpKey, tmpValue);
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

	protected void insertFunctionalModuleVisitData(HttpServletRequest request, String redisKeyPrefix) {
		insertFunctionalModuleVisitData(request, redisKeyPrefix, 30, TimeUnit.MINUTES);
	}

	protected void insertFunctionalModuleVisitData(HttpServletRequest request, String redisKeyPrefix, long timeout,
			TimeUnit unit) {
		IpRecordBO record = getIp(request);

		String key = buildRedisKeyPrefix(record, redisKeyPrefix) + "_" + snowFlake.getNextId();
		redisTemplate.opsForValue().set(key, "", timeout, unit);
	}

	protected int checkFunctionalModuleVisitData(HttpServletRequest request, String redisKeyPrefix) {
		IpRecordBO record = getIp(request);

		String keyPrefix = buildRedisKeyPrefix(record, redisKeyPrefix) + "*";
		Set<String> keys = redisTemplate.keys(keyPrefix);

		return keys.size();
	}

	private String buildRedisKeyPrefix(IpRecordBO record, String redisKeyPrefix) {
		return redisKeyPrefix + "_" + record.getForwardAddr() + "_" + record.getRemoteAddr();
	}

	public LocalDateTime getNextSettingTime(LocalDateTime datetime, TimeUnitType timeUnit, Long step) {
		LocalDateTime nextNoticeTime = null;
		if (datetime == null || timeUnit == null || step == null) {
			return nextNoticeTime;
		}

		if (timeUnit.equals(TimeUnitType.nanoSecond)) {
			nextNoticeTime = datetime.plusNanos(step);
		} else if (timeUnit.equals(TimeUnitType.milliSecond)) {
			nextNoticeTime = datetime.plusNanos(step * 1000);
		} else if (timeUnit.equals(TimeUnitType.second)) {
			nextNoticeTime = datetime.plusSeconds(step);
		} else if (timeUnit.equals(TimeUnitType.minute)) {
			nextNoticeTime = datetime.plusMinutes(step);
		} else if (timeUnit.equals(TimeUnitType.hour)) {
			nextNoticeTime = datetime.plusHours(step);
		} else if (timeUnit.equals(TimeUnitType.day)) {
			nextNoticeTime = datetime.plusDays(step);
		} else if (timeUnit.equals(TimeUnitType.week)) {
			nextNoticeTime = datetime.plusDays(step * 7);
		} else if (timeUnit.equals(TimeUnitType.month)) {
			nextNoticeTime = datetime.plusMonths(step);
		} else if (timeUnit.equals(TimeUnitType.year)) {
			nextNoticeTime = datetime.plusYears(step);
		}

		return nextNoticeTime;
	}
}
