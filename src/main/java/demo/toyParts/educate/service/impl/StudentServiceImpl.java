package demo.toyParts.educate.service.impl;

import org.springframework.stereotype.Service;

import demo.toyParts.educate.pojo.po.StudentDetail;
import demo.toyParts.educate.pojo.type.GradeType;
import demo.toyParts.educate.service.EducateCommonService;
import demo.toyParts.educate.service.StudentService;

@Service
public class StudentServiceImpl extends EducateCommonService implements StudentService {

	@Override
	public StudentDetail getStudentDetail(Long id) {
		return studentDetailMapper.selectByPrimaryKey(id);
	}

	@Override
	public void initStudentDetail(Long id, GradeType gradeType) {
		StudentDetail po = new StudentDetail();
		po.setId(id);
		po.setGradeType(gradeType.getCode().longValue());
		studentDetailMapper.insertSelective(po);
	}
}
