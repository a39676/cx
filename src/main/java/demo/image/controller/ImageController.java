package demo.image.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.baseCommon.controller.CommonController;
import demo.image.pojo.constant.ImageUrl;
import demo.image.service.ImageService;

@Controller
@RequestMapping(value = ImageUrl.root)
public class ImageController extends CommonController {

	@Autowired
	private ImageService imageService;

	public int insertImageFromArticle(List<String> imageUrls, Long articleId) {
		return imageService.insertImageFromArticle(imageUrls, articleId);
	}

}
