package demo.tool.service.impl;

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
public class MailConstantService extends CommonService {

	@Value("${optionFilePath.mail}")
	private String optionFilePath;

	private String adminMailName = "adminMailName";
	private String adminMailPwd = "adminMailPwd";

	public void refreshConstant() {
		File optionFile = new File(optionFilePath);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(optionFilePath);
			MailConstantService tmp = new Gson().fromJson(jsonStr, MailConstantService.class);
			BeanUtils.copyProperties(tmp, this);
		} catch (Exception e) {
			log.error("mail constant loading error: " + e.getLocalizedMessage());
		}
	}

	public String getAdminMailName() {
		return adminMailName;
	}

	public void setAdminMailName(String adminMailName) {
		this.adminMailName = adminMailName;
	}

	public String getAdminMailPwd() {
		return adminMailPwd;
	}

	public void setAdminMailPwd(String adminMailPwd) {
		this.adminMailPwd = adminMailPwd;
	}

	@Override
	public String toString() {
		return "MailConstantService [optionFilePath=" + optionFilePath + ", adminMailName=" + adminMailName
				+ ", adminMailPwd=" + adminMailPwd + "]";
	}

}
