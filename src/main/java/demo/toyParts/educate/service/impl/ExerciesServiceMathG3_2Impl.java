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
import demo.toyParts.educate.service.ExerciesServiceMathG3_2;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

@Service
public class ExerciesServiceMathG3_2Impl extends ExerciesMathCommonService implements ExerciesServiceMathG3_2 {

	private static final Integer MAX_CALCULATE = 999;
	private static final Integer MAX_NUM = 99;
	private static final Integer MIN_NUM = 11;
	private static final GradeType GRADE_TYPE = GradeType.GRADE_3_2;
	
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
		
		int num1 = t.nextInt(MIN_NUM, MAX_NUM + 1);
		int num2 = t.nextInt(MIN_NUM, MAX_NUM + 1);
		int num3 = t.nextInt(MIN_NUM, MAX_NUM + 1);
		
		MathBaseSymbolType mathSymbolType1 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition, MathBaseSymbolType.multiplication);
		MathBaseSymbolType mathSymbolType2 = null;
		if(MathBaseSymbolType.multiplication.equals(mathSymbolType1)){
			mathSymbolType2 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition, MathBaseSymbolType.subtraction);
		} else {
			mathSymbolType2 = MathBaseSymbolType.multiplication;
		}
		
		boolean hasBrackets = (0 == t.nextInt(0, 1 + 1));
		String exp1 = null;
		if(hasBrackets) {
			exp1 = String.valueOf("(" + num1 + mathSymbolType1.getCodeSymbol() + num2 + ")");
		} else {
			exp1 = String.valueOf(num1 + mathSymbolType1.getCodeSymbol() + num2);
		}
		
		Expression expression1 = new ExpressionBuilder(exp1).build();
		Double result1 = expression1.evaluate();
		
		if(result1 < 0) {
			if(hasBrackets) {
				exp1 = String.valueOf("(" + num2 + mathSymbolType1.getCodeSymbol() + num1 + ")");
			} else {
				exp1 = String.valueOf(num2 + mathSymbolType1.getCodeSymbol() + num1);
			}
		}
		
		boolean editFromLeft = (0 == t.nextInt(0, 1 + 1));
		String exp2 = null;
		if(editFromLeft) {
			exp2 = String.valueOf(num3 + mathSymbolType2.getCodeSymbol() + exp1);
		} else {
			exp2 = String.valueOf(exp1 + mathSymbolType2.getCodeSymbol() + num3);
		}
		
		Expression expression2 = new ExpressionBuilder(exp2).build();
		Double result2 = expression2.evaluate();
		
		if(result2 < 0) {
			editFromLeft = !editFromLeft;
			if(editFromLeft) {
				exp2 = String.valueOf(num3 + mathSymbolType2.getCodeSymbol() + exp1);
			} else {
				exp2 = String.valueOf(exp1 + mathSymbolType2.getCodeSymbol() + num3);
			}
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
