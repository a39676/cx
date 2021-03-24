package demo.finance.cryptoCoin.tool.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.tool.pojo.dto.CryptoCoinDataMutipleCompareDTO;
import demo.finance.cryptoCoin.tool.pojo.dto.CryptoCoinDataSingleCompareDTO;
import demo.finance.cryptoCoin.tool.pojo.result.CryptoDataCompareLineResult;
import demo.finance.cryptoCoin.tool.pojo.result.CryptoDataCompareRateResult;
import demo.finance.cryptoCoin.tool.pojo.result.CryptoDataCompareRateSubResult;
import demo.finance.cryptoCoin.tool.service.CryptoDataCompareService;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;

@Service
public class CryptoDataCompareServiceImpl extends CryptoCoinCommonService implements CryptoDataCompareService {

	@Override
	public ModelAndView CryptoCoinDailyDataComparetor() {
		ModelAndView view = new ModelAndView("finance/cryptoCoin/CryptoCoinDailyDataComparetor");
		List<CurrencyType> currencyTypeList = new ArrayList<>();
		currencyTypeList.add(CurrencyType.USD);
		currencyTypeList.addAll(Arrays.asList(CurrencyType.values()));
		view.addObject("currencyType", currencyTypeList);
		return view;
	}
	
	@Override
	public CryptoDataCompareRateResult cryptoCoinDailyDataSingleComparePoint(CryptoCoinDataSingleCompareDTO dto) {
		CryptoDataCompareRateResult r = new CryptoDataCompareRateResult();
		CommonResult checkDTOResult = checkDTO(dto);
		if(checkDTOResult.isFail()) {
			r.addMessage(checkDTOResult.getMessage());
			return r;
		}
		
		CurrencyType currencyType = CurrencyType.getType(dto.getCurrencyType());
		
		CryptoCoinCatalog coinTypeOrigin = coinCatalogService.findCatalog(dto.getCoinTypeOrigin());
		CryptoCoinCatalog coinTypeCompared = coinCatalogService.findCatalog(dto.getCoinTypeCompared());
		
		LocalDateTime startDateTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getStartDateTimeStr());
		CryptoCoinPriceCommonDataBO dataOriginStart = dailyDataService.getCommonData(coinTypeOrigin, currencyType, startDateTime);
		CryptoCoinPriceCommonDataBO dataComparedStart = dailyDataService.getCommonData(coinTypeCompared, currencyType, startDateTime);
		
		if(dataOriginStart == null || dataComparedStart == null) {
			r.failWithMessage("one compare data error");
			return r;
		}
		
		LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
		List<CryptoCoinPriceCommonDataBO> dataOriginList = dailyDataService.getCommonDataListFillWithCache(coinTypeOrigin, currencyType, today);
		List<CryptoCoinPriceCommonDataBO> dataComparedList = dailyDataService.getCommonDataListFillWithCache(coinTypeCompared, currencyType, today);
		
		if(dataOriginList.isEmpty() || dataComparedList.isEmpty()) {
			r.failWithMessage("one compare data list error");
			return r;
		}
		
		CryptoCoinPriceCommonDataBO dataOriginEnd = dataOriginList.get(0);
		CryptoCoinPriceCommonDataBO dataComparedEnd = dataComparedList.get(0);
		
		BigDecimal dataOriginRate = new BigDecimal(dataOriginEnd.getEndPrice().doubleValue() / dataOriginStart.getStartPrice().doubleValue());
		BigDecimal dataComparedRate = new BigDecimal(dataComparedEnd.getEndPrice().doubleValue() / dataComparedStart.getStartPrice().doubleValue());
		
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
		if(checkDTOResult.isFail()) {
			r.addMessage(checkDTOResult.getMessage());
			return r;
		}
		
		CurrencyType currencyType = CurrencyType.getType(dto.getCurrencyType());
		
		CryptoCoinCatalog coinTypeOrigin = coinCatalogService.findCatalog(dto.getCoinTypeOrigin());
		List<CryptoCoinCatalog> comparedCoinTypeList = new ArrayList<>();
		for(String coinTypeStr : dto.getCoinTypeComparedList()) {
			if(coinTypeStr.toUpperCase().equals("ALL")) {
				comparedCoinTypeList.addAll(coinCatalogService.getAllCatalog());
			} else {
				CryptoCoinCatalog coinType = coinCatalogService.findCatalog(coinTypeStr);
				if(coinType != null && !coinType.getCoinNameEnShort().equals(coinTypeOrigin.getCoinNameEnShort())) {
					comparedCoinTypeList.add(coinType);
				}
			}
		}
		if(comparedCoinTypeList.isEmpty()) {
			r.failWithMessage("there is NO compared type");
			return r;
		}
		
		LocalDateTime startDateTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getStartDateTimeStr());
		CryptoCoinPriceCommonDataBO dataOriginStart = dailyDataService.getCommonData(coinTypeOrigin, currencyType, startDateTime);
		
		if(dataOriginStart == null) {
			r.failWithMessage("origin data error");
			return r;
		}
		
		LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
		List<CryptoCoinPriceCommonDataBO> dataOriginList = dailyDataService.getCommonDataListFillWithCache(coinTypeOrigin, currencyType, today);
		
		if(dataOriginList.isEmpty()) {
			r.failWithMessage("origin data list error");
			return r;
		}
		
		CryptoCoinPriceCommonDataBO dataOriginEnd = dataOriginList.get(0);
		
		BigDecimal dataOriginRate = new BigDecimal(dataOriginEnd.getEndPrice().doubleValue() / dataOriginStart.getStartPrice().doubleValue());
		
		CryptoDataCompareRateSubResult subResult = null;
		for(CryptoCoinCatalog subComparedType : comparedCoinTypeList) {
			subResult = cryptoCoinDailyDataComparePoint(dataOriginRate, coinTypeOrigin, subComparedType, currencyType, startDateTime);
			if(subResult.isSuccess()) {
				r.addSubResult(subResult);
			}
		}
		
		if(r.getResultList() != null && !r.getResultList().isEmpty()) {
			Collections.sort(r.getResultList());
			r.setIsSuccess();
		}
		return r;
	}
	
	private CryptoDataCompareRateSubResult cryptoCoinDailyDataComparePoint(BigDecimal dataOriginRate, CryptoCoinCatalog coinTypeOrigin, CryptoCoinCatalog coinTypeCompared, CurrencyType currencyType, LocalDateTime startDateTime) {
		CryptoDataCompareRateSubResult r = new CryptoDataCompareRateSubResult();
		CryptoCoinPriceCommonDataBO dataComparedStart = dailyDataService.getCommonData(coinTypeCompared, currencyType, startDateTime);
		
		if(dataComparedStart == null) {
			r.failWithMessage("compared data error");
			return r;
		}
		
		LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
		List<CryptoCoinPriceCommonDataBO> dataComparedList = dailyDataService.getCommonDataListFillWithCache(coinTypeCompared, currencyType, today);
		
		if(dataComparedList.isEmpty()) {
			r.failWithMessage("compared data list error");
			return r;
		}
		
		CryptoCoinPriceCommonDataBO dataComparedEnd = dataComparedList.get(0);
		
		BigDecimal dataComparedRate = new BigDecimal(dataComparedEnd.getEndPrice().doubleValue() / dataComparedStart.getStartPrice().doubleValue());
		
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
		if(checkDTOResult.isFail()) {
			r.addMessage(checkDTOResult.getMessage());
			return r;
		}
		
		CurrencyType currencyType = CurrencyType.getType(dto.getCurrencyType());
		TimeUnitType timeUnitType = TimeUnitType.getType(dto.getTimeUnit());
		
		CryptoCoinCatalog coinType1 = coinCatalogService.findCatalog(dto.getCoinTypeOrigin());
		CryptoCoinCatalog coinType2 = coinCatalogService.findCatalog(dto.getCoinTypeCompared());
		
		List<CryptoCoinPriceCommonDataBO> dataList1 = getHistoryDataList(coinType1, currencyType, timeUnitType, dto.getTimeRange());
		List<CryptoCoinPriceCommonDataBO> dataList2 = getHistoryDataList(coinType2, currencyType, timeUnitType, dto.getTimeRange());
		
		if(dataList1.isEmpty() || dataList2.isEmpty()) {
			r.setDataList1(dataList1);
			r.setDataList2(dataList2);
			r.normalSuccess();
			return r;
		}
		
		LocalDateTime startTime1 = dataList1.get(0).getStartTime();
		LocalDateTime startTime2 = dataList2.get(0).getStartTime();
		LocalDateTime commonStartTime = startTime1;
		if(startTime1.isBefore(startTime2)) {
			commonStartTime = startTime2;
		}
		
		while(dataList1.get(0).getStartTime().isBefore(commonStartTime)) {
			dataList1.remove(0);
		}
		while(dataList2.get(0).getStartTime().isBefore(commonStartTime)) {
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
			if(StringUtils.isBlank(dto.getStartDateTimeStr())) {
				r.failWithMessage("time setting error");
			}
		} else {
			if (dto.getTimeRange() == null || dto.getTimeRange() < 1) {
				r.failWithMessage("time range error");
				return r;
			}
		}
		
		CurrencyType currencyType = CurrencyType.getType(dto.getCurrencyType());
		if(currencyType == null) {
			dto.setCurrencyType(CurrencyType.USD.getCode());
		}

		if (localDateTimeHandler.determineDateFormat(dto.getStartDateTimeStr()) == null) {
			r.failWithMessage("date time format error");
			return r;
		}
		
		if(StringUtils.isAnyBlank(dto.getCoinTypeOrigin(), dto.getCoinTypeCompared()) || dto.getCoinTypeOrigin().equals(dto.getCoinTypeCompared())) {
			r.failWithMessage("coin type error");
			return r;
		}

		r.normalSuccess();
		return r;

	}
	
	private CommonResult checkDTO(CryptoCoinDataMutipleCompareDTO dto) {
		CommonResult r = new CommonResult();
		
		CurrencyType currencyType = CurrencyType.getType(dto.getCurrencyType());
		if(currencyType == null) {
			dto.setCurrencyType(CurrencyType.USD.getCode());
		}

		if (localDateTimeHandler.determineDateFormat(dto.getStartDateTimeStr()) == null) {
			r.failWithMessage("date time format error");
			return r;
		}
		
		if(StringUtils.isBlank(dto.getCoinTypeOrigin()) || dto.getCoinTypeComparedList() == null || dto.getCoinTypeComparedList().isEmpty()) {
			r.failWithMessage("coin type error");
			return r;
		}

		r.normalSuccess();
		return r;

	}
}
