package demo.joy.scene.pojo.result;

import java.util.List;

import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.scene.pojo.vo.JoySceneGroupVO;

public class FindSceneGroupVOResult extends JoyCommonResult {

	private List<JoySceneGroupVO> sceneGroupVOList;

	public List<JoySceneGroupVO> getSceneGroupVOList() {
		return sceneGroupVOList;
	}

	public void setSceneGroupVOList(List<JoySceneGroupVO> sceneGroupVOList) {
		this.sceneGroupVOList = sceneGroupVOList;
	}

	@Override
	public String toString() {
		return "FindSceneGroupVOResult [sceneGroupVOList=" + sceneGroupVOList + "]";
	}

}
