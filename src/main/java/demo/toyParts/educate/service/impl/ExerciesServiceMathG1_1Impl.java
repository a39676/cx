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
import demo.toyParts.educate.pojo.type.ExerciesSubjectType;
import demo.toyParts.educate.pojo.type.GradeType;
import demo.toyParts.educate.pojo.type.MathBaseSymbolType;
import demo.toyParts.educate.service.ExerciesMathCommonService;
import demo.toyParts.educate.service.ExerciesServiceMathG1_1;

@Service
public class ExerciesServiceMathG1_1Impl extends ExerciesMathCommonService implements ExerciesServiceMathG1_1 {

	private static final Integer MAX_NUM = 10;
	private static final Integer MIN_NUM = 0;
	private static final Integer ADJUST = 4;
	
	@Override
	public ModelAndView getExercies() {
		ModelAndView v = new ModelAndView("toyJSP/educateJSP/Exercies");
		
		v.addObject("exercies", buildExercies());
		
		return v;
	}

	@Override
	public ExerciesBuildResult buildExercies() {
		ExerciesBuildResult r = new ExerciesBuildResult();
		MathExerciesDTO exerciesDTO = exerciesGenerator();
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
		exerciesDTO.setSubjectType(ExerciesSubjectType.math);
		exerciesDTO.setQuestionList(new ArrayList<>());
		exerciesDTO.setGradeType(GradeType.grade1_1);
		
		MathQuestionBaseDTO question = null;
		ThreadLocalRandom t = ThreadLocalRandom.current();
		for(int questionNumber = 1; questionNumber <= optionService.getQuestionListSize(); questionNumber++) {
			int mathSymbolTypeCode = t.nextInt(MathBaseSymbolType.addition.getCode(), MathBaseSymbolType.subtraction.getCode() + 1);
			
			if(MathBaseSymbolType.addition.getCode().equals(mathSymbolTypeCode)) {
				question = addtionGenerator();
			} else {
				question = subtractionGenerator();
			}
			question.setQuestionNumber(questionNumber);
			exerciesDTO.getQuestionList().add(question);
		}
		
		return exerciesDTO;
	}
	
	private MathQuestionBaseDTO addtionGenerator() {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		ThreadLocalRandom t = ThreadLocalRandom.current();
		q.setNum1(new BigDecimal(t.nextInt(MIN_NUM + ADJUST, MAX_NUM + 1)));
		q.setNum2(new BigDecimal(t.nextInt(MIN_NUM + ADJUST, MAX_NUM + 1)));
		q.setMathBaseSymbolType(MathBaseSymbolType.addition);
		q.setStandardAnswer(q.getNum1().add(q.getNum2()));
		return q;
	}
	
	private MathQuestionBaseDTO subtractionGenerator() {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		ThreadLocalRandom t = ThreadLocalRandom.current();
		q.setNum1(new BigDecimal(t.nextInt(MIN_NUM, MAX_NUM + MAX_NUM + 1)));
		q.setNum2(new BigDecimal(t.nextInt(MIN_NUM, MAX_NUM + 1)));
		while(q.getNum1().compareTo(q.getNum2()) < 0) {
			q.setNum1(new BigDecimal(t.nextInt(MIN_NUM, MAX_NUM + MAX_NUM + 1)));
			q.setNum2(new BigDecimal(t.nextInt(MIN_NUM, MAX_NUM + 1)));
		}
		q.setMathBaseSymbolType(MathBaseSymbolType.subtraction);
		q.setStandardAnswer(q.getNum1().subtract(q.getNum2()));
		return q;
	}
	
}
