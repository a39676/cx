package demo.ai.aiArt.service.impl;

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
	private String imageWallFilePath;
	private Integer imageWallMaxSize;
	private Integer imageWallOnShowMaxSize;
	private Integer maxHeight;
	private Integer maxWidth;
	private Integer maxBatch;
	private Integer maxCfgScale;
	private Integer maxSteps;
	private Integer maxDailyFreeJobCount;
	private Integer maxPromptLength;
	private Integer maxFailCountForJob;
	private Boolean isRunning;
	private Integer maxShowJob;
	private Integer maxJobLivingDay;
	private Integer maxLivingMinuteOfApiImageAfterFirstVisit = 10;
	private Double consumptionCoefficient;
	private String imagePkInsteadOfNsfw;
	private String apiKeyOfAdmin;
	private Long idOfAdmin;
	private Integer maxWaitingJobCount;
	private List<String> nsfwPrompt;

	public String getApiKeyOfAdmin() {
		return apiKeyOfAdmin;
	}

	public void setApiKeyOfAdmin(String apiKeyOfAdmin) {
		this.apiKeyOfAdmin = apiKeyOfAdmin;
	}

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

	public String getImageWallFilePath() {
		return imageWallFilePath;
	}

	public void setImageWallFilePath(String imageWallFilePath) {
		this.imageWallFilePath = imageWallFilePath;
	}

	public Integer getImageWallMaxSize() {
		return imageWallMaxSize;
	}

	public void setImageWallMaxSize(Integer imageWallMaxSize) {
		this.imageWallMaxSize = imageWallMaxSize;
	}

	public Integer getImageWallOnShowMaxSize() {
		return imageWallOnShowMaxSize;
	}

	public void setImageWallOnShowMaxSize(Integer imageWallOnShowMaxSize) {
		this.imageWallOnShowMaxSize = imageWallOnShowMaxSize;
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

	public String getImagePkInsteadOfNsfw() {
		return imagePkInsteadOfNsfw;
	}

	public void setImagePkInsteadOfNsfw(String imagePkInsteadOfNsfw) {
		this.imagePkInsteadOfNsfw = imagePkInsteadOfNsfw;
	}

	public Integer getMaxWaitingJobCount() {
		return maxWaitingJobCount;
	}

	public void setMaxWaitingJobCount(Integer maxWaitingJobCount) {
		this.maxWaitingJobCount = maxWaitingJobCount;
	}

	public Long getIdOfAdmin() {
		return idOfAdmin;
	}

	public void setIdOfAdmin(Long idOfAdmin) {
		this.idOfAdmin = idOfAdmin;
	}

	@Override
	public String toString() {
		return "AiArtOptionService [mainUrl=" + mainUrl + ", generateImageResultFolder=" + generateImageResultFolder
				+ ", textToImageParameterSavingFolder=" + textToImageParameterSavingFolder + ", imageWallFilePath="
				+ imageWallFilePath + ", imageWallMaxSize=" + imageWallMaxSize + ", imageWallOnShowMaxSize="
				+ imageWallOnShowMaxSize + ", maxHeight=" + maxHeight + ", maxWidth=" + maxWidth + ", maxBatch="
				+ maxBatch + ", maxCfgScale=" + maxCfgScale + ", maxSteps=" + maxSteps + ", maxDailyFreeJobCount="
				+ maxDailyFreeJobCount + ", maxPromptLength=" + maxPromptLength + ", maxFailCountForJob="
				+ maxFailCountForJob + ", isRunning=" + isRunning + ", maxShowJob=" + maxShowJob + ", maxJobLivingDay="
				+ maxJobLivingDay + ", maxLivingMinuteOfApiImageAfterFirstVisit="
				+ maxLivingMinuteOfApiImageAfterFirstVisit + ", consumptionCoefficient=" + consumptionCoefficient
				+ ", imagePkInsteadOfNsfw=" + imagePkInsteadOfNsfw + ", apiKeyOfAdmin=" + apiKeyOfAdmin + ", idOfAdmin="
				+ idOfAdmin + ", maxWaitingJobCount=" + maxWaitingJobCount + ", nsfwPrompt=" + nsfwPrompt + "]";
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
