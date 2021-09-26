package demo.test.service;

import org.springframework.stereotype.Service;

import demo.common.service.CommonService;

@Service
public class TestService extends CommonService {
	
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
	
}
