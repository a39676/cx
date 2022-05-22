package demo.joy.garden.pojo.dto;

public class JoyGardenPlantGrowingOptionDTO {

	private String stageName;

	private String stageImgUrl;

	private Integer stageLivingMinute;

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public String getStageImgUrl() {
		return stageImgUrl;
	}

	public void setStageImgUrl(String stageImgUrl) {
		this.stageImgUrl = stageImgUrl;
	}

	public Integer getStageLivingMinute() {
		return stageLivingMinute;
	}

	public void setStageLivingMinute(Integer stageLivingMinute) {
		this.stageLivingMinute = stageLivingMinute;
	}

	@Override
	public String toString() {
		return "JoyGardenPlantGrowingOptionDTO [stageName=" + stageName + ", stageImgUrl=" + stageImgUrl
				+ ", stageLivingMinute=" + stageLivingMinute + "]";
	}

}
