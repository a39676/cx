package demo.joy.image.common.service;

import javax.servlet.http.HttpServletResponse;

import demo.joy.common.pojo.type.JoyModuleType;
import demo.joy.image.common.pojo.dto.JoyImageUploadDTO;
import demo.joy.image.common.pojo.result.JoyImageUploadResult;

public interface JoyImageService {

	String imgBase64Saving(String savingFolderPath, String srcStr);

	void getImageById(HttpServletResponse response, Long id);

	JoyImageUploadResult uploadImage(JoyImageUploadDTO dto, String savingPathPrefix, JoyModuleType joyModuleType,
			Integer subModultTypeCode);

	void cleanIdPathMap();

	String buildImageUrl(Long id);

}
