package demo.joy.scene.pojo.dto;

public class EditJoySceneGroupDTO {

	private String sceneGroupPK;
	private String sceneGroupName;
	private String remark;
	private Integer weight = 0;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "EditJoySceneGroupDTO [sceneGroupPK=" + sceneGroupPK + ", sceneGroupName=" + sceneGroupName + ", remark="
				+ remark + ", weight=" + weight + "]";
	}

}
