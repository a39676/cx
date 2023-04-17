package demo.aiArt.pojo.vo;

import java.util.List;

import demo.aiArt.pojo.dto.TextToImageFromWechatDTO;

public class AiArtGenerateImageVO {

	private String jobPk;
	private TextToImageFromWechatDTO parameter;
	private List<String> imgUrlList;

	public String getJobPk() {
		return jobPk;
	}

	public void setJobPk(String jobPk) {
		this.jobPk = jobPk;
	}

	public TextToImageFromWechatDTO getParameter() {
		return parameter;
	}

	public void setParameter(TextToImageFromWechatDTO parameter) {
		this.parameter = parameter;
	}

	public List<String> getImgUrlList() {
		return imgUrlList;
	}

	public void setImgUrlList(List<String> imgUrlList) {
		this.imgUrlList = imgUrlList;
	}

	@Override
	public String toString() {
		return "AiArtGenerateImageVO [jobPk=" + jobPk + ", parameter=" + parameter + ", imgUrlList=" + imgUrlList + "]";
	}

}
