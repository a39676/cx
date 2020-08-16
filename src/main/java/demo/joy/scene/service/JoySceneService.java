package demo.joy.scene.service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.joy.scene.pojo.dto.CreateJoySceneDTO;

public interface JoySceneService {

	CommonResult createScene(CreateJoySceneDTO dto);

}
