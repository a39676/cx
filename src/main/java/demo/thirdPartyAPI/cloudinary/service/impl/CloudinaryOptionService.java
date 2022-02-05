package demo.thirdPartyAPI.cloudinary.service.impl;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class CloudinaryOptionService extends CommonService {

	@Value("${optionFilePath.cloudinary}")
	private String optionFilePath;

	private String cloudinaryName;
	private String cloudinaryApiKey;
	private String cloudinaryApiSecret;

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(optionFilePath);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(optionFilePath);
			CloudinaryOptionService tmp = new Gson().fromJson(jsonStr, CloudinaryOptionService.class);
			BeanUtils.copyProperties(tmp, this);
		} catch (Exception e) {
			log.error("cloudinary option loading error: " + e.getLocalizedMessage());
		}
		log.error("cloudinary option loaded");
	}

	public String getCloudinaryName() {
		return cloudinaryName;
	}

	public void setCloudinaryName(String cloudinaryName) {
		this.cloudinaryName = cloudinaryName;
	}

	public String getCloudinaryApiKey() {
		return cloudinaryApiKey;
	}

	public void setCloudinaryApiKey(String cloudinaryApiKey) {
		this.cloudinaryApiKey = cloudinaryApiKey;
	}

	public String getCloudinaryApiSecret() {
		return cloudinaryApiSecret;
	}

	public void setCloudinaryApiSecret(String cloudinaryApiSecret) {
		this.cloudinaryApiSecret = cloudinaryApiSecret;
	}

	@Override
	public String toString() {
		return "CloudinaryConstantService [optionFilePath=" + optionFilePath + ", cloudinaryName=" + cloudinaryName
				+ ", cloudinaryApiKey=" + cloudinaryApiKey + ", cloudinaryApiSecret=" + cloudinaryApiSecret + "]";
	}

}