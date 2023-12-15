package demo.toyParts.educate.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.common.service.CommonService;
import jakarta.annotation.PostConstruct;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class EducateOptionService extends CommonService {

	@Value("${optionFilePath.educate}")
	private String optionFilePath;

	private String exerciseStorePrefixPath;
	private Integer calculateQuestionListSize = 20;
	private BigDecimal maxScore = new BigDecimal(100);
	private BigDecimal randomMaxAwardCoefficient;
	private BigDecimal randomMinAwardCoefficient;
	private HashMap<Integer, BigDecimal> gardePointMap;
	private Integer dailyMaxHour = 22;
	private Integer dailyMinHour = 7;
	private Integer oldExerciseFileLivingDay = 7;

	@PostConstruct
	public String refreshOption() {
		File optionFile = new File(optionFilePath);
		if (!optionFile.exists()) {
			return null;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(optionFilePath);
			EducateOptionService tmp = new Gson().fromJson(jsonStr, EducateOptionService.class);
			BeanUtils.copyProperties(tmp, this);
			log.error("educate option loaded");
			return jsonStr;
		} catch (Exception e) {
			log.error("educate option loading error: " + e.getLocalizedMessage());
		}
		return null;
	}

	public String getOptionFilePath() {
		return null;
	}

	public void setOptionFilePath(String optionFilePath) {
	}

	public String getExerciseStorePrefixPath() {
		return exerciseStorePrefixPath;
	}

	public void setExerciseStorePrefixPath(String exerciseStorePrefixPath) {
		this.exerciseStorePrefixPath = exerciseStorePrefixPath;
	}

	public Integer getCalculateQuestionListSize() {
		return calculateQuestionListSize;
	}

	public void setCalculateQuestionListSize(Integer calculateQuestionListSize) {
		this.calculateQuestionListSize = calculateQuestionListSize;
	}

	public BigDecimal getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(BigDecimal maxScore) {
		this.maxScore = maxScore;
	}

	public BigDecimal getRandomMaxAwardCoefficient() {
		return randomMaxAwardCoefficient;
	}

	public void setRandomMaxAwardCoefficient(BigDecimal randomMaxAwardCoefficient) {
		this.randomMaxAwardCoefficient = randomMaxAwardCoefficient;
	}

	public BigDecimal getRandomMinAwardCoefficient() {
		return randomMinAwardCoefficient;
	}

	public void setRandomMinAwardCoefficient(BigDecimal randomMinAwardCoefficient) {
		this.randomMinAwardCoefficient = randomMinAwardCoefficient;
	}

	public HashMap<Integer, BigDecimal> getGardePointMap() {
		return gardePointMap;
	}

	public void setGardePointMap(HashMap<Integer, BigDecimal> gardePointMap) {
		this.gardePointMap = gardePointMap;
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

	public Integer getOldExerciseFileLivingDay() {
		return oldExerciseFileLivingDay;
	}

	public void setOldExerciseFileLivingDay(Integer oldExerciseFileLivingDay) {
		this.oldExerciseFileLivingDay = oldExerciseFileLivingDay;
	}

	@Override
	public String toString() {
		return "EducateOptionService [exerciseStorePrefixPath=" + exerciseStorePrefixPath + ", questionListSize="
				+ calculateQuestionListSize + ", maxScore=" + maxScore + ", randomMaxAwardCoefficient="
				+ randomMaxAwardCoefficient + ", randomMinAwardCoefficient=" + randomMinAwardCoefficient
				+ ", gardePointMap=" + gardePointMap + ", dailyMaxHour=" + dailyMaxHour + ", dailyMinHour="
				+ dailyMinHour + ", oldExerciseFileLivingDay=" + oldExerciseFileLivingDay + "]";
	}

}
