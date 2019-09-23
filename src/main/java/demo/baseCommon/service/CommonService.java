package demo.baseCommon.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import demo.baseCommon.pojo.result.CommonResult;
import demo.baseCommon.pojo.type.ResultType;
import demo.config.costom_component.SnowFlake;

public abstract class CommonService {
	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Value("${envName}")
	protected String envName;
	
	@Autowired
	protected SnowFlake snowFlake;
	
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

	protected CommonResult nullParam() {
		CommonResult result = new CommonResult();
		result.fillWithResult(ResultType.nullParam);
		return result;
	}
	
	protected CommonResult errorParam() {
		CommonResult result = new CommonResult();
		result.fillWithResult(ResultType.errorParam);
		return result;
	}
	
	protected CommonResult serviceError() {
		CommonResult result = new CommonResult();
		result.fillWithResult(ResultType.serviceError);
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
}
