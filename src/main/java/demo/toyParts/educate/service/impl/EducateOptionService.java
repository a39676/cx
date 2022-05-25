package demo.toyParts.educate.service.impl;

import java.io.File;
import java.math.BigDecimal;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class EducateOptionService extends CommonService {

	@Value("${optionFilePath.educate}")
	private String optionFilePath;

	private String exerciesStorePrefixPath;
	private Integer questionListSize = 20;
	private BigDecimal maxScore = new BigDecimal(100);
	private BigDecimal randomPointMax = BigDecimal.TEN;
	private BigDecimal randomPointMin = BigDecimal.ONE;
	private Integer dailyMaxHour = 22;
	private Integer dailyMinHour = 7;
	private Integer oldExerciesFileLivingDay = 7;

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(optionFilePath);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(optionFilePath);
			EducateOptionService tmp = new Gson().fromJson(jsonStr, EducateOptionService.class);
			BeanUtils.copyProperties(tmp, this);
		} catch (Exception e) {
			log.error("educate option loading error: " + e.getLocalizedMessage());
		}
		log.error("educate option loaded");
	}

	public String getExerciesStorePrefixPath() {
		return exerciesStorePrefixPath;
	}

	public void setExerciesStorePrefixPath(String exerciesStorePrefixPath) {
		this.exerciesStorePrefixPath = exerciesStorePrefixPath;
	}

	public Integer getQuestionListSize() {
		return questionListSize;
	}

	public void setQuestionListSize(Integer questionListSize) {
		this.questionListSize = questionListSize;
	}

	public BigDecimal getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(BigDecimal maxScore) {
		this.maxScore = maxScore;
	}

	public BigDecimal getRandomPointMax() {
		return randomPointMax;
	}

	public void setRandomPointMax(BigDecimal randomPointMax) {
		this.randomPointMax = randomPointMax;
	}

	public BigDecimal getRandomPointMin() {
		return randomPointMin;
	}

	public void setRandomPointMin(BigDecimal randomPointMin) {
		this.randomPointMin = randomPointMin;
	}

	public Integer getDailyMaxHour() {
		return dailyMaxHour;
	}

	public void setDailyMaxHour(Integer dailyMaxHour) {
		this.dailyMaxHour = dailyMaxHour;
	}

	public Integer getDailyMinHour() {
		return dailyMinHour;
	}

	public void setDailyMinHour(Integer dailyMinHour) {
		this.dailyMinHour = dailyMinHour;
	}

	public Integer getOldExerciesFileLivingDay() {
		return oldExerciesFileLivingDay;
	}

	public void setOldExerciesFileLivingDay(Integer oldExerciesFileLivingDay) {
		this.oldExerciesFileLivingDay = oldExerciesFileLivingDay;
	}

	@Override
	public String toString() {
		return "EducateOptionService [optionFilePath=" + optionFilePath + ", exerciesStorePrefixPath="
				+ exerciesStorePrefixPath + ", questionListSize=" + questionListSize + ", maxScore=" + maxScore
				+ ", randomPointMax=" + randomPointMax + ", randomPointMin=" + randomPointMin + ", dailyMaxHour="
				+ dailyMaxHour + ", dailyMinHour=" + dailyMinHour + ", oldExerciesFileLivingDay="
				+ oldExerciesFileLivingDay + "]";
	}

}
