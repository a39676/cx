package demo.joy.garden.pojo.dto;

import demo.joy.garden.pojo.type.JoyGardenPlantType;

public class JoyGardenCreatePlantDTO {

	private String plantName;
	/** {@link JoyGardenPlantType} */
	private Integer plantTypeCode;

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public Integer getPlantTypeCode() {
		return plantTypeCode;
	}

	public void setPlantTypeCode(Integer plantTypeCode) {
		this.plantTypeCode = plantTypeCode;
	}

	@Override
	public String toString() {
		return "JoyGardenCreatePlantDTO [plantName=" + plantName + ", plantTypeCode=" + plantTypeCode + "]";
	}

}
