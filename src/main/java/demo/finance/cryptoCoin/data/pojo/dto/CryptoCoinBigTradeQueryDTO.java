package demo.finance.cryptoCoin.data.pojo.dto;

public class CryptoCoinBigTradeQueryDTO {

	private String symbol;
	private Integer start;
	private Integer end;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "CryptoCoinBigTradeQueryDTO [symbol=" + symbol + ", start=" + start + ", end=" + end + "]";
	}

}
