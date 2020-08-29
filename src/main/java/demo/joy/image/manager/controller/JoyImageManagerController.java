package demo.joy.image.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.joy.common.pojo.constant.JoyManagerUrl;
import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.image.manager.pojo.constant.JoyImageManagerUrl;
import demo.joy.image.manager.service.JoyImageManagerService;

@Controller
@RequestMapping(value = JoyManagerUrl.ROOT + JoyImageManagerUrl.ROOT)
public class JoyImageManagerController {

	@Autowired
	private JoyImageManagerService imageManagerService;
	
	@GetMapping(value = JoyImageManagerUrl.CLEAN_JOY_IMAGE_REDIS)
	@ResponseBody
	public JoyCommonResult cleanJoyImageRedis() {
		return imageManagerService.cleanJoyImageRedis();
	}
}
