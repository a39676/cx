package demo.finance.cryptoCoin.common.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.finance.common.service.impl.FinanceCommonService;
import demo.finance.cryptoCoin.common.pojo.dto.CryptoCoinUserKeysCxDTO;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionMultipleUserCommonDTO;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionOrderCommonDTO;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinUserKeysDTO;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinUserSymbolRateDTO;
import finance.cryptoCoin.common.pojo.type.CryptoExchangeType;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.constant.CryptoCoinDataConstant;
import finance.cryptoCoin.pojo.type.CurrencyTypeForCryptoCoin;
import finance.cryptoCoin.pojo.vo.CryptoCoinCatalogVO;
import tool.service.TimeBasedOneTimePassword;

public abstract class CryptoCoinCommonService extends FinanceCommonService {

	@Autowired
	protected RedisTemplate<String, Object> redisTemplate;
	@Autowired
	protected CryptoCoinOptionService optionService;
	@Autowired
	protected TimeBasedOneTimePassword timeBasedOneTimePassword;

	protected static final CurrencyTypeForCryptoCoin defaultCyrrencyTypeForCryptoCoin = CurrencyTypeForCryptoCoin.USDT;
	protected static final int SCALE_FOR_PRICE_DISPLAY = 8;
	protected static final int SCALE_FOR_PRICE_CALCULATE = 12;
	protected static final int SCALE_FOR_RATE_DISPLAY = 2;
	protected static final int SCALE_FOR_RATE_CALCULATE = 4;
	protected static final int BINANCE_BIG_CAP_GROUP_ID = 1;
	protected static final String DATE_FORMAT_FOR_INDEX_CHART_IN_HOUR = "MM-dd HH:mm";
	protected static final String DATE_FORMAT_FOR_INDEX_CHART_IN_DAY = "MM-dd";

	protected CryptoCoinPriceCommonDataBO mergerData(CryptoCoinPriceCommonDataBO resultTarget,
			CryptoCoinPriceCommonDataBO otherData) {
		if (resultTarget == null || otherData == null) {
			return resultTarget;
		}

		try {
			if (resultTarget.getStartTime().isAfter(otherData.getStartTime())) {
				resultTarget.setStartTime(otherData.getStartTime());
				if (otherData.getStartPrice() != null) {
					resultTarget.setStartPrice(otherData.getStartPrice());
				}
			}
		} catch (Exception e) {
		}

		/*
		 * resultTarget could be a new object means start time & end time could be null
		 */
		if (resultTarget.getStartTime() == null && otherData.getStartTime() != null) {
			resultTarget.setStartTime(otherData.getStartTime());
			resultTarget.setStartPrice(otherData.getStartPrice());
		}

		try {
			if (resultTarget.getEndTime().isBefore(otherData.getEndTime())) {
				resultTarget.setEndTime(otherData.getEndTime());
				if (otherData.getEndPrice() != null) {
					resultTarget.setEndPrice(otherData.getEndPrice());
				}
			}
		} catch (Exception e) {
		}

		if (resultTarget.getEndTime() == null && otherData.getEndTime() != null) {
			resultTarget.setEndTime(otherData.getEndTime());
			resultTarget.setEndPrice(otherData.getEndPrice());
		}

		if (otherData.getHighPrice() != null) {
			if (resultTarget.getHighPrice() == null) {
				resultTarget.setHighPrice(otherData.getHighPrice());
			} else if (resultTarget.getHighPrice() != null
					&& resultTarget.getHighPrice().doubleValue() < otherData.getHighPrice().doubleValue()) {
				resultTarget.setHighPrice(otherData.getHighPrice());
			}
		}

		if (otherData.getLowPrice() != null) {
			if (resultTarget.getLowPrice() == null) {
				resultTarget.setLowPrice(otherData.getLowPrice());
			} else if (resultTarget.getLowPrice() != null
					&& resultTarget.getLowPrice().doubleValue() > otherData.getLowPrice().doubleValue()) {
				resultTarget.setLowPrice(otherData.getLowPrice());
			}
		}

		if (otherData.getVolume() != null) {
			if (resultTarget.getVolume() == null) {
				resultTarget.setVolume(otherData.getVolume());
			} else {
				resultTarget.setVolume(resultTarget.getVolume().add(otherData.getVolume()));
			}
		}

		return resultTarget;
	}

	/**
	 * for test use
	 */
	protected List<CryptoCoinPriceCommonDataBO> buildFakeData(String coinType, CurrencyTypeForCryptoCoin currencyType,
			LocalDateTime startTime) {
		List<CryptoCoinPriceCommonDataBO> l = new ArrayList<>();

		CryptoCoinPriceCommonDataBO bo = null;
		startTime = startTime.withSecond(0).withNano(0);
		while (!startTime.isAfter(LocalDateTime.now())) {
			bo = new CryptoCoinPriceCommonDataBO();
			bo.setId(snowFlake.getNextId());
			bo.setStartTime(startTime);
			bo.setEndTime(startTime.plusMinutes(1));
			bo.setStartPrice(new BigDecimal("-99999"));
			bo.setEndPrice(new BigDecimal("-99999"));
			bo.setHighPrice(new BigDecimal("-99999"));
			bo.setLowPrice(new BigDecimal("-99999"));
			bo.setVolume(new BigDecimal("99999"));
			bo.setCoinType(coinType);
			bo.setCurrencyType(currencyType.getCode());

			l.add(bo);
			startTime = startTime.plusMinutes(1);
		}

		return l;
	}

	/**
	 * 为各个 data summary service 提供 PO data & cache data 的通用合并逻辑
	 * 
	 * @param poDataList
	 * @param cacheDataList
	 * @param startTime
	 * @param timeRange
	 * @param timeUnitType
	 * @return
	 */
	protected List<CryptoCoinPriceCommonDataBO> mergePODataWithCache(List<CryptoCoinPriceCommonDataBO> poDataList,
			List<CryptoCoinPriceCommonDataBO> cacheDataList, LocalDateTime startTime, Integer timeRange,
			TimeUnitType timeUnitType) {
		if (cacheDataList.isEmpty()) {
			return poDataList;
		}

		Collections.sort(cacheDataList);

		LocalDateTime endTime = null;
		if (TimeUnitType.MINUTE.equals(timeUnitType)) {
			endTime = nextStepStartTimeByMinute(LocalDateTime.now(), timeRange);
			return mergeMinutePODataWithCache(poDataList, cacheDataList, startTime, endTime, timeRange);
		} else if (TimeUnitType.HOUR.equals(timeUnitType)) {
			endTime = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0).plusHours(1);
			return mergeHourPODataWithCache(poDataList, cacheDataList, startTime, endTime);
		} else if (TimeUnitType.DAY.equals(timeUnitType)) {
			endTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0).plusDays(1);
			return mergeDayPODataWithCache(poDataList, cacheDataList, startTime, endTime);
		} else if (TimeUnitType.WEEK.equals(timeUnitType)) {
			endTime = localDateTimeHandler.findNextDayOfWeek(LocalDateTime.now(), DayOfWeek.SUNDAY).withHour(0)
					.withMinute(0).withSecond(0).withNano(0);
			return mergeWeekPODataWithCache(poDataList, cacheDataList, startTime, endTime);
		} else if (TimeUnitType.MONTH.equals(timeUnitType)) {
			endTime = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0)
					.plusMonths(1);
			return mergeMonthPODataWithCache(poDataList, cacheDataList, startTime, endTime);
		}

		return poDataList;
	}

	private List<CryptoCoinPriceCommonDataBO> mergeMinutePODataWithCache(List<CryptoCoinPriceCommonDataBO> poDataList,
			List<CryptoCoinPriceCommonDataBO> cacheDataList, LocalDateTime startTime, LocalDateTime endTime,
			Integer minuteStepLong) {

		List<CryptoCoinPriceCommonDataBO> resultDataList = new ArrayList<>();
		LocalDateTime stepStart = startTime;
		LocalDateTime nextStepTime = nextStepStartTimeByMinute(startTime, minuteStepLong);
		boolean poDataExists = false;

		CryptoCoinPriceCommonDataBO tmpPOData = null;
		CryptoCoinPriceCommonDataBO tmpCacheData = null;
		while (!nextStepTime.isAfter(endTime)) {
			for (int poDataIndex = 0; poDataExists == false && poDataIndex < poDataList.size(); poDataIndex++) {
				tmpPOData = poDataList.get(poDataIndex);
				poDataExists = (!tmpPOData.getStartTime().isBefore(stepStart)
						&& !tmpPOData.getStartTime().isAfter(nextStepTime));

			}

			if (poDataExists) {
				// please sort(startTime early to later) cacheDataList before use
				for (int cacheDataIndex = 0; cacheDataIndex < cacheDataList.size(); cacheDataIndex++) {
					tmpCacheData = cacheDataList.get(cacheDataIndex);
					if (!tmpCacheData.getStartTime().isBefore(stepStart)
							&& !tmpCacheData.getStartTime().isAfter(nextStepTime)) {
						tmpPOData = mergerData(tmpPOData, tmpCacheData);
					}
				}
				resultDataList.add(tmpPOData);

			} else {
				tmpPOData = new CryptoCoinPriceCommonDataBO();
				tmpCacheData = cacheDataList.get(0);
				tmpPOData.setCoinType(tmpCacheData.getCoinType());
				tmpPOData.setCurrencyType(tmpCacheData.getCurrencyType());
				tmpPOData.setVolume(BigDecimal.ZERO);

				boolean hasMathcCache = false;
				for (int i = 0; i < cacheDataList.size(); i++) {
					tmpCacheData = cacheDataList.get(i);
					if (!tmpCacheData.getStartTime().isBefore(stepStart)
							&& !tmpCacheData.getStartTime().isAfter(nextStepTime)) {
						tmpPOData = mergerData(tmpPOData, tmpCacheData);
						hasMathcCache = true;
					}
				}

				if (hasMathcCache) {
					resultDataList.add(tmpPOData);
				}
			}

			poDataExists = false;
			stepStart = nextStepStartTimeByMinute(stepStart, minuteStepLong);
			nextStepTime = nextStepStartTimeByMinute(nextStepTime, minuteStepLong);

		}

		Collections.sort(resultDataList);
		return resultDataList;
	}

	private List<CryptoCoinPriceCommonDataBO> mergeHourPODataWithCache(List<CryptoCoinPriceCommonDataBO> poDataList,
			List<CryptoCoinPriceCommonDataBO> cacheDataList, LocalDateTime startTime, LocalDateTime endTime) {

		List<CryptoCoinPriceCommonDataBO> resultDataList = new ArrayList<>();
		LocalDateTime stepStart = startTime.withMinute(0).withSecond(0).withNano(0);
		LocalDateTime nextStepTime = stepStart.plusHours(1);
		boolean poDataExists = false;

		CryptoCoinPriceCommonDataBO tmpPOData = null;
		CryptoCoinPriceCommonDataBO tmpCacheData = null;
		while (!nextStepTime.isAfter(endTime)) {
			for (int i = 0; poDataExists == false && i < poDataList.size(); i++) {
				tmpPOData = poDataList.get(i);
				poDataExists = (!tmpPOData.getStartTime().isBefore(stepStart)
						&& !tmpPOData.getStartTime().isAfter(nextStepTime));
			}

			if (poDataExists) {
				// please sort(startTime early to later) cacheDataList before use
				for (int i = 0; i < cacheDataList.size(); i++) {
					tmpCacheData = cacheDataList.get(i);
					if (!tmpCacheData.getStartTime().isBefore(stepStart)
							&& !tmpCacheData.getStartTime().isAfter(nextStepTime)) {
						tmpPOData = mergerData(tmpPOData, tmpCacheData);
					}
				}
				resultDataList.add(tmpPOData);

			} else {
				tmpPOData = new CryptoCoinPriceCommonDataBO();
				tmpCacheData = cacheDataList.get(0);
				tmpPOData.setCoinType(tmpCacheData.getCoinType());
				tmpPOData.setCurrencyType(tmpCacheData.getCurrencyType());
				tmpPOData.setVolume(BigDecimal.ZERO);

				boolean hasMathcCache = false;
				for (int i = 0; i < cacheDataList.size(); i++) {
					tmpCacheData = cacheDataList.get(i);
					if (!tmpCacheData.getStartTime().isBefore(stepStart)
							&& !tmpCacheData.getStartTime().isAfter(nextStepTime)) {
						tmpPOData = mergerData(tmpPOData, tmpCacheData);
						hasMathcCache = true;
					}
				}

				if (hasMathcCache) {
					resultDataList.add(tmpPOData);
				}
			}

			poDataExists = false;
			stepStart = stepStart.plusHours(1);
			nextStepTime = nextStepTime.plusHours(1);

		}

		Collections.sort(resultDataList);
		return resultDataList;
	}

	private List<CryptoCoinPriceCommonDataBO> mergeDayPODataWithCache(List<CryptoCoinPriceCommonDataBO> poDataList,
			List<CryptoCoinPriceCommonDataBO> cacheDataList, LocalDateTime startTime, LocalDateTime endTime) {

		List<CryptoCoinPriceCommonDataBO> resultDataList = new ArrayList<>();
		LocalDateTime stepStart = startTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime nextStepTime = stepStart.plusDays(1);
		boolean poDataExists = false;

		CryptoCoinPriceCommonDataBO tmpPOData = null;
		CryptoCoinPriceCommonDataBO tmpCacheData = null;
		while (!nextStepTime.isAfter(endTime)) {
			for (int i = 0; poDataExists == false && i < poDataList.size(); i++) {
				tmpPOData = poDataList.get(i);
				poDataExists = (!tmpPOData.getStartTime().isBefore(stepStart)
						&& !tmpPOData.getStartTime().isAfter(nextStepTime));
			}

			if (poDataExists) {
				// please sort(startTime early to later) cacheDataList before use
				for (int i = 0; i < cacheDataList.size(); i++) {
					tmpCacheData = cacheDataList.get(i);
					if (!tmpCacheData.getStartTime().isBefore(stepStart)
							&& !tmpCacheData.getStartTime().isAfter(nextStepTime)) {
						tmpPOData = mergerData(tmpPOData, tmpCacheData);
					}
				}

				resultDataList.add(tmpPOData);

			} else {
				tmpPOData = new CryptoCoinPriceCommonDataBO();
				tmpCacheData = cacheDataList.get(0);
				tmpPOData.setCoinType(tmpCacheData.getCoinType());
				tmpPOData.setCurrencyType(tmpCacheData.getCurrencyType());
				tmpPOData.setVolume(BigDecimal.ZERO);

				boolean hasMathcCache = false;
				for (int i = 0; i < cacheDataList.size(); i++) {
					tmpCacheData = cacheDataList.get(i);
					if (!tmpCacheData.getStartTime().isBefore(stepStart)
							&& !tmpCacheData.getStartTime().isAfter(nextStepTime)) {
						tmpPOData = mergerData(tmpPOData, tmpCacheData);
						hasMathcCache = true;
					}
				}

				if (hasMathcCache) {
					resultDataList.add(tmpPOData);
				}
			}

			poDataExists = false;
			stepStart = stepStart.plusDays(1);
			nextStepTime = nextStepTime.plusDays(1);

		}

		Collections.sort(resultDataList);
		return resultDataList;
	}

	private List<CryptoCoinPriceCommonDataBO> mergeWeekPODataWithCache(List<CryptoCoinPriceCommonDataBO> poDataList,
			List<CryptoCoinPriceCommonDataBO> cacheDataList, LocalDateTime startTime, LocalDateTime endTime) {

		List<CryptoCoinPriceCommonDataBO> resultDataList = new ArrayList<>();
		LocalDateTime stepStart = localDateTimeHandler.findLastDayOfWeek(startTime,
				CryptoCoinDataConstant.START_DAY_OF_WEEK);
		LocalDateTime nextStepTime = stepStart.plusDays(7);
		boolean poDataExists = false;

		CryptoCoinPriceCommonDataBO tmpPOData = null;
		CryptoCoinPriceCommonDataBO tmpCacheData = null;
		while (!nextStepTime.isAfter(endTime)) {
			for (int i = 0; poDataExists == false && i < poDataList.size(); i++) {
				tmpPOData = poDataList.get(i);
				poDataExists = (!tmpPOData.getStartTime().isBefore(stepStart)
						&& !tmpPOData.getStartTime().isAfter(nextStepTime));
			}

			if (poDataExists) {
				// please sort(startTime early to later) cacheDataList before use
				for (int i = 0; i < cacheDataList.size(); i++) {
					tmpCacheData = cacheDataList.get(i);
					if (!tmpCacheData.getStartTime().isBefore(stepStart)
							&& !tmpCacheData.getStartTime().isAfter(nextStepTime)) {
						tmpPOData = mergerData(tmpPOData, tmpCacheData);
					}
				}

				resultDataList.add(tmpPOData);

			} else {
				tmpPOData = new CryptoCoinPriceCommonDataBO();
				tmpCacheData = cacheDataList.get(0);
				tmpPOData.setCoinType(tmpCacheData.getCoinType());
				tmpPOData.setCurrencyType(tmpCacheData.getCurrencyType());
				tmpPOData.setVolume(BigDecimal.ZERO);

				boolean hasMathcCache = false;
				for (int i = 0; i < cacheDataList.size(); i++) {
					tmpCacheData = cacheDataList.get(i);
					if (!tmpCacheData.getStartTime().isBefore(stepStart)
							&& !tmpCacheData.getStartTime().isAfter(nextStepTime)) {
						tmpPOData = mergerData(tmpPOData, tmpCacheData);
						hasMathcCache = true;
					}
				}

				if (hasMathcCache) {
					resultDataList.add(tmpPOData);
				}
			}

			poDataExists = false;
			stepStart = stepStart.plusDays(7);
			nextStepTime = nextStepTime.plusDays(7);

		}

		Collections.sort(resultDataList);
		return resultDataList;
	}

	private List<CryptoCoinPriceCommonDataBO> mergeMonthPODataWithCache(List<CryptoCoinPriceCommonDataBO> poDataList,
			List<CryptoCoinPriceCommonDataBO> cacheDataList, LocalDateTime startTime, LocalDateTime endTime) {

		List<CryptoCoinPriceCommonDataBO> resultDataList = new ArrayList<>();
		LocalDateTime stepStart = startTime.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime nextStepTime = stepStart.plusMonths(1);
		boolean poDataExists = false;

		CryptoCoinPriceCommonDataBO tmpPOData = null;
		CryptoCoinPriceCommonDataBO tmpCacheData = null;
		while (!nextStepTime.isAfter(endTime)) {
			for (int i = 0; poDataExists == false && i < poDataList.size(); i++) {
				tmpPOData = poDataList.get(i);
				poDataExists = (!tmpPOData.getStartTime().isBefore(stepStart)
						&& !tmpPOData.getStartTime().isAfter(nextStepTime));
			}

			if (poDataExists) {
				// please sort(startTime early to later) cacheDataList before use
				for (int i = 0; i < cacheDataList.size(); i++) {
					tmpCacheData = cacheDataList.get(i);
					if (!tmpCacheData.getStartTime().isBefore(stepStart)
							&& !tmpCacheData.getStartTime().isAfter(nextStepTime)) {
						tmpPOData = mergerData(tmpPOData, tmpCacheData);
					}
				}

				resultDataList.add(tmpPOData);

			} else {
				tmpPOData = new CryptoCoinPriceCommonDataBO();
				tmpCacheData = cacheDataList.get(0);
				tmpPOData.setCoinType(tmpCacheData.getCoinType());
				tmpPOData.setCurrencyType(tmpCacheData.getCurrencyType());
				tmpPOData.setVolume(BigDecimal.ZERO);

				boolean hasMathcCache = false;
				for (int i = 0; i < cacheDataList.size(); i++) {
					tmpCacheData = cacheDataList.get(i);
					if (!tmpCacheData.getStartTime().isBefore(stepStart)
							&& !tmpCacheData.getStartTime().isAfter(nextStepTime)) {
						tmpPOData = mergerData(tmpPOData, tmpCacheData);
						hasMathcCache = true;
					}
				}

				if (hasMathcCache) {
					resultDataList.add(tmpPOData);
				}
			}

			poDataExists = false;
			stepStart = stepStart.plusMonths(1);
			nextStepTime = nextStepTime.plusMonths(1);

		}

		Collections.sort(resultDataList);
		return resultDataList;
	}

	protected BigDecimal numberSetScale(BigDecimal num) {
		if (num.compareTo(new BigDecimal(100)) >= 0) {
			return num.setScale(2, RoundingMode.HALF_UP);
		} else {
			return num.setScale(6, RoundingMode.HALF_UP);
		}
	}

	protected CryptoCoinCatalogVO cryptoCoinCatalogPOToVO(CryptoCoinCatalog po) {
		CryptoCoinCatalogVO vo = new CryptoCoinCatalogVO();
		vo.setPk(systemOptionService.encryptId(po.getId()));
		vo.setEnShortname(po.getCoinNameEnShort());
		return vo;
	}

	protected boolean isValidTotpCode(String code) {
		if (StringUtils.isBlank(code)) {
			return false;
		}
		String key = systemOptionService.getTotpSecretKey();
		return timeBasedOneTimePassword.isValid(key, code);
	}

	protected String genTotpCode() {
		return timeBasedOneTimePassword.generatorCode(systemOptionService.getTotpSecretKey());
	}

	public <E extends CryptoCoinInteractionMultipleUserCommonDTO> boolean checkCryptoCoinInteractionMultipleUserCommonDTO(
			CryptoCoinInteractionMultipleUserCommonDTO dto) {
		if (dto.getUserIdList() == null || dto.getUserIdList().isEmpty() || dto.getUserNicknameList() == null
				|| dto.getUserNicknameList().isEmpty() || dto.getExchangeCode() == null) {
			return false;
		}
		if (dto.getUserIdList().size() != dto.getUserNicknameList().size()) {
			return false;
		}
		CryptoExchangeType exchangeType = CryptoExchangeType.getType(dto.getExchangeCode());
		if (exchangeType == null) {
			return false;
		}
		Map<Integer, CryptoCoinUserKeysCxDTO> userMetaDataMap = optionService.getUserMetaDataMap();
		for (int i = 0; i < dto.getUserIdList().size(); i++) {
			Integer id = dto.getUserIdList().get(i);
			CryptoCoinUserKeysDTO userMetaData = userMetaDataMap.get(id);
			if (userMetaData == null) {
				return false;
			}
			String nickname = userMetaData.getNickname();
			if (!dto.getUserNicknameList().get(i).equals(nickname)) {
				return false;
			}
		}
		return true;
	}

	protected BigDecimal cmOrderFixQuantityByUserSetting(Integer localUserId, String userNickname, String symbol,
			BigDecimal sourceQuantity) {
		CryptoCoinUserKeysCxDTO userMetaData = optionService.getUserMetaDataMap().get(localUserId);
		if (userMetaData == null) {
			return BigDecimal.ZERO;
		}

		if (userMetaData.getSymbolRateMap() == null || userMetaData.getSymbolRateMap().isEmpty()) {
			return sourceQuantity;
		} else {
			CryptoCoinUserSymbolRateDTO quantityRate = userMetaData.getSymbolRateMap().get(symbol);
			if (quantityRate == null) {
				return sourceQuantity;
			}
			BigDecimal outputQuantity = sourceQuantity.multiply(quantityRate.getRate()).setScale(0, RoundingMode.FLOOR);
			return outputQuantity;
		}
	}

	protected CryptoCoinInteractionOrderCommonDTO umOrderFixQuantityOrOrderAmountByUserSetting(Integer localUserId,
			String userNickname, CryptoCoinInteractionOrderCommonDTO dto) {
		CryptoCoinUserKeysCxDTO userMetaData = optionService.getUserMetaDataMap().get(localUserId);
		CryptoCoinInteractionOrderCommonDTO result = new CryptoCoinInteractionOrderCommonDTO();
		if (userMetaData == null) {
			return result;
		}

		BigDecimal quantityRate = userMetaData.getFutureUmRateSetting();
		if (quantityRate == null) {
			return result;
		}

		if (dto.getOrderAmount() != null) {
//			send order by amount
			BigDecimal targetAmount = dto.getOrderAmount().multiply(quantityRate);
			if (dto.getPrice() != null) {
				result.setQuantity(
						targetAmount.divide(dto.getPrice(), SCALE_FOR_PRICE_CALCULATE, RoundingMode.HALF_UP));
			} else {
				result.setOrderAmount(targetAmount);
			}
		} else {
//			send order by quantity
			/* Handle scale by receiver */
			result.setQuantity(dto.getQuantity().multiply(quantityRate));
		}

		return result;
	}
}
