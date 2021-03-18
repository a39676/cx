package demo.finance.cryptoCoin.data.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinCatalogMapper;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalogExample;
import demo.finance.cryptoCoin.data.service.CryptoCoinCatalogService;

@Service
public class CryptoCoinCatalogServiceImpl extends CryptoCoinCommonService implements CryptoCoinCatalogService {

	@Autowired
	private CryptoCoinCatalogMapper mapper;
	
	@Override
	public CryptoCoinCatalog findCatalog(String coinName) {
		if(StringUtils.isBlank(coinName)) {
			return null;
		}
		CryptoCoinCatalogExample example = new CryptoCoinCatalogExample();
		example.createCriteria().andCoinNameEnShortEqualTo(coinName.toUpperCase()).andIsdeleteEqualTo(false);
		List<CryptoCoinCatalog> poList = mapper.selectByExample(example);
		if(poList.isEmpty()) {
			return null;
		}
		return poList.get(0);
	}
	
	@Override
	public CryptoCoinCatalog findCatalog(Long id) {
		return mapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<CryptoCoinCatalog> getAllCatalog() {
		CryptoCoinCatalogExample example = new CryptoCoinCatalogExample();
		example.createCriteria().andIsdeleteEqualTo(false);
		return mapper.selectByExample(example);
	}
}
