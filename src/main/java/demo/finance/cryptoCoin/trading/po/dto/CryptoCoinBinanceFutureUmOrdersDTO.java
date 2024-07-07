package demo.finance.cryptoCoin.trading.po.dto;

import java.util.List;

import finance.cryptoCoin.binance.pojo.type.BinanceOrderSideType;
import finance.cryptoCoin.binance.pojo.type.BinancePositionSideType;

public class CryptoCoinBinanceFutureUmOrdersDTO {

	private List<String> symbols;
	private Double amount;
	/** {@link BinanceOrderSideType} */
	private Integer orderSideCode;
	/** {@link BinancePositionSideType} */
	private Integer positionSideCode;

	public List<String> getSymbols() {
		return symbols;
	}

	public void setSymbols(List<String> symbols) {
		this.symbols = symbols;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getOrderSideCode() {
		return orderSideCode;
	}

	public void setOrderSideCode(Integer orderSideCode) {
		this.orderSideCode = orderSideCode;
	}

	public Integer getPositionSideCode() {
		return positionSideCode;
	}

	public void setPositionSideCode(Integer positionSideCode) {
		this.positionSideCode = positionSideCode;
	}

	@Override
	public String toString() {
		return "CryptoCoinBinanceFutureUmOrdersDTO [amount=" + amount + ", orderSideCode=" + orderSideCode
				+ ", positionSideCode=" + positionSideCode + "]";
	}

}
