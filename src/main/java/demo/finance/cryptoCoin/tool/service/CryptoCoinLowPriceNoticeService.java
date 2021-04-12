package demo.finance.cryptoCoin.tool.service;

import java.util.List;

import demo.finance.cryptoCoin.data.pojo.vo.CryptoCoinCatalogVO;

public interface CryptoCoinLowPriceNoticeService {

	void setNewLowPriceSubscription();

	List<CryptoCoinCatalogVO> getLowPriceSubscriptionCatalog();

	void setLowPriceCoinWatching();

}
