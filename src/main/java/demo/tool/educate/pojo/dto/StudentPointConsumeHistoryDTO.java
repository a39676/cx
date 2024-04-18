package demo.tool.educate.pojo.dto;

import java.math.BigDecimal;

public class StudentPointConsumeHistoryDTO {

	private String studentPK;

	private BigDecimal point;

	private String remark;

	public String getStudentPK() {
		return studentPK;
	}

	public void setStudentPK(String studentPK) {
		this.studentPK = studentPK;
	}

	public BigDecimal getPoint() {
		return point;
	}

	public void setPoint(BigDecimal point) {
		this.point = point;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "StudentPointConsumeHistoryDTO [studentPK=" + studentPK + ", point=" + point + ", remark=" + remark
				+ "]";
	}

}
