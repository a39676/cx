package demo.aiArt.pojo.vo;

public class AiArtImageOnWallVO {

	private String jobPk;
	private String imgPk;

	public String getJobPk() {
		return jobPk;
	}

	public void setJobPk(String jobPk) {
		this.jobPk = jobPk;
	}

	public String getImgPk() {
		return imgPk;
	}

	public void setImgPk(String imgPk) {
		this.imgPk = imgPk;
	}

	@Override
	public String toString() {
		return "AiArtImageOnWallVo [jobPk=" + jobPk + ", imgPk=" + imgPk + "]";
	}

}
