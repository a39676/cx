package demo.toyParts.educate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.common.controller.CommonController;
import demo.toyParts.educate.pojo.constant.EducateUrl;
import demo.toyParts.educate.service.ExerciesServiceMathG1_1;

@Controller
@RequestMapping(value = EducateUrl.ROOT)
public class ExerciesQuestionController extends CommonController {

	@Autowired
	private ExerciesServiceMathG1_1 g1_1_math;
	
	@GetMapping(value = EducateUrl.QUESTION + "/getG1_1_math")
	@ResponseBody
	public ModelAndView t6() {
		return g1_1_math.getExercies();
	}
}
