package demo.finance.cryptoCoin.data.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice1minuteMapper;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1minute;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1minuteExample;
import demo.finance.cryptoCoin.data.service.CryptoCoin1MinuteDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;
import demo.tool.telegram.pojo.constant.TelegramStaticChatID;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.constant.CryptoCoinDataConstant;
import finance.cryptoCoin.pojo.dto.CryptoCoinHistoryPriceDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinHistoryPriceSubDTO;
import finance.cryptoCoin.pojo.type.CryptoCoinType;
import telegram.pojo.dto.TelegramMessageDTO;

@Service
public class CryptoCoin1MinuteDataSummaryServiceImpl extends CryptoCoinCommonService
		implements CryptoCoin1MinuteDataSummaryService {

	private final int minuteStepLong = 1;

	@Autowired
	private CryptoCoinPrice1minuteMapper summaryMapper;
	@Autowired
	private CryptoCoinPriceCacheService cacheService;

	@Override
	public CommonResult reciveCoinHistoryPrice(CryptoCoinHistoryPriceDTO dto) {
		CommonResult r = new CommonResult();

		List<CryptoCoinHistoryPriceSubDTO> dataList = dto.getPriceHistoryData();
		if (dataList == null || dataList.isEmpty()) {
			return r;
		}

		CryptoCoinType coinType = CryptoCoinType.getType(dto.getCryptoCoinTypeName());
		CurrencyType currencyType = CurrencyType.getType(dto.getCurrencyName());
		if (coinType == null || currencyType == null) {
			return r;
		}

		updateSummaryData(dataList, coinType, currencyType);
		constantService.setValByName(CryptoCoinDataConstant.LAST_HISTORY_DATA_ACTIVE_REDIS_KEY, "_", CryptoCoinDataConstant.LAST_HISTORY_DATA_INACTIVE_JUDGMENT_SECOND, TimeUnit.SECONDS);

		return r;
	}

	private void updateSummaryData(List<CryptoCoinHistoryPriceSubDTO> dataList, CryptoCoinType coinType,
			CurrencyType currencyType) {

		LocalDateTime dataStartTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dataList.get(0).getTime());
		LocalDateTime dataEndime = localDateTimeHandler
				.stringToLocalDateTimeUnkonwFormat(dataList.get(dataList.size() - 1).getTime());

		CryptoCoinPrice1minuteExample example = new CryptoCoinPrice1minuteExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getCode()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeBetween(dataStartTime, dataEndime);

		List<CryptoCoinPrice1minute> poList = summaryMapper.selectByExample(example);

		LocalDateTime tmpDataTime = null;
		boolean dataTimeMatchFlag = false;
		CryptoCoinHistoryPriceSubDTO data = null;
		for (int dataIndex = 0; dataIndex < dataList.size(); dataIndex++) {
			data = dataList.get(dataIndex);
			tmpDataTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(data.getTime());
			mergeLoop: for (CryptoCoinPrice1minute po : poList) {
				if (po.getStartTime().equals(tmpDataTime)) {
					dataTimeMatchFlag = true;
					mergeDataPair(po, data);
					break mergeLoop;
				}
			}
			if (!dataTimeMatchFlag) {
				insertNewData(data, coinType, currencyType);
			}
			
			dataTimeMatchFlag = false;
		}

	}

	@Override
	public void mergeDuplicateData() {
		CryptoCoinPrice1minuteExample example = null;
		List<CryptoCoinPrice1minute> poList = null;
		LocalDateTime endTime = LocalDateTime.now().withSecond(0).withNano(0).plusMinutes(1);
		LocalDateTime startTime = endTime.minusMinutes(minuteStepLong);
		// 只整理最近5分钟内的重复数据
		LocalDateTime finishTime = endTime.minusMinutes(5);

		while (startTime.isAfter(finishTime)) {
			for (CryptoCoinType coinType : CryptoCoinType.values()) {
				for (CurrencyType currencyType : CurrencyType.values()) {
					example = new CryptoCoinPrice1minuteExample();
					example.createCriteria().andCoinTypeEqualTo(coinType.getCode())
							.andCurrencyTypeEqualTo(currencyType.getCode()).andStartTimeBetween(startTime, endTime);
					poList = summaryMapper.selectByExample(example);
					if (poList == null || poList.isEmpty() || poList.size() == 1) {
						continue;
					}
					log.error("crypto coin 1min duplicate data: coinType: " + coinType.getName() + ", currencyType: " + currencyType.getName() + ", size: " + poList.size());
					startTime = poList.get(0).getStartTime();
					mergeDataList(poList);
				}
			}

			startTime = startTime.minusMinutes(minuteStepLong);
			endTime = endTime.minusMinutes(minuteStepLong);
		}

	}

	@Override
	public CommonResult deleteExpiredCacheData() {
		CommonResult r = new CommonResult();

		LocalDateTime expriedTime = LocalDateTime.now()
				.minusHours(CryptoCoinDataConstant.CRYPTO_COIN_1MINUTE_DATA_LIVE_HOURS);

		CryptoCoinPrice1minuteExample example = new CryptoCoinPrice1minuteExample();
		example.createCriteria().andCreateTimeLessThan(expriedTime);
		summaryMapper.deleteByExample(example);
		r.setIsSuccess();
		return r;
	}

	@Override
	public List<CryptoCoinPrice1minute> getData(CryptoCoinType coinType, CurrencyType currencyType,
			LocalDateTime startTime) {
		CryptoCoinPrice1minuteExample example = new CryptoCoinPrice1minuteExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getCode()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeGreaterThanOrEqualTo(startTime);
		;

		return summaryMapper.selectByExample(example);
	}

	@Override
	public List<CryptoCoinPriceCommonDataBO> getCommonData(CryptoCoinType coinType, CurrencyType currencyType,
			LocalDateTime startTime) {
		List<CryptoCoinPrice1minute> poList = getData(coinType, currencyType, startTime);

		CryptoCoinPriceCommonDataBO tmpCommonData = null;
		List<CryptoCoinPriceCommonDataBO> commonDataList = new ArrayList<>();
		for (CryptoCoinPrice1minute po : poList) {
			tmpCommonData = new CryptoCoinPriceCommonDataBO();
			BeanUtils.copyProperties(po, tmpCommonData);
			commonDataList.add(tmpCommonData);
		}

		return commonDataList;
	}

	@Override
	public List<CryptoCoinPriceCommonDataBO> getCommonDataFillWithCache(CryptoCoinType coinType,
			CurrencyType currencyType, LocalDateTime startTime) {

		List<CryptoCoinPriceCommonDataBO> poDataList = getCommonData(coinType, currencyType, startTime);
//		List<CryptoCoinPriceCommonDataBO> poDataList = buildFakeData(coinType, currencyType, startTime);

		List<CryptoCoinPriceCommonDataBO> cacheDataList = cacheService.getCommonData(coinType, currencyType, startTime);
//		List<CryptoCoinPriceCommonDataBO> cacheDataList = buildFakeData(coinType, currencyType, startTime);

		if (cacheDataList.isEmpty()) {
			return poDataList;
		}

		Collections.sort(cacheDataList);

		List<CryptoCoinPriceCommonDataBO> resultDataList = mergePODataWithCache(poDataList, cacheDataList, startTime, minuteStepLong, TimeUnitType.minute);

		return resultDataList;
	}

	private CryptoCoinPrice1minute mergeDataList(List<CryptoCoinPrice1minute> poList) {
		if (poList == null || poList.isEmpty()) {
			return null;
		}

		if (poList.size() == 1) {
			return poList.get(0);
		}

		CryptoCoinPrice1minute firstPO = poList.get(0);
		for (int i = 1; i < poList.size(); i++) {
			firstPO = mergeDataPair(firstPO, poList.get(i));
		}

		summaryMapper.updateByPrimaryKeySelective(firstPO);
		return firstPO;
	}

	private CryptoCoinPrice1minute mergeDataPair(CryptoCoinPrice1minute target, CryptoCoinPrice1minute source) {
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

		summaryMapper.deleteByPrimaryKey(source.getId());
		return target;
	}

	private CryptoCoinPrice1minute mergeDataPair(CryptoCoinPrice1minute target, CryptoCoinHistoryPriceSubDTO data) {
		target.setStartPrice(new BigDecimal(data.getStart()));
		target.setEndPrice(new BigDecimal(data.getEnd()));
		target.setHighPrice(new BigDecimal(data.getHigh()));
		target.setLowPrice(new BigDecimal(data.getLow()));
		if (data.getVolume() != null && data.getVolume() > 0) {
			target.setVolume(new BigDecimal(data.getVolume()));
		}

		summaryMapper.updateByPrimaryKeySelective(target);
		return target;
	}

	private void insertNewData(CryptoCoinHistoryPriceSubDTO data, CryptoCoinType coinType, CurrencyType currencyType) {
		CryptoCoinPrice1minute po = new CryptoCoinPrice1minute();
		po.setId(snowFlake.getNextId());
		po.setStartTime(localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(data.getTime()));
		po.setEndTime(po.getStartTime().plusMinutes(minuteStepLong));
		po.setCoinType(coinType.getCode());
		po.setCurrencyType(currencyType.getCode());
		po.setStartPrice(new BigDecimal(data.getStart()));
		po.setEndPrice(new BigDecimal(data.getEnd()));
		po.setHighPrice(new BigDecimal(data.getHigh()));
		po.setLowPrice(new BigDecimal(data.getLow()));
		po.setVolume(new BigDecimal(data.getVolume()));

		summaryMapper.insertSelective(po);
	}

	@Override
	public CommonResult historyMQIsActive() {
		CommonResult r = new CommonResult();
		Boolean flag = constantService.hasKey(CryptoCoinDataConstant.LAST_HISTORY_DATA_ACTIVE_REDIS_KEY);
		r.setSuccess(flag);
		
		if(!flag) {
			TelegramMessageDTO dto = new TelegramMessageDTO();
			dto.setId(TelegramStaticChatID.MY_ID);
			dto.setMsg("crypto compare history API hit error");
			telegramCryptoCoinMessageAckProducer.send(dto);
		}
		
		return r;
	}
}
