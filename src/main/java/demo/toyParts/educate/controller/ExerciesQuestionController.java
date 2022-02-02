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

@Controller
@RequestMapping(value = EducateUrl.ROOT)
public class ExerciesQuestionController extends CommonController {

	@Autowired
	private ExerciesServiceMathG1_1 g1_1_math;

	@GetMapping(value = EducateUrl.QUESTION)
	@ResponseBody
	public ModelAndView getQuestion(@RequestParam(value = "grade") Integer grade,
			@RequestParam(value = "subject") Integer subject) {
		GradeType gradeType = GradeType.getType(grade);
		ExerciesSubjectType subjectType = ExerciesSubjectType.getType(subject);

		if (GradeType.grade1_1.equals(gradeType)) {
			if (ExerciesSubjectType.chinese.equals(subjectType)) {

			} else if (ExerciesSubjectType.math.equals(subjectType)) {
				return getG1_1_math_exercies();
			} else if (ExerciesSubjectType.english.equals(subjectType)) {

			}
		} else if (GradeType.grade1_2.equals(gradeType)) {
			if (ExerciesSubjectType.chinese.equals(subjectType)) {

			} else if (ExerciesSubjectType.math.equals(subjectType)) {

			} else if (ExerciesSubjectType.english.equals(subjectType)) {

			}
		} else if (GradeType.grade2_2.equals(gradeType)) {
			if (ExerciesSubjectType.chinese.equals(subjectType)) {

			} else if (ExerciesSubjectType.math.equals(subjectType)) {

			} else if (ExerciesSubjectType.english.equals(subjectType)) {

			}
		} else if (GradeType.grade2_2.equals(gradeType)) {
			if (ExerciesSubjectType.chinese.equals(subjectType)) {

			} else if (ExerciesSubjectType.math.equals(subjectType)) {

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
}
