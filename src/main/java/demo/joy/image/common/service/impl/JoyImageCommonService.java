package demo.joy.image.common.service.impl;

import java.awt.image.BufferedImage;

import org.springframework.beans.factory.annotation.Autowired;

import demo.image.pojo.result.ImgHandleSrcDataResult;
import demo.image.service.ImageService;
import demo.joy.common.service.JoyCommonService;

public abstract class JoyImageCommonService extends JoyCommonService {
	
	@Autowired
	protected ImageService imgService;

	protected String imgBase64Saving(String savingFolderPath, String srcStr) {
		ImgHandleSrcDataResult srcHandleResult = imgService.imgHandleSrcData(srcStr);
		if (srcHandleResult.isFail()) {
			return null;
		}

		BufferedImage bufferedImage = imgService.base64ToBufferedImg(srcHandleResult.getBase64Str());
		if (bufferedImage == null) {
			return null;
		}

		String imgSavingPath = saveBufferedImgAsFile(bufferedImage, savingFolderPath, srcHandleResult.getImgFileType());
		if (imgSavingPath == null) {
			return null;
		}

		return imgSavingPath;
	}
	
	private String saveBufferedImgAsFile(BufferedImage bufferedImage, String savingFolderPath, String fileType) {
		String filename = String.valueOf(snowFlake.getNextId()) + "." + fileType;
		
		String imgSavingPath = savingFolderPath + "/" + filename;

		if (imgService.imgSaveAsFile(bufferedImage, imgSavingPath, fileType)) {
			return imgSavingPath;
		} else {
			return null;
		}
	}
	
}
