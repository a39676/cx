package demo.finance.cryptoCoin.data.webSocket.pojo.bo;

public class BinanceWebScoketConnetionKeyBO {

	private String symbol;
	private String interval;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	@Override
	public String toString() {
		return "BinanceWebScoketConnetionKeyBO [symbol=" + symbol + ", interval=" + interval + "]";
	}

}
