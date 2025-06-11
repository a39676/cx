package demo.finance.cryptoCoin.data.service;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import finance.cryptoCoin.pojo.vo.CryptoCoinCatalogVO;

public interface CryptoCoinCatalogService {

	CryptoCoinCatalog findCatalog(String coinName);

	CryptoCoinCatalog findCatalog(Long id);

	List<CryptoCoinCatalog> getAllCatalog();

	CommonResult addCatalog(String enShortName);

	List<CryptoCoinCatalogVO> getAllCatalogVO();

	CryptoCoinCatalog findLastCatalog();

	List<CryptoCoinCatalog> findCatalog(List<String> coinNameList);

}
