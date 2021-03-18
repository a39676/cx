package demo.finance.cryptoCoin.tool.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.tool.pojo.dto.CryptoCoinDataCompareDTO;
import demo.finance.cryptoCoin.tool.pojo.result.CryptoDataCompareResult;
import demo.finance.cryptoCoin.tool.service.CryptoDataCompareService;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;

@Service
public class CryptoDataCompareServiceImpl extends CryptoCoinCommonService implements CryptoDataCompareService {

	@Override
	public CryptoDataCompareResult cryptoCoinDailyDataComparePoint(CryptoCoinDataCompareDTO dto) {
		CryptoDataCompareResult r = new CryptoDataCompareResult();
		CommonResult checkDTOResult = checkDTO(dto);
		if(checkDTOResult.isFail()) {
			r.addMessage(checkDTOResult.getMessage());
			return r;
		}
		
		CurrencyType currencyType = CurrencyType.getType(dto.getCurrencyType());
		
		CryptoCoinCatalog coinType1 = coinCatalogService.findCatalog(dto.getCoinType1());
		CryptoCoinCatalog coinType2 = coinCatalogService.findCatalog(dto.getCoinType2());
		
		LocalDateTime startDateTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getStartDateTimeStr());
		CryptoCoinPriceCommonDataBO data1Start = dailyDataService.getCommonData(coinType1, currencyType, startDateTime);
		CryptoCoinPriceCommonDataBO data2Start = dailyDataService.getCommonData(coinType2, currencyType, startDateTime);
		
		if(data1Start == null || data2Start == null) {
			r.failWithMessage("one compare data error");
			return r;
		}
		
		LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
		List<CryptoCoinPriceCommonDataBO> data1List = dailyDataService.getCommonDataListFillWithCache(coinType1, currencyType, today);
		List<CryptoCoinPriceCommonDataBO> data2List = dailyDataService.getCommonDataListFillWithCache(coinType2, currencyType, today);
		
		if(data1List.isEmpty() || data2List.isEmpty()) {
			r.failWithMessage("one compare data list error");
			return r;
		}
		
		CryptoCoinPriceCommonDataBO data1End = data1List.get(0);
		CryptoCoinPriceCommonDataBO data2End = data2List.get(0);
		
		BigDecimal data1Rate = new BigDecimal(data1End.getEndPrice().doubleValue() / data1Start.getStartPrice().doubleValue());
		BigDecimal data2Rate = new BigDecimal(data2End.getEndPrice().doubleValue() / data2Start.getStartPrice().doubleValue());
		
		r.setDifferentRate(data1Rate.subtract(data2Rate));
		r.setIsSuccess();
		return r;
	}
	
	@Override
	public CryptoDataCompareResult cryptoCoinDataCompareLine(CryptoCoinDataCompareDTO dto) {
		CryptoDataCompareResult r = new CryptoDataCompareResult();
		CommonResult checkDTOResult = checkDTO(dto);
		if(checkDTOResult.isFail()) {
			r.addMessage(checkDTOResult.getMessage());
			return r;
		}
		
		CurrencyType currencyType = CurrencyType.getType(dto.getCurrencyType());
		TimeUnitType timeUnitType = TimeUnitType.getType(dto.getTimeUnit());
		
		CryptoCoinCatalog coinType1 = coinCatalogService.findCatalog(dto.getCoinType1());
		CryptoCoinCatalog coinType2 = coinCatalogService.findCatalog(dto.getCoinType2());
		
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
	
	private CommonResult checkDTO(CryptoCoinDataCompareDTO dto) {
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
		
		if(StringUtils.isAnyBlank(dto.getCoinType1(), dto.getCoinType2()) || dto.getCoinType1().equals(dto.getCoinType2())) {
			r.failWithMessage("coin type error");
			return r;
		}

		r.normalSuccess();
		return r;

	}
}
