package demo.finance.cryptoCoin.trading.pojo.dto;

import finance.cryptoCoin.binance.future.um.pojo.dto.CryptoCoinBinanceFutureUmSetOrderV2DTO;

public class CryptoCoinBinanceFutureUmSetOrderV2CxDTO extends CryptoCoinBinanceFutureUmSetOrderV2DTO {

	private Integer orderRepeatCounting;

	public Integer getOrderRepeatCounting() {
		return orderRepeatCounting;
	}

	public void setOrderRepeatCounting(Integer orderRepeatCounting) {
		this.orderRepeatCounting = orderRepeatCounting;
	}

	@Override
	public String toString() {
		return "CryptoCoinBinanceFutureUmSetOrderV2CxDTO [orderRepeatCounting=" + orderRepeatCounting + ", userId="
				+ userId + ", userNickname=" + userNickname + ", totpCode=" + totpCode + ", exchangeCode="
				+ exchangeCode + "]";
	}

}
