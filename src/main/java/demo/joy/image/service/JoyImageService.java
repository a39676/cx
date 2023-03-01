package demo.joy.image.service;

import javax.servlet.http.HttpServletResponse;

import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.common.pojo.type.JoyModuleType;
import demo.joy.image.pojo.dto.JoyImageUploadDTO;
import demo.joy.image.pojo.result.JoyImageUploadResult;

public interface JoyImageService {

	void getImageById(HttpServletResponse response, Long id);

	JoyImageUploadResult uploadImage(JoyImageUploadDTO dto, String savingPathPrefix, JoyModuleType joyModuleType,
			Integer subModultTypeCode);

	JoyCommonResult cleanIdPathMap();

	String buildImageUrl(Long id);

	String getImageInBase64Str(String path);

}
