package demo.toyParts.educate.service.impl;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.toyParts.educate.pojo.dto.ExerciesAnswerDTO;
import demo.toyParts.educate.pojo.dto.MathExerciesDTO;
import demo.toyParts.educate.pojo.po.StudentDetail;
import demo.toyParts.educate.pojo.po.StudentExerciesHistory;
import demo.toyParts.educate.pojo.po.StudentExerciesHistoryExample;
import demo.toyParts.educate.pojo.result.ExerciesAnswerMatchResult;
import demo.toyParts.educate.pojo.type.ExerciesSubjectType;
import demo.toyParts.educate.pojo.type.GradeType;
import demo.toyParts.educate.service.ExerciesAnswerService;
import demo.toyParts.educate.service.EducateCommonService;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class ExerciesAnswerServiceImpl extends EducateCommonService implements ExerciesAnswerService {

	@Autowired
	private FileUtilCustom ioUtil;

	@Override
	public ExerciesAnswerMatchResult answerSubmit(ExerciesAnswerDTO dto) {
		ExerciesAnswerMatchResult answerResult = new ExerciesAnswerMatchResult();
		String privateKey = URLDecoder.decode(dto.getPk(), StandardCharsets.UTF_8);
		Long exerciesId = systemConstantService.decryptPrivateKey(privateKey);

		StudentExerciesHistory exerciesPO = exerciesHistoryMapper.selectByPrimaryKey(exerciesId);
		if (exerciesPO.getCompeletionTime() != null) {
			answerResult.setMessage("已经提交过答案,无法重复提交");
			return answerResult;
		}

		String exerciesJsonStr = ioUtil.getStringFromFile(exerciesPO.getFilePath());
		MathExerciesDTO exerciesDTO = new Gson().fromJson(exerciesJsonStr, MathExerciesDTO.class);

		answerResult = matchAnswer(dto, exerciesDTO);

		Long userId = baseUtilCustom.getUserId();
		if (userId == null) {
			exerciesPO.setPoints(new BigDecimal(answerResult.getPoints()));
			exerciesPO.setCompeletionTime(LocalDateTime.now());
			exerciesPO.setScore(answerResult.getTotalScore());
			exerciesHistoryMapper.updateByPrimaryKeySelective(exerciesPO);
			return answerResult;
		}

		StudentDetail detail = studentDetailMapper.selectByPrimaryKey(userId);

		answerResult = handlePointsCalculcate(answerResult, detail, exerciesDTO);

		exerciesPO.setPoints(new BigDecimal(answerResult.getPoints()));
		exerciesPO.setCompeletionTime(LocalDateTime.now());
		exerciesPO.setScore(answerResult.getTotalScore());
		exerciesHistoryMapper.updateByPrimaryKeySelective(exerciesPO);

		detail.setPointsSummary(detail.getPointsSummary().add(exerciesPO.getPoints()));
		studentDetailMapper.updateByPrimaryKeySelective(detail);

		return answerResult;
	}

	private ExerciesAnswerMatchResult handlePointsCalculcate(ExerciesAnswerMatchResult answerResult,
			StudentDetail detail, MathExerciesDTO exerciesDTO) {
		LocalDateTime now = LocalDateTime.now();
		if (now.getHour() >= optionService.getDailyMaxHour() || now.getHour() <= optionService.getDailyMinHour()) {
			answerResult.setPoints(0);
			answerResult.addMessage("晚上 " + optionService.getDailyMaxHour() + "点, 至次日 " + optionService.getDailyMinHour() + "点, 是休息时间, 请注意劳逸结合");
			return answerResult;
		}

		ThreadLocalRandom t = ThreadLocalRandom.current();
		int randomPoints = 0;

		GradeType studentGrade = GradeType.getType(detail.getGradeType().intValue());

		BigDecimal maxScore = optionService.getMaxScore();
		BigDecimal randomPointMax = optionService.getRandomPointMax();

		// 满分 && 符合自身年级
		if (answerResult.getTotalScore().compareTo(maxScore) == 0 && studentGrade.equals(exerciesDTO.getGradeType())) {
			if (!hasMaxScoreToday(detail.getId(), studentGrade, exerciesDTO.getSubjectType())) {
				randomPoints = optionService.getRandomPointMax().intValue();
				answerResult.addMessage("今日首次获得本学期的 " + exerciesDTO.getSubjectType().getCnName() + " 满分习题! 获得最高积分: "
						+ optionService.getRandomPointMax());
				answerResult.setPoints(randomPoints);
				return answerResult;
			}
		}

		// 按错题比例扣减随机积分上限
		if (!answerResult.getWrongNumberList().isEmpty()) {
			
			if(optionService.getQuestionListSize().equals(answerResult.getWrongNumberList().size())) {
				answerResult.setPoints(0);
				return answerResult;
			} else {
				Double coefficient = Double.valueOf(answerResult.getWrongNumberList().size()) / Double.valueOf(optionService.getQuestionListSize());
				randomPointMax = randomPointMax.multiply(BigDecimal.ONE.subtract(new BigDecimal(coefficient)));
			}
		}

		// 做以往的学期题目, 每学期扣1随机积分上限
		randomPointMax = randomPointMax
				.subtract(new BigDecimal(studentGrade.getCode() - exerciesDTO.getGradeType().getCode()));

		// 最少获得1分
		if (randomPointMax.compareTo(optionService.getRandomPointMin()) <= 0) {
			randomPoints = optionService.getRandomPointMin().intValue();
		} else {
			randomPoints = t.nextInt(optionService.getRandomPointMin().intValue(), randomPointMax.intValue() + 1);
		}

		answerResult.setPoints(randomPoints);

		return answerResult;
	}

	private ExerciesAnswerMatchResult matchAnswer(ExerciesAnswerDTO dto, MathExerciesDTO exerciesDTO) {
		ExerciesAnswerMatchResult r = new ExerciesAnswerMatchResult();

		BigDecimal totalScore = BigDecimal.ZERO;
		BigDecimal maxScore = optionService.getMaxScore();
		BigDecimal scoreOfSubQuestion = maxScore.divide(new BigDecimal(exerciesDTO.getQuestionList().size()));

		List<String> gavenAnswer = null;
		List<String> standarAnswer = null;

		for (int i = 0; i < exerciesDTO.getQuestionList().size(); i++) {
			gavenAnswer = dto.getAnswerList().get(i).getAnswer();
			standarAnswer = exerciesDTO.getQuestionList().get(i).getStandardAnswer();
			if (gavenAnswer.equals(standarAnswer)) {
				totalScore = totalScore.add(scoreOfSubQuestion);
			} else {
				r.getWrongNumberList().add(dto.getAnswerList().get(i).getQuestionNumber());
				r.getAnswerMap().put(dto.getAnswerList().get(i).getQuestionNumber(), standarAnswer);
			}
		}

		r.setTotalScore(totalScore);
		r.setIsSuccess();
		return r;
	}

	private boolean hasMaxScoreToday(Long userId, GradeType gradeType, ExerciesSubjectType subjectType) {
		StudentExerciesHistoryExample example = new StudentExerciesHistoryExample();
		example.createCriteria().andUserIdEqualTo(userId)
				.andCreateTimeBetween(LocalDateTime.now().with(LocalTime.MIN), LocalDateTime.now().with(LocalTime.MAX))
				.andGradeTypeEqualTo(gradeType.getCode().longValue()).andScoreEqualTo(optionService.getMaxScore())
				.andSubjectTypeEqualTo(subjectType.getCode().longValue()).andPointsEqualTo(optionService.getRandomPointMax());
		List<StudentExerciesHistory> poList = exerciesHistoryMapper.selectByExample(example);
		return poList != null && !poList.isEmpty();
	}
}