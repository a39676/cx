package demo.tool.educate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.common.controller.CommonController;
import demo.tool.educate.math.service.ExerciesServiceMathCustom;
import demo.tool.educate.math.service.ExerciseServiceMathG1_1;
import demo.tool.educate.math.service.ExerciseServiceMathG1_2;
import demo.tool.educate.math.service.ExerciseServiceMathG2_1;
import demo.tool.educate.math.service.ExerciseServiceMathG2_2;
import demo.tool.educate.math.service.ExerciseServiceMathG3_1;
import demo.tool.educate.math.service.ExerciseServiceMathG3_2;
import demo.tool.educate.math.service.ExerciseServiceMathG4_1;
import demo.tool.educate.math.service.ExerciseServiceMathG4_2;
import demo.tool.educate.pojo.constant.EducateUrl;
import demo.tool.educate.pojo.type.ExerciseSubjectType;
import demo.tool.educate.pojo.type.GradeType;

@Controller
@RequestMapping(value = EducateUrl.ROOT)
public class ExerciseQuestionController extends CommonController {

	@Autowired
	private ExerciseServiceMathG1_1 g1_1_math;
	@Autowired
	private ExerciseServiceMathG1_2 g1_2_math;
	@Autowired
	private ExerciseServiceMathG2_1 g2_1_math;
	@Autowired
	private ExerciseServiceMathG2_2 g2_2_math;
	@Autowired
	private ExerciseServiceMathG3_1 g3_1_math;
	@Autowired
	private ExerciseServiceMathG3_2 g3_2_math;
	@Autowired
	private ExerciseServiceMathG4_1 g4_1_math;
	@Autowired
	private ExerciseServiceMathG4_2 g4_2_math;
	@Autowired
	private ExerciesServiceMathCustom gCustom_math;

	@GetMapping(value = EducateUrl.QUESTION)
	@ResponseBody
	public ModelAndView getQuestion(@RequestParam(value = "gradeTypeCode") Integer gradeTypeCode,
			@RequestParam(value = "subject") Integer subject) {
		GradeType gradeType = GradeType.getType(gradeTypeCode);
		ExerciseSubjectType subjectType = ExerciseSubjectType.getType(subject);

		if (GradeType.GRADE_1_1.equals(gradeType)) {
			if (ExerciseSubjectType.CHINESE.equals(subjectType)) {

			} else if (ExerciseSubjectType.MATH.equals(subjectType)) {
				return g1_1_math.getExercise();
			} else if (ExerciseSubjectType.ENGLISH.equals(subjectType)) {

			}
		} else if (GradeType.GRADE_1_2.equals(gradeType)) {
			if (ExerciseSubjectType.CHINESE.equals(subjectType)) {

			} else if (ExerciseSubjectType.MATH.equals(subjectType)) {
				return g1_2_math.getExercise();
			} else if (ExerciseSubjectType.ENGLISH.equals(subjectType)) {

			}
		} else if (GradeType.GRADE_2_1.equals(gradeType)) {
			if (ExerciseSubjectType.CHINESE.equals(subjectType)) {

			} else if (ExerciseSubjectType.MATH.equals(subjectType)) {
				return g2_1_math.getExercise();
			} else if (ExerciseSubjectType.ENGLISH.equals(subjectType)) {

			}
		} else if (GradeType.GRADE_2_2.equals(gradeType)) {
			if (ExerciseSubjectType.CHINESE.equals(subjectType)) {

			} else if (ExerciseSubjectType.MATH.equals(subjectType)) {
				return g2_2_math.getExercise();
			} else if (ExerciseSubjectType.ENGLISH.equals(subjectType)) {

			}
		} else if (GradeType.GRADE_3_1.equals(gradeType)) {
			if (ExerciseSubjectType.CHINESE.equals(subjectType)) {

			} else if (ExerciseSubjectType.MATH.equals(subjectType)) {
				return g3_1_math.getExercise();
			} else if (ExerciseSubjectType.ENGLISH.equals(subjectType)) {

			}
		} else if (GradeType.GRADE_3_2.equals(gradeType)) {
			if (ExerciseSubjectType.CHINESE.equals(subjectType)) {

			} else if (ExerciseSubjectType.MATH.equals(subjectType)) {
				return g3_2_math.getExercise();
			} else if (ExerciseSubjectType.ENGLISH.equals(subjectType)) {

			}
		} else if (GradeType.GRADE_4_1.equals(gradeType)) {
			if (ExerciseSubjectType.CHINESE.equals(subjectType)) {

			} else if (ExerciseSubjectType.MATH.equals(subjectType)) {
				return g4_1_math.getExercise();
			} else if (ExerciseSubjectType.ENGLISH.equals(subjectType)) {

			}
		} else if (GradeType.GRADE_4_2.equals(gradeType)) {
			if (ExerciseSubjectType.CHINESE.equals(subjectType)) {

			} else if (ExerciseSubjectType.MATH.equals(subjectType)) {
				return g4_2_math.getExercise();
			} else if (ExerciseSubjectType.ENGLISH.equals(subjectType)) {

			}
		} else if(GradeType.GRADE_CUSTOM.equals(gradeType)) {
			if (ExerciseSubjectType.CHINESE.equals(subjectType)) {

			} else if (ExerciseSubjectType.MATH.equals(subjectType)) {
				return gCustom_math.getExercise();
			} else if (ExerciseSubjectType.ENGLISH.equals(subjectType)) {

			}
		}

		return null;

	}

}
