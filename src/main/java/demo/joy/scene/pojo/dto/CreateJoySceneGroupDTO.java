package demo.joy.scene.pojo.dto;

public class CreateJoySceneGroupDTO {

	private String sceneGroupName;
	private String remark;

	public String getSceneGroupName() {
		return sceneGroupName;
	}

	public void setSceneGroupName(String sceneGroupName) {
		this.sceneGroupName = sceneGroupName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "CreateJoySceneGroupDTO [sceneGroupName=" + sceneGroupName + ", remark=" + remark + "]";
	}

}
