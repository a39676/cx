package demo.tool.educate.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.tool.educate.pojo.dto.ExerciseAnswerDTO;
import demo.tool.educate.pojo.dto.MathExerciseDTO;
import demo.tool.educate.pojo.po.StudentDetail;
import demo.tool.educate.pojo.po.StudentExerciseHistory;
import demo.tool.educate.pojo.po.StudentExerciseHistoryExample;
import demo.tool.educate.pojo.po.StudentPointHistory;
import demo.tool.educate.pojo.result.ExerciseAnswerMatchResult;
import demo.tool.educate.pojo.type.ExerciseSubjectType;
import demo.tool.educate.pojo.type.GradeType;
import demo.tool.educate.pojo.type.MatchGradeType;
import demo.tool.educate.service.EducateCommonService;
import demo.tool.educate.service.ExerciseAnswerService;
import demo.tool.textMessageForward.telegram.service.TelegramService;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.type.TelegramBotType;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class ExerciseAnswerServiceImpl extends EducateCommonService implements ExerciseAnswerService {

	@Autowired
	private FileUtilCustom ioUtil;
	@Autowired
	private TelegramService telegramService;

	@Override
	public ExerciseAnswerMatchResult answerSubmit(ExerciseAnswerDTO dto) {
		ExerciseAnswerMatchResult answerResult = new ExerciseAnswerMatchResult();
		String privateKey = URLDecoder.decode(dto.getPk(), StandardCharsets.UTF_8);
		Long exerciseId = systemConstantService.decryptPrivateKey(privateKey);

		StudentExerciseHistory exercisePO = exerciseHistoryMapper.selectByPrimaryKey(exerciseId);
		if (exercisePO == null) {
			answerResult.setMessage("考卷已过期, 请做另一份, 如有疑问, 请联系管理员.");
			return answerResult;
		}
		if (exercisePO.getCompeletionTime() != null) {
			answerResult.setMessage("已经提交过答案,无法重复提交");
			return answerResult;
		}

		String exerciseJsonStr = ioUtil.getStringFromFile(exercisePO.getFilePath());
		MathExerciseDTO exerciseDTO = new Gson().fromJson(exerciseJsonStr, MathExerciseDTO.class);

		answerResult = matchAnswer(dto, exerciseDTO);

		Long userId = baseUtilCustom.getUserId();
		if (userId == null) {
			exercisePO.setPoints(answerResult.getPoints());
			exercisePO.setCompeletionTime(LocalDateTime.now());
			exercisePO.setScore(answerResult.getTotalScore());
			exerciseHistoryMapper.updateByPrimaryKeySelective(exercisePO);
			long minutes = ChronoUnit.MINUTES.between(exercisePO.getCreateTime(), LocalDateTime.now());
			answerResult.setTimeConsumingMinute(minutes);
			return answerResult;
		}

		StudentDetail detail = studentDetailMapper.selectByPrimaryKey(userId);

		answerResult = handlePointsCalculcate(answerResult, detail, exerciseDTO);

		exercisePO.setPoints(answerResult.getPoints());
		exercisePO.setCompeletionTime(LocalDateTime.now());
		exercisePO.setScore(answerResult.getTotalScore());
		exercisePO.setMatchGradeType(answerResult.getMatchGradeType().getCode());
		exerciseHistoryMapper.updateByPrimaryKeySelective(exercisePO);

		long minutes = ChronoUnit.MINUTES.between(exercisePO.getCreateTime(), LocalDateTime.now());
		answerResult.setTimeConsumingMinute(minutes);

		detail.setPointsSummary(detail.getPointsSummary().add(exercisePO.getPoints()));
		studentDetailMapper.updateByPrimaryKeySelective(detail);

		if (answerResult.getPoints().compareTo(BigDecimal.ZERO) != 0) {
			StudentPointHistory studentPointHistory = new StudentPointHistory();
			studentPointHistory.setId(snowFlake.getNextId());
			studentPointHistory.setPoints(answerResult.getPoints());
			studentPointHistory.setRemark("exercise");
			studentPointHistory.setUserId(userId);
			studentPointHistoryMapper.insertSelective(studentPointHistory);
		}

		String msgStr = "Exercise done, by: " + baseUtilCustom.getCurrentUserName() + ", score: "
				+ answerResult.getTotalScore() + ", minute: " + answerResult.getTimeConsumingMinute();
		GradeType gradeType = GradeType.getType(exercisePO.getGradeType().intValue());
		if (gradeType != null) {
			msgStr += ", Grade: " + gradeType.getName();
		}
		ExerciseSubjectType subjectType = ExerciseSubjectType.getType(exercisePO.getSubjectType().intValue());
		if (subjectType != null) {
			msgStr += ", Subject: " + subjectType.getName();
		}
		telegramService.sendMessageByChatRecordId(TelegramBotType.CX_MESSAGE, msgStr, TelegramStaticChatID.MY_ID);

		return answerResult;
	}

	private ExerciseAnswerMatchResult handlePointsCalculcate(ExerciseAnswerMatchResult answerResult,
			StudentDetail detail, MathExerciseDTO exerciseDTO) {
		LocalDateTime now = LocalDateTime.now();
		if (now.getHour() >= optionService.getDailyMaxHour() || now.getHour() <= optionService.getDailyMinHour()) {
			answerResult.addMessage("晚上 " + optionService.getDailyMaxHour() + "点, 至次日 "
					+ optionService.getDailyMinHour() + "点, 是休息时间, 请注意劳逸结合");
			return answerResult;
		}

		ThreadLocalRandom t = ThreadLocalRandom.current();
		Double randomAwardPoints = 0D;
		BigDecimal inherentPoints = optionService.getGardePointMap().get(exerciseDTO.getGradeType().getCode());
		if (inherentPoints == null) {
			inherentPoints = BigDecimal.ZERO;
		}

		GradeType studentGrade = GradeType.getType(detail.getGradeType().intValue());

		BigDecimal maxScore = optionService.getMaxScore();
		BigDecimal randomMaxAwardCoefficient = optionService.getRandomMaxAwardCoefficient();
		BigDecimal randomMinAwardCoefficient = optionService.getRandomMinAwardCoefficient();
		BigDecimal randomAwardCoefficient = null;

		// 全错 返回0分
		if (answerResult.getQuestionListSize().equals(answerResult.getWrongNumberList().size())) {
			return answerResult;
		}

		// 满分 && 符合自身年级
		if (answerResult.getTotalScore().compareTo(maxScore) == 0 && studentGrade.equals(exerciseDTO.getGradeType())) {
			if (!hasMaxScoreToday(detail.getId(), studentGrade, exerciseDTO.getSubjectType())) {
				randomAwardCoefficient = randomMaxAwardCoefficient;
				randomAwardPoints = randomAwardCoefficient.multiply(inherentPoints).doubleValue();
				BigDecimal total = inherentPoints.add(new BigDecimal(randomAwardPoints)).setScale(2,
						RoundingMode.HALF_UP);
				answerResult.addMessage(
						"今日首次获得本学期的 " + exerciseDTO.getSubjectType().getCnName() + " 满分习题! 获得最高积分: " + (total));
				answerResult.setPoints(total);
				return answerResult;
			}
		}

		// 做以往的学期题目, 不添加随机积分
		if ((studentGrade.getCode() - exerciseDTO.getGradeType().getCode()) > 2) {
			answerResult.setMatchGradeType(MatchGradeType.PAST_GRADE);
			answerResult.addMessage("复习过往知识,巩固良好基础. 但要注意跟进最新课程");
			answerResult.setPoints(inherentPoints);
			return answerResult;
		}

		// 做往后学期的题目, 每学期加10%随机奖励积分上限
		// 做以往学期的题目, 每学期减10%随机奖励积分上限
		if (exerciseDTO.getGradeType().getCode() > studentGrade.getCode()) {
			answerResult.setMatchGradeType(MatchGradeType.FUTURE_GRADE);
		}
		randomMaxAwardCoefficient = randomMaxAwardCoefficient
				.add(new BigDecimal((exerciseDTO.getGradeType().getCode() - studentGrade.getCode()) * 0.1));

		// 错题, 按错题比例扣减总体积分上限 总体积分, 非随机积分
		Double wrongCoefficient = Double.valueOf(answerResult.getWrongNumberList().size())
				/ Double.valueOf(answerResult.getQuestionListSize());
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

	private ExerciseAnswerMatchResult matchAnswer(ExerciseAnswerDTO dto, MathExerciseDTO exerciseDTO) {
		ExerciseAnswerMatchResult r = new ExerciseAnswerMatchResult();

		BigDecimal totalScore = BigDecimal.ZERO;
		BigDecimal maxScore = optionService.getMaxScore();
		BigDecimal scoreOfSubQuestion = maxScore.divide(new BigDecimal(exerciseDTO.getQuestionList().size()),
				RoundingMode.HALF_UP);

		List<String> gavenAnswer = null;
		List<String> standarAnswer = null;

		for (int i = 0; i < exerciseDTO.getQuestionList().size(); i++) {
			gavenAnswer = dto.getAnswerList().get(i).getAnswer();
			standarAnswer = exerciseDTO.getQuestionList().get(i).getStandardAnswer();
			if (gavenAnswer.equals(standarAnswer)) {
				totalScore = totalScore.add(scoreOfSubQuestion);
			} else {
				r.getWrongNumberList().add(dto.getAnswerList().get(i).getQuestionNumber());
				r.getAnswerMap().put(dto.getAnswerList().get(i).getQuestionNumber(), standarAnswer);
			}
		}

		r.setQuestionListSize(exerciseDTO.getQuestionList().size());
		r.setTotalScore(totalScore);
		r.setIsSuccess();
		return r;
	}

	private boolean hasMaxScoreToday(Long userId, GradeType gradeType, ExerciseSubjectType subjectType) {
		StudentExerciseHistoryExample example = new StudentExerciseHistoryExample();
		LocalDateTime now = LocalDateTime.now();
		example.createCriteria().andUserIdEqualTo(userId)
				.andCompeletionTimeBetween(now.with(LocalTime.MIN), now.with(LocalTime.MAX))
				.andGradeTypeEqualTo(gradeType.getCode().longValue()).andScoreEqualTo(optionService.getMaxScore())
				.andSubjectTypeEqualTo(subjectType.getCode().longValue()).andScoreEqualTo(optionService.getMaxScore());
		List<StudentExerciseHistory> poList = exerciseHistoryMapper.selectByExample(example);
		return poList != null && !poList.isEmpty();
	}
}
