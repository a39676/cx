package demo.joy.scene.service;

import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.scene.pojo.dto.EditJoySceneGroupDTO;
import demo.joy.scene.pojo.dto.FindSceneGroupByConditionDTO;
import demo.joy.scene.pojo.result.FindSceneGroupVOResult;

public interface JoySceneGroupManagerService {

	JoyCommonResult createJoySceneGroup(EditJoySceneGroupDTO dto);

	JoyCommonResult deleteJoySceneGroup(EditJoySceneGroupDTO dto);

	FindSceneGroupVOResult findSceneGroupVOListByCondition(FindSceneGroupByConditionDTO dto);

	FindSceneGroupVOResult findAllSceneGroupVOList();

}
