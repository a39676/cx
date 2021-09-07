package demo.finance.cryptoCoin.data.service;

import java.util.List;
import java.util.Set;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.vo.CryptoCoinCatalogVO;

public interface CryptoCoinCatalogService {

	CryptoCoinCatalog findCatalog(String coinName);

	CryptoCoinCatalog findCatalog(Long id);

	List<CryptoCoinCatalog> getAllCatalog();

	CommonResult addCatalog(String enShortName);

	List<CryptoCoinCatalogVO> getAllCatalogVO();

	CryptoCoinCatalog findLastCatalog();

	List<CryptoCoinCatalogVO> getSubscriptionCatalogVOList();

	void addSubscriptionCatalog(String catalog);

	void removeSubscriptionCatalog(String catalog);

	void removeAllSubscriptionCatalog();

	List<CryptoCoinCatalog> findCatalog(List<String> coinNameList);

	void addSubscriptionCatalog(List<String> catalogList);

	void removeSubscriptionCatalog(List<String> catalogList);

	Set<String> getSubscriptionNameList();

}
