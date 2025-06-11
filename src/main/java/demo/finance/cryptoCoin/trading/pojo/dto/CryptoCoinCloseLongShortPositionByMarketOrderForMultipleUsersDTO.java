package demo.finance.cryptoCoin.trading.pojo.dto;

public class CryptoCoinCloseLongShortPositionByMarketOrderForMultipleUsersDTO
		extends CryptoCoinBinanceFutureCmSetOrderForMultipleUserDTO {

	private Integer orderRepeatCounting;

	public Integer getOrderRepeatCounting() {
		return orderRepeatCounting;
	}

	public void setOrderRepeatCounting(Integer orderRepeatCounting) {
		this.orderRepeatCounting = orderRepeatCounting;
	}

	@Override
	public String toString() {
		return "CryptoCoinCloseLongShortPositionByMarketOrderForMultipleUsersDTO [orderRepeatCounting="
				+ orderRepeatCounting + "]";
	}

}
