package demo.joy.garden.pojo.dto;

public class JoyGardenPlantStageUpdateDTO {

	private String stagePK;

	private String stageName;

	private Integer livingMinute;

	public String getStagePK() {
		return stagePK;
	}

	public void setStagePK(String stagePK) {
		this.stagePK = stagePK;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public Integer getLivingMinute() {
		return livingMinute;
	}

	public void setLivingMinute(Integer livingMinute) {
		this.livingMinute = livingMinute;
	}

	@Override
	public String toString() {
		return "JoyGardenPlantStageUpdateDTO [stagePK=" + stagePK + ", stageName=" + stageName + ", livingMinute="
				+ livingMinute + "]";
	}

}
