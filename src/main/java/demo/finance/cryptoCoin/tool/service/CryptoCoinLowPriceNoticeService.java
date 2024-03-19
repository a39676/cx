package demo.finance.cryptoCoin.tool.service;

import java.util.List;

import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import finance.cryptoCoin.pojo.vo.CryptoCoinCatalogVO;

public interface CryptoCoinLowPriceNoticeService {

	void setNewLowPriceSubscription();

	List<CryptoCoinCatalog> getLowPriceSubscriptionCatalogList();

	List<CryptoCoinCatalogVO> getLowPriceSubscriptionCatalogVOList();

	void setLowPriceCoinWatching();

}
