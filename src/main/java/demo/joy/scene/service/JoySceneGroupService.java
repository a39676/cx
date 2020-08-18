package demo.joy.scene.service;

import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.scene.pojo.dto.CreateJoySceneGroupDTO;

public interface JoySceneGroupService {

	JoyCommonResult createJoySceneGroup(CreateJoySceneGroupDTO dto);

}
