package demo.joy.image.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.joy.common.controller.JoyCommonController;
import demo.joy.common.pojo.constant.JoyManagerUrl;
import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.image.pojo.constant.JoyImageUrl;
import demo.joy.image.service.JoyImageService;

@Controller
@RequestMapping(value = JoyManagerUrl.ROOT + JoyImageUrl.ROOT)
public class JoyImageManagerController extends JoyCommonController {

	@Autowired
	private JoyImageService imageService; 
	
	@GetMapping(value = JoyImageUrl.CLEAN_ID_PATH_MAP)
	@ResponseBody
	public JoyCommonResult cleanIdPathMap() {
		return imageService.cleanIdPathMap();
	}
}
