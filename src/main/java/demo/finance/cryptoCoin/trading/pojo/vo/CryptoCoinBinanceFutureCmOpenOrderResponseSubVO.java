package demo.finance.cryptoCoin.trading.pojo.vo;

import finance.cryptoCoin.binance.future.cm.pojo.dto.CryptoCoinBinanceFutureCmOpenOrderResponseSubDTO;

public class CryptoCoinBinanceFutureCmOpenOrderResponseSubVO extends CryptoCoinBinanceFutureCmOpenOrderResponseSubDTO {

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
		return "CryptoCoinBinanceFutureCmOpenOrderResponseSubVO [orderTimeStr=" + orderTimeStr + ", updateTimeStr="
				+ updateTimeStr + ", orderTypeInSimpleWord=" + orderTypeInSimpleWord + ", getAvgPrice()="
				+ getAvgPrice() + ", getClientOrderId()=" + getClientOrderId() + ", getCumBase()=" + getCumBase()
				+ ", getExecutedQty()=" + getExecutedQty() + ", getOrderId()=" + getOrderId() + ", getOrigQty()="
				+ getOrigQty() + ", getOrigType()=" + getOrigType() + ", getPrice()=" + getPrice()
				+ ", getReduceOnly()=" + getReduceOnly() + ", getSide()=" + getSide() + ", getPositionSide()="
				+ getPositionSide() + ", getStatus()=" + getStatus() + ", getStopPrice()=" + getStopPrice()
				+ ", getClosePosition()=" + getClosePosition() + ", getSymbol()=" + getSymbol() + ", getTime()="
				+ getTime() + ", getTimeInForce()=" + getTimeInForce() + ", getType()=" + getType()
				+ ", getActivatePrice()=" + getActivatePrice() + ", getPriceRate()=" + getPriceRate()
				+ ", getUpdateTime()=" + getUpdateTime() + ", getWorkingType()=" + getWorkingType()
				+ ", getPriceProtect()=" + getPriceProtect() + ", toString()=" + super.toString() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
