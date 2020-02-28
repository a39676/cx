package demo.image.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.baseCommon.controller.CommonController;
import demo.image.pojo.constant.ImageUrl;

@Controller
@RequestMapping(value = ImageUrl.root)
public class ImageController extends CommonController {

	
}
