package demo.tool.educate.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.educate.pojo.dto.MathQuestionBaseDTO;
import demo.tool.educate.pojo.type.ExerciseSubjectType;
import demo.tool.educate.pojo.type.GradeType;

public class ExerciseBuildResult extends CommonResult {

	private String exercisePK;

	private ExerciseSubjectType subjectType;

	private GradeType gradeType;

	private List<MathQuestionBaseDTO> questionList;

	private String startTimeStr;

	public String getExercisePK() {
		return exercisePK;
	}

	public void setExercisePK(String exercisePK) {
		this.exercisePK = exercisePK;
	}

	public ExerciseSubjectType getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(ExerciseSubjectType subjectType) {
		this.subjectType = subjectType;
	}

	public GradeType getGradeType() {
		return gradeType;
	}

	public void setGradeType(GradeType gradeType) {
		this.gradeType = gradeType;
	}

	public List<MathQuestionBaseDTO> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<MathQuestionBaseDTO> questionList) {
		this.questionList = questionList;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	@Override
	public String toString() {
		return "ExerciseBuildResult [exercisePK=" + exercisePK + ", subjectType=" + subjectType + ", gradeType="
				+ gradeType + ", questionList=" + questionList + ", startTimeStr=" + startTimeStr + "]";
	}

}
