package demo.tool.mail.service.impl;

import java.io.File;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import jakarta.annotation.PostConstruct;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class MailOptionService extends CommonService {

	@Value("${optionFilePath.mail}")
	private String optionFilePath;

	private String adminMailName = "adminMailName";
	private String adminMailPwd = "adminMailPwd";

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(optionFilePath);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(optionFilePath);
			MailOptionService tmp = new Gson().fromJson(jsonStr, MailOptionService.class);
			BeanUtils.copyProperties(tmp, this);
			log.error("mail option loaded");
		} catch (Exception e) {
			log.error("mail option loading error: " + e.getLocalizedMessage());
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
