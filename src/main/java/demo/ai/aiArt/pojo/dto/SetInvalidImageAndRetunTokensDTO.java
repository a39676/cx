package demo.ai.aiArt.pojo.dto;

public class SetInvalidImageAndRetunTokensDTO {

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
		return "SetInvalidImageAndRetunTokensDTO [jobPk=" + jobPk + ", imgPk=" + imgPk + "]";
	}

}
