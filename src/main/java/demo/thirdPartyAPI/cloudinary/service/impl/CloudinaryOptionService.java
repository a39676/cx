package demo.thirdPartyAPI.cloudinary.service.impl;

import java.io.File;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import demo.config.customComponent.OptionFilePathConfigurer;
import jakarta.annotation.PostConstruct;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class CloudinaryOptionService extends CommonService {

	private String cloudinaryName;
	private String cloudinaryApiKey;
	private String cloudinaryApiSecret;

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(OptionFilePathConfigurer.CLOUDINARY);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(OptionFilePathConfigurer.CLOUDINARY);
			CloudinaryOptionService tmp = new Gson().fromJson(jsonStr, this.getClass());
			BeanUtils.copyProperties(tmp, this);
			log.error("cloudinary option loaded");
		} catch (Exception e) {
			log.error("cloudinary option loading error: " + e.getLocalizedMessage());
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
		return "CloudinaryOptionService [cloudinaryName=" + cloudinaryName + ", cloudinaryApiKey=" + cloudinaryApiKey
				+ ", cloudinaryApiSecret=" + cloudinaryApiSecret + "]";
	}
}
