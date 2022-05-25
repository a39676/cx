package demo.joy.garden.pojo.dto;

public class JoyGardenCreatePlantStageDTO {

	private String plantPK;

	private String stageName;

	private String stageImgSrc;

	private Integer livingMinute;

	private Boolean cycleStage = false;

	public String getPlantPK() {
		return plantPK;
	}

	public void setPlantPK(String plantPK) {
		this.plantPK = plantPK;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public String getStageImgSrc() {
		return stageImgSrc;
	}

	public void setStageImgSrc(String stageImgSrc) {
		this.stageImgSrc = stageImgSrc;
	}

	public Integer getLivingMinute() {
		return livingMinute;
	}

	public void setLivingMinute(Integer livingMinute) {
		this.livingMinute = livingMinute;
	}

	public Boolean getCycleStage() {
		return cycleStage;
	}

	public void setCycleStage(Boolean cycleStage) {
		this.cycleStage = cycleStage;
	}

	@Override
	public String toString() {
		return "JoyGardenCreatePlantStageDTO [plantPK=" + plantPK + ", stageName=" + stageName + ", stageImgSrc="
				+ stageImgSrc + ", livingMinute=" + livingMinute + ", cycleStage=" + cycleStage + "]";
	}

}
