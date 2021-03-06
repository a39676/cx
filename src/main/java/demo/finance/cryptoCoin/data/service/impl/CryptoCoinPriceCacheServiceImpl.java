package demo.finance.cryptoCoin.data.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.pojo.constant.CryptoCoinConstant;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;
import demo.tool.telegram.pojo.constant.TelegramStaticChatID;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.constant.CryptoCoinDataConstant;
import net.sf.json.JSONObject;
import telegram.pojo.constant.TelegramBotType;
import telegram.pojo.dto.TelegramMessageDTO;

@Service
public class CryptoCoinPriceCacheServiceImpl extends CryptoCoinCommonService implements CryptoCoinPriceCacheService {

	@Override
	public void reciveData(CryptoCoinPriceCommonDataBO newBO) {
		CryptoCoinCatalog coinType = coinCatalogService.findCatalog(newBO.getCoinType());
		String key = buildCoinRedisKey(coinType, CurrencyType.getType(newBO.getCurrencyType()), newBO.getStartTime());

		String oldCacheDataStr = redisConnectService.getValByName(key);

		if (StringUtils.isBlank(oldCacheDataStr)) {
			newBO.setStartPrice(newBO.getEndPrice());
			newBO.setHighPrice(newBO.getEndPrice());
			newBO.setLowPrice(newBO.getEndPrice());
			redisConnectService.setValByName(key, boToDataStr(newBO),
					CryptoCoinDataConstant.CRYPTO_COIN_CACHE_DATA_LIVE_MINUTES, TimeUnit.MINUTES);

		} else {
			CryptoCoinPriceCommonDataBO oldBO = dataStrToBO(oldCacheDataStr);
			oldBO = dataMerge(oldBO, newBO);
			redisConnectService.setValByName(key, boToDataStr(oldBO),
					CryptoCoinDataConstant.CRYPTO_COIN_CACHE_DATA_LIVE_MINUTES, TimeUnit.MINUTES);

		}

	}

	@Override
	public CryptoCoinPriceCommonDataBO dataStrToBO(String str) {
		CryptoCoinPriceCommonDataBO bo = null;
		try {
			JSONObject j = JSONObject.fromObject(str);
			bo = new CryptoCoinPriceCommonDataBO();
			bo.setCoinType(j.getString("coinType"));
			bo.setCurrencyType(j.getInt("currencyType"));
			bo.setStartPrice(new BigDecimal(j.getDouble("startPrice")));
			bo.setEndPrice(new BigDecimal(j.getDouble("endPrice")));
			bo.setHighPrice(new BigDecimal(j.getDouble("highPrice")));
			bo.setLowPrice(new BigDecimal(j.getDouble("lowPrice")));
			bo.setStartTime(localDateTimeHandler.jsonStrToLocalDateTime(String.valueOf(j.getJSONObject("startTime"))));
			bo.setEndTime(localDateTimeHandler.jsonStrToLocalDateTime(String.valueOf(j.getJSONObject("endTime"))));
		} catch (Exception e) {
		}
		return bo;
	}

	private String boToDataStr(CryptoCoinPriceCommonDataBO bo) {
		JSONObject j = JSONObject.fromObject(bo);
		return j.toString();
	}

	private CryptoCoinPriceCommonDataBO dataMerge(CryptoCoinPriceCommonDataBO oldBO,
			CryptoCoinPriceCommonDataBO newBO) {

		if (oldBO.getHighPrice().doubleValue() < newBO.getEndPrice().doubleValue()) {
			oldBO.setHighPrice(newBO.getHighPrice());
		}

		if (oldBO.getLowPrice().doubleValue() > newBO.getEndPrice().doubleValue()) {
			oldBO.setLowPrice(newBO.getEndPrice());
		}

		if (oldBO.getEndTime() == null) {
			oldBO.setEndTime(newBO.getEndTime());
			oldBO.setEndPrice(newBO.getEndPrice());
		} else {
			if (oldBO.getEndTime().isBefore(newBO.getEndTime())) {
				oldBO.setEndTime(newBO.getEndTime());
				oldBO.setEndPrice(newBO.getEndPrice());
			}
		}

		return oldBO;
	}

	@Override
	public CryptoCoinPriceCommonDataBO getNewPrice(CryptoCoinCatalog coinType, CurrencyType currencyType) {
		List<CryptoCoinPriceCommonDataBO> cacheDataList = cacheService.getCommonDataList(coinType, currencyType,
				LocalDateTime.now().minusMinutes(CryptoCoinDataConstant.CRYPTO_COIN_CACHE_DATA_LIVE_MINUTES));
		if (cacheDataList == null || cacheDataList.isEmpty()) {
			return null;
		}

		return cacheDataList.get(cacheDataList.size() - 1);
	}

	@Override
	public CryptoCoinPriceCommonDataBO getCommonData(CryptoCoinCatalog coinType, CurrencyType currencyType,
			LocalDateTime datetime) {
		List<CryptoCoinPriceCommonDataBO> cacheDataList = cacheService.getCommonDataList(coinType, currencyType,
				datetime);
		for (CryptoCoinPriceCommonDataBO bo : cacheDataList) {
			if (!datetime.isBefore(bo.getStartTime()) && !datetime.isAfter(bo.getEndTime())) {
				return bo;
			}
		}

		return null;
	}

	@Override
	public List<CryptoCoinPriceCommonDataBO> getCommonDataList(CryptoCoinCatalog coinType, CurrencyType currencyType,
			LocalDateTime startTime) {
		List<CryptoCoinPriceCommonDataBO> commonDataList = new ArrayList<>();
		CryptoCoinPriceCommonDataBO tmpCommonData = null;

		LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
		if (startTime == null) {
			startTime = now.minusMinutes(CryptoCoinDataConstant.CRYPTO_COIN_CACHE_DATA_LIVE_MINUTES);
		}
		String tmpDataKey = null;
		String tmpDataStr = null;

		while (!startTime.isAfter(now)) {
			tmpDataKey = buildCoinRedisKey(coinType, currencyType, startTime);

			tmpDataStr = redisConnectService.getValByName(tmpDataKey);

			try {
				tmpCommonData = dataStrToBO(tmpDataStr);

				if (tmpCommonData != null) {
					commonDataList.add(tmpCommonData);
				}
			} catch (Exception e) {
			}
			startTime = startTime.plusMinutes(1);
		}

		return commonDataList;
	}

	@Override
	public boolean isSocketAlive() {
		String key = buildCoinRedisKey(CryptoCoinConstant.DEFAULT_COIN_CATALOG, CurrencyType.USD, LocalDateTime.now());

		Boolean flag = redisConnectService.hasKey(key);

		if (!flag) {
			TelegramMessageDTO dto = new TelegramMessageDTO();
			dto.setId(TelegramStaticChatID.MY_ID);
			dto.setMsg("crypto compare socket hit error");
			dto.setBotName(TelegramBotType.BOT_2.getName());
			telegramCryptoCoinMessageAckProducer.send(dto);
		}
		return flag;
	}

	private String buildCoinRedisKey(CryptoCoinCatalog coinType, CurrencyType currencyType, LocalDateTime datetime) {
		return String.format(CryptoCoinDataConstant.CRYPTO_COIN_CACHE_REDIS_KEY_FORMAT, coinType.getCoinNameEnShort(),
				currencyType.getName(), localDateTimeHandler.dateToStr(datetime,
						CryptoCoinDataConstant.CRYPTO_COIN_CACHE_REDIS_KEY_DATETIME_FORMAT));
	}

	private String buildCoinRedisKey(String coinTypeEnShort, CurrencyType currencyType, LocalDateTime datetime) {
		return String.format(CryptoCoinDataConstant.CRYPTO_COIN_CACHE_REDIS_KEY_FORMAT, coinTypeEnShort,
				currencyType.getName(), localDateTimeHandler.dateToStr(datetime,
						CryptoCoinDataConstant.CRYPTO_COIN_CACHE_REDIS_KEY_DATETIME_FORMAT));
	}
}
