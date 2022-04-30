package demo.finance.cryptoCoin.mining.pojo.dto;

import java.math.BigDecimal;

public class CryptoCoinShareCalculateDTO {

	private String machineIdStr;
	private String markDateStr;
	private BigDecimal getCoinCounting;

	public String getMachineIdStr() {
		return machineIdStr;
	}

	public void setMachineIdStr(String machineIdStr) {
		this.machineIdStr = machineIdStr;
	}

	public String getMarkDateStr() {
		return markDateStr;
	}

	public void setMarkDateStr(String markDateStr) {
		this.markDateStr = markDateStr;
	}

	public BigDecimal getGetCoinCounting() {
		return getCoinCounting;
	}

	public void setGetCoinCounting(BigDecimal getCoinCounting) {
		this.getCoinCounting = getCoinCounting;
	}

	@Override
	public String toString() {
		return "CryptoCoinShareCalculateDTO [machineIdStr=" + machineIdStr + ", markDateStr=" + markDateStr
				+ ", getCoinCounting=" + getCoinCounting + "]";
	}

}
