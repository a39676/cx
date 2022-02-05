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
import demo.toyParts.educate.service.ExerciesServiceMathG2_1;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

@Service
public class ExerciesServiceMathG2_1Impl extends ExerciesMathCommonService implements ExerciesServiceMathG2_1 {

	private static final Integer MAX_CALCULATE = 100;
	private static final Integer MAX_ADDITION_NUM = 50;
	private static final Integer MIN_ADDITION_NUM = 11;
	private static final Integer MAX_MULTIPLICATION_NUM = 9;
	private static final Integer MIN_MULTIPLICATION_NUM = 3;
	private static final GradeType GRADE_TYPE = GradeType.GRADE_2_1;
	
	@Override
	public ModelAndView getExercies() {
		return buildExerciesView(buildExercies());
	}

	@Override
	public ExerciesBuildResult buildExercies() {
		Long userId = baseUtilCustom.getUserId();
		MathExerciesDTO exerciesDTO = null;

		if (userId != null) {
			exerciesDTO = reloadExercies(GRADE_TYPE, userId);
			if (exerciesDTO != null) {
				return buildExercies(exerciesDTO);
			}
		}
		exerciesDTO = exerciesGenerator();
		return buildExercies(exerciesDTO, true);
	}
	
	private MathExerciesDTO exerciesGenerator() {
		MathExerciesDTO exerciesDTO = new MathExerciesDTO(); 
		exerciesDTO.setExerciesID(snowFlake.getNextId());
		exerciesDTO.setUserId(baseUtilCustom.getUserId());
		exerciesDTO.setSubjectType(SUBJECT_TYPE);
		exerciesDTO.setQuestionList(new ArrayList<>());
		exerciesDTO.setGradeType(GRADE_TYPE);
		
		MathQuestionBaseDTO question = null;
		for(int questionNumber = 1; questionNumber <= optionService.getQuestionListSize(); questionNumber++) {
			question = null;
			Integer standardAnswer = -1;
			while(standardAnswer < 0 || standardAnswer > MAX_CALCULATE) {
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
		
		int num1 = t.nextInt(MIN_MULTIPLICATION_NUM, MAX_MULTIPLICATION_NUM + 1);
		int num2 = t.nextInt(MIN_MULTIPLICATION_NUM, MAX_MULTIPLICATION_NUM + 1);
		int num3 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);
		
		MathBaseSymbolType multiplicationSymbol = MathBaseSymbolType.multiplication;
		MathBaseSymbolType mathSymbolType1 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition, MathBaseSymbolType.subtraction);
		
		String exp1 = String.valueOf(num1 + multiplicationSymbol.getCodeSymbol() + num2);
		
		String exp2 = String.valueOf(exp1 + mathSymbolType1.getCodeSymbol() + num3);
		Expression expression2 = new ExpressionBuilder(exp2).build();
		Double result2 = expression2.evaluate();
		
		if(result2 < 0) {
			exp2 = String.valueOf(num3 + mathSymbolType1.getCodeSymbol() + exp1);
		}
		
		expression2 = new ExpressionBuilder(exp2).build();
		result2 = expression2.evaluate();

		exp2 = replaceCodingSymbolToMathSymbol(exp2);
		q.setExpression(exp2);
		q.getStandardAnswer().clear();
		q.getStandardAnswer().add(String.valueOf(result2.intValue()));
		return q;
	}
	

}
