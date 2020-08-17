package demo.joy.scene.pojo.result;

import java.util.List;

import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.scene.pojo.vo.JoySceneVO;

public class FindSceneVOListBySceneGroupIdResult extends JoyCommonResult {

	private List<JoySceneVO> sceneVOList;

	public List<JoySceneVO> getSceneVOList() {
		return sceneVOList;
	}

	public void setSceneVOList(List<JoySceneVO> sceneVOList) {
		this.sceneVOList = sceneVOList;
	}

}
