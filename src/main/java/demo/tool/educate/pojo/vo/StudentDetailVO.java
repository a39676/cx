package demo.tool.educate.pojo.vo;

import java.math.BigDecimal;

import demo.tool.educate.pojo.type.GradeType;

public class StudentDetailVO {

	private String pk;

	private String studentName;

	private BigDecimal points;

	private GradeType gradeType;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public BigDecimal getPoints() {
		return points;
	}

	public void setPoints(BigDecimal points) {
		this.points = points;
	}

	public GradeType getGradeType() {
		return gradeType;
	}

	public void setGradeType(GradeType gradeType) {
		this.gradeType = gradeType;
	}

	@Override
	public String toString() {
		return "StudentDetailVO [pk=" + pk + ", studentName=" + studentName + ", points=" + points + ", gradeType="
				+ gradeType + "]";
	}

}
