package demo.joy.scene.pojo.vo;

public class JoySceneGroupVO {

	private String pk;
	private String name;
	private String remark;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "JoySceneGroupVO [pk=" + pk + ", name=" + name + ", remark=" + remark + "]";
	}

}
