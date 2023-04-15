package demo.aiArt.service.impl;

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
public class AiArtOptionService extends CommonService {

	@Value("${optionFilePath.aiArt}")
	private String optionFilePath;
	private String mainUrl;
	private String imageSavingFolder;
	private Integer maxHeight;
	private Integer maxWidth;
	private Integer maxBatch;
	private Integer maxCfgScale;
	private Integer maxSteps;

	public String getMainUrl() {
		return mainUrl;
	}

	public void setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
	}

	public String getImageSavingFolder() {
		return imageSavingFolder;
	}

	public void setImageSavingFolder(String imageSavingFolder) {
		this.imageSavingFolder = imageSavingFolder;
	}

	public Integer getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(Integer maxHeight) {
		this.maxHeight = maxHeight;
	}

	public Integer getMaxWidth() {
		return maxWidth;
	}

	public void setMaxWidth(Integer maxWidth) {
		this.maxWidth = maxWidth;
	}

	public Integer getMaxBatch() {
		return maxBatch;
	}

	public void setMaxBatch(Integer maxBatch) {
		this.maxBatch = maxBatch;
	}

	public Integer getMaxCfgScale() {
		return maxCfgScale;
	}

	public void setMaxCfgScale(Integer maxCfgScale) {
		this.maxCfgScale = maxCfgScale;
	}

	public Integer getMaxSteps() {
		return maxSteps;
	}

	public void setMaxSteps(Integer maxSteps) {
		this.maxSteps = maxSteps;
	}

	@Override
	public String toString() {
		return "AiArtOptionService [mainUrl=" + mainUrl + ", imageSavingFolder=" + imageSavingFolder + ", maxHeight="
				+ maxHeight + ", maxWidth=" + maxWidth + ", maxBatch=" + maxBatch + ", maxCfgScale=" + maxCfgScale
				+ ", maxSteps=" + maxSteps + "]";
	}

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(optionFilePath);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(optionFilePath);
			AiArtOptionService tmp = new Gson().fromJson(jsonStr, AiArtOptionService.class);
			BeanUtils.copyProperties(tmp, this);
			log.error("Open AI option loaded");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Open AI option loading error: " + e.getLocalizedMessage());
		}
	}
}
