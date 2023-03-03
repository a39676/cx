package demo.interaction.image.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.common.controller.CommonController;
import demo.image.service.ImageService;
import demo.interaction.image.service.ImageInteractionService;
import image.pojo.constant.ImageInteractionUrl;
import image.pojo.dto.ImageSavingTransDTO;
import image.pojo.dto.UploadImageToCloudinaryDTO;
import image.pojo.result.ImageSavingResult;
import image.pojo.result.UploadImageToCloudinaryResult;

@Controller
@RequestMapping(value = ImageInteractionUrl.ROOT)
public class ImageInteractionController extends CommonController {

	@Autowired
	private ImageInteractionService imageInteractionService;
	@Autowired
	private ImageService imgService;
	
	@PostMapping(value = ImageInteractionUrl.UPLOAD_IMAGE_TO_CLOUDINARY)
	@ResponseBody
	public UploadImageToCloudinaryResult uploadImageToCloudinary(@RequestBody UploadImageToCloudinaryDTO dto) {
		return imageInteractionService.uploadImageToCloudinary(dto);
	}
	
	@PostMapping(value = ImageInteractionUrl.IMAGE_SAVING_FROM_BBT)
	@ResponseBody
	public ImageSavingResult imageSaving(@RequestBody ImageSavingTransDTO dto) {
		return imgService.__saveImgFromBBT(dto);
	}
}
