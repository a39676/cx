package demo.toyParts.educate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.common.controller.CommonController;
import demo.toyParts.educate.pojo.constant.EducateUrl;
import demo.toyParts.educate.pojo.type.ExerciesSubjectType;
import demo.toyParts.educate.pojo.type.GradeType;
import demo.toyParts.educate.service.ExerciesServiceMathG1_1;
import demo.toyParts.educate.service.ExerciesServiceMathG1_2;
import demo.toyParts.educate.service.ExerciesServiceMathG2_1;
import demo.toyParts.educate.service.ExerciesServiceMathG3_1;
import demo.toyParts.educate.service.ExerciesServiceMathG3_2;

@Controller
@RequestMapping(value = EducateUrl.ROOT)
public class ExerciesQuestionController extends CommonController {

	@Autowired
	private ExerciesServiceMathG1_1 g1_1_math;
	@Autowired
	private ExerciesServiceMathG1_2 g1_2_math;
	@Autowired
	private ExerciesServiceMathG2_1 g2_1_math;
	@Autowired
	private ExerciesServiceMathG3_1 g3_1_math;
	@Autowired
	private ExerciesServiceMathG3_2 g3_2_math;

	@GetMapping(value = EducateUrl.QUESTION)
	@ResponseBody
	public ModelAndView getQuestion(@RequestParam(value = "gradeTypeCode") Integer gradeTypeCode,
			@RequestParam(value = "subject") Integer subject) {
		GradeType gradeType = GradeType.getType(gradeTypeCode);
		ExerciesSubjectType subjectType = ExerciesSubjectType.getType(subject);

		if (GradeType.GRADE_1_1.equals(gradeType)) {
			if (ExerciesSubjectType.chinese.equals(subjectType)) {

			} else if (ExerciesSubjectType.math.equals(subjectType)) {
				return getG1_1_math_exercies();
			} else if (ExerciesSubjectType.english.equals(subjectType)) {

			}
		} else if (GradeType.GRADE_1_2.equals(gradeType)) {
			if (ExerciesSubjectType.chinese.equals(subjectType)) {

			} else if (ExerciesSubjectType.math.equals(subjectType)) {
				return g1_2_math.getExercies();
			} else if (ExerciesSubjectType.english.equals(subjectType)) {

			}
		} else if (GradeType.GRADE_2_1.equals(gradeType)) {
			if (ExerciesSubjectType.chinese.equals(subjectType)) {

			} else if (ExerciesSubjectType.math.equals(subjectType)) {
				return g2_1_math.getExercies(); 
			} else if (ExerciesSubjectType.english.equals(subjectType)) {

			}
		} else if (GradeType.GRADE_2_2.equals(gradeType)) {
			if (ExerciesSubjectType.chinese.equals(subjectType)) {

			} else if (ExerciesSubjectType.math.equals(subjectType)) {

			} else if (ExerciesSubjectType.english.equals(subjectType)) {

			}
		}else if (GradeType.GRADE_3_1.equals(gradeType)) {
			if (ExerciesSubjectType.chinese.equals(subjectType)) {

			} else if (ExerciesSubjectType.math.equals(subjectType)) {
				return g3_1_math.getExercies();
			} else if (ExerciesSubjectType.english.equals(subjectType)) {

			}
		} else if (GradeType.GRADE_3_2.equals(gradeType)) {
			if (ExerciesSubjectType.chinese.equals(subjectType)) {

			} else if (ExerciesSubjectType.math.equals(subjectType)) {
				return g3_2_math.getExercies();
			} else if (ExerciesSubjectType.english.equals(subjectType)) {

			}
		}
		
		return null;

	}

	@GetMapping(value = "/getG1_1_math")
	@ResponseBody
	public ModelAndView getG1_1_math_exercies() {
		return g1_1_math.getExercies();
	}
	
	@GetMapping(value = "/getG1_2_math")
	@ResponseBody
	public ModelAndView getG1_2_math_exercies() {
		return g1_2_math.getExercies();
	}
	
	@GetMapping(value = "/getG2_1_math")
	@ResponseBody
	public ModelAndView getG2_1_math_exercies() {
		return g3_2_math.getExercies();
	}
}
