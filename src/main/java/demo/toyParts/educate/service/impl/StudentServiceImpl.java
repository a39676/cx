package demo.toyParts.educate.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
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
	
	@Override
	public CommonResult pointConsume(Long studentId, Double consumePoint) {
		CommonResult r = new CommonResult();
		StudentDetail po = studentDetailMapper.selectByPrimaryKey(studentId);
		if(po == null) {
			r.setMessage("Studen NOT exists");
			return r;
		}
		
		
		if(po.getPointsSummary().doubleValue() < consumePoint) {
			r.setMessage("Point NOT enough");
			return r;
		}
		
		po.setPointsSummary(po.getPointsSummary().subtract(new BigDecimal(consumePoint)));
		studentDetailMapper.updateByPrimaryKeySelective(po);
		
		r.setIsSuccess();
		return r;
	}
}
