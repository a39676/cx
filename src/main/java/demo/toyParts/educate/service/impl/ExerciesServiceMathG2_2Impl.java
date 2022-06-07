package demo.toyParts.educate.service.impl;

import java.math.BigDecimal;
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
import demo.toyParts.educate.service.ExerciesServiceMathG2_2;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

@Service
public class ExerciesServiceMathG2_2Impl extends ExerciesMathCommonService implements ExerciesServiceMathG2_2 {

	private static final Integer MAX_ADDITION_NUM = 99;
	private static final Integer MIN_ADDITION_NUM = 0;
	private static final Integer MIN_RESULT = 0;
	private static final Integer MAX_RESULT = 10000;
	private static final GradeType GRADE_TYPE = GradeType.GRADE_2_2;
	
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
			question = createQuestion();
			question.setQuestionNumber(questionNumber);
			exerciesDTO.getQuestionList().add(question);
		}
		
		return exerciesDTO;
	}
	
	private MathQuestionBaseDTO createQuestion() {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		ThreadLocalRandom t = ThreadLocalRandom.current();
		
		MathBaseSymbolType mathSymbolType1 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition, MathBaseSymbolType.division);
		String exp1 = null;
		Expression expression1 = null;
		Double result1 = -1D;
		MathQuestionBaseDTO tmpQuestionDTO = null;
		
		if(MathBaseSymbolType.division.equals(mathSymbolType1)) {
			tmpQuestionDTO = createBaseSimpleDivision();
			exp1 = tmpQuestionDTO.getExpression();
			expression1 = new ExpressionBuilder(exp1).build();
			result1 = expression1.evaluate();
		} else {
			while(result1 < MIN_RESULT || result1 > MAX_RESULT) {
				tmpQuestionDTO = createSimpleEquation(mathSymbolType1);
				exp1 = tmpQuestionDTO.getExpression();
				expression1 = new ExpressionBuilder(exp1).build();
				result1 = expression1.evaluate();
			}
		}
		
		MathBaseSymbolType mathSymbolType2 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition, MathBaseSymbolType.division);
//		if(MathBaseSymbolType.division.equals(mathSymbolType2)) {
//			System.out.println("///////////");
//		}
//		MathBaseSymbolType mathSymbolType2 = MathBaseSymbolType.division;
	
		String resultExpStr = null;
		Expression resultExpression = null;
		Double finalResult = null;
		Integer finalResult2 = null;
		
		
		if(MathBaseSymbolType.division.equals(mathSymbolType2)) {
			if(MathBaseSymbolType.addition.equals(mathSymbolType1) || MathBaseSymbolType.subtraction.equals(mathSymbolType1)) {
				exp1 = "(" + exp1 + ")";
			}
			
			int num3 = 1;
			if(result1 == 0) {
				num3 = t.nextInt(MIN_ADDITION_NUM + 1, MAX_ADDITION_NUM + 1);
			} else if(result1 > 0 && result1 < 6) {
				num3 = 1;
			} else {
				num3 = t.nextInt(MIN_ADDITION_NUM + 1, result1.intValue() / 2);
			}
			resultExpStr = String.valueOf(exp1 + MathBaseSymbolType.division.getCodeSymbol() + num3);
			resultExpression = new ExpressionBuilder(resultExpStr).build();
			finalResult = resultExpression.evaluate();
			if(finalResult % 1 != 0) {
				finalResult = finalResult.intValue() + 0D;
				finalResult2 = new BigDecimal(result1).subtract(new BigDecimal(finalResult * num3)).intValue();
			}
		} else {
			boolean numOrEquation = t.nextInt(0, 2) > 0;
			if(numOrEquation) {
				int num3 = 0;
				if(MathBaseSymbolType.subtraction.equals(mathSymbolType2)) {
					num3 = t.nextInt(MIN_ADDITION_NUM, result1.intValue() + 1);						
				} else {
					num3 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);
				}
				
				resultExpStr = String.valueOf(exp1 + mathSymbolType2.getCodeSymbol() + num3);
				resultExpression = new ExpressionBuilder(resultExpStr).build();
				finalResult = resultExpression.evaluate();
				while(finalResult < MIN_RESULT || finalResult > MAX_RESULT) {
					num3 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);
					resultExpStr = String.valueOf(exp1 + mathSymbolType2.getCodeSymbol() + num3);
					resultExpression = new ExpressionBuilder(resultExpStr).build();
					finalResult = resultExpression.evaluate();
				}
			} else {
				MathBaseSymbolType mathSymbolType3 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition, MathBaseSymbolType.multiplication);
				boolean brackets = (MathBaseSymbolType.addition.equals(mathSymbolType3) || MathBaseSymbolType.subtraction.equals(mathSymbolType3));
				
				tmpQuestionDTO = createSimpleEquation(mathSymbolType3);
				String exp3 = tmpQuestionDTO.getExpression();
				Expression expression3 = new ExpressionBuilder(exp3).build();
				Double result3 = expression3.evaluate();
				
				if(brackets) {
					exp3 = "(" + exp3 + ")";
				}
				
				resultExpStr = exp1 + mathSymbolType2.getCodeSymbol() + exp3;
				resultExpression = new ExpressionBuilder(resultExpStr).build();
				finalResult = resultExpression.evaluate();
				
				while(finalResult < MIN_RESULT || finalResult > MAX_RESULT) {
					if(MathBaseSymbolType.subtraction.equals(mathSymbolType2) && result1 <  MAX_ADDITION_NUM / 2) {
						mathSymbolType2 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition, MathBaseSymbolType.multiplication);
					}
					
					tmpQuestionDTO = createSimpleEquation(mathSymbolType3);
					exp3 = tmpQuestionDTO.getExpression();
					expression3 = new ExpressionBuilder(exp3).build();
					result3 = expression3.evaluate();
					
					if(brackets) {
						exp3 = "(" + exp3 + ")";
					}
					resultExpStr = exp1 + mathSymbolType2.getCodeSymbol() + exp3;
					resultExpression = new ExpressionBuilder(resultExpStr).build();
					finalResult = resultExpression.evaluate();
				}
				
				if(MathBaseSymbolType.division.equals(mathSymbolType2)) {
					if(finalResult % 1 != 0) {
						finalResult2 = new BigDecimal(result1).subtract(new BigDecimal(result3).multiply(new BigDecimal(finalResult))).intValue();
					}
				}
			}
		}
		
		resultExpStr = replaceCodingSymbolToMathSymbol(resultExpStr);
		q.setExpression(resultExpStr);
		q.getStandardAnswer().clear();
		q.getStandardAnswer().add(String.valueOf(finalResult.intValue()));
		if(finalResult2 != null) {
			q.getStandardAnswer().add(String.valueOf(finalResult2));
		}
		return q;
	}
	
	private MathQuestionBaseDTO createBaseSimpleDivision() {
		MathQuestionBaseDTO dto = new MathQuestionBaseDTO();
		ThreadLocalRandom t = ThreadLocalRandom.current();
		
		int multiplication_1 = t.nextInt(1, 9 + 1);
		int multiplication_2 = t.nextInt(1, 9 + 1);
		String exp = String.valueOf(multiplication_1 + MathBaseSymbolType.multiplication.getCodeSymbol() + multiplication_2);
		Expression expression = new ExpressionBuilder(exp).build();
		Double result = expression.evaluate();
		
		exp = String.valueOf(result.intValue() + MathBaseSymbolType.division.getCodeSymbol() + multiplication_2);
		dto.setExpression(exp);
		dto.getStandardAnswer().add(String.valueOf(multiplication_1));
		return dto;
	}

	/** NOT for division!!! */
	private MathQuestionBaseDTO createSimpleEquation(MathBaseSymbolType symbolType) {
		MathQuestionBaseDTO dto = new MathQuestionBaseDTO();
		ThreadLocalRandom t = ThreadLocalRandom.current();
		int num1 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);
		int num2 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);
		String exp = String.valueOf(num1 + symbolType.getCodeSymbol() + num2);
		Expression expression = new ExpressionBuilder(exp).build();
		Double result = expression.evaluate();
		while(result < MIN_RESULT || result > MAX_RESULT) {
			num1 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);
			num2 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);
			exp = String.valueOf(num1 + symbolType.getCodeSymbol() + num2);
			expression = new ExpressionBuilder(exp).build();
			result = expression.evaluate();
		}
		
		dto.setExpression(exp);
		dto.getStandardAnswer().add(String.valueOf(result));
		return dto;
	}
}
