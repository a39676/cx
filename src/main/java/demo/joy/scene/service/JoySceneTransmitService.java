package demo.joy.scene.service;

import demo.joy.scene.pojo.dto.FindSceneTransmitDTO;
import demo.joy.scene.pojo.result.FindSceneTransmitResult;

public interface JoySceneTransmitService {

	FindSceneTransmitResult findSceneTransmitByFromScenePK(FindSceneTransmitDTO dto);

}
