package demo.aiArt.service.impl;

import java.io.File;
import java.util.List;

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
	private String generateImageResultFolder;
	private String textToImageParameterSavingFolder;
	private Integer maxHeight;
	private Integer maxWidth;
	private Integer maxBatch;
	private Integer maxCfgScale;
	private Integer maxSteps;
	private Integer dailyFreeGenerateCount;
	private Integer maxPromptLength;
	private Integer maxFailCountForJob;
	private Boolean isRunning;
	private Integer maxShowJob;
	private Integer maxJobLivingDay;
	private Integer maxDailyFreeJobCount;
	private Integer maxLivingMinuteOfApiImageAfterFirstVisit = 10;
	private List<String> nsfwPrompt;
	private Double consumptionCoefficient;

	public String getMainUrl() {
		return mainUrl;
	}

	public void setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
	}

	public String getGenerateImageResultFolder() {
		return generateImageResultFolder;
	}

	public void setGenerateImageResultFolder(String generateImageResultFolder) {
		this.generateImageResultFolder = generateImageResultFolder;
	}

	public String getTextToImageParameterSavingFolder() {
		return textToImageParameterSavingFolder;
	}

	public void setTextToImageParameterSavingFolder(String textToImageParameterSavingFolder) {
		this.textToImageParameterSavingFolder = textToImageParameterSavingFolder;
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

	public Integer getDailyFreeGenerateCount() {
		return dailyFreeGenerateCount;
	}

	public void setDailyFreeGenerateCount(Integer dailyFreeGenerateCount) {
		this.dailyFreeGenerateCount = dailyFreeGenerateCount;
	}

	public Integer getMaxPromptLength() {
		return maxPromptLength;
	}

	public void setMaxPromptLength(Integer maxPromptLength) {
		this.maxPromptLength = maxPromptLength;
	}

	public Integer getMaxFailCountForJob() {
		return maxFailCountForJob;
	}

	public void setMaxFailCountForJob(Integer maxFailCountForJob) {
		this.maxFailCountForJob = maxFailCountForJob;
	}

	public Boolean getIsRunning() {
		return isRunning;
	}

	public void setIsRunning(Boolean isRunning) {
		this.isRunning = isRunning;
	}

	public Integer getMaxShowJob() {
		return maxShowJob;
	}

	public void setMaxShowJob(Integer maxShowJob) {
		this.maxShowJob = maxShowJob;
	}

	public Integer getMaxJobLivingDay() {
		return maxJobLivingDay;
	}

	public void setMaxJobLivingDay(Integer maxJobLivingDay) {
		this.maxJobLivingDay = maxJobLivingDay;
	}

	public Integer getMaxDailyFreeJobCount() {
		return maxDailyFreeJobCount;
	}

	public void setMaxDailyFreeJobCount(Integer maxDailyFreeJobCount) {
		this.maxDailyFreeJobCount = maxDailyFreeJobCount;
	}

	public Integer getMaxLivingMinuteOfApiImageAfterFirstVisit() {
		return maxLivingMinuteOfApiImageAfterFirstVisit;
	}

	public void setMaxLivingMinuteOfApiImageAfterFirstVisit(Integer maxLivingMinuteOfApiImageAfterFirstVisit) {
		this.maxLivingMinuteOfApiImageAfterFirstVisit = maxLivingMinuteOfApiImageAfterFirstVisit;
	}

	public List<String> getNsfwPrompt() {
		return nsfwPrompt;
	}

	public void setNsfwPrompt(List<String> nsfwPrompt) {
		this.nsfwPrompt = nsfwPrompt;
	}

	public Double getConsumptionCoefficient() {
		return consumptionCoefficient;
	}

	public void setConsumptionCoefficient(Double consumptionCoefficient) {
		this.consumptionCoefficient = consumptionCoefficient;
	}

	@Override
	public String toString() {
		return "AiArtOptionService [mainUrl=" + mainUrl + ", generateImageResultFolder=" + generateImageResultFolder
				+ ", textToImageParameterSavingFolder=" + textToImageParameterSavingFolder + ", maxHeight=" + maxHeight
				+ ", maxWidth=" + maxWidth + ", maxBatch=" + maxBatch + ", maxCfgScale=" + maxCfgScale + ", maxSteps="
				+ maxSteps + ", dailyFreeGenerateCount=" + dailyFreeGenerateCount + ", maxPromptLength="
				+ maxPromptLength + ", maxFailCountForJob=" + maxFailCountForJob + ", isRunning=" + isRunning
				+ ", maxShowJob=" + maxShowJob + ", maxJobLivingDay=" + maxJobLivingDay + ", maxDailyFreeJobCount="
				+ maxDailyFreeJobCount + ", maxLivingMinuteOfApiImageAfterFirstVisit="
				+ maxLivingMinuteOfApiImageAfterFirstVisit + ", nsfwPrompt=" + nsfwPrompt + ", consumptionCoefficient="
				+ consumptionCoefficient + "]";
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
			log.error("AI art option loaded");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("AI art option loading error: " + e.getLocalizedMessage());
		}
	}
}
