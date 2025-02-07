package demo.finance.cryptoCoin.trading.pojo.vo;

import finance.cryptoCoin.binance.future.um.pojo.dto.CryptoCoinBinanceFutureCmPositionDetailDTO;

public class CryptoCoinBinanceFutureCmPositionDetailVO extends CryptoCoinBinanceFutureCmPositionDetailDTO
		implements Comparable<CryptoCoinBinanceFutureCmPositionDetailVO> {

	@Override
	public int compareTo(CryptoCoinBinanceFutureCmPositionDetailVO o) {
		return compareWithLastTime(o);
	}

	private int compareWithLastTime(CryptoCoinBinanceFutureCmPositionDetailVO o) {
		if (this.getUpdateTime() > o.getUpdateTime()) {
			return -1;
		}
		if (this.getUpdateTime() == o.getUpdateTime()) {
			return 0;
		}
		return 1;
	}
}
