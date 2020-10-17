package demo.finance.cryptoCoin.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.common.pojo.result.CommonResultCX;
import demo.finance.cryptoCoin.pojo.constant.CryptoCoinConstant;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPrice;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPriceExample;
import demo.finance.cryptoCoin.service.CryptoCoinPriceService;
import finance.cryptoCoin.pojo.dto.CryptoCoinNewPriceDTO;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

@Service
public class CryptoCoinPriceServiceImpl extends CryptoCoinCommonService implements CryptoCoinPriceService {

	@Override
	public CommonResult reciveCoinPrice(CryptoCoinNewPriceDTO dto) {
		CommonResult result = new CommonResult();

		CryptoCoinPrice tmpPO = null;
		int insertCount = 0;
		if (dto.getPrice() == null || StringUtils.isAnyBlank(dto.getCroptoCoinName(), dto.getCurrency())) {
			return result;
		}

		CryptoCoinType coinType = CryptoCoinType.getType(dto.getCroptoCoinName());
		if (coinType == null) {
			return result;
		}

		CurrencyType currencyType = CurrencyType.getType(dto.getCurrency());
		if (currencyType == null) {
			return result;
		}

		tmpPO = new CryptoCoinPrice();
		tmpPO.setId(snowFlake.getNextId());
		tmpPO.setPrice(new BigDecimal(dto.getPrice()));
		tmpPO.setCoinType(coinType.getCode());
		tmpPO.setCurrencyType(currencyType.getCode());

		insertCount += cryptoCoinPriceMapper.insertSelective(tmpPO);

		if (insertCount > 0) {
			result.setIsSuccess();
		}

		return result;
	}
	
	@Override
	public CommonResultCX deleteExpiredCacheData() {
		CommonResultCX r = new CommonResultCX();

		LocalDateTime expriedTime = LocalDateTime.now().minusHours(CryptoCoinConstant.CRYPTO_COIN_10MINUTE_DATA_LIVE_HOURS);

		CryptoCoinPriceExample example = new CryptoCoinPriceExample();
		example.createCriteria().andCreateTimeLessThan(expriedTime);
		cryptoCoinPriceMapper.deleteByExample(example);
		r.setIsSuccess();
		return r;
	}
}
