package demo.joy.garden.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.common.controller.CommonController;
import demo.joy.common.pojo.constant.JoyUrl;

@RequestMapping(value = JoyUrl.ROOT)
@Controller
public class JoyGardenController extends CommonController {

	
}
