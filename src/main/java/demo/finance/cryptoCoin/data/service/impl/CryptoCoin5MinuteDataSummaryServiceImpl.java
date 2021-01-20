package demo.finance.cryptoCoin.data.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice5minuteMapper;
import demo.finance.cryptoCoin.data.pojo.bo.CryptoCoinPriceCommonDataBO;
import demo.finance.cryptoCoin.data.pojo.constant.CryptoCoinDataConstant;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice5minute;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice5minuteExample;
import demo.finance.cryptoCoin.data.service.CryptoCoin1MinuteDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin5MinuteDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

@Service
public class CryptoCoin5MinuteDataSummaryServiceImpl extends CryptoCoinCommonService
		implements CryptoCoin5MinuteDataSummaryService {

	private final int minuteStepLong = 5;

	@Autowired
	private CryptoCoin1MinuteDataSummaryService _1MinDataService;
	@Autowired
	private CryptoCoinPrice5minuteMapper _5MinDataMapper;
	@Autowired
	private CryptoCoinPriceCacheService cacheService;

	@Override
	public CommonResult summaryHistoryData() {
		CommonResult r = new CommonResult();

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime thereStepBefore = nextStepTimeByMinute(now, minuteStepLong).minusMinutes(minuteStepLong * 3);

		for (CryptoCoinType coinType : CryptoCoinType.values()) {
			for (CurrencyType currencyType : CurrencyType.values()) {
				for (LocalDateTime i = thereStepBefore; i.isBefore(now); i = nextStepTimeByMinute(i, minuteStepLong)) {
					handleHistoryDataList(i, coinType, currencyType);
				}
			}
		}

		return r;
	}

	private void handleHistoryDataList(LocalDateTime startTime, CryptoCoinType coinType, CurrencyType currencyType) {

		List<CryptoCoinPriceCommonDataBO> cacheDataList = _1MinDataService.getCommonData(coinType, currencyType,
				startTime);
		if (cacheDataList == null || cacheDataList.isEmpty()) {
			return;
		}

		CryptoCoinPrice5minuteExample example = new CryptoCoinPrice5minuteExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getCode()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeEqualTo(startTime);
		List<CryptoCoinPrice5minute> poList = _5MinDataMapper.selectByExample(example);
		CryptoCoinPrice5minute po = null;
		boolean newPOFlag = false;
		if (poList == null || poList.isEmpty()) {
			newPOFlag = true;
			po = new CryptoCoinPrice5minute();
			po.setId(snowFlake.getNextId());
			po.setCoinType(coinType.getCode());
			po.setCurrencyType(currencyType.getCode());
			po.setVolume(BigDecimal.ZERO);
		} else {
			po = poList.get(0);
		}

		for (CryptoCoinPriceCommonDataBO cache : cacheDataList) {
			po = mergeDataWithCache(po, cache);
		}

		if (newPOFlag) {
			_5MinDataMapper.insertSelective(po);
		} else {
			_5MinDataMapper.updateByPrimaryKeySelective(po);
		}
	}

	private CryptoCoinPrice5minute mergeDataWithCache(CryptoCoinPrice5minute po, CryptoCoinPriceCommonDataBO cache) {
		if (po.getStartTime() == null || cache.getStartTime().isBefore(po.getStartTime())
				|| cache.getStartTime().isEqual(po.getStartTime())) {
			po.setStartTime(cache.getStartTime());
			po.setStartPrice(cache.getStartPrice());
		}
		if (po.getEndTime() == null || cache.getEndTime().isAfter(po.getEndTime())
				|| cache.getEndTime().isEqual(po.getEndTime())) {
			po.setEndTime(cache.getEndTime());
			po.setEndPrice(cache.getEndPrice());
		}
		if (po.getHighPrice() == null || po.getHighPrice().compareTo(cache.getHighPrice()) < 0) {
			po.setHighPrice(cache.getHighPrice());
		}
		if (po.getLowPrice() == null || po.getLowPrice().compareTo(cache.getLowPrice()) > 0) {
			po.setLowPrice(cache.getLowPrice());
		}
		if (cache.getVolume() != null) {
			po.setVolume(po.getVolume().add(cache.getVolume()));
		}
		return po;
	}

	@Override
	public CommonResult deleteExpiredCacheData() {
		CommonResult r = new CommonResult();

		LocalDateTime expriedTime = LocalDateTime.now()
				.minusHours(CryptoCoinDataConstant.CRYPTO_COIN_5MINUTE_DATA_LIVE_HOURS);

		CryptoCoinPrice5minuteExample example = new CryptoCoinPrice5minuteExample();
		example.createCriteria().andCreateTimeLessThan(expriedTime);
		_5MinDataMapper.deleteByExample(example);
		r.setIsSuccess();
		return r;
	}

	@Override
	public List<CryptoCoinPrice5minute> getData(CryptoCoinType coinType, CurrencyType currencyType,
			LocalDateTime startTime) {
		CryptoCoinPrice5minuteExample example = new CryptoCoinPrice5minuteExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getCode()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeGreaterThanOrEqualTo(startTime);
		;
		example.setOrderByClause("create_time desc");

		return _5MinDataMapper.selectByExample(example);
	}

	@Override
	public List<CryptoCoinPriceCommonDataBO> getCommonData(CryptoCoinType coinType, CurrencyType currencyType,
			LocalDateTime startTime) {
		List<CryptoCoinPrice5minute> poList = getData(coinType, currencyType, startTime);

		CryptoCoinPriceCommonDataBO tmpCommonData = null;
		List<CryptoCoinPriceCommonDataBO> commonDataList = new ArrayList<>();
		for (CryptoCoinPrice5minute po : poList) {
			tmpCommonData = new CryptoCoinPriceCommonDataBO();
			BeanUtils.copyProperties(po, tmpCommonData);
			commonDataList.add(tmpCommonData);
		}

		return commonDataList;
	}

	@Override
	public List<CryptoCoinPriceCommonDataBO> getCommonDataFillWithCache(CryptoCoinType coinType,
			CurrencyType currencyType, LocalDateTime startTime) {

		List<CryptoCoinPriceCommonDataBO> poDataList = getCommonData(coinType, currencyType, startTime);

		List<CryptoCoinPriceCommonDataBO> cacheDataList = cacheService.getCommonData(coinType, currencyType);
		if (poDataList.isEmpty() && cacheDataList.isEmpty()) {
			return poDataList;
		} else if (!poDataList.isEmpty() && cacheDataList.isEmpty()) {
			return poDataList;
		} else if (poDataList.isEmpty() && !cacheDataList.isEmpty()) {
			return cacheDataList;
		}

		LocalDateTime twoStepBefore = nextStepTimeByMinute(LocalDateTime.now().withSecond(0).withNano(0),
				minuteStepLong).minusMinutes(minuteStepLong * 2);

		Collections.sort(cacheDataList);

		LocalDateTime endTimeNow = LocalDateTime.now().withSecond(0).withNano(0);
		LocalDateTime cacheStartTime = twoStepBefore;

		boolean poDataExistsFlag = false;
		CryptoCoinPriceCommonDataBO tmpPOData = null;
		CryptoCoinPriceCommonDataBO tmpCacheData = null;
		LocalDateTime cacheNextStepTime = nextStepTimeByMinute(cacheStartTime, minuteStepLong);
		
		while (!cacheStartTime.isAfter(endTimeNow)) {
			for (int i = 0; i < poDataList.size() && poDataExistsFlag == false; i++) {
				tmpPOData = poDataList.get(i);
				if (tmpPOData.getStartTime().equals(cacheStartTime)) {
					poDataExistsFlag = true;
				}
			}
			
			if(poDataExistsFlag) {
				cacheDataLimit: for(int i = 0; i < cacheDataList.size(); i++) {
					tmpCacheData = cacheDataList.get(i);
					if(!tmpCacheData.getStartTime().isBefore(cacheNextStepTime)) {
						break cacheDataLimit;
					}
					tmpPOData = mergerData(tmpPOData, tmpCacheData);
				}
			
			} else {
				tmpPOData = new CryptoCoinPriceCommonDataBO();
				tmpPOData.setCoinType(coinType.getCode());
				tmpPOData.setCurrencyType(currencyType.getCode());
				tmpPOData.setVolume(BigDecimal.ZERO);
				
				cacheDataLimit: for(int i = 0; i < cacheDataList.size(); i++) {
					tmpCacheData = cacheDataList.get(i);
					if(!tmpCacheData.getStartTime().isBefore(cacheNextStepTime)) {
						break cacheDataLimit;
					}
					if(tmpCacheData.getStartTime().isBefore(cacheStartTime)) {
						continue;
					}
					tmpPOData = mergerData(tmpPOData, tmpCacheData);
					poDataList.add(tmpPOData);
				}
			}


			poDataExistsFlag = false;
			cacheStartTime = nextStepTimeByMinute(cacheStartTime, minuteStepLong);
			cacheNextStepTime = nextStepTimeByMinute(cacheNextStepTime, minuteStepLong);
		}

		Collections.sort(poDataList);
		return poDataList;
	}

}
