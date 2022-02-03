package demo.toyParts.educate.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.toyParts.educate.pojo.dto.MathExerciesDTO;
import demo.toyParts.educate.pojo.dto.MathQuestionBaseDTO;
import demo.toyParts.educate.pojo.po.StudentExerciesHistory;
import demo.toyParts.educate.pojo.result.ExerciesBuildResult;
import demo.toyParts.educate.pojo.result.ExerciesFileSaveResult;
import demo.toyParts.educate.pojo.type.GradeType;
import demo.toyParts.educate.pojo.type.MathBaseSymbolType;
import demo.toyParts.educate.service.ExerciesMathCommonService;
import demo.toyParts.educate.service.ExerciesServiceMathG2_1;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

@Service
public class ExerciesServiceMathG2_1Impl extends ExerciesMathCommonService implements ExerciesServiceMathG2_1 {

	private static final Integer MAX_CALCULATE = 999;
	private static final Integer MAX_NUM = 99;
	private static final Integer MIN_NUM = 11;
	private static final GradeType gradeType = GradeType.grade2_1;
	
	@Override
	public ModelAndView getExercies() {
		ModelAndView v = new ModelAndView("toyJSP/educateJSP/Exercies");
		
		v.addObject("exercies", buildExercies());
		
		return v;
	}

	@Override
	public ExerciesBuildResult buildExercies() {
		Long userId = baseUtilCustom.getUserId();
		ExerciesBuildResult r = new ExerciesBuildResult();
		MathExerciesDTO exerciesDTO = null;
		if(userId != null) {
			exerciesDTO = reloadExercies(gradeType, userId);
			if(exerciesDTO != null) {
				try {
					r.setExerciesPK(URLEncoder.encode(systemConstantService.encryptId(exerciesDTO.getExerciesID()), StandardCharsets.UTF_8.toString()));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				r.setGradeType(exerciesDTO.getGradeType());
				r.setSubjectType(exerciesDTO.getSubjectType());
				r.setQuestionList(exerciesDTO.getQuestionList());
				r.setIsSuccess();
				return r;
			}
		}
		
		exerciesDTO = exerciesGenerator();
		ExerciesFileSaveResult saveResult = saveExerciesFile(exerciesDTO);
		
		if(saveResult.isFail()) {
			r.setMessage(saveResult.getMessage());
			return r;
		}
		
		StudentExerciesHistory po = new StudentExerciesHistory();
		po.setExerciesId(exerciesDTO.getExerciesID());
		po.setFilePath(saveResult.getFilePath());
		po.setGradeType(exerciesDTO.getGradeType().getCode().longValue());
		po.setSubjectType(exerciesDTO.getSubjectType().getCode().longValue());
		po.setUserId(exerciesDTO.getUserId());
		exerciesHistoryMapper.insertSelective(po);
		
		try {
			r.setExerciesPK(URLEncoder.encode(systemConstantService.encryptId(exerciesDTO.getExerciesID()), StandardCharsets.UTF_8.toString()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		r.setGradeType(exerciesDTO.getGradeType());
		r.setSubjectType(exerciesDTO.getSubjectType());
		r.setQuestionList(exerciesDTO.getQuestionList());
		r.setIsSuccess();
		return r;
	}
	
	private MathExerciesDTO exerciesGenerator() {
		MathExerciesDTO exerciesDTO = new MathExerciesDTO(); 
		exerciesDTO.setExerciesID(snowFlake.getNextId());
		exerciesDTO.setUserId(baseUtilCustom.getUserId());
		exerciesDTO.setSubjectType(SUBJECT_TYPE);
		exerciesDTO.setQuestionList(new ArrayList<>());
		exerciesDTO.setGradeType(gradeType);
		
		MathQuestionBaseDTO question = null;
		for(int questionNumber = 1; questionNumber <= optionService.getQuestionListSize(); questionNumber++) {
			question = createQuestion();
			while(question.getStandardAnswer().compareTo(new BigDecimal(MAX_CALCULATE)) > 0 || 
					question.getStandardAnswer().compareTo(BigDecimal.ZERO) < 0) {
				question = createQuestion();
			}
			question.setQuestionNumber(questionNumber);
			exerciesDTO.getQuestionList().add(question);
		}
		
		return exerciesDTO;
	}
	
//	TODO 
	/*
	 * 如果生成的数据不符合条件, 直接重新生成
	 * 先决定括号外的运算符
	 * if = *
	 *   子式如果不是 * , 需要加括号
	 *   需要先判定 子式 的运算结果 < MULTIPLICATION_MAX_NUM
	 * if = -
	 *   需要判定 子式 & 最终 的运算结果不能为负
	 */
	
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
		double result1 = expression1.evaluate();
		
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
		double result2 = expression2.evaluate();
		
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
		q.setStandardAnswer(new BigDecimal(result2));
		return q;
	}
	

}
