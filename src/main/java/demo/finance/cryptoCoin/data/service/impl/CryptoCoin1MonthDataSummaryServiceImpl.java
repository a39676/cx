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
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice1weekMapper;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1day;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1dayExample;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1week;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1weekExample;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPriceCommonData;
import demo.finance.cryptoCoin.data.service.CryptoCoin1MonthDataSummaryService;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

@Service
public class CryptoCoin1MonthDataSummaryServiceImpl extends CryptoCoinCommonService
		implements CryptoCoin1MonthDataSummaryService {

	private final int monthStepLong = 1;

	@Autowired
	private CryptoCoinPrice1dayMapper cacheMapper;
	@Autowired
	private CryptoCoinPrice1weekMapper summaryMapper;

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

		CryptoCoinPrice1weekExample example = new CryptoCoinPrice1weekExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getCode()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeEqualTo(startTime);
		List<CryptoCoinPrice1week> poList = summaryMapper.selectByExample(example);
		CryptoCoinPrice1week po = null;
		boolean newPOFlag = false;
		if (poList == null || poList.isEmpty()) {
			newPOFlag = true;
			po = new CryptoCoinPrice1week();
			po.setId(snowFlake.getNextId());
			po.setCoinType(coinType.getCode());
			po.setCurrencyType(currencyType.getCode());
		} else {
			po = poList.get(0);
		}

		Double volumeSummary = 0D;
		for (CryptoCoinPrice1day cache : cacheList) {
			if (po.getStartTime() == null || cache.getStartTime().isBefore(po.getStartTime())) {
				po.setStartTime(cache.getStartTime());
				po.setStartPrice(cache.getStartPrice());
			}
			if (po.getEndTime() == null || cache.getStartTime().isAfter(po.getEndTime())) {
				po.setEndTime(cache.getStartTime().plusMinutes(1));
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
	public List<CryptoCoinPrice1week> getData(CryptoCoinType coinType, CurrencyType currencyType, Integer minutes) {
		CryptoCoinPrice1weekExample example = new CryptoCoinPrice1weekExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getCode()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andCreateTimeGreaterThanOrEqualTo(LocalDateTime.now().minusMinutes(minutes));
		;
		example.setOrderByClause("create_time desc");

		return summaryMapper.selectByExample(example);
	}

	@Override
	public List<CryptoCoinPriceCommonData> getCommonData(CryptoCoinType coinType, CurrencyType currencyType,
			Integer minutes) {
		List<CryptoCoinPrice1week> poList = getData(coinType, currencyType, minutes);

		CryptoCoinPriceCommonData tmpCommonData = null;
		List<CryptoCoinPriceCommonData> commonDataList = new ArrayList<>();
		for (CryptoCoinPrice1week po : poList) {
			tmpCommonData = new CryptoCoinPriceCommonData();
			BeanUtils.copyProperties(po, tmpCommonData);
			commonDataList.add(tmpCommonData);
		}

		return commonDataList;
	}
}
