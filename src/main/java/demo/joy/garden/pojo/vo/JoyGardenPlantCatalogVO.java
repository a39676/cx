package demo.joy.garden.pojo.vo;

import demo.joy.garden.pojo.type.JoyGardenPlantType;

public class JoyGardenPlantCatalogVO {

	private String pk;

	private String plantName;

	private String creatorName;

	private JoyGardenPlantType plantType;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public JoyGardenPlantType getPlantType() {
		return plantType;
	}

	public void setPlantType(JoyGardenPlantType plantType) {
		this.plantType = plantType;
	}

	@Override
	public String toString() {
		return "JoyGardenPlantCatalogVO [pk=" + pk + ", plantName=" + plantName + ", creatorName=" + creatorName
				+ ", plantType=" + plantType + "]";
	}

}
