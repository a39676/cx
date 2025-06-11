package demo.finance.cryptoCoin.trading.pojo.dto;

import finance.cryptoCoin.binance.future.cm.pojo.dto.CryptoCoinBinanceFutureCmSetOrderDTO;

public class CryptoCoinBinanceFutureCmSetOrderCxDTO extends CryptoCoinBinanceFutureCmSetOrderDTO {

	private Integer orderRepeatCounting;

	public Integer getOrderRepeatCounting() {
		return orderRepeatCounting;
	}

	public void setOrderRepeatCounting(Integer orderRepeatCounting) {
		this.orderRepeatCounting = orderRepeatCounting;
	}

	@Override
	public String toString() {
		return "CryptoCoinBinanceFutureCmSetOrderCxDTO [orderRepeatCounting=" + orderRepeatCounting + ", userId="
				+ userId + ", userNickname=" + userNickname + ", totpCode=" + totpCode + ", exchangeCode="
				+ exchangeCode + "]";
	}

}
