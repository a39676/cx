package demo.finance.cryptoCoin.data.pojo.dto;

public class InsertCryptoCoinLowPriceNoticeSettingDTO extends InsertCryptoCoinPriceNoticeSettingDTO {

	private String botName;

	public String getBotName() {
		return botName;
	}

	public void setBotName(String botName) {
		this.botName = botName;
	}

	@Override
	public String toString() {
		return "InsertCryptoCoinLowPriceNoticeSettingDTO [botName=" + botName + ", getCoinType()=" + getCoinType()
				+ ", getCoinTypeCode()=" + getCoinTypeCode() + ", getCurrencyType()=" + getCurrencyType()
				+ ", getMaxPrice()=" + getMaxPrice() + ", getMinPrice()=" + getMinPrice() + ", getOriginalPrice()="
				+ getOriginalPrice() + ", getPricePercentage()=" + getPricePercentage() + ", getTimeUnitOfDataWatch()="
				+ getTimeUnitOfDataWatch() + ", getPriceRange()=" + getPriceRange()
				+ ", getFluctuationSpeedPercentage()=" + getFluctuationSpeedPercentage() + ", getValidTime()="
				+ getValidTime() + ", getTimeRangeOfDataWatch()=" + getTimeRangeOfDataWatch() + ", getNoticeCount()="
				+ getNoticeCount() + ", getTelegramChatPK()=" + getTelegramChatPK() + ", getTimeUnitOfNoticeInterval()="
				+ getTimeUnitOfNoticeInterval() + ", getTimeRangeOfNoticeInterval()=" + getTimeRangeOfNoticeInterval()
				+ ", getStartNoticeTime()=" + getStartNoticeTime() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
