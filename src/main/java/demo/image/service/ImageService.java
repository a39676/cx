package demo.image.service;

import javax.servlet.http.HttpServletResponse;

import image.pojo.dto.ImageSavingTransDTO;
import image.pojo.result.ImageSavingResult;

public interface ImageService {
	
	void getImage(HttpServletResponse response, String imgPK);

	ImageSavingResult imageSaving(ImageSavingTransDTO dto);

}
