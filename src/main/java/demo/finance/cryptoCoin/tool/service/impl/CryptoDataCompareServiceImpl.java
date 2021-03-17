package demo.finance.cryptoCoin.tool.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.tool.pojo.dto.CryptoCoinDataCompareDTO;
import demo.finance.cryptoCoin.tool.pojo.result.CryptoDataCompareResult;
import demo.finance.cryptoCoin.tool.service.CryptoDataCompareService;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

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
		
		CryptoCoinType coinType1 = CryptoCoinType.getType(dto.getCoinType1());
		CryptoCoinType coinType2 = CryptoCoinType.getType(dto.getCoinType2());
		CurrencyType currencyType = CurrencyType.getType(dto.getCurrencyType());
		
		LocalDateTime startDateTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getStartDateTimeStr());
		CryptoCoinPriceCommonDataBO data1Start = dailyDataService.getCommonData(coinType1, currencyType, startDateTime);
		CryptoCoinPriceCommonDataBO data2Start = dailyDataService.getCommonData(coinType2, currencyType, startDateTime);
		
		if(data1Start == null || data2Start == null) {
			r.failWithMessage("one compare data error");
			return r;
		}
		
		LocalDateTime now = LocalDateTime.now();
		List<CryptoCoinPriceCommonDataBO> data1List = dailyDataService.getCommonDataListFillWithCache(coinType1, currencyType, now);
		List<CryptoCoinPriceCommonDataBO> data2List = dailyDataService.getCommonDataListFillWithCache(coinType2, currencyType, now);
		
		if(data1List.isEmpty() || data2List.isEmpty()) {
			r.failWithMessage("one compare data list error");
			return r;
		}
		
		CryptoCoinPriceCommonDataBO data1End = data1List.get(0);
		CryptoCoinPriceCommonDataBO data2End = data2List.get(0);
		
		BigDecimal data1Rate = data1End.getEndPrice().divide(data1Start.getStartPrice());
		BigDecimal data2Rate = data2End.getEndPrice().divide(data2Start.getStartPrice());
		
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
		
		CryptoCoinType coinType1 = CryptoCoinType.getType(dto.getCoinType1());
		CryptoCoinType coinType2 = CryptoCoinType.getType(dto.getCoinType2());
		CurrencyType currencyType = CurrencyType.getType(dto.getCurrencyType());
		TimeUnitType timeUnitType = TimeUnitType.getType(dto.getTimeUnit());
		
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
			r.failWithMessage("time unit error");
			return r;
		}
		
		CurrencyType currencyType = CurrencyType.getType(dto.getCurrencyType());
		if(currencyType == null) {
			dto.setCurrencyType(CurrencyType.USD.getCode());
		}

		if (dto.getTimeRange() < 1) {
			r.failWithMessage("time range error");
			return r;
		}

		if (localDateTimeHandler.determineDateFormat(dto.getStartDateTimeStr()) == null) {
			r.failWithMessage("date time format error");
			return r;
		}
		
		CryptoCoinType coinType1 = CryptoCoinType.getType(dto.getCoinType1());
		CryptoCoinType coinType2 = CryptoCoinType.getType(dto.getCoinType1());
		
		if(coinType1 == null || coinType2 == null || coinType1.equals(coinType2)) {
			r.failWithMessage("coin type error");
			return r;
		}

		r.normalSuccess();
		return r;

	}
}
