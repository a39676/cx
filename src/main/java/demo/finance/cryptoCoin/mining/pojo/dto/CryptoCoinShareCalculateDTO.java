package demo.finance.cryptoCoin.mining.pojo.dto;

import java.math.BigDecimal;

public class CryptoCoinShareCalculateDTO {

	private String machinePK;
	private String markDateStr;
	private BigDecimal getCoinCounting;

	public String getMachinePK() {
		return machinePK;
	}

	public void setMachinePK(String machinePK) {
		this.machinePK = machinePK;
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
		return "CryptoCoinShareCalculateDTO [machinePK=" + machinePK + ", markDateStr=" + markDateStr
				+ ", getCoinCounting=" + getCoinCounting + "]";
	}

}
