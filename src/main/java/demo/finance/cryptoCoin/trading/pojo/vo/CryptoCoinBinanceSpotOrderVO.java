package demo.finance.cryptoCoin.trading.pojo.vo;

import finance.cryptoCoin.binance.spot.pojo.dto.CryptoCoinBinanceSpotOrderDTO;

public class CryptoCoinBinanceSpotOrderVO extends CryptoCoinBinanceSpotOrderDTO {

	private String timeStr;
	private String updateTimeStr;
	private String workingTimeStr;

	public String getTimeStr() {
		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public String getWorkingTimeStr() {
		return workingTimeStr;
	}

	public void setWorkingTimeStr(String workingTimeStr) {
		this.workingTimeStr = workingTimeStr;
	}

	@Override
	public String toString() {
		return "CryptoCoinBinanceSpotOrderVO [timeStr=" + timeStr + ", updateTimeStr=" + updateTimeStr
				+ ", workingTimeStr=" + workingTimeStr + "]";
	}

}
