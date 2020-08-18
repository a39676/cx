package demo.joy.scene.service;

import demo.joy.scene.pojo.po.JoyScene;
import demo.joy.scene.pojo.result.FindSceneVOListResult;
import demo.joy.scene.pojo.vo.JoySceneVO;

public interface JoySceneService {

	FindSceneVOListResult findSceneVOListBySceneGroupId(Long sceneGroupId);

	JoySceneVO scenePOToVO(JoyScene po);

}
