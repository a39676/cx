package demo.finance.cryptoCoin.data.pojo.bo;

import java.util.List;

public class CryptoCompareSocketConfigBO {

	private String apiKey;
	private String uri;
	private List<String> tragetCoins;
	private List<String> targetCurrency;

	public List<String> getTargetCurrency() {
		return targetCurrency;
	}

	public void setTargetCurrency(List<String> targetCurrency) {
		this.targetCurrency = targetCurrency;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public List<String> getTragetCoins() {
		return tragetCoins;
	}

	public void setTragetCoins(List<String> tragetCoins) {
		this.tragetCoins = tragetCoins;
	}

	@Override
	public String toString() {
		return "CryptoCompareSocketConfigBO [apiKey=" + apiKey + ", uri=" + uri + ", tragetCoins=" + tragetCoins
				+ ", targetCurrency=" + targetCurrency + "]";
	}

}
