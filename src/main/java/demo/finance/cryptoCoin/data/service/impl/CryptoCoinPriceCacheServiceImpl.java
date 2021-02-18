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
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;
import demo.tool.telegram.pojo.constant.TelegramStaticChatID;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.constant.CryptoCoinDataConstant;
import finance.cryptoCoin.pojo.type.CryptoCoinType;
import net.sf.json.JSONObject;
import telegram.pojo.dto.TelegramMessageDTO;

@Service
public class CryptoCoinPriceCacheServiceImpl extends CryptoCoinCommonService implements CryptoCoinPriceCacheService {

	@Override
	public void reciveData(CryptoCoinPriceCommonDataBO newBO) {
		String key = String.format(CryptoCoinDataConstant.CRYPTO_COIN_CACHE_REDIS_KEY_FORMAT,
				CryptoCoinType.getType(newBO.getCoinType()).getName(),
				CurrencyType.getType(newBO.getCurrencyType()).getName(),
				localDateTimeHandler.dateToStr(newBO.getStartTime(), (CryptoCoinDataConstant.CRYPTO_COIN_CACHE_REDIS_KEY_DATETIME_FORMAT))
				);
		
		String oldCacheDataStr = constantService.getValByName(key);
		
		if(StringUtils.isBlank(oldCacheDataStr)) {
			newBO.setStartPrice(newBO.getEndPrice());
			newBO.setHighPrice(newBO.getEndPrice());
			newBO.setLowPrice(newBO.getEndPrice());
			
		} else {
			CryptoCoinPriceCommonDataBO oldBO = dataStrToBO(oldCacheDataStr);
			oldBO = dataMerge(oldBO, newBO);
		}

		constantService.setValByName(key, boToDataStr(newBO), CryptoCoinDataConstant.CRYPTO_COIN_CACHE_DATA_LIVE_MINUTES, TimeUnit.MINUTES);
	}
	
	@Override
	public CryptoCoinPriceCommonDataBO dataStrToBO(String str) {
		CryptoCoinPriceCommonDataBO bo = null;
		try {
			JSONObject j = JSONObject.fromObject(str);
			bo = new CryptoCoinPriceCommonDataBO();
			bo.setCoinType(j.getInt("coinType"));
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
	
	private CryptoCoinPriceCommonDataBO dataMerge(CryptoCoinPriceCommonDataBO oldBO, CryptoCoinPriceCommonDataBO newBO) {
		oldBO.setEndPrice(newBO.getEndPrice());
		if(oldBO.getHighPrice().doubleValue() < newBO.getEndPrice().doubleValue()) {
			oldBO.setHighPrice(newBO.getHighPrice());
		}
		
		if(oldBO.getLowPrice().doubleValue() > newBO.getEndPrice().doubleValue()) {
			oldBO.setLowPrice(newBO.getEndPrice());
		}
		
		return oldBO;
	}
	
	@Override
	public List<CryptoCoinPriceCommonDataBO> getCommonData(CryptoCoinType coinType, CurrencyType currencyType, LocalDateTime startTime) {
		List<CryptoCoinPriceCommonDataBO> commonDataList = new ArrayList<>();
		CryptoCoinPriceCommonDataBO tmpCommonData = null;
		
		LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
		if(startTime == null) {
			startTime = now.minusMinutes(CryptoCoinDataConstant.CRYPTO_COIN_CACHE_DATA_LIVE_MINUTES);
		}
		String tmpDataKey = null;
		String tmpDataStr = null;
		

		while(!startTime.isAfter(now)) {
			tmpDataKey = String.format(CryptoCoinDataConstant.CRYPTO_COIN_CACHE_REDIS_KEY_FORMAT,
					coinType.getName(),
					currencyType.getName(),
					localDateTimeHandler.dateToStr(startTime, CryptoCoinDataConstant.CRYPTO_COIN_CACHE_REDIS_KEY_DATETIME_FORMAT)
					);
			
			tmpDataStr = constantService.getValByName(tmpDataKey);
			
			try {
				tmpCommonData = dataStrToBO(tmpDataStr);
				
				if(tmpCommonData != null) {
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
		String key = String.format(CryptoCoinDataConstant.CRYPTO_COIN_CACHE_REDIS_KEY_FORMAT,
				CryptoCoinType.BTC.getName(),
				CurrencyType.USD.getName(),
				localDateTimeHandler.dateToStr(LocalDateTime.now(), (CryptoCoinDataConstant.CRYPTO_COIN_CACHE_REDIS_KEY_DATETIME_FORMAT))
				);
		
		Boolean flag = constantService.hasKey(key);
		if(flag) {
			return flag;
		}
		
		key = String.format(CryptoCoinDataConstant.CRYPTO_COIN_CACHE_REDIS_KEY_FORMAT,
				CryptoCoinType.BTC.getName(),
				CurrencyType.USD.getName(),
				localDateTimeHandler.dateToStr(LocalDateTime.now().minusMinutes(1), (CryptoCoinDataConstant.CRYPTO_COIN_CACHE_REDIS_KEY_DATETIME_FORMAT))
				);
		
		if(!flag) {
			TelegramMessageDTO dto = new TelegramMessageDTO();
			dto.setId(TelegramStaticChatID.MY_ID);
			dto.setMsg("crypto compare socket hit error");
			telegramCryptoCoinMessageAckProducer.send(dto);
		}
		return flag;
	}
}
