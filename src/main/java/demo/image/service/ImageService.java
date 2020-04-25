package demo.image.service;

import java.awt.image.BufferedImage;

import javax.servlet.http.HttpServletResponse;

import image.pojo.dto.ImageSavingTransDTO;
import image.pojo.result.ImageSavingResult;

public interface ImageService {
	
	void getImage(HttpServletResponse response, String imgPK);
	
	ImageSavingResult imageSaving(ImageSavingTransDTO dto);

	ImageSavingResult __saveImgFromBBT(ImageSavingTransDTO dto);

	void imageClean();
	
	BufferedImage base64ToImg(String base64);

	boolean imgSaveAsFile(BufferedImage image, String filePath, String fileType);

}
