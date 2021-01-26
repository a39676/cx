//package demo.test.service;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import auxiliaryCommon.pojo.type.CurrencyType;
//import demo.common.service.CommonService;
//import demo.finance.cryptoCoin.data.service.CryptoCoin5MinuteDataSummaryService;
//import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;
//import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
//import finance.cryptoCoin.pojo.type.CryptoCoinType;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//@Service
//public class TestService extends CommonService {
//	
//	@Autowired
//	private CryptoCoin5MinuteDataSummaryService _5minDataService;
//	
//	public String get5MinData() {
//		LocalDateTime startTime = LocalDateTime.now().withMinute(0).withHour(20).withSecond(0).withNano(0);
//		List<CryptoCoinPriceCommonDataBO> dataList = _5minDataService.getCommonDataFillWithCache(CryptoCoinType.BTC, CurrencyType.USD, startTime);
//		
//		JSONObject j = null;
//		JSONArray ja = new JSONArray();
//		for(CryptoCoinPriceCommonDataBO data : dataList) {
//			j = JSONObject.fromObject(data);
//			ja.add(j);
//		}
//		
//		j.put("data", ja);
//		return j.toString();
//	}
//
//	@Autowired
//	private CryptoCoinPriceCacheService cacheService;
//	
//	public void createCacheData() {
//		CryptoCoinPriceCommonDataBO newBO = null;
//		for(int i = 0; i < 50; i++) {
//			newBO = new CryptoCoinPriceCommonDataBO();
//			newBO.setCoinType(0);
//			newBO.setCurrencyType(1);
//			newBO.setStartPrice(new BigDecimal(20));
//			newBO.setEndPrice(new BigDecimal(i));
//			newBO.setHighPrice(new BigDecimal(90));
//			newBO.setLowPrice(new BigDecimal(10));
//			newBO.setStartTime(LocalDateTime.now().minusMinutes(i).minusSeconds(i));
//			newBO.setEndTime(newBO.getStartTime());
//			cacheService.reciveData(newBO);
//		}
//	}
//}
