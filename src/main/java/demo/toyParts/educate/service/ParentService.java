package demo.toyParts.educate.service;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.toyParts.educate.pojo.dto.StudentPointConsumeHistoryDTO;

public interface ParentService {

	ModelAndView findStudent();

	CommonResult addPointConsumeHistory(StudentPointConsumeHistoryDTO dto);

}
