package demo.finance.cryptoCoin.data.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.testEvent.cryptoCoin.pojo.type.CryptoCoinFlowType;
import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testModule.pojo.type.TestModuleType;
import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.automationTest.mq.producer.TestEventInsertAckProducer;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice1dayMapper;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1day;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1dayExample;
import demo.finance.cryptoCoin.data.service.CryptoCoin1DayDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoinCatalogService;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;
import demo.tool.telegram.pojo.constant.TelegramStaticChatID;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.dto.CryptoCoinDailyDataQueryDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinDataDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinDataSubDTO;
import finance.cryptoCoin.pojo.type.CryptoCoinDataSourceType;
import telegram.pojo.constant.TelegramBotType;
import telegram.pojo.dto.TelegramMessageDTO;

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

	@Autowired
	private TestEventInsertAckProducer testEventInsertAckProducer;

	@Override
	public CommonResult receiveDailyData(CryptoCoinDataDTO dto) {
		CommonResult r = new CommonResult();
		List<CryptoCoinDataSubDTO> dataList = dto.getPriceHistoryData();
		CryptoCoinDataSourceType dataSourceType = CryptoCoinDataSourceType.getType(dto.getDataSourceCode());
		if (dataSourceType == null) {
			dataSourceType = CryptoCoinDataSourceType.CRYPTO_COMPARE;
		}
		
		CryptoCoinCatalog coinType = coinCatalogService.findCatalog(dto.getCryptoCoinTypeName());
		CurrencyType currencyType = CurrencyType.getType(dto.getCurrencyName());
		if (coinType == null || currencyType == null) {
			return r;
		}
		
		if (!isValidData(dataList)) {
			TelegramMessageDTO msgDTO = new TelegramMessageDTO();
			msgDTO.setId(TelegramStaticChatID.MY_ID);
			msgDTO.setMsg(dto.getCryptoCoinTypeName() + ", get error data(all zero) from: " + dataSourceType.getName());
			msgDTO.setBotName(TelegramBotType.BOT_2.getName());
			telegramCryptoCoinMessageAckProducer.send(msgDTO);
			
			if(CryptoCoinDataSourceType.CRYPTO_COMPARE.equals(dataSourceType)) {
				sendDailyDataQuery(dto.getCryptoCoinTypeName(), optionService.getDefaultCurrency(),
						optionService.getDefaultDailyDataQueryLenth(), CryptoCoinDataSourceType.BINANCE);
			} else if(CryptoCoinDataSourceType.BINANCE.equals(dataSourceType)) {
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
	private boolean isValidData(List<CryptoCoinDataSubDTO> dataList) {
		if (dataList == null || dataList.isEmpty()) {
			return false;
		}
		int zeroDataCountDown = dataList.size();
		CryptoCoinDataSubDTO tmpData = null;
		for (int i = 0; i < dataList.size() && zeroDataCountDown > 0; i++) {
			tmpData = dataList.get(i);
			if (tmpData.getStart() == 0 && tmpData.getEnd() == 0 && tmpData.getHigh() == 0 && tmpData.getLow() == 0) {
				zeroDataCountDown--;
			}
		}
		return zeroDataCountDown > 0;
	}

	private void updateSummaryData(List<CryptoCoinDataSubDTO> dataList, CryptoCoinCatalog coinType,
			CurrencyType currencyType) {

		LocalDateTime dataStartTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dataList.get(0).getTime());
		dataStartTime = dataStartTime.with(LocalTime.MIN);
		LocalDateTime dataEndime = localDateTimeHandler
				.stringToLocalDateTimeUnkonwFormat(dataList.get(dataList.size() - 1).getTime());

		CryptoCoinPrice1dayExample example = new CryptoCoinPrice1dayExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getId()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeBetween(dataStartTime, dataEndime);

		List<CryptoCoinPrice1day> poList = dataMapper.selectByExample(example);

		LocalDateTime tmpDataTime = null;
		boolean dataTimeMatchFlag = false;
		CryptoCoinDataSubDTO tmpNewData = null;
		for (int dataIndex = 0; dataIndex < dataList.size(); dataIndex++) {
			tmpNewData = dataList.get(dataIndex);
			tmpDataTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(tmpNewData.getTime());
			tmpDataTime = tmpDataTime.with(LocalTime.MIN);
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

	private void insertNewData(CryptoCoinDataSubDTO data, CryptoCoinCatalog coinType, CurrencyType currencyType) {
		CryptoCoinPrice1day po = new CryptoCoinPrice1day();
		po.setId(snowFlake.getNextId());
		po.setStartTime(localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(data.getTime()).withHour(0).withMinute(0)
				.withSecond(0).withNano(0));
		po.setEndTime(po.getStartTime().plusDays(DAY_STEP_LONG));
		po.setCoinType(coinType.getId());
		po.setCurrencyType(currencyType.getCode());
		po.setStartPrice(new BigDecimal(data.getStart()));
		po.setEndPrice(new BigDecimal(data.getEnd()));
		po.setHighPrice(new BigDecimal(data.getHigh()));
		po.setLowPrice(new BigDecimal(data.getLow()));
		po.setVolume(new BigDecimal(data.getVolume()));

		dataMapper.insertSelective(po);
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
		List<CryptoCoinPrice1day> poList = dataMapper.selectByExample(example);
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

		return dataMapper.selectByExample(example);
	}

	@Override
	public List<CryptoCoinPrice1day> getDataList(CryptoCoinCatalog coinType, CurrencyType currencyType,
			LocalDateTime startTime, LocalDateTime endTime) {
		CryptoCoinPrice1dayExample example = new CryptoCoinPrice1dayExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getId()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeGreaterThanOrEqualTo(startTime).andEndTimeLessThanOrEqualTo(endTime);

		return dataMapper.selectByExample(example);
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
	public List<CryptoCoinPriceCommonDataBO> getCommonDataList(CryptoCoinCatalog coinType, CurrencyType currencyType,
			LocalDateTime startTime, LocalDateTime endTime) {
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
			CurrencyType currencyType, LocalDateTime startTime) {

		List<CryptoCoinPriceCommonDataBO> poDataList = getCommonDataList(coinType, currencyType, startTime);
//		List<CryptoCoinPriceCommonDataBO> poDataList = buildFakeData(coinType, currencyType, startTime);

		List<CryptoCoinPriceCommonDataBO> cacheDataList = cacheService.getCommonDataList(coinType, currencyType,
				startTime);

		if (cacheDataList.isEmpty()) {
			return poDataList;
		}

		List<CryptoCoinPriceCommonDataBO> resultDataList = mergePODataWithCache(poDataList, cacheDataList, startTime,
				DAY_STEP_LONG, TimeUnitType.day);

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
		example.createCriteria().andCoinTypeEqualTo(coinType.getId()).andCurrencyTypeEqualTo(CurrencyType.USD.getCode())
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
						+ ", currencyType: " + CurrencyType.USD.getName() + ", size: " + mergeDataList.size());
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
				sendDailyDataQuery(catalog.getCoinNameEnShort(), optionService.getDefaultCurrency(),
						optionService.getDefaultDailyDataQueryLenth(), CryptoCoinDataSourceType.CRYPTO_COMPARE);
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

	@Override
	public void sendCryptoCoinDailyDataQueryMsg(String coinName, String currencyName, Integer counting) {
		if (StringUtils.isBlank(coinName) || counting < 1) {
			return;
		}

		if (StringUtils.isBlank(currencyName)) {
			currencyName = optionService.getDefaultCurrency();
		}

		sendDailyDataQuery(coinName, currencyName, counting, CryptoCoinDataSourceType.CRYPTO_COMPARE);
	}

	private void sendDailyDataQuery(String coinName, String currencyName, Integer counting,
			CryptoCoinDataSourceType dataSource) {
		AutomationTestInsertEventDTO dto = new AutomationTestInsertEventDTO();
		dto.setTestModuleType(TestModuleType.CRYPTO_COIN.getId());
		dto.setFlowType(CryptoCoinFlowType.DAILY_DATA.getId());
		dto.setTestEventId(snowFlake.getNextId());

		CryptoCoinDailyDataQueryDTO paramDTO = new CryptoCoinDailyDataQueryDTO();
		paramDTO.setApiKey(optionService.getCryptoCompareApiKey());
		paramDTO.setCoinName(coinName);
		paramDTO.setCurrencyName(optionService.getDefaultCurrency());
		paramDTO.setCounting(counting);
		paramDTO.setDataSourceCode(CryptoCoinDataSourceType.CRYPTO_COMPARE.getCode());
		
		dto = automationTestInsertEventDtoAddParamStr(dto, paramDTO);

		testEventInsertAckProducer.send(dto);
	}
}
