package demo.toyParts.educate.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import demo.toyParts.educate.pojo.type.MatchGradeType;
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
		if (exerciesPO == null) {
			answerResult.setMessage("考卷已过期, 请做另一份, 如有疑问, 请联系管理员.");
			return answerResult;
		}
		if (exerciesPO.getCompeletionTime() != null) {
			answerResult.setMessage("已经提交过答案,无法重复提交");
			return answerResult;
		}

		String exerciesJsonStr = ioUtil.getStringFromFile(exerciesPO.getFilePath());
		MathExerciesDTO exerciesDTO = new Gson().fromJson(exerciesJsonStr, MathExerciesDTO.class);

		answerResult = matchAnswer(dto, exerciesDTO);

		Long userId = baseUtilCustom.getUserId();
		if (userId == null) {
			exerciesPO.setPoints(answerResult.getPoints());
			exerciesPO.setCompeletionTime(LocalDateTime.now());
			exerciesPO.setScore(answerResult.getTotalScore());
			exerciesHistoryMapper.updateByPrimaryKeySelective(exerciesPO);
			return answerResult;
		}

		StudentDetail detail = studentDetailMapper.selectByPrimaryKey(userId);

		answerResult = handlePointsCalculcate(answerResult, detail, exerciesDTO);

		exerciesPO.setPoints(answerResult.getPoints());
		exerciesPO.setCompeletionTime(LocalDateTime.now());
		exerciesPO.setScore(answerResult.getTotalScore());
		exerciesPO.setMatchGradeType(answerResult.getMatchGradeType().getCode());
		exerciesHistoryMapper.updateByPrimaryKeySelective(exerciesPO);

		detail.setPointsSummary(detail.getPointsSummary().add(exerciesPO.getPoints()));
		studentDetailMapper.updateByPrimaryKeySelective(detail);

		return answerResult;
	}

	private ExerciesAnswerMatchResult handlePointsCalculcate(ExerciesAnswerMatchResult answerResult,
			StudentDetail detail, MathExerciesDTO exerciesDTO) {
		LocalDateTime now = LocalDateTime.now();
		if (now.getHour() >= optionService.getDailyMaxHour() || now.getHour() <= optionService.getDailyMinHour()) {
			answerResult.addMessage("晚上 " + optionService.getDailyMaxHour() + "点, 至次日 "
					+ optionService.getDailyMinHour() + "点, 是休息时间, 请注意劳逸结合");
			return answerResult;
		}

		ThreadLocalRandom t = ThreadLocalRandom.current();
		Double randomAwardPoints = 0D;
		BigDecimal inherentPoints = optionService.getGardePointMap().get(exerciesDTO.getGradeType().getCode());

		GradeType studentGrade = GradeType.getType(detail.getGradeType().intValue());

		BigDecimal maxScore = optionService.getMaxScore();
		BigDecimal randomMaxAwardCoefficient = optionService.getRandomMaxAwardCoefficient();
		BigDecimal randomMinAwardCoefficient = optionService.getRandomMinAwardCoefficient();
		BigDecimal randomAwardCoefficient = null;

		// 全错 返回0分
		if (optionService.getQuestionListSize().equals(answerResult.getWrongNumberList().size())) {
			return answerResult;
		}

		// 满分 && 符合自身年级
		if (answerResult.getTotalScore().compareTo(maxScore) == 0 && studentGrade.equals(exerciesDTO.getGradeType())) {
			if (!hasMaxScoreToday(detail.getId(), studentGrade, exerciesDTO.getSubjectType())) {
				randomAwardCoefficient = randomMaxAwardCoefficient;
				randomAwardPoints = randomAwardCoefficient.multiply(inherentPoints).doubleValue();
				BigDecimal total = inherentPoints.add(new BigDecimal(randomAwardPoints));
				answerResult.addMessage("今日首次获得本学期的 " + exerciesDTO.getSubjectType().getCnName() + " 满分习题! 获得最高积分: "
						+ (total));
				answerResult.setPoints(total);
				return answerResult;
			}
		}

		// 做以往的学期题目, 不添加随机积分
		if ((studentGrade.getCode() - exerciesDTO.getGradeType().getCode()) > 2) {
			answerResult.setMatchGradeType(MatchGradeType.PAST_GRADE);
			answerResult.addMessage("复习过往知识,巩固良好基础. 但要注意跟进最新课程");
			answerResult.setPoints(inherentPoints);
			return answerResult;
		}

		// 做往后学期的题目, 每学期加10%随机奖励积分上限
		// 做以往学期的题目, 每学期减10%随机奖励积分上限
		if (exerciesDTO.getGradeType().getCode() > studentGrade.getCode()) {
			answerResult.setMatchGradeType(MatchGradeType.FUTURE_GRADE);
		}
		randomMaxAwardCoefficient = randomMaxAwardCoefficient
				.add(new BigDecimal((exerciesDTO.getGradeType().getCode() - studentGrade.getCode()) * 0.1));

		// 错题, 按错题比例扣减总体积分上限 总体积分, 非随机积分
		Double wrongCoefficient = Double.valueOf(answerResult.getWrongNumberList().size())
				/ Double.valueOf(optionService.getQuestionListSize());
		// 错题率超 40% 不奖励积分
		if (wrongCoefficient <= 0.4) {
			randomAwardCoefficient = new BigDecimal(
					t.nextDouble(randomMinAwardCoefficient.doubleValue(), randomMaxAwardCoefficient.doubleValue()));
			randomAwardPoints = randomAwardCoefficient.multiply(inherentPoints)
					.multiply(new BigDecimal(1 - wrongCoefficient)).doubleValue();
		}
		Double totalPoint = inherentPoints.doubleValue() * (1 - wrongCoefficient) + randomAwardPoints;
		answerResult.setPoints(new BigDecimal(totalPoint).setScale(2, RoundingMode.HALF_UP));

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
		LocalDateTime now = LocalDateTime.now();
		example.createCriteria().andUserIdEqualTo(userId)
				.andCompeletionTimeBetween(now.with(LocalTime.MIN), now.with(LocalTime.MAX))
				.andGradeTypeEqualTo(gradeType.getCode().longValue()).andScoreEqualTo(optionService.getMaxScore())
				.andSubjectTypeEqualTo(subjectType.getCode().longValue()).andScoreEqualTo(optionService.getMaxScore());
		List<StudentExerciesHistory> poList = exerciesHistoryMapper.selectByExample(example);
		return poList != null && !poList.isEmpty();
	}
}
