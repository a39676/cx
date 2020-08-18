package demo.joy.scene.pojo.vo;

public class JoySceneGroupVO {

	private String sceneGroupPK;
	private String sceneGroupName;
	private String sceneGroupRemark;

	public String getSceneGroupPK() {
		return sceneGroupPK;
	}

	public void setSceneGroupPK(String sceneGroupPK) {
		this.sceneGroupPK = sceneGroupPK;
	}

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

	@Override
	public String toString() {
		return "JoySceneGroupVO [sceneGroupPK=" + sceneGroupPK + ", sceneGroupName=" + sceneGroupName
				+ ", sceneGroupRemark=" + sceneGroupRemark + "]";
	}

}
