package demo.thirdPartyAPI.cloudinary.service.impl;

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
public class CloudinaryConstantService extends CommonService {

	@Value("${optionFilePath.cloudinary}")
	private String optionFilePath;

	private String cloudinaryName;
	private String cloudinaryApiKey;
	private String cloudinaryApiSecret;

	public void refreshConstant() {
		File optionFile = new File(optionFilePath);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(optionFilePath);
			CloudinaryConstantService tmp = new Gson().fromJson(jsonStr, CloudinaryConstantService.class);
			BeanUtils.copyProperties(tmp, this);
		} catch (Exception e) {
			log.error("cloudinary constant loading error: " + e.getLocalizedMessage());
		}
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
