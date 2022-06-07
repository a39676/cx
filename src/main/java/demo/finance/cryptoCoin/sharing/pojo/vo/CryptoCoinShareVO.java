package demo.finance.cryptoCoin.sharing.pojo.vo;

import java.time.LocalDateTime;

public class CryptoCoinShareVO {

	private String pk;

	private LocalDateTime outputTime;

	private String outputTimeStr;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public LocalDateTime getOutputTime() {
		return outputTime;
	}

	public void setOutputTime(LocalDateTime outputTime) {
		this.outputTime = outputTime;
	}

	public String getOutputTimeStr() {
		return outputTimeStr;
	}

	public void setOutputTimeStr(String outputTimeStr) {
		this.outputTimeStr = outputTimeStr;
	}

	@Override
	public String toString() {
		return "CryptoCoinShareVO [pk=" + pk + ", outputTime=" + outputTime + ", outputTimeStr=" + outputTimeStr + "]";
	}

}
