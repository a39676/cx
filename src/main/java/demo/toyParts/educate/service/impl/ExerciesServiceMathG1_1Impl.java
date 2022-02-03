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
import demo.toyParts.educate.service.ExerciesServiceMathG1_1;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

@Service
public class ExerciesServiceMathG1_1Impl extends ExerciesMathCommonService implements ExerciesServiceMathG1_1 {

	private static final Integer MAX_NUM = 10;
	private static final Integer MIN_NUM = 0;
	private static final GradeType gradeType = GradeType.grade1_1;
	
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
		MathBaseSymbolType mathSymbolType1 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition, MathBaseSymbolType.subtraction);
		
		String exp = String.valueOf(num1 + mathSymbolType1.getCodeSymbol() + num2);
		Expression expression = new ExpressionBuilder(exp).build();
		double result = expression.evaluate();
		
		if(result < 0) {
			exp = String.valueOf(num2 + mathSymbolType1.getCodeSymbol() + num1);
		}
		
		boolean threeNum = (0 == t.nextInt(0, 1 + 1));
		if(!threeNum) {
			q.setExpression(exp);
			q.setStandardAnswer(new BigDecimal(result));
			return q;
		}
		
		int num3 = t.nextInt(MIN_NUM, MAX_NUM + 1);
		MathBaseSymbolType mathSymbolType2 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition, MathBaseSymbolType.subtraction);
		
		exp = String.valueOf(num1 + mathSymbolType1.getCodeSymbol() + num2);
		expression = new ExpressionBuilder(exp).build();
		result = expression.evaluate();
		if(result < 0) {
			exp = String.valueOf(num3 + mathSymbolType2.getCodeSymbol() + num2 + mathSymbolType1.getCodeSymbol() + num1);
		}
		
		expression = new ExpressionBuilder(exp).build();
		result = expression.evaluate();
		
		exp = replaceCodingSymbolToMathSymbol(exp);
		q.setExpression(exp);
		q.setStandardAnswer(new BigDecimal(result));
		return q;
	}
	

}
