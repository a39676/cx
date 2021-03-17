package demo.joy.start.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import demo.joy.common.controller.JoyCommonController;
import demo.joy.common.pojo.constant.JoyUrl;
import demo.joy.start.pojo.constant.JoyStartUrl;
import demo.joy.start.service.JoyStartService;

@Controller
@RequestMapping(value = JoyUrl.ROOT)
public class JoyStartController extends JoyCommonController {

	@Autowired
	private JoyStartService joyStartService;
	
	@GetMapping(value = JoyStartUrl.START)
	public ModelAndView getStartView() {
		return joyStartService.getStartView();
	}
	
}
