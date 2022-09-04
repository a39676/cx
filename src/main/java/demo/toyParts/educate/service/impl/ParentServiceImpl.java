package demo.toyParts.educate.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.user.pojo.vo.UsersDetailVO;
import demo.base.user.service.UsersService;
import demo.toyParts.educate.mapper.StudentParentMapper;
import demo.toyParts.educate.pojo.dto.StudentPointConsumeHistoryDTO;
import demo.toyParts.educate.pojo.po.StudentDetail;
import demo.toyParts.educate.pojo.po.StudentDetailExample;
import demo.toyParts.educate.pojo.po.StudentParent;
import demo.toyParts.educate.pojo.po.StudentParentExample;
import demo.toyParts.educate.pojo.po.StudentParentKey;
import demo.toyParts.educate.pojo.po.StudentPointHistory;
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

	@Override
	@Transactional(value = "cxTransactionManager", rollbackFor = Exception.class)
	public CommonResult addPointConsumeHistory(StudentPointConsumeHistoryDTO dto) {
		CommonResult r = new CommonResult();
		
		if(dto.getPoint() == null) {
			r.setMessage("Please input point correct");
			return r;
		}
		
		Long studentId = systemConstantService.decryptPrivateKey(dto.getStudentPK());
		if(studentId == null) {
			r.setMessage("Can NOT find correct student");
			return r;
		}
		
		Long userId = baseUtilCustom.getUserId();
		
		if(!baseUtilCustom.hasSuperAdminRole()) {
			StudentParentKey key = new StudentParentKey();
			key.setParentId(userId);
			key.setStudentId(studentId);
			StudentParent studentParentPO = studentParentMapper.selectByPrimaryKey(key);
			
			if(studentParentPO == null) {
				r.setMessage("Not correct releationship student");
				return r;
			}
		}
		
		StudentPointHistory studentPointHistory = new StudentPointHistory();
		studentPointHistory.setId(snowFlake.getNextId());
		studentPointHistory.setPoints(dto.getPoint());
		studentPointHistory.setRemark(dto.getRemark());
		studentPointHistory.setUserId(studentId);
		studentPointHistoryMapper.insertSelective(studentPointHistory);
		
		StudentDetail studentDetail = studentDetailMapper.selectByPrimaryKey(studentId);
		studentDetail.setPointsSummary(studentDetail.getPointsSummary().add(dto.getPoint()));
		studentDetailMapper.updateByPrimaryKeySelective(studentDetail);
		
		r.setMessage("Point update success");
		r.setIsSuccess();
		return r;
	}

}
