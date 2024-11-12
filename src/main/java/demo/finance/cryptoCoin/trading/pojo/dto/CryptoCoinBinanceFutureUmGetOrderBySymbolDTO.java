package demo.finance.cryptoCoin.trading.pojo.dto;

import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionCommonDTO;

public class CryptoCoinBinanceFutureUmGetOrderBySymbolDTO extends CryptoCoinInteractionCommonDTO {

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
