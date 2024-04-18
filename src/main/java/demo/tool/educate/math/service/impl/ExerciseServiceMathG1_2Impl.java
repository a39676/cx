package demo.tool.educate.math.service.impl;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.tool.educate.math.service.ExerciseMathCommonService;
import demo.tool.educate.math.service.ExerciseServiceMathG1_2;
import demo.tool.educate.pojo.dto.MathExerciseDTO;
import demo.tool.educate.pojo.dto.MathQuestionBaseDTO;
import demo.tool.educate.pojo.result.ExerciseBuildResult;
import demo.tool.educate.pojo.type.GradeType;
import demo.tool.educate.pojo.type.MathBaseSymbolType;
import demo.tool.educate.pojo.type.MathQuestionType;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

@Service
public class ExerciseServiceMathG1_2Impl extends ExerciseMathCommonService implements ExerciseServiceMathG1_2 {

	private static final Integer MAX_NUM = 100;
	private static final Integer MIN_NUM = 0;
	private static final Integer MAX_RESULT = 100;
	private static final GradeType GRADE_TYPE = GradeType.GRADE_1_2;

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
		for (int questionNumber = 1; questionNumber <= optionService.getCalculateQuestionListSize(); questionNumber++) {
			question = null;
			Integer standardAnswer = -1;
			while (standardAnswer < 0) {
				question = createQuestion();
				try {
					standardAnswer = Integer.parseInt(question.getStandardAnswer().get(0));
				} catch (Exception e) {
				}
			}
			question.setQuestionNumber(questionNumber);
			exerciseDTO.getQuestionList().add(question);
		}

		return exerciseDTO;
	}

	private MathQuestionBaseDTO createQuestion() {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.CALCULATE);
		ThreadLocalRandom t = ThreadLocalRandom.current();

		int num1 = t.nextInt(MIN_NUM, MAX_NUM + 1);
		int num2 = t.nextInt(MIN_NUM, MAX_NUM + 1);
		MathBaseSymbolType mathSymbolType1 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition,
				MathBaseSymbolType.subtraction);

		String exp1 = String.valueOf(num1 + mathSymbolType1.getCodeSymbol() + num2);
		Expression expression1 = new ExpressionBuilder(exp1).build();
		Double result1 = expression1.evaluate();

		while (result1 < MIN_NUM || result1 > MAX_RESULT) {
			num1 = t.nextInt(MIN_NUM, MAX_NUM + 1);
			num2 = t.nextInt(MIN_NUM, MAX_NUM + 1);
			exp1 = String.valueOf(num1 + mathSymbolType1.getCodeSymbol() + num2);
			expression1 = new ExpressionBuilder(exp1).build();
			result1 = expression1.evaluate();
		}

		boolean threeNum = (0 == t.nextInt(0, 1 + 1));
		if (!threeNum) {
			q.setExpression(exp1);
			q.getStandardAnswer().clear();
			q.getStandardAnswer().add(String.valueOf(result1.intValue()));
			return q;
		}

		int num3 = t.nextInt(MIN_NUM, MAX_NUM + 1);
		MathBaseSymbolType mathSymbolType2 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition,
				MathBaseSymbolType.subtraction);

		String exp2 = String.valueOf(exp1 + mathSymbolType2.getCodeSymbol() + num3);
		Expression expression2 = new ExpressionBuilder(exp2).build();
		Double result2 = expression2.evaluate();
		while (result2 < MIN_NUM || result2 > MAX_RESULT) {
			num3 = t.nextInt(MIN_NUM, MAX_NUM + 1);
			exp2 = String.valueOf(exp1 + mathSymbolType2.getCodeSymbol() + num3);
			expression2 = new ExpressionBuilder(exp2).build();
			result2 = expression2.evaluate();
		}

		exp2 = replaceCodingSymbolToMathSymbol(exp2);
		q.setExpression(exp2);
		q.getStandardAnswer().clear();
		q.getStandardAnswer().add(String.valueOf(result2.intValue()));
		return q;
	}

}
