package demo.finance.cryptoCoin.data.service.impl;

import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice1minuteMapper;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1minute;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1minuteExample;
import demo.finance.cryptoCoin.data.service.CryptoCoinCatalogService;
import demo.finance.cryptoCoin.data.service.CryptoCoinIndexDataService;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.type.CurrencyTypeForCryptoCoin;

@Service
public class CryptoCoinIndexDataServiceImpl extends CryptoCoinCommonService implements CryptoCoinIndexDataService {

	@Autowired
	private CryptoCoinPrice1minuteMapper mapper;
	@Autowired
	private CryptoCoinCatalogService coinCatalogService;

	@Override
	public void receiveData(CryptoCoinPriceCommonDataBO bo) {
		if (bo == null) {
			return;
		}

		String symbol = bo.getSymbol();
		CryptoCoinCatalog coinType = coinCatalogService
				.findCatalog(symbol.replaceAll(defaultCyrrencyTypeForCryptoCoin.getName(), ""));
		CurrencyTypeForCryptoCoin currencyType = defaultCyrrencyTypeForCryptoCoin;
		if (coinType == null || currencyType == null) {
			return;
		}
		CryptoCoinPrice1minuteExample example = new CryptoCoinPrice1minuteExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getId()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeEqualTo(bo.getStartTime());

		bo.setVolume(bo.getVolume().setScale(SCALE_FOR_PRICE_CALCULATE, RoundingMode.HALF_UP));
		bo.setEndPrice(bo.getEndPrice().setScale(SCALE_FOR_PRICE_CALCULATE, RoundingMode.HALF_UP));
		
		List<CryptoCoinPrice1minute> poList = mapper.selectByExample(example);
		CryptoCoinPrice1minute po = null;
		if (poList == null || poList.isEmpty()) {
			po = new CryptoCoinPrice1minute();
			po.setId(snowFlake.getNextId());
			po.setCoinType(coinType.getId());
			po.setCurrencyType(currencyType.getCode());
//			po.setVolume(bo.getVolume()); // huge number and did NOT used
			po.setStartTime(bo.getStartTime());
			po.setEndTime(po.getStartTime().plusMinutes(1));
			po.setStartPrice(bo.getEndPrice());
			po.setEndPrice(bo.getEndPrice());
			po.setHighPrice(bo.getEndPrice());
			po.setLowPrice(bo.getEndPrice());
			mapper.insertSelective(po);
			return;
		}

		po = poList.get(0);
//		po.setVolume(bo.getVolume()); // huge number and did NOT used
		po.setEndPrice(bo.getEndPrice());
		if (po.getHighPrice().compareTo(bo.getEndPrice()) < 0) {
			po.setHighPrice(bo.getEndPrice());
		}
		if (po.getLowPrice().compareTo(bo.getEndPrice()) > 0) {
			po.setLowPrice(bo.getEndPrice());
		}
		mapper.updateByPrimaryKeySelective(po);
	}
}
