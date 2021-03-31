package demo.finance.cryptoCoin.data.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice1dayMapper;
import demo.finance.cryptoCoin.data.pojo.constant.CryptoCoinConstant;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1day;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1dayExample;
import demo.finance.cryptoCoin.data.service.CryptoCoin1DayDataSummaryService;
import demo.finance.cryptoCoin.mq.producer.CryptoCoinDailyDataQueryAckProducer;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.dto.CryptoCoinDailyDataQueryDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinDataDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinDataSubDTO;

@Service
public class CryptoCoin1DayDataSummaryServiceImpl extends CryptoCoinCommonService
		implements CryptoCoin1DayDataSummaryService {

	private final int dayStepLong = 1;

	@Autowired
	private CryptoCoinPrice1dayMapper _1DayDataMapper;
	
	@Autowired
	private CryptoCoinDailyDataQueryAckProducer cryptoCoinDailyDataQueryAckProducer;

	@Override
	public CommonResult reciveDailyData(CryptoCoinDataDTO dto) {
		CommonResult r = new CommonResult();
		List<CryptoCoinDataSubDTO> dataList = dto.getPriceHistoryData();
		if (dataList == null || dataList.isEmpty()) {
			return r;
		}

		CryptoCoinCatalog coinType = coinCatalogService.findCatalog((dto.getCryptoCoinTypeName()));
		CurrencyType currencyType = CurrencyType.getType(dto.getCurrencyName());
		if (coinType == null || currencyType == null) {
			return r;
		}

		mergeDuplicateData(coinType);
		updateSummaryData(dataList, coinType, currencyType);

		findMissingDailyData(coinType.getId());
		return r;
	}

	private void updateSummaryData(List<CryptoCoinDataSubDTO> dataList, CryptoCoinCatalog coinType,
			CurrencyType currencyType) {

		LocalDateTime dataStartTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dataList.get(0).getTime());
		LocalDateTime dataEndime = localDateTimeHandler
				.stringToLocalDateTimeUnkonwFormat(dataList.get(dataList.size() - 1).getTime());

		CryptoCoinPrice1dayExample example = new CryptoCoinPrice1dayExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getId()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeBetween(dataStartTime, dataEndime);

		List<CryptoCoinPrice1day> poList = _1DayDataMapper.selectByExample(example);

		LocalDateTime tmpDataTime = null;
		boolean dataTimeMatchFlag = false;
		CryptoCoinDataSubDTO tmpNewData = null;
		for (int dataIndex = 0; dataIndex < dataList.size(); dataIndex++) {
			tmpNewData = dataList.get(dataIndex);
			tmpDataTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(tmpNewData.getTime());
			for (int i = 0; i < poList.size() && !dataTimeMatchFlag; i++) {
				CryptoCoinPrice1day po = poList.get(i);
				if (po.getStartTime().isEqual(tmpDataTime)) {
					dataTimeMatchFlag = true;
					mergeDataPair(po, tmpNewData);
				}
			}
			if (!dataTimeMatchFlag) {
				insertNewData(tmpNewData, coinType, currencyType);
			}

			dataTimeMatchFlag = false;
		}

	}

	private CryptoCoinPrice1day mergeDataPair(CryptoCoinPrice1day target, CryptoCoinDataSubDTO data) {
		target.setStartPrice(new BigDecimal(data.getStart()));
		target.setEndPrice(new BigDecimal(data.getEnd()));
		target.setHighPrice(new BigDecimal(data.getHigh()));
		target.setLowPrice(new BigDecimal(data.getLow()));
		if (data.getVolume() != null && data.getVolume() > 0) {
			target.setVolume(new BigDecimal(data.getVolume()));
		}

		_1DayDataMapper.updateByPrimaryKeySelective(target);
		return target;
	}

	private CryptoCoinPrice1day mergeDataPair(CryptoCoinPrice1day target, CryptoCoinPrice1day source) {
		if (target.getStartTime() == null || target.getStartTime().isAfter(source.getStartTime())) {
			target.setStartTime(source.getStartTime());
			target.setStartPrice(source.getStartPrice());
		}
		if (target.getEndTime() == null || target.getEndTime().isBefore(source.getEndTime())) {
			target.setEndTime(source.getEndTime());
			target.setEndPrice(source.getEndPrice());
		}
		target.setVolume(target.getVolume().add(source.getVolume()));
		if (target.getHighPrice() == null || target.getHighPrice().doubleValue() < source.getHighPrice().byteValue()) {
			target.setHighPrice(source.getHighPrice());
		}
		if (target.getLowPrice() == null || target.getLowPrice().doubleValue() > source.getLowPrice().byteValue()) {
			target.setLowPrice(source.getLowPrice());
		}

		_1DayDataMapper.deleteByPrimaryKey(source.getId());
		return target;
	}

	private void insertNewData(CryptoCoinDataSubDTO data, CryptoCoinCatalog coinType, CurrencyType currencyType) {
		CryptoCoinPrice1day po = new CryptoCoinPrice1day();
		po.setId(snowFlake.getNextId());
		po.setStartTime(localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(data.getTime()).withHour(0).withMinute(0)
				.withSecond(0).withNano(0));
		po.setEndTime(po.getStartTime().plusDays(dayStepLong));
		po.setCoinType(coinType.getId());
		po.setCurrencyType(currencyType.getCode());
		po.setStartPrice(new BigDecimal(data.getStart()));
		po.setEndPrice(new BigDecimal(data.getEnd()));
		po.setHighPrice(new BigDecimal(data.getHigh()));
		po.setLowPrice(new BigDecimal(data.getLow()));
		po.setVolume(new BigDecimal(data.getVolume()));

		_1DayDataMapper.insertSelective(po);
	}

	@Override
	public CryptoCoinPriceCommonDataBO getCommonData(CryptoCoinCatalog coinType, CurrencyType currencyType,
			LocalDateTime datetime) {
		CryptoCoinPriceCommonDataBO tmpCommonData = null;
		if (datetime == null || !LocalDate.now().isAfter(datetime.toLocalDate())) {
			return tmpCommonData;
		}

		if (LocalDate.now().equals(datetime.toLocalDate())) {
			tmpCommonData = cacheService.getCommonData(coinType, currencyType, datetime);
		}

		CryptoCoinPrice1dayExample example = new CryptoCoinPrice1dayExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getId()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeLessThanOrEqualTo(datetime).andEndTimeGreaterThanOrEqualTo(datetime);
		List<CryptoCoinPrice1day> poList = _1DayDataMapper.selectByExample(example);
		if (!poList.isEmpty()) {
			tmpCommonData = new CryptoCoinPriceCommonDataBO();
			BeanUtils.copyProperties(poList.get(0), tmpCommonData);
			return tmpCommonData;
		}

		return tmpCommonData;
	}

	@Override
	public List<CryptoCoinPrice1day> getDataList(CryptoCoinCatalog coinType, CurrencyType currencyType,
			LocalDateTime startTime) {
		CryptoCoinPrice1dayExample example = new CryptoCoinPrice1dayExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getId()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeGreaterThanOrEqualTo(startTime);
		;

		return _1DayDataMapper.selectByExample(example);
	}

	@Override
	public List<CryptoCoinPriceCommonDataBO> getCommonDataList(CryptoCoinCatalog coinType, CurrencyType currencyType,
			LocalDateTime startTime) {
		List<CryptoCoinPrice1day> poList = getDataList(coinType, currencyType, startTime);

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
	public List<CryptoCoinPriceCommonDataBO> getCommonDataListFillWithCache(CryptoCoinCatalog coinType,
			CurrencyType currencyType, LocalDateTime startTime) {

		List<CryptoCoinPriceCommonDataBO> poDataList = getCommonDataList(coinType, currencyType, startTime);
//		List<CryptoCoinPriceCommonDataBO> poDataList = buildFakeData(coinType, currencyType, startTime);

		List<CryptoCoinPriceCommonDataBO> cacheDataList = cacheService.getCommonDataList(coinType, currencyType,
				startTime);

		if (cacheDataList.isEmpty()) {
			return poDataList;
		}

		List<CryptoCoinPriceCommonDataBO> resultDataList = mergePODataWithCache(poDataList, cacheDataList, startTime,
				dayStepLong, TimeUnitType.day);

		return resultDataList;

	}

	private void mergeDuplicateData(CryptoCoinCatalog coinType) {
		CryptoCoinPrice1dayExample example = null;
		List<CryptoCoinPrice1day> poList = null;
		LocalDateTime endTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime startTime = endTime.minusDays(dayStepLong);
		// 只整理最近一个月内的重复数据, 采用时间倒序
		LocalDateTime finishTime = endTime.minusMonths(1);

		while (startTime.isAfter(finishTime)) {
			for (CurrencyType currencyType : CurrencyType.values()) {
				example = new CryptoCoinPrice1dayExample();
				example.createCriteria().andCoinTypeEqualTo(coinType.getId())
						.andCurrencyTypeEqualTo(currencyType.getCode()).andStartTimeBetween(startTime, endTime);
				poList = _1DayDataMapper.selectByExample(example);
				if (poList == null || poList.size() <= 1) {
					continue;
				}
				log.error("crypto coin 1day duplicate data: coinType: " + coinType.getCoinNameEnShort()
						+ ", currencyType: " + currencyType.getName() + ", size: " + poList.size());
				mergeDataList(poList);
			}

			startTime = startTime.minusDays(dayStepLong);
			endTime = endTime.minusDays(dayStepLong);
		}

	}

	private CryptoCoinPrice1day mergeDataList(List<CryptoCoinPrice1day> poList) {
		if (poList == null || poList.isEmpty()) {
			return null;
		}

		if (poList.size() == 1) {
			return poList.get(0);
		}

		CryptoCoinPrice1day firstPO = poList.get(0);
		for (int i = 1; i < poList.size(); i++) {
			firstPO = mergeDataPair(firstPO, poList.get(i));
		}

		_1DayDataMapper.updateByPrimaryKeySelective(firstPO);
		return firstPO;
	}

	@Override
	public void findMissingDailyData(Long preCoinType) {
		CryptoCoinDailyDataQueryDTO dto = new CryptoCoinDailyDataQueryDTO();

		CryptoCoinCatalog lastCatalog = coinCatalogService.findLastCatalog();
		if (lastCatalog == null) {
			log.error("crypto coin catalog service error, can NOT find any coin type");
			return;
		}

		if (preCoinType >= lastCatalog.getId()) {
			return;
		}

		CryptoCoinPrice1day data = findLastDailyData(preCoinType + 1);
		CryptoCoinCatalog catalog = coinCatalogService.findCatalog(preCoinType + 1);
		
		if (data == null) {
			// 新币, 未获取过数据/ 数据曾经清零
			dto.setCoinName(catalog.getCoinNameEnShort());
			dto.setCounting(CryptoCoinConstant.CRYPTO_COMPARE_API_DATA_MAX_LENGTH);
			cryptoCoinDailyDataQueryAckProducer.send(dto);
			
		} else {
			// 可能需要刷新数据
			Long days = ChronoUnit.DAYS.between(data.getStartTime(), LocalDateTime.now());
			if(days > 0) {
				dto.setCounting(days.intValue());
				cryptoCoinDailyDataQueryAckProducer.send(dto);
			} else {
				for(int i = 2; preCoinType + i <= lastCatalog.getId(); i++) {
					data = findLastDailyData(preCoinType + i);
					days = ChronoUnit.DAYS.between(data.getStartTime(), LocalDateTime.now());
					if(days > 0) {
						catalog = coinCatalogService.findCatalog(preCoinType + i);
						dto.setCoinName(catalog.getCoinNameEnShort());
						dto.setCounting(days.intValue());
						cryptoCoinDailyDataQueryAckProducer.send(dto);
						return;
					}
				}
			}
		}
	}

	private CryptoCoinPrice1day findLastDailyData(Long coinType) {
		if (coinType == null) {
			CryptoCoinCatalog defaultCoinCatalog = coinCatalogService
					.findCatalog(CryptoCoinConstant.DEFAULT_COIN_CATALOG);
			if (defaultCoinCatalog == null) {
				log.error("crypto coin catalog service error, can NOT find default coin");
				return null;
			}
			coinType = defaultCoinCatalog.getId();
		}

		return _1DayDataMapper.findLastDailyData(coinType);
	}
}
