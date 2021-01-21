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
import demo.finance.cryptoCoin.data.pojo.constant.CryptoCoinDataConstant;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.type.CryptoCoinType;
import net.sf.json.JSONObject;

@Service
public class CryptoCoinPriceCacheServiceImpl extends CryptoCoinCommonService implements CryptoCoinPriceCacheService {

	@Override
	public void reciveData(CryptoCoinPriceCommonDataBO newBO) {
//		TODO
		/*
		 * 需要统计最近10分钟
		 * 提醒服务器中. 统计最近10分钟的逻辑需要更改
		 */
		String key = String.format(CryptoCoinDataConstant.CRYPTO_COIN_CACHE_REDIS_KEY_FORMAT,
				CryptoCoinType.getType(newBO.getCoinType()).getName(),
				CurrencyType.getType(newBO.getCurrencyType()).getName(),
				localDateTimeHandler.dateToStr(newBO.getStartTime(), (CryptoCoinDataConstant.CRYPTO_COIN_CACHE_REDIS_KEY_DATETIME_FORMAT))
				);
		
		String dataStr = constantService.getValByName(key);
		
		if(StringUtils.isBlank(dataStr)) {
			newBO.setStartPrice(newBO.getEndPrice());
			newBO.setHighPrice(newBO.getEndPrice());
			newBO.setLowPrice(newBO.getEndPrice());
			constantService.setValByName(key, boToDataStr(newBO), CryptoCoinDataConstant.CRYPTO_COIN_CACHE_DATA_LIVE_MINUTES, TimeUnit.MINUTES);
			
		} else {
			CryptoCoinPriceCommonDataBO oldBO = dataStrToBO(dataStr);
			oldBO = dataMerge(oldBO, newBO);
			constantService.setValByName(key, boToDataStr(newBO), CryptoCoinDataConstant.CRYPTO_COIN_CACHE_DATA_LIVE_MINUTES, TimeUnit.MINUTES);
		}
	}
	
	private CryptoCoinPriceCommonDataBO dataStrToBO(String str) {
		CryptoCoinPriceCommonDataBO bo = null;
		/* 
		 * TODO
		 * 尽量填充所有字段
		 */
		try {
			JSONObject j = JSONObject.fromObject(str);
			bo = new CryptoCoinPriceCommonDataBO();
			bo.setStartPrice(new BigDecimal(j.getDouble("startPrice")));
			bo.setEndPrice(new BigDecimal(j.getDouble("endPrice")));
			bo.setHighPrice(new BigDecimal(j.getDouble("highPrice")));
			bo.setLowPrice(new BigDecimal(j.getDouble("lowPrice")));
			bo.setStartTime(localDateTimeHandler.jsonStrToLocalDateTime(j.getString("startTime")));
			bo.setCoinType(j.getInt("coinType"));
			bo.setCurrencyType(j.getInt("currencyType"));
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
	public List<CryptoCoinPriceCommonDataBO> getCommonData(CryptoCoinType coinType, CurrencyType currencyType) {
		List<CryptoCoinPriceCommonDataBO> commonDataList = new ArrayList<>();
		CryptoCoinPriceCommonDataBO tmpCommonData = null;
		
		LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
		LocalDateTime startTime = now.minusMinutes(10);
		String tmpDataKey = null;
		String tmpDataStr = null;
		

		while(!startTime.isAfter(now)) {
			tmpDataKey = String.format(CryptoCoinDataConstant.CRYPTO_COIN_CACHE_REDIS_KEY_FORMAT,
					coinType.getName(),
					currencyType.getName(),
					localDateTimeHandler.dateToStr(startTime, CryptoCoinDataConstant.CRYPTO_COIN_CACHE_REDIS_KEY_DATETIME_FORMAT)
					);
			
			tmpDataStr = constantService.getValByName(tmpDataKey);
			
			tmpCommonData = dataStrToBO(tmpDataStr);
			
			if(tmpCommonData != null) {
				commonDataList.add(tmpCommonData);
			}
			
			startTime = startTime.plusMinutes(1);
		}

		return commonDataList;
	}

}
