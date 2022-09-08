package demo.finance.cryptoCoin.data.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice1monthMapper;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1month;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1monthExample;
import demo.finance.cryptoCoin.data.service.CryptoCoin1DayDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin1MonthDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoinCatalogService;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.type.CurrencyTypeForCryptoCoin;

@Service
public class CryptoCoin1MonthDataSummaryServiceImpl extends CryptoCoinCommonService
		implements CryptoCoin1MonthDataSummaryService {

	private final int MONTH_STEP_LONG = 1;

	@Autowired
	private CryptoCoinPrice1monthMapper summaryMapper;
	@Autowired
	private CryptoCoinCatalogService coinCatalogService;
	@Autowired
	private CryptoCoinPriceCacheService cacheService;
	@Autowired
	private CryptoCoin1DayDataSummaryService dailyDataService;

	@Override
	public CommonResult summaryHistoryData() {
		CommonResult r = new CommonResult();

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime thereStepBefore = now.withDayOfMonth(1).minusMonths(MONTH_STEP_LONG * 3).withHour(0).withMinute(0)
				.withSecond(0).withNano(0);

		List<CryptoCoinCatalog> coinCatalogList = coinCatalogService.getAllCatalog();
		for (CryptoCoinCatalog coinType : coinCatalogList) {
			for (CurrencyTypeForCryptoCoin currencyType : CurrencyTypeForCryptoCoin.values()) {
				for (LocalDateTime datetime = thereStepBefore; datetime
						.isBefore(now); datetime = datetime.plusMonths(MONTH_STEP_LONG)) {
					handleHistoryDataList(datetime, coinType, currencyType);
				}
			}
		}

		return r;
	}

	private void handleHistoryDataList(LocalDateTime startTime, CryptoCoinCatalog coinType, CurrencyTypeForCryptoCoin currencyType) {
		LocalDateTime endTime = startTime.plusMonths(MONTH_STEP_LONG).with(LocalTime.MAX);
		List<CryptoCoinPriceCommonDataBO> cacheDataList = dailyDataService.getCommonDataList(coinType, currencyType,
				startTime, endTime);
		if (cacheDataList == null || cacheDataList.isEmpty()) {
			return;
		}

		CryptoCoinPrice1monthExample example = new CryptoCoinPrice1monthExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getId()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeEqualTo(startTime);
		List<CryptoCoinPrice1month> poList = summaryMapper.selectByExample(example);
		CryptoCoinPrice1month po = null;
		boolean newPOFlag = false;
		if (poList == null || poList.isEmpty()) {
			newPOFlag = true;
			po = new CryptoCoinPrice1month();
			po.setId(snowFlake.getNextId());
			po.setCoinType(coinType.getId());
			po.setCurrencyType(currencyType.getCode());
		} else {
			po = poList.get(0);
		}

		Double volumeSummary = 0D;
		for (CryptoCoinPriceCommonDataBO cache : cacheDataList) {
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
	public List<CryptoCoinPrice1month> getDataList(CryptoCoinCatalog coinType, CurrencyTypeForCryptoCoin currencyType,
			LocalDateTime startTime) {
		CryptoCoinPrice1monthExample example = new CryptoCoinPrice1monthExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getId()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeGreaterThanOrEqualTo(startTime);
		;

		return summaryMapper.selectByExample(example);
	}

	@Override
	public List<CryptoCoinPriceCommonDataBO> getCommonDataList(CryptoCoinCatalog coinType, CurrencyTypeForCryptoCoin currencyType,
			LocalDateTime startTime) {
		List<CryptoCoinPrice1month> poList = getDataList(coinType, currencyType, startTime);

		CryptoCoinPriceCommonDataBO tmpCommonData = null;
		List<CryptoCoinPriceCommonDataBO> commonDataList = new ArrayList<>();
		for (CryptoCoinPrice1month po : poList) {
			tmpCommonData = new CryptoCoinPriceCommonDataBO();
			BeanUtils.copyProperties(po, tmpCommonData);
			commonDataList.add(tmpCommonData);
		}

		return commonDataList;
	}
	
	@Override
	public List<CryptoCoinPriceCommonDataBO> getCommonDataListFillWithCache(CryptoCoinCatalog coinType,
			CurrencyTypeForCryptoCoin currencyType, LocalDateTime startTime) {

		List<CryptoCoinPriceCommonDataBO> poDataList = getCommonDataList(coinType, currencyType, startTime);
//		List<CryptoCoinPriceCommonDataBO> poDataList = buildFakeData(coinType, currencyType, startTime);

		List<CryptoCoinPriceCommonDataBO> cacheDataList = cacheService.getCommonDataList(coinType, currencyType, startTime);

		if (cacheDataList.isEmpty()) {
			return poDataList;
		}

		List<CryptoCoinPriceCommonDataBO> resultDataList = mergePODataWithCache(poDataList, cacheDataList, startTime, MONTH_STEP_LONG, TimeUnitType.month);

		return resultDataList;
	}
}
