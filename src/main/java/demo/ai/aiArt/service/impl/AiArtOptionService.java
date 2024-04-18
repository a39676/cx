package demo.ai.aiArt.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import demo.config.costom_component.OptionFilePathConfigurer;
import jakarta.annotation.PostConstruct;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class AiArtOptionService extends CommonService {

	private String generateImageResultFolder;
	private Integer imageWallOnShowMaxSize;
	private Integer maxHeight;
	private Integer maxWidth;
	private Integer maxBatch;
	private Integer maxCfgScale;
	private Integer maxSteps;
	private Integer maxDailyFreeJobCount;
	private Integer maxPromptLength;
	private Integer maxFailCountForJob;
	private Integer maxShowJob;
	private Integer maxJobLivingDay;
	private Integer maxLivingMinuteOfApiImageAfterFirstVisit = 10;
	private Double consumptionCoefficient;
	private String imagePkInsteadOfNsfw;
	private String apiKeyOfAdmin;
	private Integer maxWaitingJobCount;
	private List<String> nsfwPrompt;
	private String serviceStartTimeStr;
	private String serviceEndTimeStr;
	private Integer freeJobDelaySeconds;
	private Integer higerFixMaxHeight;
	private Integer higerFixMaxWidth;
	private Integer higerFixMaxStep;

	public String getApiKeyOfAdmin() {
		return apiKeyOfAdmin;
	}

	public void setApiKeyOfAdmin(String apiKeyOfAdmin) {
		this.apiKeyOfAdmin = apiKeyOfAdmin;
	}

	public String getGenerateImageResultFolder() {
		return generateImageResultFolder;
	}

	public void setGenerateImageResultFolder(String generateImageResultFolder) {
		this.generateImageResultFolder = generateImageResultFolder;
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

	public String getServiceStartTimeStr() {
		return serviceStartTimeStr;
	}

	public void setServiceStartTimeStr(String serviceStartTimeStr) {
		this.serviceStartTimeStr = serviceStartTimeStr;
	}

	public String getServiceEndTimeStr() {
		return serviceEndTimeStr;
	}

	public void setServiceEndTimeStr(String serviceEndTimeStr) {
		this.serviceEndTimeStr = serviceEndTimeStr;
	}

	public Integer getFreeJobDelaySeconds() {
		return freeJobDelaySeconds;
	}

	public void setFreeJobDelaySeconds(Integer freeJobDelaySeconds) {
		this.freeJobDelaySeconds = freeJobDelaySeconds;
	}

	public Integer getHigerFixMaxHeight() {
		return higerFixMaxHeight;
	}

	public void setHigerFixMaxHeight(Integer higerFixMaxHeight) {
		this.higerFixMaxHeight = higerFixMaxHeight;
	}

	public Integer getHigerFixMaxWidth() {
		return higerFixMaxWidth;
	}

	public void setHigerFixMaxWidth(Integer higerFixMaxWidth) {
		this.higerFixMaxWidth = higerFixMaxWidth;
	}

	public Integer getHigerFixMaxStep() {
		return higerFixMaxStep;
	}

	public void setHigerFixMaxStep(Integer higerFixMaxStep) {
		this.higerFixMaxStep = higerFixMaxStep;
	}

	@Override
	public String toString() {
		return "AiArtOptionService [generateImageResultFolder=" + generateImageResultFolder
				+ ", imageWallOnShowMaxSize=" + imageWallOnShowMaxSize + ", maxHeight=" + maxHeight + ", maxWidth="
				+ maxWidth + ", maxBatch=" + maxBatch + ", maxCfgScale=" + maxCfgScale + ", maxSteps=" + maxSteps
				+ ", maxDailyFreeJobCount=" + maxDailyFreeJobCount + ", maxPromptLength=" + maxPromptLength
				+ ", maxFailCountForJob=" + maxFailCountForJob + ", maxShowJob=" + maxShowJob + ", maxJobLivingDay="
				+ maxJobLivingDay + ", maxLivingMinuteOfApiImageAfterFirstVisit="
				+ maxLivingMinuteOfApiImageAfterFirstVisit + ", consumptionCoefficient=" + consumptionCoefficient
				+ ", imagePkInsteadOfNsfw=" + imagePkInsteadOfNsfw + ", apiKeyOfAdmin=" + apiKeyOfAdmin
				+ ", maxWaitingJobCount=" + maxWaitingJobCount + ", nsfwPrompt=" + nsfwPrompt + ", serviceStartTimeStr="
				+ serviceStartTimeStr + ", serviceEndTimeStr=" + serviceEndTimeStr + ", freeJobDelaySeconds="
				+ freeJobDelaySeconds + ", higerFixMaxHeight=" + higerFixMaxHeight + ", higerFixMaxWidth="
				+ higerFixMaxWidth + ", higerFixMaxStep=" + higerFixMaxStep + "]";
	}

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(OptionFilePathConfigurer.AI_ART);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(OptionFilePathConfigurer.AI_ART);
			AiArtOptionService tmp = new Gson().fromJson(jsonStr, this.getClass());
			BeanUtils.copyProperties(tmp, this);
			System.out.println(this.toString());
			log.error("AI art option loaded");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("AI art option loading error: " + e.getLocalizedMessage());
		}
	}
}
