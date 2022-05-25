package demo.toyParts.educate.service.impl;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.toyParts.educate.pojo.dto.MathExerciesDTO;
import demo.toyParts.educate.pojo.dto.MathQuestionBaseDTO;
import demo.toyParts.educate.pojo.result.ExerciesBuildResult;
import demo.toyParts.educate.pojo.type.GradeType;
import demo.toyParts.educate.pojo.type.MathBaseSymbolType;
import demo.toyParts.educate.service.ExerciesMathCommonService;
import demo.toyParts.educate.service.ExerciesServiceMathG1_2;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

@Service
public class ExerciesServiceMathG1_2Impl extends ExerciesMathCommonService implements ExerciesServiceMathG1_2 {

	private static final Integer MAX_NUM = 100;
	private static final Integer MIN_NUM = 0;
	private static final Integer MAX_RESULT = 100;
	private static final GradeType GRADE_TYPE = GradeType.GRADE_1_2;

	@Override
	public ModelAndView getExercies() {
		return buildExerciesView(buildExercies());
	}

	@Override
	public ExerciesBuildResult buildExercies() {
		MathExerciesDTO exerciesDTO = reloadExercies(GRADE_TYPE);

		boolean newExerciesFlag = (exerciesDTO == null);
		if (newExerciesFlag) {
			exerciesDTO = exerciesGenerator();
		}
		return buildExercies(exerciesDTO, newExerciesFlag);
	}
	
	private MathExerciesDTO exerciesGenerator() {
		MathExerciesDTO exerciesDTO = new MathExerciesDTO();
		exerciesDTO.setExerciesID(snowFlake.getNextId());
		exerciesDTO.setUserId(baseUtilCustom.getUserId());
		exerciesDTO.setSubjectType(SUBJECT_TYPE);
		exerciesDTO.setQuestionList(new ArrayList<>());
		exerciesDTO.setGradeType(GRADE_TYPE);

		MathQuestionBaseDTO question = null;
		for (int questionNumber = 1; questionNumber <= optionService.getQuestionListSize(); questionNumber++) {
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
			exerciesDTO.getQuestionList().add(question);
		}

		return exerciesDTO;
	}

	private MathQuestionBaseDTO createQuestion() {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
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
