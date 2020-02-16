package demo.baseCommon.service;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.system.service.impl.SystemConstantService;
import demo.base.user.pojo.type.RolesType;
import demo.baseCommon.pojo.result.CommonResultCX;
import demo.baseCommon.pojo.type.ResultTypeCX;
import demo.config.costom_component.BaseUtilCustom;
import demo.config.costom_component.EncryptUtil;
import demo.config.costom_component.SnowFlake;
import demo.tool.service.VisitDataService;
import toolPack.dateTimeHandle.DateHandler;
import toolPack.dateTimeHandle.LocalDateTimeHandler;
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
	protected RedisTemplate<String, String> redisTemplate;

	protected static final long theStartTime = 946656000000L;

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
		if(os != null) {
			if(os.toLowerCase().contains("windows")) {
				return true;
			}
		}
		return false;
	}
	
	protected boolean isLinux() {
		String os = System.getProperty("os.name");
		if(os != null) {
			if(os.toLowerCase().contains("linux")) {
				return true;
			}
		}
		return false;
	}

	protected String getSuffixName(String str) {
		if(StringUtils.isBlank(str)) {
			return "";
		}
		return str.substring(str.lastIndexOf("."));
	}
	
	protected String pathChangeByDetectOS(String oldPath) {
		if(isWindows()) {
			return oldPath.replaceAll("/", "\\\\");
		} else {
			return oldPath.replaceAll("\\\\", "/");
		}
	}

	protected String findHostNameFromRequst(HttpServletRequest request) {
		if("dev".equals(constantService.getValByName("envName"))) {
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
		if(m.find()) {
			r = r + " from pattern: " + m.group(0);
		}
		
		return r;
	}
	
	private List<List<Character>> getCustomKey() {
		return constantService.getCustomKey();
	}
	
	public String encryptId(Long id) {
		return encryptId(id, getCustomKey());
	}
	
	public String encryptId(Long articleId, List<List<Character>> keys) {
		if(articleId == null) {
			return null;
		}
		
		if(keys == null || keys.size() < 1) {
			return null;
		}
		return encryptUtil.customEncrypt(keys, articleId.toString());
	}
	
	public Long decryptPrivateKey(String inputPk) {
		String encryptId = encryptUtil.customDecrypt(getCustomKey(), inputPk);
		if(encryptId == null || !numberUtil.matchInteger(encryptId)) {
			return null;
		}
		return Long.parseLong(encryptId);
	}
	
	protected boolean isBigUser() {
		return baseUtilCustom.hasAnyRole(
				RolesType.ROLE_POSTER.getName(),
				RolesType.ROLE_SUPER_ADMIN.getName());
	}
}
