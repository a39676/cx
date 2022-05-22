package demo.joy.garden.pojo.dto;

import java.util.List;

public class JoyGardenPlantOptionDTO {

	private List<JoyGardenPlantGrowingOptionDTO> growingOptionList;

	public List<JoyGardenPlantGrowingOptionDTO> getGrowingOptionList() {
		return growingOptionList;
	}

	public void setGrowingOptionList(List<JoyGardenPlantGrowingOptionDTO> growingOptionList) {
		this.growingOptionList = growingOptionList;
	}

	@Override
	public String toString() {
		return "JoyGardenPlantOptionDTO [growingOptionList=" + growingOptionList + "]";
	}

}
