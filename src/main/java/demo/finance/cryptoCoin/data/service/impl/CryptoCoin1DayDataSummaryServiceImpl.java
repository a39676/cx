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
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice1dayMapper;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice60minuteMapper;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1day;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1dayExample;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice60minute;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice60minuteExample;
import demo.finance.cryptoCoin.data.service.CryptoCoin1DayDataSummaryService;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

@Service
public class CryptoCoin1DayDataSummaryServiceImpl extends CryptoCoinCommonService
		implements CryptoCoin1DayDataSummaryService {

	private final int dayStepLong = 1;

	@Autowired
	private CryptoCoinPrice60minuteMapper cacheMapper;
	@Autowired
	private CryptoCoinPrice1dayMapper summaryMapper;

	@Override
	public CommonResult summaryHistoryData() {
		CommonResult r = new CommonResult();

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime thereStepBefore = now.minusDays(dayStepLong * 3).withHour(0).withMinute(0).withSecond(0)
				.withNano(0);

		for (CryptoCoinType coinType : CryptoCoinType.values()) {
			for (CurrencyType currencyType : CurrencyType.values()) {
				for (LocalDateTime datetime = thereStepBefore; datetime
						.isBefore(now); datetime = datetime.plusDays(dayStepLong)) {
					handleHistoryDataList(datetime, coinType, currencyType);
				}
			}
		}

		return r;
	}

	private void handleHistoryDataList(LocalDateTime startTime, CryptoCoinType coinType, CurrencyType currencyType) {
		LocalDateTime endTime = startTime.plusDays(dayStepLong);

		CryptoCoinPrice60minuteExample cacheExample = new CryptoCoinPrice60minuteExample();
		cacheExample.createCriteria().andCoinTypeEqualTo(coinType.getCode())
				.andCurrencyTypeEqualTo(currencyType.getCode()).andStartTimeGreaterThanOrEqualTo(startTime)
				.andStartTimeLessThan(endTime);
		List<CryptoCoinPrice60minute> cacheList = cacheMapper.selectByExample(cacheExample);
		if (cacheList == null || cacheList.isEmpty()) {
			return;
		}

		CryptoCoinPrice1dayExample example = new CryptoCoinPrice1dayExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getCode()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeEqualTo(startTime);
		List<CryptoCoinPrice1day> poList = summaryMapper.selectByExample(example);
		CryptoCoinPrice1day po = null;
		boolean newPOFlag = false;
		if (poList == null || poList.isEmpty()) {
			newPOFlag = true;
			po = new CryptoCoinPrice1day();
			po.setId(snowFlake.getNextId());
			po.setCoinType(coinType.getCode());
			po.setCurrencyType(currencyType.getCode());
		} else {
			po = poList.get(0);
		}

		Double volumeSummary = 0D;
		for (CryptoCoinPrice60minute cache : cacheList) {
			if (po.getStartTime() == null || cache.getStartTime().isBefore(po.getStartTime()) || cache.getStartTime().isEqual(po.getStartTime())) {
				po.setStartTime(cache.getStartTime());
				po.setStartPrice(cache.getStartPrice());
			}
			if (po.getEndTime() == null || cache.getEndTime().isAfter(po.getEndTime()) || cache.getEndTime().isEqual(po.getEndTime())) {
				po.setEndTime(cache.getEndTime());
				po.setEndPrice(cache.getEndPrice());
			}
			if (po.getHighPrice() == null || po.getHighPrice().compareTo(cache.getHighPrice()) < 0) {
				po.setHighPrice(cache.getHighPrice());
			}
			if (po.getLowPrice() == null || po.getLowPrice().compareTo(cache.getLowPrice()) > 0) {
				po.setLowPrice(cache.getLowPrice());
			}
			volumeSummary += cache.getVolume().doubleValue();
		}
		po.setVolume(new BigDecimal(volumeSummary));

		if (newPOFlag) {
			summaryMapper.insertSelective(po);
		} else {
			summaryMapper.updateByPrimaryKeySelective(po);
		}
	}

	@Override
	public List<CryptoCoinPrice1day> getData(CryptoCoinType coinType, CurrencyType currencyType,
			LocalDateTime startTime) {
		CryptoCoinPrice1dayExample example = new CryptoCoinPrice1dayExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getCode()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeGreaterThanOrEqualTo(startTime);
		;

		return summaryMapper.selectByExample(example);
	}

	@Override
	public List<CryptoCoinPriceCommonDataBO> getCommonData(CryptoCoinType coinType, CurrencyType currencyType,
			LocalDateTime startTime) {
		List<CryptoCoinPrice1day> poList = getData(coinType, currencyType, startTime);

		CryptoCoinPriceCommonDataBO tmpCommonData = null;
		List<CryptoCoinPriceCommonDataBO> commonDataList = new ArrayList<>();
		for (CryptoCoinPrice1day po : poList) {
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
//		List<CryptoCoinPriceCommonDataBO> poDataList = buildFakeData(coinType, currencyType, startTime);

		List<CryptoCoinPriceCommonDataBO> cacheDataList = cacheService.getCommonData(coinType, currencyType);

		if (cacheDataList.isEmpty()) {
			return poDataList;
		}

		Collections.sort(cacheDataList);
		Collections.sort(poDataList);
		
		CryptoCoinPriceCommonDataBO tmpPOData = null;
		CryptoCoinPriceCommonDataBO tmpCacheData = null;
		if(poDataList.isEmpty()) {
			tmpPOData = new CryptoCoinPriceCommonDataBO();
			tmpPOData.setCoinType(coinType.getCode());
			tmpPOData.setCurrencyType(currencyType.getCode());
			tmpPOData.setVolume(BigDecimal.ZERO);
		} else {
			tmpPOData = poDataList.get(poDataList.size() - 1);
		}
		
		for (int i = 0; i < cacheDataList.size(); i++) {
			tmpCacheData = cacheDataList.get(i);
			tmpPOData = mergerData(tmpPOData, tmpCacheData);
		}
		
		if(poDataList.isEmpty()) {
			poDataList.add(tmpPOData);
		} else {
			poDataList.set(poDataList.size() - 1, tmpPOData);
		}

		return poDataList;
	}
}
