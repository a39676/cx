package demo.image.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.baseCommon.controller.CommonController;
import demo.image.service.ImageService;
import image.pojo.constant.ImageInteractionUrl;
import image.pojo.dto.UploadImageToCloudinaryDTO;
import image.pojo.result.UploadImageToCloudinaryResult;

@Controller
@RequestMapping(value = ImageInteractionUrl.root)
public class ImageInteractionController extends CommonController {

	@Autowired
	private ImageService imageService;

	public int insertImageFromArticle(List<String> imageUrls, Long articleId) {
		return imageService.insertImageFromArticle(imageUrls, articleId);
	}

	@PostMapping(value = ImageInteractionUrl.uploadImageToCloudinary)
	@ResponseBody
	public UploadImageToCloudinaryResult uploadImageToCloudinary(@RequestBody UploadImageToCloudinaryDTO dto) {
		return imageService.uploadImageToCloudinary(dto);
	}
	
	
	@GetMapping(value = ImageInteractionUrl.uploadImageToCloudinary)
	@ResponseBody
	public UploadImageToCloudinaryResult uploadImageToCloudinary(@RequestParam(value = "filePath", defaultValue = "") String filePath) {
		UploadImageToCloudinaryDTO dto = new UploadImageToCloudinaryDTO();
		dto.setFilePath(filePath);
		return imageService.uploadImageToCloudinary(dto);
	}
}
