package demo.toyParts.afk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import demo.baseCommon.controller.CommonController;
import demo.toyParts.afk.service.AFKService;

@Controller
@RequestMapping(value = "/afk")
public class AFKController extends CommonController {

	@Autowired
	private AFKService afkService;
	
	@GetMapping(value = "/afk")
	public ModelAndView afk() {
		return afkService.afk();
	}
}
