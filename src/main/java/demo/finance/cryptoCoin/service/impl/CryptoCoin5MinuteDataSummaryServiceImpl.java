package demo.finance.cryptoCoin.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.mapper.CryptoCoinPrice1minuteMapper;
import demo.finance.cryptoCoin.mapper.CryptoCoinPrice5minuteMapper;
import demo.finance.cryptoCoin.pojo.constant.CryptoCoinConstant;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPrice1minute;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPrice1minuteExample;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPrice5minute;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPrice5minuteExample;
import demo.finance.cryptoCoin.service.CryptoCoin5MinuteDataSummaryService;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

@Service
public class CryptoCoin5MinuteDataSummaryServiceImpl extends CryptoCoinCommonService
		implements CryptoCoin5MinuteDataSummaryService {

	@Autowired
	private CryptoCoinPrice1minuteMapper cacheMapper;
	@Autowired
	private CryptoCoinPrice5minuteMapper summaryMapper;
	
	@Override
	public CommonResult summaryHistoryData() {
		CommonResult r = new CommonResult();
		
		int minuteStepLong = 5;
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime thereStepBefore = nextStepTime(now, minuteStepLong).minusMinutes(minuteStepLong * 3);
		
		
		for(CryptoCoinType coinType : CryptoCoinType.values()) {
			for(CurrencyType currencyType : CurrencyType.values()) {
				for(LocalDateTime i = thereStepBefore; i.isBefore(now); i = nextStepTime(i, minuteStepLong)) {
					handleHistoryDataList(i, coinType, currencyType);
				}
			}
		}
		
		return r;
	}
	
	private void handleHistoryDataList(LocalDateTime startTime, CryptoCoinType coinType, CurrencyType currencyType) {
		LocalDateTime endTime = startTime.plusMinutes(5);
		
		CryptoCoinPrice1minuteExample cacheExample = new CryptoCoinPrice1minuteExample();
		cacheExample.createCriteria()
		.andCoinTypeEqualTo(coinType.getCode())
		.andCurrencyTypeEqualTo(currencyType.getCode())
		.andStartTimeGreaterThanOrEqualTo(startTime)
		.andStartTimeLessThan(endTime);
		List<CryptoCoinPrice1minute> cacheList = cacheMapper.selectByExample(cacheExample);
		if(cacheList == null || cacheList.isEmpty()) {
			return;
		}
		
		CryptoCoinPrice5minuteExample example = new CryptoCoinPrice5minuteExample();
		example.createCriteria()
		.andCoinTypeEqualTo(coinType.getCode())
		.andCurrencyTypeEqualTo(currencyType.getCode())
		.andStartTimeEqualTo(startTime)
		;
		List<CryptoCoinPrice5minute> poList = summaryMapper.selectByExample(example);
		CryptoCoinPrice5minute po = null;
		boolean newPOFlag = false;
		if(poList == null || poList.isEmpty()) {
			newPOFlag = true;
			po = new CryptoCoinPrice5minute();
			po.setId(snowFlake.getNextId());
			po.setCoinType(coinType.getCode());
			po.setCurrencyType(currencyType.getCode());
		} else {
			po = poList.get(0);
		}
		
		for(CryptoCoinPrice1minute cache : cacheList) {
			if(po.getStartTime() == null || cache.getStartTime().isBefore(po.getStartTime())) {
				po.setStartTime(cache.getStartTime());
				po.setStartPrice(cache.getStartPrice());
			}
			if(po.getEndTime() == null || cache.getStartTime().isAfter(po.getEndTime())) {
				po.setEndTime(cache.getStartTime().plusMinutes(1));
				po.setEndPrice(cache.getEndPrice());
			}
			if(po.getHighPrice() == null || po.getHighPrice().compareTo(cache.getHighPrice()) < 0) {
				po.setHighPrice(cache.getHighPrice());
			}
			if(po.getLowPrice() == null || po.getLowPrice().compareTo(cache.getLowPrice()) > 0) {
				po.setLowPrice(cache.getLowPrice());
			}
		}
		
		if(newPOFlag) {
			summaryMapper.insertSelective(po);
		} else {
			summaryMapper.updateByPrimaryKeySelective(po);
		}
	}

	@Override
	public CommonResult deleteExpiredCacheData() {
		CommonResult r = new CommonResult();

		LocalDateTime expriedTime = LocalDateTime.now()
				.minusHours(CryptoCoinConstant.CRYPTO_COIN_5MINUTE_DATA_LIVE_HOURS);

		CryptoCoinPrice5minuteExample example = new CryptoCoinPrice5minuteExample();
		example.createCriteria().andCreateTimeLessThan(expriedTime);
		summaryMapper.deleteByExample(example);
		r.setIsSuccess();
		return r;
	}
}
