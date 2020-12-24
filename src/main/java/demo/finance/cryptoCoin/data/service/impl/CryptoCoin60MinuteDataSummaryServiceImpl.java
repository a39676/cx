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
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice5minuteMapper;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice60minuteMapper;
import demo.finance.cryptoCoin.data.pojo.bo.CryptoCoinPriceCommonDataBO;
import demo.finance.cryptoCoin.data.pojo.constant.CryptoCoinDataConstant;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice5minute;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice5minuteExample;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice60minute;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice60minuteExample;
import demo.finance.cryptoCoin.data.service.CryptoCoin60MinuteDataSummaryService;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

@Service
public class CryptoCoin60MinuteDataSummaryServiceImpl extends CryptoCoinCommonService
		implements CryptoCoin60MinuteDataSummaryService {

	private final int minuteStepLong = 60;
	@Autowired
	private CryptoCoinPrice5minuteMapper cacheMapper;
	@Autowired
	private CryptoCoinPrice60minuteMapper summaryMapper;

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
		LocalDateTime endTime = startTime.plusMinutes(minuteStepLong);

		CryptoCoinPrice5minuteExample cacheExample = new CryptoCoinPrice5minuteExample();
		cacheExample.createCriteria().andCoinTypeEqualTo(coinType.getCode())
				.andCurrencyTypeEqualTo(currencyType.getCode()).andStartTimeGreaterThanOrEqualTo(startTime)
				.andStartTimeLessThan(endTime);
		List<CryptoCoinPrice5minute> cacheList = cacheMapper.selectByExample(cacheExample);
		if (cacheList == null || cacheList.isEmpty()) {
			return;
		}

		CryptoCoinPrice60minuteExample example = new CryptoCoinPrice60minuteExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getCode()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeEqualTo(startTime);
		List<CryptoCoinPrice60minute> poList = summaryMapper.selectByExample(example);
		CryptoCoinPrice60minute po = null;
		boolean newPOFlag = false;
		if (poList == null || poList.isEmpty()) {
			newPOFlag = true;
			po = new CryptoCoinPrice60minute();
			po.setId(snowFlake.getNextId());
			po.setCoinType(coinType.getCode());
			po.setCurrencyType(currencyType.getCode());
		} else {
			po = poList.get(0);
		}

		Double volumeSummary = 0D;
		for (CryptoCoinPrice5minute cache : cacheList) {
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
	public CommonResult deleteExpiredCacheData() {
		CommonResult r = new CommonResult();

		LocalDateTime expriedTime = LocalDateTime.now()
				.minusHours(CryptoCoinDataConstant.CRYPTO_COIN_60MINUTE_DATA_LIVE_HOURS);

		CryptoCoinPrice60minuteExample example = new CryptoCoinPrice60minuteExample();
		example.createCriteria().andCreateTimeLessThan(expriedTime);
		summaryMapper.deleteByExample(example);
		r.setIsSuccess();
		return r;
	}

	@Override
	public List<CryptoCoinPrice60minute> getData(CryptoCoinType coinType, CurrencyType currencyType,
			LocalDateTime startTime) {
		CryptoCoinPrice60minuteExample example = new CryptoCoinPrice60minuteExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getCode()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeGreaterThanOrEqualTo(startTime);
		;
		example.setOrderByClause("create_time desc");

		return summaryMapper.selectByExample(example);
	}

	@Override
	public List<CryptoCoinPriceCommonDataBO> getCommonData(CryptoCoinType coinType, CurrencyType currencyType,
			LocalDateTime startTime) {
		List<CryptoCoinPrice60minute> poList = getData(coinType, currencyType, startTime);

		CryptoCoinPriceCommonDataBO tmpCommonData = null;
		List<CryptoCoinPriceCommonDataBO> commonDataList = new ArrayList<>();
		for (CryptoCoinPrice60minute po : poList) {
			tmpCommonData = new CryptoCoinPriceCommonDataBO();
			BeanUtils.copyProperties(po, tmpCommonData);
			commonDataList.add(tmpCommonData);
		}

		return commonDataList;
	}
}
