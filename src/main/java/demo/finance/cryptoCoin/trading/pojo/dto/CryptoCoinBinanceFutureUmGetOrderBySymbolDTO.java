package demo.finance.cryptoCoin.trading.pojo.dto;

import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionSingleUserCommonDTO;

public class CryptoCoinBinanceFutureUmGetOrderBySymbolDTO extends CryptoCoinInteractionSingleUserCommonDTO {

	private String symbol;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String toString() {
		return "CryptoCoinBinanceFutureUmGetOrderBySymbolDTO [symbol=" + symbol + "]";
	}

}
