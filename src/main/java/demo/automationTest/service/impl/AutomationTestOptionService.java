package demo.automationTest.service.impl;

import java.io.File;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import jakarta.annotation.PostConstruct;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class AutomationTestOptionService extends CommonService {

	@Value("${optionFilePath.automationTest}")
	private String optionFilePath;

	private String inputParamStorePrefixPath;

	private String reportStorePrefixPath;
	private String paramStorePrefixPath;
	private String imageStorePrefixPath;

	/** test event 数据存活时长, 单位:月 */
	private Integer testEventLiveLimitMonth = 3;

	/** 任务分派后, 经过 maxWaitingRunHour 小时, 如仍无返回数据, 执行相关逻辑 */
	private Integer maxWaitingRunHour = 3;

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(optionFilePath);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(optionFilePath);
			AutomationTestOptionService tmp = new Gson().fromJson(jsonStr, AutomationTestOptionService.class);
			BeanUtils.copyProperties(tmp, this);
			log.error("automation test option loaded");
		} catch (Exception e) {
			log.error("automation test option loading error: " + e.getLocalizedMessage());
		}
	}

	public String getInputParamStorePrefixPath() {
		return inputParamStorePrefixPath;
	}

	public void setInputParamStorePrefixPath(String inputParamStorePrefixPath) {
		this.inputParamStorePrefixPath = inputParamStorePrefixPath;
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

	public String getImageStorePrefixPath() {
		return imageStorePrefixPath;
	}

	public void setImageStorePrefixPath(String imageStorePrefixPath) {
		this.imageStorePrefixPath = imageStorePrefixPath;
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
		return "AutomationTestOptionService [optionFilePath=" + optionFilePath + ", inputParamStorePrefixPath="
				+ inputParamStorePrefixPath + ", reportStorePrefixPath=" + reportStorePrefixPath
				+ ", paramStorePrefixPath=" + paramStorePrefixPath + ", imageStorePrefixPath=" + imageStorePrefixPath
				+ ", testEventLiveLimitMonth=" + testEventLiveLimitMonth + ", maxWaitingRunHour=" + maxWaitingRunHour
				+ "]";
	}

}
