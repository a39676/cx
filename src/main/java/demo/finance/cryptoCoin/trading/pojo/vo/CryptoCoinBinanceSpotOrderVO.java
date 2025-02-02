package demo.finance.cryptoCoin.trading.pojo.vo;

import finance.cryptoCoin.binance.spot.pojo.dto.CryptoCoinBinanceSpotOrderDTO;

public class CryptoCoinBinanceSpotOrderVO extends CryptoCoinBinanceSpotOrderDTO
		implements Comparable<CryptoCoinBinanceSpotOrderVO> {

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

	@Override
	public int compareTo(CryptoCoinBinanceSpotOrderVO o) {
		int c = compareWithSymbol(o);
		if (c != 0) {
			return c;
		}
		c = compareWithSide(o);
		if (c != 0) {
			return c;
		}
		c = compareWithStatus(o);
		if (c != 0) {
			return c;
		}
		return compareWithPrice(o);
	}

	private int compareWithSymbol(CryptoCoinBinanceSpotOrderVO o) {
		return getSymbol().compareTo(o.getSymbol());
	}

	private int compareWithSide(CryptoCoinBinanceSpotOrderVO o) {
		return getSide().compareTo(o.getSide());
	}

	private int compareWithStatus(CryptoCoinBinanceSpotOrderVO o) {
		return getStatus().compareTo(o.getStatus());
	}

	private int compareWithPrice(CryptoCoinBinanceSpotOrderVO o) {
		return getPrice().compareTo(o.getPrice());
	}
}
