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
@RequestMapping(value = ImageUrl.ROOT)
public class ImageController extends CommonController {

	@Autowired
	private ImageService imgService;

	@GetMapping(value = ImageUrl.GET_IMAGE, produces = MediaType.IMAGE_JPEG_VALUE)
	public void getImage(HttpServletResponse response, @RequestParam(value = "imgPK", defaultValue = "") String imgPK) {
		imgService.getImage(response, imgPK);
	}

	@GetMapping(value = ImageUrl.IMAGE_PROXY, produces = MediaType.IMAGE_JPEG_VALUE)
	public void imageProxy(HttpServletResponse response,
			@RequestParam(value = "imgPK", defaultValue = "") String imgPK) {
		imgService.imgProxy(response, imgPK);
	}

	@GetMapping(value = ImageUrl.GET_THUMBNAIL, produces = MediaType.IMAGE_JPEG_VALUE)
	public void getThumbnail(HttpServletResponse response,
			@RequestParam(value = "imgPK", defaultValue = "") String imgPK,
			@RequestParam(value = "width", defaultValue = "100") Integer width,
			@RequestParam(value = "height", defaultValue = "100") Integer height) {
		imgService.getThumbnailImage(response, imgPK, width, height);
	}

}
