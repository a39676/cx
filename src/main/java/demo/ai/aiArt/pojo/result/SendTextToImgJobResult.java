package demo.ai.aiArt.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;

public class SendTextToImgJobResult extends CommonResult {

	private String jobPk;

	public String getJobPk() {
		return jobPk;
	}

	public void setJobPk(String jobPk) {
		this.jobPk = jobPk;
	}

	@Override
	public String toString() {
		return "SendTextToImgJobResult [jobPk=" + jobPk + "]";
	}

}
