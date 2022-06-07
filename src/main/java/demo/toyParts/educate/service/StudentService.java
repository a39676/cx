package demo.toyParts.educate.service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.toyParts.educate.pojo.po.StudentDetail;
import demo.toyParts.educate.pojo.type.GradeType;

public interface StudentService {

	void initStudentDetail(Long id, GradeType gradeType);

	StudentDetail getStudentDetail(Long id);

	CommonResult pointConsume(Long studentId, Double consumePoint);

}
