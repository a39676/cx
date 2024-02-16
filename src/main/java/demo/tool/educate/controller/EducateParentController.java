package demo.tool.educate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.controller.CommonController;
import demo.tool.educate.pojo.constant.EducateUrl;
import demo.tool.educate.pojo.dto.StudentPointConsumeHistoryDTO;
import demo.tool.educate.service.ParentService;

@Controller
@RequestMapping(value = EducateUrl.ROOT)
public class EducateParentController extends CommonController {

	@Autowired
	private ParentService service;
	
	@GetMapping(value = EducateUrl.FOR_PARENT)
	@ResponseBody
	public ModelAndView findStudent() {
		return service.findStudent();
	}
	
	@PostMapping(value = EducateUrl.ADD_POINT_CONSUME_HISTORY)
	@ResponseBody
	public CommonResult addPointConsumeHistory(@RequestBody StudentPointConsumeHistoryDTO dto) {
		return service.addPointConsumeHistory(dto);
	}
}
