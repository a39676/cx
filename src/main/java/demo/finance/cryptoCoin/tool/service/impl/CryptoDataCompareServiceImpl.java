package demo.finance.cryptoCoin.tool.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice1dayMapper;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1day;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1dayExample;
import demo.finance.cryptoCoin.data.service.CryptoCoin1DayDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoinCatalogService;
import demo.finance.cryptoCoin.data.service.CryptoCoinHistoryDataService;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;
import demo.finance.cryptoCoin.tool.pojo.dto.CryptoCoinDataMutipleCompareDTO;
import demo.finance.cryptoCoin.tool.pojo.dto.CryptoCoinDataSingleCompareDTO;
import demo.finance.cryptoCoin.tool.pojo.dto.CryptoCoinLowPriceCompareDTO;
import demo.finance.cryptoCoin.tool.pojo.result.CryptoCoinLowPriceCompareResult;
import demo.finance.cryptoCoin.tool.pojo.result.CryptoDataCompareLineResult;
import demo.finance.cryptoCoin.tool.pojo.result.CryptoDataCompareRateResult;
import demo.finance.cryptoCoin.tool.pojo.result.CryptoDataCompareRateSubResult;
import demo.finance.cryptoCoin.tool.service.CryptoCoinLowPriceNoticeService;
import demo.finance.cryptoCoin.tool.service.CryptoDataCompareService;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.type.CurrencyTypeForCryptoCoin;

@Service
public class CryptoDataCompareServiceImpl extends CryptoCoinCommonService implements CryptoDataCompareService {

	@Autowired
	private CryptoCoinLowPriceNoticeService lowPriceNoticeService;
	@Autowired
	private CryptoCoinPrice1dayMapper dailyDataMapper;
	@Autowired
	private CryptoCoinPriceCacheService cacheService;
	@Autowired
	private CryptoCoinCatalogService coinCatalogService;
	@Autowired
	private CryptoCoin1DayDataSummaryService dailyDataService;
	@Autowired
	private CryptoCoinHistoryDataService cryptoCoinHistoryDataService;

	@Override
	public ModelAndView CryptoCoinDailyDataComparetor() {
		ModelAndView view = new ModelAndView("finance/cryptoCoin/CryptoCoinDailyDataComparetor");
		List<CurrencyTypeForCryptoCoin> currencyTypeList = new ArrayList<>();
		currencyTypeList.add(CurrencyTypeForCryptoCoin.USD);
		currencyTypeList.addAll(Arrays.asList(CurrencyTypeForCryptoCoin.values()));
		view.addObject("currencyType", currencyTypeList);
		return view;
	}

	@Override
	public CryptoDataCompareRateResult cryptoCoinDailyDataSingleComparePoint(CryptoCoinDataSingleCompareDTO dto) {
		CryptoDataCompareRateResult r = new CryptoDataCompareRateResult();
		CommonResult checkDTOResult = checkDTO(dto);
		if (checkDTOResult.isFail()) {
			r.addMessage(checkDTOResult.getMessage());
			return r;
		}

		CurrencyTypeForCryptoCoin currencyType = CurrencyTypeForCryptoCoin.getType(dto.getCurrencyType());

		CryptoCoinCatalog coinTypeOrigin = coinCatalogService.findCatalog(dto.getCoinTypeOrigin());
		CryptoCoinCatalog coinTypeCompared = coinCatalogService.findCatalog(dto.getCoinTypeCompared());

		LocalDateTime startDateTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getStartDateTimeStr());
		CryptoCoinPriceCommonDataBO dataOriginStart = dailyDataService.getCommonData(coinTypeOrigin, currencyType,
				startDateTime);
		CryptoCoinPriceCommonDataBO dataComparedStart = dailyDataService.getCommonData(coinTypeCompared, currencyType,
				startDateTime);

		if (dataOriginStart == null || dataComparedStart == null) {
			r.failWithMessage("one compare data error");
			return r;
		}

		LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
		List<CryptoCoinPriceCommonDataBO> dataOriginList = dailyDataService
				.getCommonDataListFillWithCache(coinTypeOrigin, currencyType, today);
		List<CryptoCoinPriceCommonDataBO> dataComparedList = dailyDataService
				.getCommonDataListFillWithCache(coinTypeCompared, currencyType, today);

		if (dataOriginList.isEmpty() || dataComparedList.isEmpty()) {
			r.failWithMessage("one compare data list error");
			return r;
		}

		CryptoCoinPriceCommonDataBO dataOriginEnd = dataOriginList.get(0);
		CryptoCoinPriceCommonDataBO dataComparedEnd = dataComparedList.get(0);

		BigDecimal dataOriginRate = new BigDecimal(
				dataOriginEnd.getEndPrice().doubleValue() / dataOriginStart.getStartPrice().doubleValue());
		BigDecimal dataComparedRate = new BigDecimal(
				dataComparedEnd.getEndPrice().doubleValue() / dataComparedStart.getStartPrice().doubleValue());

		CryptoDataCompareRateSubResult subResult = new CryptoDataCompareRateSubResult();
		subResult.setDifferentRate(dataOriginRate.subtract(dataComparedRate));
		subResult.setIsSuccess();

		r.addSubResult(subResult);
		r.setIsSuccess();
		return r;
	}

	@Override
	public CryptoDataCompareRateResult cryptoCoinDailyDataMutipleComparePoint(CryptoCoinDataMutipleCompareDTO dto) {
		CryptoDataCompareRateResult r = new CryptoDataCompareRateResult();
		CommonResult checkDTOResult = checkDTO(dto);
		if (checkDTOResult.isFail()) {
			r.addMessage(checkDTOResult.getMessage());
			return r;
		}

		CurrencyTypeForCryptoCoin currencyType = CurrencyTypeForCryptoCoin.getType(dto.getCurrencyType());

		CryptoCoinCatalog coinTypeOrigin = coinCatalogService.findCatalog(dto.getCoinTypeOrigin());
		List<CryptoCoinCatalog> comparedCoinTypeList = new ArrayList<>();
		for (String coinTypeStr : dto.getCoinTypeComparedList()) {
			CryptoCoinCatalog coinType = coinCatalogService.findCatalog(coinTypeStr.trim());
			if (coinType != null && !coinType.getCoinNameEnShort().equals(coinTypeOrigin.getCoinNameEnShort())) {
				comparedCoinTypeList.add(coinType);
			}
		}
		if (comparedCoinTypeList.isEmpty()) {
			r.failWithMessage("there is NO compared type");
			return r;
		}

		LocalDateTime startDateTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getStartDateTimeStr());
		CryptoCoinPriceCommonDataBO dataOriginStart = dailyDataService.getCommonData(coinTypeOrigin, currencyType,
				startDateTime);

		if (dataOriginStart == null) {
			r.failWithMessage("origin data error");
			return r;
		}

		LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
		List<CryptoCoinPriceCommonDataBO> dataOriginList = dailyDataService
				.getCommonDataListFillWithCache(coinTypeOrigin, currencyType, today);

		if (dataOriginList.isEmpty()) {
			r.failWithMessage("origin data list error");
			return r;
		}

		CryptoCoinPriceCommonDataBO dataOriginEnd = dataOriginList.get(0);

		BigDecimal dataOriginRate = new BigDecimal(
				dataOriginEnd.getEndPrice().doubleValue() / dataOriginStart.getStartPrice().doubleValue());

		CryptoDataCompareRateSubResult subResult = null;
		for (CryptoCoinCatalog subComparedType : comparedCoinTypeList) {
			subResult = cryptoCoinDailyDataComparePoint(dataOriginRate, coinTypeOrigin, subComparedType, currencyType,
					startDateTime);
			if (subResult.isSuccess()) {
				r.addSubResult(subResult);
			}
		}

		if (r.getResultList() != null && !r.getResultList().isEmpty()) {
			Collections.sort(r.getResultList());
			r.setIsSuccess();
		}
		return r;
	}

	private CryptoDataCompareRateSubResult cryptoCoinDailyDataComparePoint(BigDecimal dataOriginRate,
			CryptoCoinCatalog coinTypeOrigin, CryptoCoinCatalog coinTypeCompared, CurrencyTypeForCryptoCoin currencyType,
			LocalDateTime startDateTime) {
		CryptoDataCompareRateSubResult r = new CryptoDataCompareRateSubResult();
		CryptoCoinPriceCommonDataBO dataComparedStart = dailyDataService.getCommonData(coinTypeCompared, currencyType,
				startDateTime);

		if (dataComparedStart == null) {
			r.failWithMessage("compared data error");
			return r;
		}

		LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
		List<CryptoCoinPriceCommonDataBO> dataComparedList = dailyDataService
				.getCommonDataListFillWithCache(coinTypeCompared, currencyType, today);

		if (dataComparedList.isEmpty()) {
			r.failWithMessage("compared data list error");
			return r;
		}

		CryptoCoinPriceCommonDataBO dataComparedEnd = dataComparedList.get(0);

		if (dataComparedStart.getStartPrice().compareTo(BigDecimal.ZERO) == 0) {
			r.failWithMessage("start price = 0");
			return r;
		}
		BigDecimal dataComparedRate = null;
		try {
			dataComparedRate = new BigDecimal(
					dataComparedEnd.getEndPrice().doubleValue() / dataComparedStart.getStartPrice().doubleValue());
		} catch (Exception e) {
			System.out.println(dataComparedEnd + ", " + dataComparedStart);
		}

		r.setOriginCoinTypeName(coinTypeOrigin.getCoinNameEnShort());
		r.setComparedCoinTypeName(coinTypeCompared.getCoinNameEnShort());
		r.setDifferentRate(dataOriginRate.subtract(dataComparedRate));
		r.setIsSuccess();
		return r;
	}

	@Override
	public CryptoDataCompareLineResult cryptoCoinDataSingleCompareLine(CryptoCoinDataSingleCompareDTO dto) {
		CryptoDataCompareLineResult r = new CryptoDataCompareLineResult();
		CommonResult checkDTOResult = checkDTO(dto);
		if (checkDTOResult.isFail()) {
			r.addMessage(checkDTOResult.getMessage());
			return r;
		}

		CurrencyTypeForCryptoCoin currencyType = CurrencyTypeForCryptoCoin.getType(dto.getCurrencyType());
		TimeUnitType timeUnitType = TimeUnitType.getType(dto.getTimeUnit());

		CryptoCoinCatalog coinType1 = coinCatalogService.findCatalog(dto.getCoinTypeOrigin());
		CryptoCoinCatalog coinType2 = coinCatalogService.findCatalog(dto.getCoinTypeCompared());

		List<CryptoCoinPriceCommonDataBO> dataList1 = cryptoCoinHistoryDataService.getHistoryDataList(coinType1, currencyType, timeUnitType,
				dto.getTimeRange());
		List<CryptoCoinPriceCommonDataBO> dataList2 = cryptoCoinHistoryDataService.getHistoryDataList(coinType2, currencyType, timeUnitType,
				dto.getTimeRange());

		if (dataList1.isEmpty() || dataList2.isEmpty()) {
			r.setDataList1(dataList1);
			r.setDataList2(dataList2);
			r.normalSuccess();
			return r;
		}

		LocalDateTime startTime1 = dataList1.get(0).getStartTime();
		LocalDateTime startTime2 = dataList2.get(0).getStartTime();
		LocalDateTime commonStartTime = startTime1;
		if (startTime1.isBefore(startTime2)) {
			commonStartTime = startTime2;
		}

		while (dataList1.get(0).getStartTime().isBefore(commonStartTime)) {
			dataList1.remove(0);
		}
		while (dataList2.get(0).getStartTime().isBefore(commonStartTime)) {
			dataList2.remove(0);
		}

		r.setDataList1(dataList1);
		r.setDataList2(dataList2);
		r.normalSuccess();
		return r;
	}

	private CommonResult checkDTO(CryptoCoinDataSingleCompareDTO dto) {
		CommonResult r = new CommonResult();
		TimeUnitType timeUnitType = TimeUnitType.getType(dto.getTimeUnit());
		if (timeUnitType == null) {
			if (StringUtils.isBlank(dto.getStartDateTimeStr())) {
				r.failWithMessage("time setting error");
			}
		} else {
			if (dto.getTimeRange() == null || dto.getTimeRange() < 1) {
				r.failWithMessage("time range error");
				return r;
			}
		}

		CurrencyTypeForCryptoCoin currencyType = CurrencyTypeForCryptoCoin.getType(dto.getCurrencyType());
		if (currencyType == null) {
			dto.setCurrencyType(CurrencyTypeForCryptoCoin.USD.getCode());
		}

		if (localDateTimeHandler.determineDateFormat(dto.getStartDateTimeStr()) == null) {
			r.failWithMessage("date time format error");
			return r;
		}

		if (StringUtils.isAnyBlank(dto.getCoinTypeOrigin(), dto.getCoinTypeCompared())
				|| dto.getCoinTypeOrigin().equals(dto.getCoinTypeCompared())) {
			r.failWithMessage("coin type error");
			return r;
		}

		r.normalSuccess();
		return r;

	}

	private CommonResult checkDTO(CryptoCoinDataMutipleCompareDTO dto) {
		CommonResult r = new CommonResult();

		CurrencyTypeForCryptoCoin currencyType = CurrencyTypeForCryptoCoin.getType(dto.getCurrencyType());
		if (currencyType == null) {
			dto.setCurrencyType(CurrencyTypeForCryptoCoin.USD.getCode());
		}

		if (localDateTimeHandler.determineDateFormat(dto.getStartDateTimeStr()) == null) {
			r.failWithMessage("date time format error");
			return r;
		}

		if (StringUtils.isBlank(dto.getCoinTypeOrigin()) || dto.getCoinTypeComparedList() == null
				|| dto.getCoinTypeComparedList().isEmpty()) {
			r.failWithMessage("coin type error");
			return r;
		}

		r.normalSuccess();
		return r;

	}

	@Override
	public CryptoCoinLowPriceCompareResult lowPriceCompareToYesterday() {
		CryptoCoinLowPriceCompareResult r = new CryptoCoinLowPriceCompareResult();
		List<CryptoCoinCatalog> lowPriceSubscriptionList = lowPriceNoticeService.getLowPriceSubscriptionCatalogList();
		if (lowPriceSubscriptionList.isEmpty()) {
			r.setMessage("low price subscription NOT found");
			return r;
		}

		List<Long> coinTypeIdList = lowPriceSubscriptionList.stream().map(po -> po.getId())
				.collect(Collectors.toList());

		CryptoCoinPrice1dayExample dailyDataExample = new CryptoCoinPrice1dayExample();
		dailyDataExample.createCriteria().andCoinTypeIn(coinTypeIdList)
				.andStartTimeEqualTo(LocalDateTime.now().with(LocalTime.MIN).minusDays(1));
		List<CryptoCoinPrice1day> dailyDataList = dailyDataMapper.selectByExample(dailyDataExample);
		if (dailyDataList.isEmpty()) {
			r.setMessage("low price daily data NOT found");
			return r;
		}

		Map<Long, CryptoCoinPrice1day> dailyDataMap = new HashMap<>();
		for (CryptoCoinPrice1day dailyData : dailyDataList) {
			dailyDataMap.put(dailyData.getCoinType(), dailyData);
		}

		Map<Long, CryptoCoinPriceCommonDataBO> minuteDataMap = new HashMap<>();
		CryptoCoinPriceCommonDataBO tmpCachePriceBO = null;
		for (CryptoCoinCatalog catalog : lowPriceSubscriptionList) {
			tmpCachePriceBO = cacheService.getNewPrice(catalog, CurrencyTypeForCryptoCoin.USD);
			minuteDataMap.put(catalog.getId(), tmpCachePriceBO);
		}
		if (minuteDataMap.isEmpty()) {
			r.setMessage("low price cache data NOT found");
			return r;
		}

		CryptoCoinLowPriceCompareDTO tmpDTO = null;
		CryptoCoinPrice1day tmpDailyData = null;
		CryptoCoinPriceCommonDataBO tmpCacheData = null;
		for (CryptoCoinCatalog catalog : lowPriceSubscriptionList) {
			tmpDailyData = dailyDataMap.get(catalog.getId());
			tmpCacheData = minuteDataMap.get(catalog.getId());
			if (tmpDailyData == null || tmpCacheData == null) {
				continue;
			}
			tmpDTO = new CryptoCoinLowPriceCompareDTO();
			tmpDTO.setShortNameEN(catalog.getCoinNameEnShort());
			tmpDTO.setRate(tmpCacheData.getEndPrice().doubleValue() / tmpDailyData.getEndPrice().doubleValue());
			r.addData(tmpDTO);
		}
		Collections.sort(r.getDataList());
		r.setIsSuccess();
		return r;
	}
}
