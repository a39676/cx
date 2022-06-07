package demo.joy.garden.pojo.vo;

public class JoyGardenPlantGrowingStageVO {

	private String pk;

	private String stageName;

	private Integer livingMinute;

	private String imgUrlPath;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
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

	public String getImgUrlPath() {
		return imgUrlPath;
	}

	public void setImgUrlPath(String imgUrlPath) {
		this.imgUrlPath = imgUrlPath;
	}

	@Override
	public String toString() {
		return "JoyGardenPlantGrowingStageVO [pk=" + pk + ", stageName=" + stageName + ", livingMinute=" + livingMinute
				+ ", imgUrlPath=" + imgUrlPath + "]";
	}

}
