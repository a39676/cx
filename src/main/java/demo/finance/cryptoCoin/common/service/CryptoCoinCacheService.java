package demo.finance.cryptoCoin.common.service;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinSymbolLeverage;

@Scope("singleton")
@Service
public class CryptoCoinCacheService extends CommonService {

	private List<CryptoCoinSymbolLeverage> lastLeverageList;

	public List<CryptoCoinSymbolLeverage> getLastLeverageList() {
		return lastLeverageList;
	}

	public void setLastLeverageList(List<CryptoCoinSymbolLeverage> lastLeverageList) {
		this.lastLeverageList = lastLeverageList;
	}

}
