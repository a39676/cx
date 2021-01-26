package demo.test.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.type.CurrencyType;
import demo.common.service.CommonService;
import demo.finance.cryptoCoin.data.service.CryptoCoin1MinuteDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin5MinuteDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.type.CryptoCoinType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class TestService extends CommonService {
	
	@Autowired
	private CryptoCoinPriceCacheService cacheService;
	
	public void addCache() {
		CryptoCoinPriceCommonDataBO newBO = null;
		for(CryptoCoinType coinType : CryptoCoinType.values()) {
			for(int minute = 0; minute < 10; minute++) {
				for(int second = 0; second < 50; second++) {
					newBO = new CryptoCoinPriceCommonDataBO();
					newBO.setCoinType(coinType.getCode());
					newBO.setCurrencyType(CurrencyType.USD.getCode());
					newBO.setStartPrice(new BigDecimal(10));
					newBO.setEndPrice(new BigDecimal(20));
					newBO.setHighPrice(new BigDecimal(90));
					newBO.setLowPrice(new BigDecimal(5));
					newBO.setStartTime(LocalDateTime.now().minusMinutes(minute).minusSeconds(second));
					newBO.setEndTime(newBO.getStartTime());
					cacheService.reciveData(newBO);
				}
			}
		}
	}
	
	public String getCache(Integer coinType) {
		CryptoCoinType ct = CryptoCoinType.getType(coinType);
		List<CryptoCoinPriceCommonDataBO> dataList = cacheService.getCommonData(ct, CurrencyType.USD);
		
		JSONObject j = null;
		JSONArray ja = new JSONArray();
		for(CryptoCoinPriceCommonDataBO data : dataList) {
			j = JSONObject.fromObject(data);
			ja.add(j);
		}
		
		j.put("data", ja);
		return j.toString();
	}

	@Autowired
	private CryptoCoin1MinuteDataSummaryService _1minDataService;
	
	public String getFake1MinData() {
		List<CryptoCoinPriceCommonDataBO> dataList = _1minDataService.getCommonDataFillWithCache(CryptoCoinType.BTC, CurrencyType.USD, LocalDateTime.now().minusMinutes(50));
		
		JSONObject j = null;
		JSONArray ja = new JSONArray();
		for(CryptoCoinPriceCommonDataBO data : dataList) {
			j = JSONObject.fromObject(data);
			ja.add(j);
		}
		
		j.put("data", ja);
		return j.toString();
	}
	
	@Autowired
	private CryptoCoin5MinuteDataSummaryService _5minDataService;
	
	public String getFake5MinData() {
		List<CryptoCoinPriceCommonDataBO> dataList = _5minDataService.getCommonDataFillWithCache(CryptoCoinType.BTC, CurrencyType.USD, LocalDateTime.now().minusMinutes(400));
		
		JSONObject j = null;
		JSONArray ja = new JSONArray();
		for(CryptoCoinPriceCommonDataBO data : dataList) {
			j = JSONObject.fromObject(data);
			ja.add(j);
		}
		
		j.put("data", ja);
		return j.toString();
	}
}
