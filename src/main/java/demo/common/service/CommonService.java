package demo.common.service;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.common.pojo.result.CommonResultCX;
import demo.common.pojo.type.ResultTypeCX;
import demo.config.costom_component.BaseUtilCustom;
import demo.config.costom_component.SnowFlake;
import net.sf.json.JSONObject;
import tool.pojo.bo.IpRecordBO;
import toolPack.dateTimeHandle.DateHandler;
import toolPack.dateTimeHandle.LocalDateTimeHandler;

public abstract class CommonService {
	
	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	protected SnowFlake snowFlake;
	@Autowired
	protected LocalDateTimeHandler localDateTimeHandler;
	@Autowired
	protected DateHandler dateHandler;
	@Autowired
	protected BaseUtilCustom baseUtilCustom;

	protected static final Integer NORMAL_PAGE_SIZE = 10;
	protected static final LocalDateTime BLOG_ARTICLE_START_TIME = LocalDateTime.of(2020, 5, 1, 0, 0, 0);
	protected static final String MAIN_FOLDER_PATH = "/home/u2/cx";
	
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

	

	protected boolean isBigUser() {
		return baseUtilCustom.hasSuperAdminRole();
	}

	
	protected IpRecordBO getIp(HttpServletRequest request) {
		IpRecordBO record = new IpRecordBO();
		record.setRemoteAddr(request.getRemoteAddr());
		record.setForwardAddr(request.getHeader("X-FORWARDED-FOR"));

		return record;
	}

	protected String buildRedisKeyPrefix(IpRecordBO record, String redisKeyPrefix) {
		return redisKeyPrefix + "_" + record.getForwardAddr() + "_" + record.getRemoteAddr();
	}

	protected LocalDateTime getNextSettingTime(LocalDateTime datetime, TimeUnitType timeUnit, Long step) {
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

	protected AutomationTestInsertEventDTO automationTestInsertEventDtoAddParamStr(AutomationTestInsertEventDTO dto, Object obj) {
		JSONObject json = null;
		if(StringUtils.isBlank(dto.getParamStr())) {
			json = new JSONObject();
		} else {
			try {
				json = JSONObject.fromObject(dto.getParamStr());
			} catch (Exception e) {
				json = new JSONObject();
			}
		}
		
		json.put(obj.getClass().getSimpleName(), obj);
		
		dto.setParamStr(json.toString());
		
		return dto;
	}
}
