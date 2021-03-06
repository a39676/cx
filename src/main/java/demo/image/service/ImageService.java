package demo.image.service;

import java.awt.image.BufferedImage;

import javax.servlet.http.HttpServletResponse;

import demo.image.pojo.result.ImgHandleSrcDataResult;
import image.pojo.dto.ImageSavingTransDTO;
import image.pojo.result.ImageSavingResult;

public interface ImageService {
	
	void getImage(HttpServletResponse response, String imgPK);
	
	void getImageByPath(HttpServletResponse response, String path);
	
	ImageSavingResult imageSaving(ImageSavingTransDTO dto);

	ImageSavingResult __saveImgFromBBT(ImageSavingTransDTO dto);

	void imageClean();
	
	BufferedImage base64ToBufferedImg(String base64);

	/**
	 * 可能会有其他模块的图片服务会使用此功能
	 * 如{@link JoyImageNpcService }
	 * @param image
	 * @param filePath
	 * @param fileType
	 * @return
	 */
	boolean imgSaveAsFile(BufferedImage image, String filePath, String fileType);

	/**
	 * 处理用src字段, base64字符 上传的图片
	 * @param src
	 * @return
	 */
	ImgHandleSrcDataResult imgHandleSrcData(String src);

}
