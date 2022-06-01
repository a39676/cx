package demo.toyParts.educate.service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.web.servlet.ModelAndView;

import demo.toyParts.educate.pojo.dto.MathExerciesDTO;
import demo.toyParts.educate.pojo.po.StudentExerciesHistory;
import demo.toyParts.educate.pojo.result.ExerciesBuildResult;
import demo.toyParts.educate.pojo.result.ExerciesFileSaveResult;
import demo.toyParts.educate.pojo.type.ExerciesSubjectType;
import demo.toyParts.educate.pojo.type.GradeType;
import demo.toyParts.educate.pojo.type.MathBaseSymbolType;

public abstract class ExerciesMathCommonService extends EducateCommonService {

	protected static final ExerciesSubjectType SUBJECT_TYPE = ExerciesSubjectType.math;
	
	protected ExerciesBuildResult buildExercies(MathExerciesDTO exerciesDTO) {
		return buildExercies(exerciesDTO, false);
	}
	
	protected ExerciesBuildResult buildExercies(MathExerciesDTO exerciesDTO, boolean newExercies) {
		ExerciesBuildResult r = new ExerciesBuildResult();
		
		if(newExercies) {
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
		}
		
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
	
	protected ModelAndView buildExerciesView(ExerciesBuildResult exercies) {
		ModelAndView v = new ModelAndView("toyJSP/educateJSP/MathExercies");
		v.addObject("exercies", exercies);
		v.addObject("questionListSize", exercies.getQuestionList().size());
		return v;
	}
	
	protected MathBaseSymbolType getRandomMathBaseSymbolType(MathBaseSymbolType start, MathBaseSymbolType end) {
		ThreadLocalRandom t = ThreadLocalRandom.current();
		int mathSymbolTypeCode = t.nextInt(start.getCode(), end.getCode() + 1);
		return MathBaseSymbolType.getType(mathSymbolTypeCode);
	}
	
	protected String replaceCodingSymbolToMathSymbol(String exp) {
		exp = exp.replaceAll("\\*", "×");
		exp = exp.replaceAll("\\/", "÷");
		return exp;
	}
	
	protected MathExerciesDTO reloadExercies(GradeType gradeType) {
		Long userId = baseUtilCustom.getUserId();
		if(userId == null) {
			return null;
		}
		List<StudentExerciesHistory> oldExerciesList = reloadExercies(gradeType, SUBJECT_TYPE, userId);
		if(oldExerciesList == null || oldExerciesList.isEmpty()) {
			return null;
		}
		MathExerciesDTO resultDTO = null;
		for(StudentExerciesHistory oldExercies : oldExerciesList) {
			try {
				resultDTO = buildExerciesFromFile(oldExercies, MathExerciesDTO.class);
				if(resultDTO != null) {
					return resultDTO;
				}
			} catch (Exception e) {
			}
		}
		return null;
	}
	
	protected Double doubleSetScale(Double d, int scaleIndex) {
		return BigDecimal.valueOf(d)
			    .setScale(scaleIndex, RoundingMode.HALF_UP)
			    .doubleValue();
	}
	
}
