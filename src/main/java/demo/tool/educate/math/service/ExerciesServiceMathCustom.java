package demo.tool.educate.math.service;

import org.springframework.web.servlet.ModelAndView;

import demo.tool.educate.pojo.dto.MathQuestionBaseDTO;
import demo.tool.educate.pojo.result.ExerciseBuildResult;

public interface ExerciesServiceMathCustom {
	
	ModelAndView getExercise();
	
	ExerciseBuildResult buildExercise();

	MathQuestionBaseDTO findGreastestCommonDivisor();

	MathQuestionBaseDTO findLowestCommonMultiple();

}
