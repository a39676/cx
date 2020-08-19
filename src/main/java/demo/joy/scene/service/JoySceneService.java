package demo.joy.scene.service;

import demo.joy.scene.pojo.dto.FindSceneVOListBySceneGroupPKDTO;
import demo.joy.scene.pojo.po.JoyScene;
import demo.joy.scene.pojo.result.FindSceneVOListResult;
import demo.joy.scene.pojo.vo.JoySceneVO;

public interface JoySceneService {

	JoySceneVO scenePOToVO(JoyScene po);

	FindSceneVOListResult findSceneVOListBySceneGroupPK(FindSceneVOListBySceneGroupPKDTO dto);

}
