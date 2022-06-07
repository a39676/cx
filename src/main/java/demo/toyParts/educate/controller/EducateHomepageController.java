package demo.toyParts.educate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.common.controller.CommonController;
import demo.toyParts.educate.pojo.constant.EducateUrl;
import demo.toyParts.educate.service.ExerciesHomepageService;

@Controller
@RequestMapping(value = EducateUrl.ROOT)
public class EducateHomepageController extends CommonController {

	@Autowired
	private ExerciesHomepageService service;
	
	@GetMapping(value = "/")
	@ResponseBody
	public ModelAndView homepage() {
		return service.homepage();
	}
}
