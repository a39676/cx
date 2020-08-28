package demo.joy.scene.service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.joy.scene.pojo.dto.FindSceneByConditionDTO;
import demo.joy.scene.pojo.dto.JoySceneOperationDTO;
import demo.joy.scene.pojo.result.FindSceneVOListResult;

public interface JoySceneManagerService {

	CommonResult createScene(JoySceneOperationDTO dto);

	CommonResult editScene(JoySceneOperationDTO dto);

	CommonResult deleteScene(JoySceneOperationDTO dto);

	CommonResult restoreScene(JoySceneOperationDTO dto);

	void defaultSceneInit();

	FindSceneVOListResult findSceneByCondition(FindSceneByConditionDTO dto);

}
