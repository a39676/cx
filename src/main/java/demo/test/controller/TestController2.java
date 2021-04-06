package demo.test.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.common.controller.CommonController;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinCatalogMapper;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalogExample;
import demo.finance.cryptoCoin.mq.producer.CryptoCoinDailyDataQueryAckProducer;
import demo.test.pojo.constant.TestUrl;
import finance.cryptoCoin.pojo.dto.CryptoCoinDailyDataQueryDTO;

@Controller
@RequestMapping(value = { TestUrl.root2 })
public class TestController2 extends CommonController {

////	@SuppressWarnings("unused")
//	@Autowired
//	private TestService testService;
//	
//	@GetMapping(value = "/get5MinData")
//	@ResponseBody
//	public String get5MinData() {
//		return testService.get5MinData();
//	}
//	
//	@GetMapping(value = "/createCacheData")
//	@ResponseBody
//	public String createCacheData() {
//		testService.createCacheData();
//		return "done";
//	}
	
//	@Autowired
//	private CryptoCoin1MinuteDataSummaryService _1MinuteDataService;
//	
//	@GetMapping(value = "/getNewData")
//	@ResponseBody
//	public String getNewData() {
//		List<CryptoCoinPriceCommonDataBO> dataList = _1MinuteDataService.getCommonDataFillWithCache(CryptoCoinType.BTC, CurrencyType.USD, LocalDateTime.now().minusMinutes(2));
//		for(CryptoCoinPriceCommonDataBO bo : dataList) {
//			System.out.println(bo.toString());
//		}
//		return "done";
//	}

	@Autowired
	private CryptoCoinDailyDataQueryAckProducer producer;
	
	@GetMapping("/test")
	@ResponseBody
	public String test(@RequestParam("coin") String coinName, @RequestParam("counting") Integer counting) {
		CryptoCoinDailyDataQueryDTO dto = new CryptoCoinDailyDataQueryDTO();
		dto.setCoinName(coinName);
		dto.setCounting(counting);
		producer.send(dto);
		return "done";
	}
	
	@Autowired
	private CryptoCoinCatalogMapper catalogMapper;
	
	@GetMapping("/coin")
	@ResponseBody
	public String coin() {
		Set<String> s = new HashSet<>();
		s.addAll(Arrays.asList("ZRX","ZKS","ZIL","ZEN","ZEC","ZEC","ZEC","YFI","YFII","YEE","YAM","XTZ","XRT","XRP","XRP","XRP","XMX","XMR","XLM","XEM","WXT","WTC","WOO","WNXM","WICC","WAXP","WAVES","VSYS","VIDY","VET","VALUE","UUU","UTK","USDC","UNI","UNI","UNI","UMA","UIP","TT","TRX","TRB","TOP","TNB","TITAN","THETA","SWRV","SWFTC","SUSHI","SUN","STPT","STORJ","STN","STEEM","SOL","SOC","SNX","SNT","SMT","SKM","SKL","SEELE","SAND","RVN","RUFF","RSR","RNDR","RING","REN","REEF","QTUM","PVT","POND","POLS","PHA","PEARL","PAX","PAI","OXT","ONT","ONE","OMG","OGO","OGN","OCN","NULS","NSURE","NODE","NKN","NHBTC","NEXO","NEW","NEST","NEO","NEAR","NBS","NAS","NANO","MX","MXC","MTA","MLN","MKR","MDX","MDS","MATIC","MASS","MASK","MANA","LXT","LUNA","LTC","LTC","LTC","LRC","LOOM","LOL","LINK","LINK","LINK","LINA","LET","LBAST","LAMB","KSM","KNC","KCASH","KAVA","KAN","JST","ITC","IRIS","IOTX","IOTA","IOST","INSUR","INJ","ICX","HT","HPT","HOT","HIVE","HIT","HC","HBC","HBAR","GXC","GT","GRT","GOF","GNX","GLM","FTT","FTI","FSN","FRONT","FOR","FLOW","FIS","FIRO","FIL","FILDA","FIL","FIL","ETH","ETH","ETH","ETH","ETC","EOS","EOS","EOS","ENJ","EM","ELF","ELA","EKT","EGT","DTA","DOT","DOT","DOT","DOGE","DOCK","DKA","DHT","DF","DCR","DASH","DAI","DAC","CVP","CVC","CTXC","CRV","CRU","CRO","CRE","COMP","CNNS","CMT","CKB","CHZ","CHR","BTT","BTS","BTM","BTC","BTC","BTC","BTC","BSV","BSV","BSV","BOR","BNT","BLZ","BIX","BHD","BETH","BCH","BCHA","BCH","BCH","BAT","BAND","BAL","BAGS","BADGER","AXS","AVAX","AUCTION","ATP","ATOM","AST","AR","ARPA","API","ANT","ANKR","ALGO","AKRO","AE","ADA","ACT","ACH","ABT","AAVE","AAC","1INCH"));
		Long id = 103L;
		CryptoCoinCatalogExample example = null;
		List<CryptoCoinCatalog> poList = null;
		CryptoCoinCatalog newPO = null;
		int count = 0;
		for(String newCatalog : s) {
			example = new CryptoCoinCatalogExample();
			example.createCriteria().andCoinNameEnShortEqualTo(newCatalog);
			poList = catalogMapper.selectByExample(example);
			if(poList == null || poList.isEmpty()) {
				newPO = new CryptoCoinCatalog();
				newPO.setId(id);
				newPO.setCoinNameEnShort(newCatalog);
				count += catalogMapper.insertSelective(newPO);
				id++;
			}
		}
		return String.valueOf(count);
	}
}
