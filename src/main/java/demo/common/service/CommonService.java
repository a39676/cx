package demo.common.service;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import autoTest.testEvent.common.pojo.dto.AutomationTestInsertEventDTO;
import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.config.customComponent.BaseUtilCustom;
import demo.config.customComponent.SnowFlake;
import net.sf.json.JSONObject;
import tool.pojo.bo.IpRecordBO;
import toolPack.dateTimeHandle.DateHandler;
import toolPack.dateTimeHandle.LocalDateTimeAdapter;
import toolPack.dateTimeHandle.LocalDateTimeHandler;
import toolPack.encryptHandle.EncryptUtil;

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
	@Autowired
	protected LocalDateTimeAdapter localDateTimeAdapter;
	@Autowired
	protected EncryptUtil encryptUtil;

	protected static final Integer NORMAL_PAGE_SIZE = 10;
	protected static final LocalDateTime BLOG_ARTICLE_START_TIME = LocalDateTime.of(2020, 5, 1, 0, 0, 0);
	protected static final String MAIN_FOLDER_PATH = "/home/u2/cx";

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

	protected LocalDateTime getNextSettingTime(LocalDateTime datetime, TimeUnitType timeUnit, Long step) {
		LocalDateTime nextNoticeTime = null;
		if (datetime == null || timeUnit == null || step == null) {
			return nextNoticeTime;
		}

		if (timeUnit.equals(TimeUnitType.NANO_SECOND)) {
			nextNoticeTime = datetime.plusNanos(step);
		} else if (timeUnit.equals(TimeUnitType.MILLION_SECOND)) {
			nextNoticeTime = datetime.plusNanos(step * 1000);
		} else if (timeUnit.equals(TimeUnitType.SECOND)) {
			nextNoticeTime = datetime.plusSeconds(step);
		} else if (timeUnit.equals(TimeUnitType.MINUTE)) {
			nextNoticeTime = datetime.plusMinutes(step);
		} else if (timeUnit.equals(TimeUnitType.HOUR)) {
			nextNoticeTime = datetime.plusHours(step);
		} else if (timeUnit.equals(TimeUnitType.DAY)) {
			nextNoticeTime = datetime.plusDays(step);
		} else if (timeUnit.equals(TimeUnitType.WEEK)) {
			nextNoticeTime = datetime.plusDays(step * 7);
		} else if (timeUnit.equals(TimeUnitType.MONTH)) {
			nextNoticeTime = datetime.plusMonths(step);
		} else if (timeUnit.equals(TimeUnitType.YEAR)) {
			nextNoticeTime = datetime.plusYears(step);
		}

		return nextNoticeTime;
	}

	protected AutomationTestInsertEventDTO automationTestInsertEventDtoAddParamStr(AutomationTestInsertEventDTO dto,
			Object obj) {
		JSONObject json = null;
		if (StringUtils.isBlank(dto.getParamStr())) {
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

	protected <T> T buildObjFromJsonCustomization(String jsonStr, Class<T> clazz) {
		String className = clazz.getSimpleName();

		try {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, localDateTimeAdapter).create();

			return gson.fromJson(jsonStr, clazz);

		} catch (Exception e) {
			e.printStackTrace();
			String msg = String.format("Build gson error, param name: %s ", className);
			log.error(msg);
		}
		return null;

	}

	protected boolean isBasicDataTypesOrString(Object obj) {
		return obj instanceof Boolean || obj instanceof Byte || obj instanceof Short || obj instanceof Integer
				|| obj instanceof Long || obj instanceof Float || obj instanceof Double || obj instanceof Character
				|| obj instanceof String;
	}
}
