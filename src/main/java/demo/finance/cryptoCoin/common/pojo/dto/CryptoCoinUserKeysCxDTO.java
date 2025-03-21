package demo.finance.cryptoCoin.common.pojo.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import finance.cryptoCoin.common.pojo.dto.CryptoCoinUserKeysDTO;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinUserSymbolRateDTO;

public class CryptoCoinUserKeysCxDTO extends CryptoCoinUserKeysDTO {

	private BigDecimal futureUmRateSetting = BigDecimal.ZERO;
	private List<CryptoCoinUserSymbolRateDTO> symbolRateSettingList;
	private Map<String, CryptoCoinUserSymbolRateDTO> symbolRateMap;

	public BigDecimal getFutureUmRateSetting() {
		return futureUmRateSetting;
	}

	public void setFutureUmRateSetting(BigDecimal futureUmRateSetting) {
		this.futureUmRateSetting = futureUmRateSetting;
	}

	public List<CryptoCoinUserSymbolRateDTO> getSymbolRateSettingList() {
		return symbolRateSettingList;
	}

	public void setSymbolRateSettingList(List<CryptoCoinUserSymbolRateDTO> symbolRateSettingList) {
		this.symbolRateSettingList = symbolRateSettingList;
	}

	public Map<String, CryptoCoinUserSymbolRateDTO> getSymbolRateMap() {
		return symbolRateMap;
	}

	public void setSymbolRateMap(Map<String, CryptoCoinUserSymbolRateDTO> symbolRateMap) {
		this.symbolRateMap = symbolRateMap;
	}

	@Override
	public String toString() {
		return "CryptoCoinUserKeysCxDTO [futureUmRateSetting=" + futureUmRateSetting + ", symbolRateSettingList="
				+ symbolRateSettingList + ", symbolRateMap=" + symbolRateMap + ", localUserId=" + localUserId
				+ ", nickname=" + nickname + ", binanceApiKey=" + binanceApiKey + ", binanceSecretKey="
				+ binanceSecretKey + ", gateIoApiKey=" + gateIoApiKey + ", gateIoSecretKey=" + gateIoSecretKey
				+ ", okxApiKey=" + okxApiKey + ", okxSecretKey=" + okxSecretKey + ", okxPassPhrase=" + okxPassPhrase
				+ ", connectBinanceUserDataStream=" + connectBinanceUserDataStream + "]";
	}

}
