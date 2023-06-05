package demo.ai.aiArt.pojo.dto;

public class RegenerateImageDTO {

	private String imgPk;
	private String jobPk;

	public String getImgPk() {
		return imgPk;
	}

	public void setImgPk(String imgPk) {
		this.imgPk = imgPk;
	}

	public String getJobPk() {
		return jobPk;
	}

	public void setJobPk(String jobPk) {
		this.jobPk = jobPk;
	}

	@Override
	public String toString() {
		return "RegenerateImageDTO [imgPk=" + imgPk + ", jobPk=" + jobPk + "]";
	}

}
