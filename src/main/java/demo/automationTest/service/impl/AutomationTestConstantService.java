package demo.automationTest.service.impl;

import java.io.File;

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
			log.error("article constant loading error: " + e.getLocalizedMessage());
		}
	}

	public String getReportStorePrefixPath() {
		return reportStorePrefixPath;
	}

	public void setReportStorePrefixPath(String reportStorePrefixPath) {
		this.reportStorePrefixPath = reportStorePrefixPath;
	}

	@Override
	public String toString() {
		return "AutomationTestConstantService [optionFilePath=" + optionFilePath + ", reportStorePrefixPath="
				+ reportStorePrefixPath + "]";
	}

}
