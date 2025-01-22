package demo.finance.cryptoCoin.trading.pojo.dto;

import java.math.BigDecimal;

import finance.cryptoCoin.binance.pojo.type.BinanceOrderSideType;
import finance.cryptoCoin.binance.pojo.type.BinanceOrderTypeType;
import finance.cryptoCoin.binance.pojo.type.BinancePositionSideType;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionMultipleUserCommonDTO;

public class CryptoCoinBinanceFutureCmCancelMultipleOrderMultipleUserDTO
		extends CryptoCoinInteractionMultipleUserCommonDTO {

	private String symbol;
	/** {@link BinanceOrderSideType} */
	private Integer orderSideCode;
	/** {@link BinancePositionSideType} */
	private Integer positionSideCode;
	/** {@link BinanceOrderTypeType} */
	private Integer orderTypeCode;
	private BigDecimal cancelIfOrderPriceHigherThan;
	private BigDecimal cancelIfOrderPriceLowerThan;
	private Boolean cancelAllOpenOrder = false;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
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

	public BigDecimal getCancelIfOrderPriceHigherThan() {
		return cancelIfOrderPriceHigherThan;
	}

	public void setCancelIfOrderPriceHigherThan(BigDecimal cancelIfOrderPriceHigherThan) {
		this.cancelIfOrderPriceHigherThan = cancelIfOrderPriceHigherThan;
	}

	public BigDecimal getCancelIfOrderPriceLowerThan() {
		return cancelIfOrderPriceLowerThan;
	}

	public void setCancelIfOrderPriceLowerThan(BigDecimal cancelIfOrderPriceLowerThan) {
		this.cancelIfOrderPriceLowerThan = cancelIfOrderPriceLowerThan;
	}

	public Boolean getCancelAllOpenOrder() {
		return cancelAllOpenOrder;
	}

	public void setCancelAllOpenOrder(Boolean cancelAllOpenOrder) {
		this.cancelAllOpenOrder = cancelAllOpenOrder;
	}

	@Override
	public String toString() {
		return "CryptoCoinBinanceFutureCmCancelMultipleOrderMultipleUserDTO [symbol=" + symbol + ", orderSideCode="
				+ orderSideCode + ", positionSideCode=" + positionSideCode + ", orderTypeCode=" + orderTypeCode
				+ ", cancelIfOrderPriceHigherThan=" + cancelIfOrderPriceHigherThan + ", cancelIfOrderPriceLowerThan="
				+ cancelIfOrderPriceLowerThan + ", cancelAllOpenOrder=" + cancelAllOpenOrder + "]";
	}

}
