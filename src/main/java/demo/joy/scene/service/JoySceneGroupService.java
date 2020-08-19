package demo.joy.scene.service;

import demo.joy.scene.pojo.po.JoySceneGroup;
import demo.joy.scene.pojo.result.FindSceneGroupVOResult;
import demo.joy.scene.pojo.vo.JoySceneGroupVO;

public interface JoySceneGroupService {

	JoySceneGroupVO buildVOByPO(JoySceneGroup po);

	FindSceneGroupVOResult findAllSceneGroupVOList();

}
