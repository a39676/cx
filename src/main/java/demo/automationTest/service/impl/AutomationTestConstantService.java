package demo.automationTest.service.impl;

import java.io.File;
import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class AutomationTestConstantService extends CommonService {

	@Value("${optionFilePath.automationTest}")
	private String optionFilePath;

	private String reportStorePrefixPath;
	private String paramStorePrefixPath;
	private LocalDateTime lastHeartBeat = LocalDateTime.now();
	/** test event 数据存活时长, 单位:月 */
	private Integer testEventLiveLimitMonth = 3;
	/** 任务分派后, 经过 maxWaitingRunHour 小时, 如仍无返回数据, 执行相关逻辑 */
	private Integer maxWaitingRunHour = 3;

	public void refreshConstant() {
		File optionFile = new File(optionFilePath);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(optionFilePath);
			AutomationTestConstantService tmp = new Gson().fromJson(jsonStr, AutomationTestConstantService.class);
			BeanUtils.copyProperties(tmp, this);
		} catch (Exception e) {
			log.error("automation test constant loading error: " + e.getLocalizedMessage());
		}
	}

	public String getReportStorePrefixPath() {
		return reportStorePrefixPath;
	}

	public void setReportStorePrefixPath(String reportStorePrefixPath) {
		this.reportStorePrefixPath = reportStorePrefixPath;
	}

	public String getOptionFilePath() {
		return optionFilePath;
	}

	public String getParamStorePrefixPath() {
		return paramStorePrefixPath;
	}

	public void setParamStorePrefixPath(String paramStorePrefixPath) {
		this.paramStorePrefixPath = paramStorePrefixPath;
	}

	public LocalDateTime getLastHeartBeat() {
		return lastHeartBeat;
	}

	public void setLastHeartBeat(LocalDateTime lastHeartBeat) {
		this.lastHeartBeat = lastHeartBeat;
	}

	public Integer getTestEventLiveLimitMonth() {
		return testEventLiveLimitMonth;
	}

	public void setTestEventLiveLimitMonth(Integer testEventLiveLimitMonth) {
		this.testEventLiveLimitMonth = testEventLiveLimitMonth;
	}

	public Integer getMaxWaitingRunHour() {
		return maxWaitingRunHour;
	}

	public void setMaxWaitingRunHour(Integer maxWaitingRunHour) {
		this.maxWaitingRunHour = maxWaitingRunHour;
	}

	@Override
	public String toString() {
		return "AutomationTestConstantService [optionFilePath=" + optionFilePath + ", reportStorePrefixPath="
				+ reportStorePrefixPath + ", paramStorePrefixPath=" + paramStorePrefixPath + ", lastHeartBeat="
				+ lastHeartBeat + ", testEventLiveLimitMonth=" + testEventLiveLimitMonth + ", maxWaitingRunHour="
				+ maxWaitingRunHour + "]";
	}

}
