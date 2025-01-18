package demo.finance.cryptoCoin.trading.pojo.vo;

import finance.cryptoCoin.binance.future.cm.pojo.dto.CryptoCoinBinanceFutureCmOpenOrderResponseSubDTO;

public class CryptoCoinBinanceFutureCmOpenOrderResponseSubVO extends CryptoCoinBinanceFutureCmOpenOrderResponseSubDTO
		implements Comparable<CryptoCoinBinanceFutureCmOpenOrderResponseSubVO> {

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

	@Override
	public int compareTo(CryptoCoinBinanceFutureCmOpenOrderResponseSubVO o) {
		int c = compareWithSymbol(o);
		if (c != 0) {
			return c;
		}
		c = compareWithOrderSide(o);
		if (c != 0) {
			return c;
		}
		c = compareWithPositionSide(o);
		if (c != 0) {
			return c;
		}
		c = compareWithStatus(o);
		if (c != 0) {
			return c;
		}
		return compareWithPrice(o);
	}

	private int compareWithSymbol(CryptoCoinBinanceFutureCmOpenOrderResponseSubVO o) {
		return getSymbol().compareTo(o.getSymbol());
	}

	private int compareWithOrderSide(CryptoCoinBinanceFutureCmOpenOrderResponseSubVO o) {
		return getSide().compareTo(o.getSide());
	}

	private int compareWithPositionSide(CryptoCoinBinanceFutureCmOpenOrderResponseSubVO o) {
		return getPositionSide().compareTo(o.getPositionSide());
	}

	private int compareWithStatus(CryptoCoinBinanceFutureCmOpenOrderResponseSubVO o) {
		return getStatus().compareTo(o.getStatus());
	}

	private int compareWithPrice(CryptoCoinBinanceFutureCmOpenOrderResponseSubVO o) {
		return getPrice().compareTo(o.getPrice());
	}
}
