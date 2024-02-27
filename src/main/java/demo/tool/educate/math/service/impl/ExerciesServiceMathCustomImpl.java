package demo.tool.educate.math.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.tool.educate.math.service.ExerciesServiceMathCustom;
import demo.tool.educate.math.service.ExerciseMathCommonService;
import demo.tool.educate.pojo.dto.MathExerciseDTO;
import demo.tool.educate.pojo.dto.MathQuestionBaseDTO;
import demo.tool.educate.pojo.result.ExerciseBuildResult;
import demo.tool.educate.pojo.type.GradeType;
import demo.tool.educate.pojo.type.MathQuestionType;

@Service
public class ExerciesServiceMathCustomImpl extends ExerciseMathCommonService implements ExerciesServiceMathCustom {
	
	private static final GradeType GRADE_TYPE = GradeType.GRADE_CUSTOM;

	@Override
	public ModelAndView getExercise() {
		return buildExerciseView(buildExercise());
	}

	@Override
	public ExerciseBuildResult buildExercise() {
		MathExerciseDTO exerciseDTO = reloadExercise(GRADE_TYPE);

		boolean newExerciseFlag = (exerciseDTO == null);
		if (newExerciseFlag) {
			exerciseDTO = exerciseGenerator();
		}
		return buildExercise(exerciseDTO, newExerciseFlag);
	}

	private MathExerciseDTO exerciseGenerator() {
		MathExerciseDTO exerciseDTO = new MathExerciseDTO();
		exerciseDTO.setExerciseID(snowFlake.getNextId());
		exerciseDTO.setUserId(baseUtilCustom.getUserId());
		exerciseDTO.setSubjectType(SUBJECT_TYPE);
		exerciseDTO.setQuestionList(new ArrayList<>());
		exerciseDTO.setGradeType(GRADE_TYPE);
		
		MathQuestionBaseDTO question = null;
		int questionNumber = 1;
//		int calculateQuestionSize = 10;
//		for (; questionNumber <= calculateQuestionSize; questionNumber++) {
//			question = createCalculateQuestion();
//			question.setQuestionNumber(questionNumber);
//			exerciseDTO.getQuestionList().add(question);
//		}

		question = findGreastestCommonDivisor();
		question.setQuestionNumber(questionNumber);
		exerciseDTO.getQuestionList().add(question);
		questionNumber++;
		
		question = findLowestCommonMultiple();
		question.setQuestionNumber(questionNumber);
		exerciseDTO.getQuestionList().add(question);
		questionNumber++;

		return exerciseDTO;
	}

	@Override
	public MathQuestionBaseDTO findGreastestCommonDivisor() {
		String moduleStr = "求以下數字的最大公約數, %d, %d, %d";

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

		ThreadLocalRandom t = ThreadLocalRandom.current();

		int randomCommon = t.nextInt(30, 98);
		while (PRIME_NUMBERS_UNDER_100.contains(randomCommon)) {
			randomCommon = t.nextInt(30, 98);
		}
		List<Integer> subPrimeNumberList = PRIME_NUMBERS_UNDER_100.subList(0, 6);
		int intA = randomCommon;
		int intB = randomCommon;
		int intC = randomCommon;
		int randomPrimeNumberIndex = 0;
		int minInt = 3000;
		int maxInt = 10000;
		while (intA < maxInt) {
			randomPrimeNumberIndex = t.nextInt(0, 5);
			intA = intA * subPrimeNumberList.get(randomPrimeNumberIndex);
			if (intA > minInt) {
				break;
			}
		}
		while (intB < maxInt) {
			randomPrimeNumberIndex = t.nextInt(0, 5);
			intB = intB * subPrimeNumberList.get(randomPrimeNumberIndex);
			if (intB > minInt) {
				break;
			}
		}
		while (intC < maxInt) {
			randomPrimeNumberIndex = t.nextInt(0, 5);
			intC = intC * subPrimeNumberList.get(randomPrimeNumberIndex);
			if (intC > minInt) {
				break;
			}
		}

		Integer greastestCommonDivisor = findGreastestCommonDivisorUnder10000(Arrays.asList(intA, intB, intC));

		q.setExpression(String.format(moduleStr, intA, intB, intC));

		q.addStandardAnswer(String.valueOf(greastestCommonDivisor));

		return q;
	}

	@Override
	public MathQuestionBaseDTO findLowestCommonMultiple() {
		String moduleStr = "求以下數字的最小公倍數, %d, %d, %d";

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

		ThreadLocalRandom t = ThreadLocalRandom.current();

		List<Integer> subPrimeNumberList = PRIME_NUMBERS_UNDER_100.subList(0, 6);

		int intA = 1;
		int intB = 1;
		int intC = 1;
		for (int i = 0; i < 2; i++) {
			intA = intA * subPrimeNumberList.get(t.nextInt(0, 6));
		}
		for (int i = 0; i < 2; i++) {
			intB = intB * subPrimeNumberList.get(t.nextInt(0, 6));
		}
		for (int i = 0; i < 2; i++) {
			intC = intC * subPrimeNumberList.get(t.nextInt(0, 6));
		}

		Integer lowestCommonMultiple = findLowestCommonMultiple(Arrays.asList(intA, intB, intC));

		q.setExpression(String.format(moduleStr, intA, intB, intC));

		q.addStandardAnswer(String.valueOf(lowestCommonMultiple));

		return q;
	}
}
