package demo.interaction.image.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.baseCommon.controller.CommonController;
import demo.interaction.image.service.ImageInteractionService;
import image.pojo.constant.ImageInteractionUrl;
import image.pojo.dto.UploadImageToCloudinaryDTO;
import image.pojo.result.UploadImageToCloudinaryResult;

@Controller
@RequestMapping(value = ImageInteractionUrl.root)
public class ImageInteractionController extends CommonController {

	@Autowired
	private ImageInteractionService imageInteractionService;
	
	@PostMapping(value = ImageInteractionUrl.uploadImageToCloudinary)
	@ResponseBody
	public UploadImageToCloudinaryResult uploadImageToCloudinary(@RequestBody UploadImageToCloudinaryDTO dto) {
		return imageInteractionService.uploadImageToCloudinary(dto);
	}
	
}
