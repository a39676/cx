package demo.tool.educate.service;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.educate.pojo.dto.StudentPointConsumeHistoryDTO;

public interface ParentService {

	ModelAndView findStudent();

	CommonResult addPointConsumeHistory(StudentPointConsumeHistoryDTO dto);

}
