package demo.joy.image.service;

import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.common.pojo.type.JoyModuleType;
import demo.joy.image.pojo.dto.JoyImageUploadDTO;
import demo.joy.image.pojo.result.JoyImageUploadResult;
import jakarta.servlet.http.HttpServletResponse;

public interface JoyImageService {

	void getImageById(HttpServletResponse response, Long id);

	JoyImageUploadResult uploadImage(JoyImageUploadDTO dto, String savingPathPrefix, JoyModuleType joyModuleType,
			Integer subModultTypeCode);

	JoyCommonResult cleanIdPathMap();

	String buildImageUrl(Long id);

	String getImageInBase64Str(String path);

}
