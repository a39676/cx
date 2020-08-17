package demo.joy.scene.service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.joy.scene.pojo.dto.JoySceneOperationDTO;

public interface JoySceneOperationService {

	CommonResult createScene(JoySceneOperationDTO dto);

	CommonResult editScene(JoySceneOperationDTO dto);

	CommonResult deleteScene(JoySceneOperationDTO dto);

	CommonResult restoreScene(JoySceneOperationDTO dto);

	void defaultSceneInit();

}
