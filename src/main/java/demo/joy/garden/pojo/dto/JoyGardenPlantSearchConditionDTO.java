package demo.joy.garden.pojo.dto;

import demo.joy.garden.pojo.type.JoyGardenPlantType;

public class JoyGardenPlantSearchConditionDTO {

	private String name;

	/** {@link JoyGardenPlantType} */
	private Integer plantType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPlantType() {
		return plantType;
	}

	public void setPlantType(Integer plantType) {
		this.plantType = plantType;
	}

	@Override
	public String toString() {
		return "JoyGardenPlantSearchConditionDTO [name=" + name + ", plantType=" + plantType + "]";
	}

}
