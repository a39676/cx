package demo.joy.common.service;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import demo.config.costom_component.OptionFilePathConfigurer;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class JoyOptionService extends CommonService {

	private String imgStorePathPrefix;

	private String iconImageStorePathPrefix;

	private String npcImageStorePathPrefix;

	private String gardenImageStorePathPrefix;

	public String getImgStorePathPrefix() {
		return imgStorePathPrefix;
	}

	public void setImgStorePathPrefix(String imgStorePathPrefix) {
		this.imgStorePathPrefix = imgStorePathPrefix;
	}

	public String getIconImageStorePathPrefix() {
		return iconImageStorePathPrefix;
	}

	public void setIconImageStorePathPrefix(String iconImageStorePathPrefix) {
		this.iconImageStorePathPrefix = iconImageStorePathPrefix;
	}

	public String getNpcImageStorePathPrefix() {
		return npcImageStorePathPrefix;
	}

	public void setNpcImageStorePathPrefix(String npcImageStorePathPrefix) {
		this.npcImageStorePathPrefix = npcImageStorePathPrefix;
	}

	public String getGardenImageStorePathPrefix() {
		return gardenImageStorePathPrefix;
	}

	public void setGardenImageStorePathPrefix(String gardenImageStorePathPrefix) {
		this.gardenImageStorePathPrefix = gardenImageStorePathPrefix;
	}

	@Override
	public String toString() {
		return "JoyOptionService [imgStorePathPrefix=" + imgStorePathPrefix + ", iconImageStorePathPrefix="
				+ iconImageStorePathPrefix + ", npcImageStorePathPrefix=" + npcImageStorePathPrefix
				+ ", gardenImageStorePathPrefix=" + gardenImageStorePathPrefix + "]";
	}

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(OptionFilePathConfigurer.JOY);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(OptionFilePathConfigurer.JOY);
			JoyOptionService tmp = new Gson().fromJson(jsonStr, JoyOptionService.class);
			BeanUtils.copyProperties(tmp, this);
			log.error("article option loaded");
		} catch (Exception e) {
			log.error("article option loading error: " + e.getLocalizedMessage());
		}
	}

}
