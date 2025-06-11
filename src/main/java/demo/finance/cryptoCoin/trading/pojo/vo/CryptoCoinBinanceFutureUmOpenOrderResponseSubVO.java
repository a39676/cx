package demo.finance.cryptoCoin.trading.pojo.vo;

import finance.cryptoCoin.binance.future.um.pojo.dto.CryptoCoinBinanceFutureUmOrderDTO;

public class CryptoCoinBinanceFutureUmOpenOrderResponseSubVO extends CryptoCoinBinanceFutureUmOrderDTO {

	private String orderTimeStr;
	private String updateTimeStr;
	private String orderTypeInSimpleWord;

	public String getOrderTimeStr() {
		return orderTimeStr;
	}

	public void setOrderTimeStr(String orderTimeStr) {
		this.orderTimeStr = orderTimeStr;
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public String getOrderTypeInSimpleWord() {
		return orderTypeInSimpleWord;
	}

	public void setOrderTypeInSimpleWord(String orderTypeInSimpleWord) {
		this.orderTypeInSimpleWord = orderTypeInSimpleWord;
	}

	@Override
	public String toString() {
		return "CryptoCoinBinanceFutureUmOpenOrderResponseSubVO [orderTimeStr=" + orderTimeStr + ", updateTimeStr="
				+ updateTimeStr + ", orderTypeInSimpleWord=" + orderTypeInSimpleWord + ", getAvgPrice()="
				+ getAvgPrice() + ", getClientOrderId()=" + getClientOrderId() + ", getCumQuote()=" + getCumQuote()
				+ ", getExecutedQty()=" + getExecutedQty() + ", getOrderId()=" + getOrderId() + ", getOrigQty()="
				+ getOrigQty() + ", getOrigType()=" + getOrigType() + ", getPrice()=" + getPrice()
				+ ", getReduceOnly()=" + getReduceOnly() + ", getSide()=" + getSide() + ", getPositionSide()="
				+ getPositionSide() + ", getStatus()=" + getStatus() + ", getStopPrice()=" + getStopPrice()
				+ ", getClosePosition()=" + getClosePosition() + ", getSymbol()=" + getSymbol() + ", getTime()="
				+ getTime() + ", getTimeInForce()=" + getTimeInForce() + ", getType()=" + getType()
				+ ", getActivatePrice()=" + getActivatePrice() + ", getPriceRate()=" + getPriceRate()
				+ ", getUpdateTime()=" + getUpdateTime() + ", getWorkingType()=" + getWorkingType()
				+ ", getPriceProtect()=" + getPriceProtect() + ", getPriceMatch()=" + getPriceMatch()
				+ ", getSelfTradePreventionMode()=" + getSelfTradePreventionMode() + ", getGoodTillDate()="
				+ getGoodTillDate() + ", toString()=" + super.toString() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + "]";
	}

}
