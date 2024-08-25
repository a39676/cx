package demo.finance.cryptoCoin.data.pojo.vo;

public class CryptoCoinBigTradeBubbleChartVO {

	private Integer timeGap;
	private Double price;
	private Integer r;
	private Double amount;
	private Double quantity;
	private String eventTime;

	public Integer getTimeGap() {
		return timeGap;
	}

	public void setTimeGap(Integer timeGap) {
		this.timeGap = timeGap;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getR() {
		return r;
	}

	public void setR(Integer r) {
		this.r = r;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	@Override
	public String toString() {
		return "CryptoCoinBigTradeBubbleChartVO [timeGap=" + timeGap + ", price=" + price + ", r=" + r + ", amount="
				+ amount + ", quantity=" + quantity + ", eventTime=" + eventTime + "]";
	}

}
