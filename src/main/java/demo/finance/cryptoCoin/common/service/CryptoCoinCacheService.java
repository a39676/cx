package demo.finance.cryptoCoin.common.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinSymbolLeverage;

@Scope("singleton")
@Service
public class CryptoCoinCacheService extends CommonService {

	private Map<String, CryptoCoinSymbolLeverage> binanceLastLeverageMap = new HashMap<>();

	public Map<String, CryptoCoinSymbolLeverage> getBinanceLastLeverageMap() {
		return binanceLastLeverageMap;
	}

	public void setBinanceLastLeverageMap(Map<String, CryptoCoinSymbolLeverage> binanceLastLeverageMap) {
		this.binanceLastLeverageMap = binanceLastLeverageMap;
	}

	@Override
	public String toString() {
		return "CryptoCoinCacheService [binanceLastLeverageMap=" + binanceLastLeverageMap + "]";
	}

}
