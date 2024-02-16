package demo.tool.educate.math.service.impl;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.tool.educate.math.service.ExerciseMathCommonService;
import demo.tool.educate.math.service.ExerciseServiceMathG2_1;
import demo.tool.educate.pojo.dto.MathExerciseDTO;
import demo.tool.educate.pojo.dto.MathQuestionBaseDTO;
import demo.tool.educate.pojo.result.ExerciseBuildResult;
import demo.tool.educate.pojo.type.GradeType;
import demo.tool.educate.pojo.type.MathBaseSymbolType;
import demo.tool.educate.pojo.type.MathQuestionType;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

@Service
public class ExerciseServiceMathG2_1Impl extends ExerciseMathCommonService implements ExerciseServiceMathG2_1 {

	private static final Integer MAX_ADDITION_NUM = 50;
	private static final Integer MIN_ADDITION_NUM = 11;
	private static final Integer MAX_MULTIPLICATION_NUM = 9;
	private static final Integer MIN_MULTIPLICATION_NUM = 3;
	private static final Integer MIN_RESULT = 0;
	private static final Integer MAX_RESULT = 100;
	private static final GradeType GRADE_TYPE = GradeType.GRADE_2_1;
	
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
		for(int questionNumber = 1; questionNumber <= optionService.getCalculateQuestionListSize(); questionNumber++) {
			question = null;
			Integer standardAnswer = -1;
			while(standardAnswer < MIN_RESULT || standardAnswer > MAX_RESULT) {
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
		
		int multiplication_1 = t.nextInt(MIN_MULTIPLICATION_NUM, MAX_MULTIPLICATION_NUM + 1);
		int multiplication_2 = t.nextInt(MIN_MULTIPLICATION_NUM, MAX_MULTIPLICATION_NUM + 1);
		int num3 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);
		
		MathBaseSymbolType multiplicationSymbol = MathBaseSymbolType.multiplication;
		MathBaseSymbolType mathSymbolType1 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition, MathBaseSymbolType.subtraction);
		
		String exp1 = String.valueOf(multiplication_1 + multiplicationSymbol.getCodeSymbol() + multiplication_2);
		
		String exp2 = String.valueOf(exp1 + mathSymbolType1.getCodeSymbol() + num3);
		Expression expression2 = new ExpressionBuilder(exp2).build();
		Double result2 = expression2.evaluate();
		
		if(result2 < MIN_RESULT) {
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
