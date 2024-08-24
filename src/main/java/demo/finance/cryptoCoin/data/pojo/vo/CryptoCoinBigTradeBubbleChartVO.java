package demo.finance.cryptoCoin.data.pojo.vo;

public class CryptoCoinBigTradeBubbleChartVO {

	private Integer timeGap;
	private Double price;
	private Integer r;

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

	@Override
	public String toString() {
		return "CryptoCoinBigTradeVO [timeGap=" + timeGap + ", price=" + price + ", r=" + r + "]";
	}

}
