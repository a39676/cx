package demo.finance.cryptoCoin.trading.pojo.dto;

import java.math.BigDecimal;

import finance.cryptoCoin.binance.pojo.type.BinanceOrderSideType;
import finance.cryptoCoin.binance.pojo.type.BinanceOrderTypeType;
import finance.cryptoCoin.binance.pojo.type.BinancePositionSideType;
import finance.cryptoCoin.binance.pojo.type.BinanceTimeInForceType;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionMultipleUserCommonDTO;

public class CryptoCoinBinanceFutureUmSetOrderForMultipleUserDTO extends CryptoCoinInteractionMultipleUserCommonDTO {

	private String symbol;
	private BigDecimal quantity;
	/** {@link BinanceOrderSideType} */
	private Integer orderSideCode;
	/** {@link BinancePositionSideType} */
	private Integer positionSideCode;
	/** {@link BinanceOrderTypeType} */
	private Integer orderTypeCode;
	/** {@link BinanceTimeInForceType} */
	private Integer timeInForceCode = BinanceTimeInForceType.GTC.getCode();
	private BigDecimal price;
	private Integer orderRepeatCounting;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
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

	public Integer getOrderTypeCode() {
		return orderTypeCode;
	}

	public void setOrderTypeCode(Integer orderTypeCode) {
		this.orderTypeCode = orderTypeCode;
	}

	public Integer getTimeInForceCode() {
		return timeInForceCode;
	}

	public void setTimeInForceCode(Integer timeInForceCode) {
		this.timeInForceCode = timeInForceCode;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getOrderRepeatCounting() {
		return orderRepeatCounting;
	}

	public void setOrderRepeatCounting(Integer orderRepeatCounting) {
		this.orderRepeatCounting = orderRepeatCounting;
	}

	@Override
	public String toString() {
		return "CryptoCoinBinanceFutureUmSetOrderForMultipleUserDTO [symbol=" + symbol + ", quantity=" + quantity
				+ ", orderSideCode=" + orderSideCode + ", positionSideCode=" + positionSideCode + ", orderTypeCode="
				+ orderTypeCode + ", timeInForceCode=" + timeInForceCode + ", price=" + price + ", orderRepeatCounting="
				+ orderRepeatCounting + ", userIdList=" + userIdList + ", userNicknameList=" + userNicknameList
				+ ", totpCode=" + totpCode + ", exchangeCode=" + exchangeCode + "]";
	}

}
