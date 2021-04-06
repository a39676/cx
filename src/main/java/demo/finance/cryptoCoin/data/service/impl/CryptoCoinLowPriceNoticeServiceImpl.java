package demo.finance.cryptoCoin.data.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice1dayMapper;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1day;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1dayExample;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;
import demo.finance.cryptoCoin.notice.service.CryptoCoinLowPriceNoticeService;

@Service
public class CryptoCoinLowPriceNoticeServiceImpl extends CryptoCoinCommonService
		implements CryptoCoinLowPriceNoticeService {

	@Autowired
	private CryptoCoinPrice1dayMapper dailyDataMaper;
	@Autowired
	private CryptoCoinPriceCacheService cacheService;
	
	/*
	 * TODO
	 * crypto coin socket 需要改成动态订阅
	 * compare 如果订阅了不存在的币种, 会断连, 并且订阅上限100个?
	 * binance 新建连接时, 除配置文件外, 需参考缓存的订阅列表
	 * 
	 * 定时任务 刷新低价币监控列表?
	 * 连升n日 / 近n日总升幅=x? / 脱离均线? / 回调?
	 */
	
	private List<Long> findLowPriceCoinType() {
		LocalDateTime yesterday = LocalDateTime.now().with(LocalTime.MIN).minusDays(1);
		BigDecimal lowPrice = new BigDecimal(0.5);
		
		CryptoCoinPrice1dayExample example = new CryptoCoinPrice1dayExample();
		example.createCriteria().andIsDeleteEqualTo(false).andStartTimeEqualTo(yesterday).andEndPriceLessThan(lowPrice);
		List<CryptoCoinPrice1day> dataList = dailyDataMaper.selectByExample(example);
		List<Long> coinTypeIdList = dataList.stream().map(po -> po.getCoinType()).collect(Collectors.toList());
		return coinTypeIdList;
	}
}
