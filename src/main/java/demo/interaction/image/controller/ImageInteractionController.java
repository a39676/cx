package demo.interaction.image.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.baseCommon.controller.CommonController;
import demo.image.service.ImageService;
import demo.interaction.image.service.ImageInteractionService;
import image.pojo.constant.ImageInteractionUrl;
import image.pojo.dto.ImageSavingDTO;
import image.pojo.dto.UploadImageToCloudinaryDTO;
import image.pojo.result.UploadImageToCloudinaryResult;

@Controller
@RequestMapping(value = ImageInteractionUrl.root)
public class ImageInteractionController extends CommonController {

	@Autowired
	private ImageInteractionService imageInteractionService;
	@Autowired
	private ImageService imgService;
	
	@PostMapping(value = ImageInteractionUrl.uploadImageToCloudinary)
	@ResponseBody
	public UploadImageToCloudinaryResult uploadImageToCloudinary(@RequestBody UploadImageToCloudinaryDTO dto) {
		return imageInteractionService.uploadImageToCloudinary(dto);
	}
	
	@PostMapping(value = ImageInteractionUrl.imageSaving)
	public CommonResult imageSaving(ImageSavingDTO dto) {
		return imgService.imageSaving(dto);
	}
}
