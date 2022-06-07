package demo.joy.garden.pojo.vo;

import demo.joy.garden.pojo.type.JoyGardenLandType;

public class JoyGardenLandVO {

	private String pk;

	private Long userId;

	private JoyGardenLandType landType;

	private Integer landLevel;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public JoyGardenLandType getLandType() {
		return landType;
	}

	public void setLandType(JoyGardenLandType landType) {
		this.landType = landType;
	}

	public Integer getLandLevel() {
		return landLevel;
	}

	public void setLandLevel(Integer landLevel) {
		this.landLevel = landLevel;
	}

	@Override
	public String toString() {
		return "JoyGardenLandVO [pk=" + pk + ", userId=" + userId + ", landType=" + landType + ", landLevel="
				+ landLevel + "]";
	}

}
