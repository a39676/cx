package demo.finance.cryptoCoin.data.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice1dayMapper;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1day;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1dayExample;
import demo.finance.cryptoCoin.data.service.CryptoCoin1DayDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoinCatalogService;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;
import finance.common.pojo.bo.KLineCommonDataBO;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.dto.CryptoCoinDataDTO;
import finance.cryptoCoin.pojo.type.CryptoCoinDataSourceType;
import finance.cryptoCoin.pojo.type.CurrencyTypeForCryptoCoin;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.type.TelegramBotType;

@Service
public class CryptoCoin1DayDataSummaryServiceImpl extends CryptoCoinCommonService
		implements CryptoCoin1DayDataSummaryService {

	private final int DAY_STEP_LONG = 1;

	@Autowired
	private CryptoCoinPrice1dayMapper dataMapper;
	@Autowired
	private CryptoCoinCatalogService coinCatalogService;
	@Autowired
	private CryptoCoinPriceCacheService cacheService;

	@Override
	public CommonResult receiveDailyData(CryptoCoinDataDTO dto) {
		CommonResult r = new CommonResult();
		List<CryptoCoinPriceCommonDataBO> dataList = dto.getPriceHistoryData();
		CryptoCoinDataSourceType dataSourceType = CryptoCoinDataSourceType.getType(dto.getDataSourceCode());
		if (dataSourceType == null) {
			dataSourceType = CryptoCoinDataSourceType.CRYPTO_COMPARE;
		}

		String symbol = dto.getSymbol();
		CryptoCoinCatalog coinType = coinCatalogService
				.findCatalog(symbol.replaceAll(defaultCyrrencyTypeForCryptoCoin.getName(), ""));
		CurrencyTypeForCryptoCoin currencyType = defaultCyrrencyTypeForCryptoCoin;
		if (coinType == null || currencyType == null) {
			return r;
		}

		if (!isValidData(dataList)) {
			telegramService.sendMessageByChatRecordId(TelegramBotType.BBT_MESSAGE,
					dto.getSymbol() + ", get error data(all zero) from: " + dataSourceType.getName(),
					TelegramStaticChatID.MY_ID);

			if (CryptoCoinDataSourceType.CRYPTO_COMPARE.equals(dataSourceType)) {
//				sendDailyDataQuery(dto.getCryptoCoinTypeName(), optionService.getDefaultCurrency(),
//						optionService.getDefaultDailyDataQueryLenth(), CryptoCoinDataSourceType.BINANCE);
//				TODO rebuild API if need
			} else if (CryptoCoinDataSourceType.BINANCE.equals(dataSourceType)) {
				constantService.getDailyDataWaitingQuerySet().remove(coinType.getCoinNameEnShort());
			}

			return r;
		}

		updateSummaryData(dataList, coinType, currencyType);

		constantService.getDailyDataWaitingQuerySet().remove(coinType.getCoinNameEnShort());

		r.setIsSuccess();
		return r;
	}

	/*
	 * 2021-04-14 crypto compare 有时会返回全0数据 暂不处理此类数据 dto 应该附带数据源
	 */
	private <E extends KLineCommonDataBO> boolean isValidData(List<E> dataList) {
		if (dataList == null || dataList.isEmpty()) {
			return false;
		}
		int zeroDataCountDown = dataList.size();
		KLineCommonDataBO tmpData = null;
		for (int i = 0; i < dataList.size() && zeroDataCountDown > 0; i++) {
			tmpData = dataList.get(i);
			if (tmpData.getStartPrice().equals(BigDecimal.ZERO) && tmpData.getEndPrice().equals(BigDecimal.ZERO)
					&& tmpData.getHighPrice().equals(BigDecimal.ZERO)
					&& tmpData.getLowPrice().equals(BigDecimal.ZERO)) {
				zeroDataCountDown--;
			}
		}
		return zeroDataCountDown > 0;
	}

	private <E extends KLineCommonDataBO> void updateSummaryData(List<E> dataList, CryptoCoinCatalog coinType,
			CurrencyTypeForCryptoCoin currencyType) {

		LocalDateTime dataStartTime = dataList.get(0).getStartTime();
		dataStartTime = dataStartTime.with(LocalTime.MIN);
		LocalDateTime dataEndime = dataList.get(dataList.size() - 1).getStartTime();

		CryptoCoinPrice1dayExample example = new CryptoCoinPrice1dayExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getId()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeBetween(dataStartTime, dataEndime);

		List<CryptoCoinPrice1day> poList = dataMapper.selectByExample(example);

		boolean dataTimeMatchFlag = false;
		KLineCommonDataBO tmpNewData = null;
		for (int dataIndex = 0; dataIndex < dataList.size(); dataIndex++) {
			tmpNewData = dataList.get(dataIndex);
			for (int i = 0; i < poList.size() && !dataTimeMatchFlag; i++) {
				CryptoCoinPrice1day po = poList.get(i);
				if (po.getStartTime().isEqual(tmpNewData.getStartTime().with(LocalTime.MIN))) {
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

	private CryptoCoinPrice1day mergeDataPair(CryptoCoinPrice1day target, KLineCommonDataBO data) {
		target.setStartPrice(data.getStartPrice());
		target.setEndPrice(data.getEndPrice());
		target.setHighPrice(data.getHighPrice());
		target.setLowPrice(data.getLowPrice());
		if (data.getVolume() != null && data.getVolume().compareTo(BigDecimal.ZERO) > 0) {
			target.setVolume(data.getVolume());
		}

		dataMapper.updateByPrimaryKeySelective(target);
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
		target.setVolume(source.getVolume());
		if (target.getHighPrice() == null || target.getHighPrice().doubleValue() < source.getHighPrice().byteValue()) {
			target.setHighPrice(source.getHighPrice());
		}
		if (target.getLowPrice() == null || target.getLowPrice().doubleValue() > source.getLowPrice().byteValue()) {
			target.setLowPrice(source.getLowPrice());
		}

		dataMapper.deleteByPrimaryKey(source.getId());
		return target;
	}

	private void insertNewData(KLineCommonDataBO data, CryptoCoinCatalog coinType,
			CurrencyTypeForCryptoCoin currencyType) {
		CryptoCoinPrice1day po = new CryptoCoinPrice1day();
		po.setId(snowFlake.getNextId());
		po.setStartTime(data.getStartTime().with(LocalTime.MIN));
		po.setEndTime(po.getStartTime().plusDays(DAY_STEP_LONG));
		po.setCoinType(coinType.getId());
		po.setCurrencyType(currencyType.getCode());
		po.setStartPrice(data.getStartPrice());
		po.setEndPrice(data.getEndPrice());
		po.setHighPrice(data.getHighPrice());
		po.setLowPrice(data.getLowPrice());
		po.setVolume(data.getVolume());

		dataMapper.insertSelective(po);
	}

	@Override
	public CryptoCoinPriceCommonDataBO getCommonData(CryptoCoinCatalog coinType, CurrencyTypeForCryptoCoin currencyType,
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
		List<CryptoCoinPrice1day> poList = dataMapper.selectByExample(example);
		if (!poList.isEmpty()) {
			tmpCommonData = new CryptoCoinPriceCommonDataBO();
			BeanUtils.copyProperties(poList.get(0), tmpCommonData);
			return tmpCommonData;
		}

		return tmpCommonData;
	}

	@Override
	public List<CryptoCoinPrice1day> getDataList(CryptoCoinCatalog coinType, CurrencyTypeForCryptoCoin currencyType,
			LocalDateTime startTime) {
		CryptoCoinPrice1dayExample example = new CryptoCoinPrice1dayExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getId()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeGreaterThanOrEqualTo(startTime);

		return dataMapper.selectByExample(example);
	}

	@Override
	public List<CryptoCoinPrice1day> getDataList(CryptoCoinCatalog coinType, CurrencyTypeForCryptoCoin currencyType,
			LocalDateTime startTime, LocalDateTime endTime) {
		CryptoCoinPrice1dayExample example = new CryptoCoinPrice1dayExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getId()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeGreaterThanOrEqualTo(startTime).andEndTimeLessThanOrEqualTo(endTime);

		return dataMapper.selectByExample(example);
	}

	@Override
	public List<CryptoCoinPriceCommonDataBO> getCommonDataList(CryptoCoinCatalog coinType,
			CurrencyTypeForCryptoCoin currencyType, LocalDateTime startTime) {
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
	public List<CryptoCoinPriceCommonDataBO> getCommonDataList(CryptoCoinCatalog coinType,
			CurrencyTypeForCryptoCoin currencyType, LocalDateTime startTime, LocalDateTime endTime) {
		List<CryptoCoinPrice1day> poList = getDataList(coinType, currencyType, startTime, endTime);

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
			CurrencyTypeForCryptoCoin currencyType, LocalDateTime startTime) {

		List<CryptoCoinPriceCommonDataBO> poDataList = getCommonDataList(coinType, currencyType, startTime);
//		List<CryptoCoinPriceCommonDataBO> poDataList = buildFakeData(coinType, currencyType, startTime);

		List<CryptoCoinPriceCommonDataBO> cacheDataList = cacheService.getCommonDataList(coinType, currencyType,
				startTime);

		if (cacheDataList.isEmpty()) {
			return poDataList;
		}

		List<CryptoCoinPriceCommonDataBO> resultDataList = mergePODataWithCache(poDataList, cacheDataList, startTime,
				DAY_STEP_LONG, TimeUnitType.DAY);

		return resultDataList;

	}

	/* trying */
	@SuppressWarnings("unused")
	private void mergeDuplicateData(CryptoCoinCatalog coinType) {
		LocalDateTime todayStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime indexTime = todayStart;
		// 只整理最近一个月内的重复数据, 采用时间倒序
		LocalDateTime finishTime = todayStart.minusMonths(1);

		CryptoCoinPrice1dayExample example = new CryptoCoinPrice1dayExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getId())
				.andCurrencyTypeEqualTo(CurrencyTypeForCryptoCoin.USD.getCode())
				.andStartTimeBetween(finishTime, todayStart);
		List<CryptoCoinPrice1day> poList = dataMapper.selectByExample(example);

		if (poList == null || poList.size() <= 1) {
			return;
		}

		List<CryptoCoinPrice1day> mergeDataList = null;

		while (indexTime.isAfter(finishTime)) {
			mergeDataList = new ArrayList<>();
			for (CryptoCoinPrice1day po : poList) {
				if (po.getStartTime().isEqual(indexTime)) {
					mergeDataList.add(po);
				}
			}

			if (mergeDataList.size() > 1) {
				log.error("crypto coin 1day duplicate data: coinType: " + coinType.getCoinNameEnShort()
						+ ", currencyType: " + CurrencyTypeForCryptoCoin.USD.getName() + ", size: "
						+ mergeDataList.size());
				mergeDataList(mergeDataList);
			}

			indexTime = indexTime.minusDays(DAY_STEP_LONG);
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

		dataMapper.updateByPrimaryKeySelective(firstPO);
		return firstPO;
	}

	@Override
	public void sendAllCryptoCoinDailyDataQueryMsg() {
		Set<String> waitingQuerySet = constantService.getDailyDataWaitingQuerySet();
		CryptoCoinCatalog catalog = null;
		for (String catalogName : waitingQuerySet) {
			catalog = coinCatalogService.findCatalog(catalogName);
			if (catalog != null) {
//				sendDailyDataQuery(catalog.getCoinNameEnShort(), optionService.getDefaultCurrency(),
//						optionService.getDefaultDailyDataQueryLenth(), CryptoCoinDataSourceType.CRYPTO_COMPARE);
//				TODO rebuild API if need
			}
		}
	}

	@Override
	public void resetDailyDataWaitingQuerySet() {
		List<CryptoCoinCatalog> allCatalogList = coinCatalogService.getAllCatalog();
		constantService.getDailyDataWaitingQuerySet().clear();
		constantService.getDailyDataWaitingQuerySet()
				.addAll(allCatalogList.stream().map(po -> po.getCoinNameEnShort()).collect(Collectors.toSet()));
	}

}
