package demo.finance.cryptoCoin.data.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice1minuteMapper;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1minute;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1minuteExample;
import demo.finance.cryptoCoin.data.service.CryptoCoin1MinuteDataSummaryService;
import demo.finance.cryptoCoin.tool.service.CryptoCoinLowPriceNoticeService;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.constant.CryptoCoinDataConstant;
import finance.cryptoCoin.pojo.dto.CryptoCoinDataDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinDataSubDTO;

@Service
public class CryptoCoin1MinuteDataSummaryServiceImpl extends CryptoCoinCommonService
		implements CryptoCoin1MinuteDataSummaryService {

	private final int minuteStepLong = 1;

	@Autowired
	private CryptoCoinPrice1minuteMapper summaryMapper;
	@Autowired
	private CryptoCoinLowPriceNoticeService lowPriceNoticeService;

	@Override
	public CommonResult reciveMinuteData(CryptoCoinDataDTO dto) {
		CommonResult r = new CommonResult();

		List<CryptoCoinDataSubDTO> dataList = dto.getPriceHistoryData();
		if (dataList == null || dataList.isEmpty()) {
			return r;
		}

		CryptoCoinCatalog coinType = coinCatalogService.findCatalog(dto.getCryptoCoinTypeName());
		CurrencyType currencyType = CurrencyType.getType(dto.getCurrencyName());
		if (coinType == null || currencyType == null) {
			return r;
		}

		updateSummaryData(dataList, coinType, currencyType);

		return r;
	}

	@Override
	public void summaryLowPriceRedisData() {
		List<CryptoCoinCatalog> catalogPOList = lowPriceNoticeService.getLowPriceSubscriptionCatalogList();
		if (catalogPOList.isEmpty()) {
			return;
		}

		List<CryptoCoinPriceCommonDataBO> cacheDataList = null;
		for (CryptoCoinCatalog catalog : catalogPOList) {
			cacheDataList = cacheService.getCommonDataList(catalog, CurrencyType.USD,
					LocalDateTime.now().minusMinutes(2));
			for(CryptoCoinPriceCommonDataBO cacheData : cacheDataList) {
				updateSummaryDataWithCache(cacheData, catalog, CurrencyType.USD);
			}
		}
	}

	private void updateSummaryData(List<CryptoCoinDataSubDTO> dataList, CryptoCoinCatalog coinType,
			CurrencyType currencyType) {

		LocalDateTime dataStartTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dataList.get(0).getTime());
		LocalDateTime dataEndime = localDateTimeHandler
				.stringToLocalDateTimeUnkonwFormat(dataList.get(dataList.size() - 1).getTime());

		CryptoCoinPrice1minuteExample example = new CryptoCoinPrice1minuteExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getId()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeBetween(dataStartTime, dataEndime);

		List<CryptoCoinPrice1minute> poList = summaryMapper.selectByExample(example);

		LocalDateTime tmpDataTime = null;
		boolean dataTimeMatchFlag = false;
		CryptoCoinDataSubDTO tmpNewData = null;
		for (int dataIndex = 0; dataIndex < dataList.size(); dataIndex++) {
			tmpNewData = dataList.get(dataIndex);
			tmpDataTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(tmpNewData.getTime());
			mergeLoop: for (CryptoCoinPrice1minute po : poList) {
				if (po.getStartTime().isEqual(tmpDataTime)) {
					dataTimeMatchFlag = true;
					mergeDataPair(po, tmpNewData);
					break mergeLoop;
				}
			}
			if (!dataTimeMatchFlag) {
				insertNewData(tmpNewData, coinType, currencyType);
			}

			dataTimeMatchFlag = false;
		}

	}

	private void updateSummaryDataWithCache(CryptoCoinPriceCommonDataBO cacheData, CryptoCoinCatalog coinType,
			CurrencyType currencyType) {

		LocalDateTime dataStartTime = cacheData.getStartTime();

		CryptoCoinPrice1minuteExample example = new CryptoCoinPrice1minuteExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getId()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeEqualTo(dataStartTime);

		List<CryptoCoinPrice1minute> poList = summaryMapper.selectByExample(example);

		if (poList.isEmpty()) {
			insertNewData(cacheData, coinType, currencyType);
		} else if (poList.size() == 1) {
			CryptoCoinPrice1minute po = mergeDataPair(poList.get(0), cacheData);
			summaryMapper.updateByPrimaryKeySelective(po);
		} else if (poList.size() > 1) {
			CryptoCoinPrice1minute po = mergeDataList(poList);
			po = mergeDataPair(poList.get(0), cacheData);
			summaryMapper.updateByPrimaryKeySelective(po);
		}

	}

	@Override
	public void mergeDuplicateData() {
		CryptoCoinPrice1minuteExample example = null;
		List<CryptoCoinPrice1minute> poList = null;
		LocalDateTime endTime = LocalDateTime.now().withSecond(0).withNano(0).plusMinutes(1);
		LocalDateTime startTime = endTime.minusMinutes(minuteStepLong);
		// 只整理最近5分钟内的重复数据, 采用时间倒序
		LocalDateTime finishTime = endTime.minusMinutes(5);

		List<CryptoCoinCatalog> allCoinCatalogList = coinCatalogService.getAllCatalog();
		while (startTime.isAfter(finishTime)) {
			for (CryptoCoinCatalog coinType : allCoinCatalogList) {
				for (CurrencyType currencyType : CurrencyType.values()) {
					example = new CryptoCoinPrice1minuteExample();
					example.createCriteria().andCoinTypeEqualTo(coinType.getId())
							.andCurrencyTypeEqualTo(currencyType.getCode()).andStartTimeBetween(startTime, endTime);
					poList = summaryMapper.selectByExample(example);
					if (poList == null || poList.isEmpty() || poList.size() == 1) {
						continue;
					}
					log.error("crypto coin 1min duplicate data: coinType: " + coinType.getCoinNameEnShort()
							+ ", currencyType: " + currencyType.getName() + ", size: " + poList.size());
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
	public CryptoCoinPriceCommonDataBO getCommonData(CryptoCoinCatalog coinType, CurrencyType currencyType,
			LocalDateTime datetime) {
		CryptoCoinPriceCommonDataBO tmpCommonData = null;
		if (datetime == null || !LocalDateTime.now().isAfter(datetime)) {
			return tmpCommonData;
		}

		CryptoCoinPrice1minuteExample example = new CryptoCoinPrice1minuteExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getId()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeLessThanOrEqualTo(datetime).andEndTimeGreaterThanOrEqualTo(datetime);
		List<CryptoCoinPrice1minute> poList = summaryMapper.selectByExample(example);
		if (!poList.isEmpty()) {
			tmpCommonData = new CryptoCoinPriceCommonDataBO();
			BeanUtils.copyProperties(poList.get(0), tmpCommonData);
			return tmpCommonData;
		}

		tmpCommonData = cacheService.getCommonData(coinType, currencyType, datetime);

		return tmpCommonData;

	}

	@Override
	public List<CryptoCoinPrice1minute> getDataList(CryptoCoinCatalog coinType, CurrencyType currencyType,
			LocalDateTime startTime) {
		CryptoCoinPrice1minuteExample example = new CryptoCoinPrice1minuteExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getId()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeGreaterThanOrEqualTo(startTime);
		;

		return summaryMapper.selectByExample(example);
	}

	@Override
	public List<CryptoCoinPriceCommonDataBO> getCommonDataList(CryptoCoinCatalog coinType, CurrencyType currencyType,
			LocalDateTime startTime) {
		List<CryptoCoinPrice1minute> poList = getDataList(coinType, currencyType, startTime);

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
	public List<CryptoCoinPriceCommonDataBO> getCommonDataListFillWithCache(CryptoCoinCatalog coinType,
			CurrencyType currencyType, LocalDateTime startTime) {

		List<CryptoCoinPriceCommonDataBO> poDataList = getCommonDataList(coinType, currencyType, startTime);
//		List<CryptoCoinPriceCommonDataBO> poDataList = buildFakeData(coinType, currencyType, startTime);

		List<CryptoCoinPriceCommonDataBO> cacheDataList = cacheService.getCommonDataList(coinType, currencyType,
				startTime);
//		List<CryptoCoinPriceCommonDataBO> cacheDataList = buildFakeData(coinType, currencyType, startTime);

		if (cacheDataList.isEmpty()) {
			return poDataList;
		}

		Collections.sort(cacheDataList);

		List<CryptoCoinPriceCommonDataBO> resultDataList = mergePODataWithCache(poDataList, cacheDataList, startTime,
				minuteStepLong, TimeUnitType.minute);

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
		target.setVolume(source.getVolume());
		if (target.getHighPrice() == null || target.getHighPrice().doubleValue() < source.getHighPrice().byteValue()) {
			target.setHighPrice(source.getHighPrice());
		}
		if (target.getLowPrice() == null || target.getLowPrice().doubleValue() > source.getLowPrice().byteValue()) {
			target.setLowPrice(source.getLowPrice());
		}

		summaryMapper.deleteByPrimaryKey(source.getId());
		return target;
	}

	private CryptoCoinPrice1minute mergeDataPair(CryptoCoinPrice1minute target, CryptoCoinDataSubDTO data) {
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

	private CryptoCoinPrice1minute mergeDataPair(CryptoCoinPrice1minute target, CryptoCoinPriceCommonDataBO data) {
		if (target.getStartTime() == null || target.getStartTime().isAfter(data.getStartTime())) {
			target.setStartTime(data.getStartTime());
			target.setStartPrice(data.getStartPrice());
		}
		if (target.getEndTime() == null || target.getEndTime().isBefore(data.getEndTime())) {
			target.setEndTime(data.getEndTime());
			target.setEndPrice(data.getEndPrice());
		}
		target.setVolume(data.getVolume());
		if (target.getHighPrice() == null || target.getHighPrice().doubleValue() < data.getHighPrice().byteValue()) {
			target.setHighPrice(data.getHighPrice());
		}
		if (target.getLowPrice() == null || target.getLowPrice().doubleValue() > data.getLowPrice().byteValue()) {
			target.setLowPrice(data.getLowPrice());
		}

		summaryMapper.updateByPrimaryKeySelective(target);
		return target;
	}

	private void insertNewData(CryptoCoinDataSubDTO data, CryptoCoinCatalog coinType, CurrencyType currencyType) {
		CryptoCoinPrice1minute po = new CryptoCoinPrice1minute();
		po.setId(snowFlake.getNextId());
		po.setStartTime(localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(data.getTime()));
		po.setEndTime(po.getStartTime().plusMinutes(minuteStepLong));
		po.setCoinType(coinType.getId());
		po.setCurrencyType(currencyType.getCode());
		po.setStartPrice(new BigDecimal(data.getStart()));
		po.setEndPrice(new BigDecimal(data.getEnd()));
		po.setHighPrice(new BigDecimal(data.getHigh()));
		po.setLowPrice(new BigDecimal(data.getLow()));
		po.setVolume(new BigDecimal(data.getVolume()));

		summaryMapper.insertSelective(po);
	}

	private void insertNewData(CryptoCoinPriceCommonDataBO data, CryptoCoinCatalog coinType,
			CurrencyType currencyType) {
		CryptoCoinPrice1minute po = new CryptoCoinPrice1minute();
		po.setId(snowFlake.getNextId());
		po.setStartTime(data.getStartTime().withSecond(0));
		po.setEndTime(po.getStartTime().plusMinutes(minuteStepLong));
		po.setCoinType(coinType.getId());
		po.setCurrencyType(currencyType.getCode());
		po.setStartPrice(data.getStartPrice());
		po.setEndPrice(data.getEndPrice());
		po.setHighPrice(data.getHighPrice());
		po.setLowPrice(data.getLowPrice());
		po.setVolume(data.getVolume());

		summaryMapper.insertSelective(po);
	}

}
