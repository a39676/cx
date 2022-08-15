package demo.toyParts.educate.math.service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.web.servlet.ModelAndView;

import demo.toyParts.educate.pojo.dto.MathExerciseDTO;
import demo.toyParts.educate.pojo.po.StudentExerciseHistory;
import demo.toyParts.educate.pojo.result.ExerciseBuildResult;
import demo.toyParts.educate.pojo.result.ExerciseFileSaveResult;
import demo.toyParts.educate.pojo.type.ExerciseSubjectType;
import demo.toyParts.educate.pojo.type.GradeType;
import demo.toyParts.educate.pojo.type.MathBaseSymbolType;
import demo.toyParts.educate.service.EducateCommonService;

public abstract class ExerciseMathCommonService extends EducateCommonService {

	protected static final ExerciseSubjectType SUBJECT_TYPE = ExerciseSubjectType.MATH;
	
	protected ExerciseBuildResult buildExercise(MathExerciseDTO exerciseDTO) {
		return buildExercise(exerciseDTO, false);
	}
	
	protected ExerciseBuildResult buildExercise(MathExerciseDTO exerciseDTO, boolean newExercise) {
		ExerciseBuildResult r = new ExerciseBuildResult();
		
		if(newExercise) {
			ExerciseFileSaveResult saveResult = saveExerciseFile(exerciseDTO);
			if(saveResult.isFail()) {
				r.setMessage(saveResult.getMessage());
				return r;
			}
			StudentExerciseHistory po = new StudentExerciseHistory();
			po.setExerciseId(exerciseDTO.getExerciseID());
			po.setFilePath(saveResult.getFilePath());
			po.setGradeType(exerciseDTO.getGradeType().getCode().longValue());
			po.setSubjectType(exerciseDTO.getSubjectType().getCode().longValue());
			po.setUserId(exerciseDTO.getUserId());
			exerciseHistoryMapper.insertSelective(po);
		}
		
		try {
			r.setExercisePK(URLEncoder.encode(systemConstantService.encryptId(exerciseDTO.getExerciseID()), StandardCharsets.UTF_8.toString()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		r.setGradeType(exerciseDTO.getGradeType());
		r.setSubjectType(exerciseDTO.getSubjectType());
		r.setQuestionList(exerciseDTO.getQuestionList());
		r.setIsSuccess();
		return r;
	}
	
	protected ModelAndView buildExerciseView(ExerciseBuildResult exercise) {
		ModelAndView v = new ModelAndView("toyJSP/educateJSP/MathExercise");
		v.addObject("exercise", exercise);
		v.addObject("questionListSize", exercise.getQuestionList().size());
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
	
	protected MathExerciseDTO reloadExercise(GradeType gradeType) {
		Long userId = baseUtilCustom.getUserId();
		if(userId == null) {
			return null;
		}
		List<StudentExerciseHistory> oldExerciseList = reloadExercise(gradeType, SUBJECT_TYPE, userId);
		if(oldExerciseList == null || oldExerciseList.isEmpty()) {
			return null;
		}
		MathExerciseDTO resultDTO = null;
		for(StudentExerciseHistory oldExercise : oldExerciseList) {
			try {
				resultDTO = buildExerciseFromFile(oldExercise, MathExerciseDTO.class);
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
	
	protected Integer getLowestCommonMultiple(int i1, int i2) {
		if (i1 == 0 || i2 == 0) {
	        return 0;
	    }
	    int absNumber1 = Math.abs(i1);
	    int absNumber2 = Math.abs(i2);
	    int absHigherNumber = Math.max(absNumber1, absNumber2);
	    int absLowerNumber = Math.min(absNumber1, absNumber2);
	    int lcm = absHigherNumber;
	    while (lcm % absLowerNumber != 0) {
	        lcm += absHigherNumber;
	    }
	    return lcm;
	}
}
