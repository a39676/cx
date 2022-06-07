package demo.image.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import demo.common.controller.CommonController;
import demo.image.pojo.constant.ImageUrl;
import demo.image.service.ImageService;

@Controller
@RequestMapping(value = ImageUrl.root)
public class ImageController extends CommonController {

	@Autowired
	private ImageService imgService;
	
	@GetMapping(value = ImageUrl.getImage, produces = MediaType.IMAGE_JPEG_VALUE)
	public void getImage(HttpServletResponse response, @RequestParam(value = "imgPK", defaultValue = "") String imgPK) {
		imgService.getImage(response, imgPK);
	}
}
