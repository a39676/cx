package demo.toyParts.educate.service;

import demo.toyParts.educate.pojo.po.StudentDetail;
import demo.toyParts.educate.pojo.type.GradeType;

public interface StudentService {

	void initStudentDetail(Long id, GradeType gradeType);

	StudentDetail getStudentDetail(Long id);

}
