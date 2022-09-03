package demo.toyParts.educate.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.base.user.pojo.vo.UsersDetailVO;
import demo.base.user.service.UsersService;
import demo.toyParts.educate.mapper.StudentParentMapper;
import demo.toyParts.educate.pojo.po.StudentDetail;
import demo.toyParts.educate.pojo.po.StudentDetailExample;
import demo.toyParts.educate.pojo.po.StudentParent;
import demo.toyParts.educate.pojo.po.StudentParentExample;
import demo.toyParts.educate.pojo.type.GradeType;
import demo.toyParts.educate.pojo.vo.StudentDetailVO;
import demo.toyParts.educate.service.EducateCommonService;
import demo.toyParts.educate.service.ParentService;

@Service
public class ParentServiceImpl extends EducateCommonService implements ParentService {

	@Autowired
	private UsersService usersService;
	@Autowired
	private StudentParentMapper studentParentMapper;

	@Override
	public ModelAndView findStudent() {
		Long loginParentId = baseUtilCustom.getUserId();

		List<StudentDetailVO> studentDetailVoList = findStudent(loginParentId);

		ModelAndView view = new ModelAndView("toyJSP/educateJSP/searchStudentByParent");

		view.addObject("studentList", studentDetailVoList);

		return view;
	}

	private List<StudentDetailVO> findStudent(Long loginParentId) {
		List<StudentDetailVO> studentDetailVoList = new ArrayList<>();

		if (loginParentId == null) {
			return studentDetailVoList;
		}

		StudentParentExample example = new StudentParentExample();
		example.createCriteria().andParentIdEqualTo(loginParentId);
		List<StudentParent> studentParentList = studentParentMapper.selectByExample(example);
		if (studentParentList == null || studentParentList.isEmpty()) {
			return studentDetailVoList;
		}

		List<Long> studentIdList = studentParentList.stream().map(po -> po.getStudentId()).collect(Collectors.toList());

		StudentDetailExample studentDetailExample = new StudentDetailExample();
		studentDetailExample.createCriteria().andIdIn(studentIdList);
		List<StudentDetail> studentDetailList = studentDetailMapper.selectByExample(studentDetailExample);
		if (studentDetailList == null || studentDetailList.isEmpty()) {
			return studentDetailVoList;
		}
		for (StudentDetail studentDetail : studentDetailList) {
			studentDetailVoList.add(buildStudentDetailVO(studentDetail));
		}

		return studentDetailVoList;
	}

	private StudentDetailVO buildStudentDetailVO(StudentDetail po) {
		StudentDetailVO vo = new StudentDetailVO();

		vo.setPk(systemConstantService.encryptId(po.getId()));
		GradeType gradeType = GradeType.getType(po.getGradeType().intValue());
		vo.setGradeType(gradeType);
		vo.setPoints(po.getPointsSummary());

		UsersDetailVO userDetail = usersService.findUserDetail(po.getId());
		vo.setStudentName(userDetail.getNickName());

		return vo;
	}
}
