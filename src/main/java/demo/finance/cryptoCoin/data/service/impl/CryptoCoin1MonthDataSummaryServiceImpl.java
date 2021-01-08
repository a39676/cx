package demo.finance.cryptoCoin.data.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice1dayMapper;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice1monthMapper;
import demo.finance.cryptoCoin.data.pojo.bo.CryptoCoinPriceCommonDataBO;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1day;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1dayExample;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1month;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1monthExample;
import demo.finance.cryptoCoin.data.service.CryptoCoin1MonthDataSummaryService;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

@Service
public class CryptoCoin1MonthDataSummaryServiceImpl extends CryptoCoinCommonService
		implements CryptoCoin1MonthDataSummaryService {

	private final int monthStepLong = 1;

	@Autowired
	private CryptoCoinPrice1dayMapper cacheMapper;
	@Autowired
	private CryptoCoinPrice1monthMapper summaryMapper;

	@Override
	public CommonResult summaryHistoryData() {
		CommonResult r = new CommonResult();

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime thereStepBefore = now.withDayOfMonth(1).minusMonths(monthStepLong * 3).withHour(0).withMinute(0)
				.withSecond(0).withNano(0);

		for (CryptoCoinType coinType : CryptoCoinType.values()) {
			for (CurrencyType currencyType : CurrencyType.values()) {
				for (LocalDateTime datetime = thereStepBefore; datetime
						.isBefore(now); datetime = datetime.plusMonths(monthStepLong)) {
					handleHistoryDataList(datetime, coinType, currencyType);
				}
			}
		}

		return r;
	}

	private void handleHistoryDataList(LocalDateTime startTime, CryptoCoinType coinType, CurrencyType currencyType) {
		LocalDateTime endTime = startTime.plusMonths(monthStepLong);

		CryptoCoinPrice1dayExample cacheExample = new CryptoCoinPrice1dayExample();
		cacheExample.createCriteria().andCoinTypeEqualTo(coinType.getCode())
				.andCurrencyTypeEqualTo(currencyType.getCode()).andStartTimeGreaterThanOrEqualTo(startTime)
				.andStartTimeLessThan(endTime);
		List<CryptoCoinPrice1day> cacheList = cacheMapper.selectByExample(cacheExample);
		if (cacheList == null || cacheList.isEmpty()) {
			return;
		}

		CryptoCoinPrice1monthExample example = new CryptoCoinPrice1monthExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getCode()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeEqualTo(startTime);
		List<CryptoCoinPrice1month> poList = summaryMapper.selectByExample(example);
		CryptoCoinPrice1month po = null;
		boolean newPOFlag = false;
		if (poList == null || poList.isEmpty()) {
			newPOFlag = true;
			po = new CryptoCoinPrice1month();
			po.setId(snowFlake.getNextId());
			po.setCoinType(coinType.getCode());
			po.setCurrencyType(currencyType.getCode());
		} else {
			po = poList.get(0);
		}

		Double volumeSummary = 0D;
		for (CryptoCoinPrice1day cache : cacheList) {
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
	public List<CryptoCoinPrice1month> getData(CryptoCoinType coinType, CurrencyType currencyType,
			LocalDateTime startTime) {
		CryptoCoinPrice1monthExample example = new CryptoCoinPrice1monthExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getCode()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeGreaterThanOrEqualTo(startTime);
		;
		example.setOrderByClause("create_time desc");

		return summaryMapper.selectByExample(example);
	}

	@Override
	public List<CryptoCoinPriceCommonDataBO> getCommonData(CryptoCoinType coinType, CurrencyType currencyType,
			LocalDateTime startTime) {
		List<CryptoCoinPrice1month> poList = getData(coinType, currencyType, startTime);

		CryptoCoinPriceCommonDataBO tmpCommonData = null;
		List<CryptoCoinPriceCommonDataBO> commonDataList = new ArrayList<>();
		for (CryptoCoinPrice1month po : poList) {
			tmpCommonData = new CryptoCoinPriceCommonDataBO();
			BeanUtils.copyProperties(po, tmpCommonData);
			commonDataList.add(tmpCommonData);
		}

		return commonDataList;
	}
}