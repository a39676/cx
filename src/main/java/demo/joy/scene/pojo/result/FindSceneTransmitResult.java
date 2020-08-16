package demo.joy.scene.pojo.result;

import java.util.List;

import demo.joy.common.pojo.result.JoyCommonResult;

public class FindSceneTransmitResult extends JoyCommonResult {

	private List<Long> toSceneIdList;

	public List<Long> getToSceneIdList() {
		return toSceneIdList;
	}

	public void setToSceneIdList(List<Long> toSceneIdList) {
		this.toSceneIdList = toSceneIdList;
	}

	@Override
	public String toString() {
		return "FindSceneTransmitResult [toSceneIdList=" + toSceneIdList + "]";
	}

}
