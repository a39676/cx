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

	@Override
	public String toString() {
		return "AutomationTestConstantService [optionFilePath=" + optionFilePath + ", reportStorePrefixPath="
				+ reportStorePrefixPath + ", paramStorePrefixPath=" + paramStorePrefixPath + ", lastHeartBeat="
				+ lastHeartBeat + "]";
	}

}
