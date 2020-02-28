package demo.image.service;

import auxiliaryCommon.pojo.result.CommonResult;
import image.pojo.dto.ImageSavingDTO;

public interface ImageService {

	CommonResult imageSaving(ImageSavingDTO dto);

}
