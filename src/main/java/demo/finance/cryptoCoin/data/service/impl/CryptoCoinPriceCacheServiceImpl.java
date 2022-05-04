package demo.finance.cryptoCoin.data.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.pojo.bo.CacheMapBO;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.service.CryptoCoinCatalogService;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.constant.CryptoCoinDataConstant;
import net.sf.json.JSONObject;
import telegram.pojo.constant.TelegramBotType;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.dto.TelegramMessageDTO;

@Service
public class CryptoCoinPriceCacheServiceImpl extends CryptoCoinCommonService implements CryptoCoinPriceCacheService {

	@Autowired
	private CryptoCoinCatalogService coinCatalogService;
	
	@Override
	public void reciveData(CryptoCoinPriceCommonDataBO newBO) {
		CryptoCoinCatalog coinType = coinCatalogService.findCatalog(newBO.getCoinType());
		CacheMapBO key = buildCacheMapKey(coinType, CurrencyType.getType(newBO.getCurrencyType()), newBO.getStartTime());

		CryptoCoinPriceCommonDataBO oldBO = constantService.getCacheMap().get(key);

		if (null == oldBO) {
			newBO.setStartPrice(newBO.getEndPrice());
			newBO.setHighPrice(newBO.getEndPrice());
			newBO.setLowPrice(newBO.getEndPrice());
			constantService.getCacheMap().put(key, newBO);

		} else {
			oldBO = dataMerge(oldBO, newBO);
			constantService.getCacheMap().put(key, oldBO);
			
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
		List<CryptoCoinPriceCommonDataBO> cacheDataList = getCommonDataList(coinType, currencyType,
				LocalDateTime.now().minusMinutes(CryptoCoinDataConstant.CRYPTO_COIN_CACHE_DATA_LIVE_MINUTES));
		if (cacheDataList == null || cacheDataList.isEmpty()) {
			return null;
		}

		return cacheDataList.get(cacheDataList.size() - 1);
	}

	@Override
	public CryptoCoinPriceCommonDataBO getCommonData(CryptoCoinCatalog coinType, CurrencyType currencyType,
			LocalDateTime datetime) {
		List<CryptoCoinPriceCommonDataBO> cacheDataList = getCommonDataList(coinType, currencyType,
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
		CacheMapBO tmpKey = null;

		while (!startTime.isAfter(now)) {
			tmpKey = buildCacheMapKey(coinType, currencyType, startTime);
			tmpCommonData = constantService.getCacheMap().get(tmpKey);
			if(tmpCommonData != null) {
				commonDataList.add(tmpCommonData);
			}

			startTime = startTime.plusMinutes(1);
		}

		return commonDataList;
	}

	@Override
	public boolean isSocketAlive() {

		Set<CacheMapBO> keySet = constantService.getCacheMap().keySet();
		boolean flag = false;

		LocalDateTime now = LocalDateTime.now();
		keySetLoop: for(CacheMapBO key : keySet) {
			if(ChronoUnit.MINUTES.between(key.getStartTime(), now) <= 1) {
				flag = true;
				break keySetLoop;
			}
		}

		if (!flag) {
			TelegramMessageDTO dto = new TelegramMessageDTO();
			dto.setId(TelegramStaticChatID.MY_ID);
			dto.setMsg("crypto compare socket hit error");
			dto.setBotName(TelegramBotType.BOT_2.getName());
			telegramCryptoCoinMessageAckProducer.send(dto);
		}
		return flag;
	}

	private CacheMapBO buildCacheMapKey(CryptoCoinCatalog coinType, CurrencyType currencyType, LocalDateTime datetime) {
		CacheMapBO bo = new CacheMapBO();
		bo.setCoinTypeCode(coinType.getId().intValue());
		bo.setCurrencyCode(currencyType.getCode());
		bo.setStartTime(datetime.withSecond(0).withNano(0));
		return bo;
	}
	
	@Override
	public void cleanOldHistoryData() {
		LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
		LocalDateTime limitDateTime = now.minusMinutes(CryptoCoinDataConstant.CRYPTO_COIN_CACHE_DATA_LIVE_MINUTES);
		
		Set<CacheMapBO> keySet = constantService.getCacheMap().keySet();
		Set<CacheMapBO> targetKey = new HashSet<>();
		for(CacheMapBO key : keySet) {
			if(key.getStartTime().isBefore(limitDateTime)) {
				targetKey.add(key);
			}
		}
		
		for(CacheMapBO key : targetKey) {
			constantService.getCacheMap().remove(key);
		}

	}

}
