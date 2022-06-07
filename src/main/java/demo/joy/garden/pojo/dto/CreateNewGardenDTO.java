package demo.joy.garden.pojo.dto;

public class CreateNewGardenDTO {

	private String gardenName;

	public String getGardenName() {
		return gardenName;
	}

	public void setGardenName(String gardenName) {
		this.gardenName = gardenName;
	}

	@Override
	public String toString() {
		return "CreateNewGardenDTO [gardenName=" + gardenName + "]";
	}

}
