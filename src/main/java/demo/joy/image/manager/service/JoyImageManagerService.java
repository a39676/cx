package demo.joy.image.manager.service;

import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.image.manager.pojo.dto.JoyImageUploadDTO;
import demo.joy.image.manager.pojo.result.JoyImageUploadResult;

public interface JoyImageManagerService {

	JoyCommonResult cleanJoyImageRedis();

	JoyImageUploadResult uploadImage(JoyImageUploadDTO dto, String savingPathPrefix);

}
