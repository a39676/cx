package demo.automationTest.service.impl;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import demo.config.costom_component.OptionFilePathConfigurer;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class AutomationTestOptionService extends CommonService {

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
		File optionFile = new File(OptionFilePathConfigurer.AUTOMATION_TEST);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(OptionFilePathConfigurer.AUTOMATION_TEST);
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

}
