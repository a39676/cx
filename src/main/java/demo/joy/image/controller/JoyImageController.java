package demo.joy.image.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import demo.joy.common.controller.JoyCommonController;
import demo.joy.common.pojo.constant.JoyUrl;
import demo.joy.image.pojo.constant.JoyImageUrl;
import demo.joy.image.service.JoyImageService;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = JoyUrl.ROOT + JoyImageUrl.ROOT)
public class JoyImageController extends JoyCommonController {

	@Autowired
	private JoyImageService imageService;

	@GetMapping(value = JoyImageUrl.GET_IMAGE_BY_ID)
	public void getImageById(HttpServletResponse response, @RequestParam("id") Long id) {
		imageService.getImageById(response, id);
	}
}
