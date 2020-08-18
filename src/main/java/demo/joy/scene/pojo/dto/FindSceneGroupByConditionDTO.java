package demo.joy.scene.pojo.dto;

public class FindSceneGroupByConditionDTO {

	private String sceneGroupName;
	private String sceneGroupRemark;
	private String sceneGroupPK;

	public String getSceneGroupName() {
		return sceneGroupName;
	}

	public void setSceneGroupName(String sceneGroupName) {
		this.sceneGroupName = sceneGroupName;
	}

	public String getSceneGroupRemark() {
		return sceneGroupRemark;
	}

	public void setSceneGroupRemark(String sceneGroupRemark) {
		this.sceneGroupRemark = sceneGroupRemark;
	}

	public String getSceneGroupPK() {
		return sceneGroupPK;
	}

	public void setSceneGroupPK(String sceneGroupPK) {
		this.sceneGroupPK = sceneGroupPK;
	}

	@Override
	public String toString() {
		return "FindSceneGroupByConditionDTO [sceneGroupName=" + sceneGroupName + ", sceneGroupRemark="
				+ sceneGroupRemark + ", sceneGroupPK=" + sceneGroupPK + "]";
	}

}
