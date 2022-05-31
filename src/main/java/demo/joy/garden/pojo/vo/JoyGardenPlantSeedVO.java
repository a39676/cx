package demo.joy.garden.pojo.vo;

public class JoyGardenPlantSeedVO extends JoyGardenPlantGrowingStageVO {

	private String plantName;

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	@Override
	public String toString() {
		return "JoyGardenPlantSeedVO [plantName=" + plantName + ", getPk()=" + getPk() + ", getStageName()="
				+ getStageName() + ", getLivingMinute()=" + getLivingMinute() + ", getImgUrlPath()=" + getImgUrlPath()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}

}
