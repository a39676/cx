package demo.finance.metal.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import demo.finance.common.service.impl.FinanceCommonService;
import demo.finance.metal.mapper.MetalPriceMapper;
import demo.finance.metal.pojo.po.MetalPrice;
import demo.finance.metal.pojo.po.MetalPriceExample;
import precious_metal.pojo.type.MetalType;

public abstract class PreciousMetalCommonService extends FinanceCommonService {
	
	@Autowired
	protected MetalPriceMapper metalPriceMapper;

	protected List<MetalPrice> findCacheDataByTime(MetalType metalType, LocalDateTime startTime, LocalDateTime endTime) {
		MetalPriceExample cacheExample = new MetalPriceExample();
		cacheExample.createCriteria()
		.andIsDeleteEqualTo(false)
		.andMetalTypeEqualTo(metalType.getCode())
		.andCreateTimeGreaterThanOrEqualTo(startTime)
		.andCreateTimeLessThan(endTime);
		cacheExample.setOrderByClause("create_time");
		return metalPriceMapper.selectByExample(cacheExample);
	}
	
	protected boolean isPreciousMetalsTransactionTime(LocalDateTime beiJingDateTime) {
		LocalDateTime washtonNow = beiJingDateTime.minusHours(12);
		int dayOfWeek = beiJingDateTime.getDayOfWeek().getValue();
		
		/*
		 * 2020-07-06
		 * 
		 * TODO
		 * 未加入美国节假日
		 * 待明确 goldprice.org 的价格是否受美国节假日影响, 是否受冬令时影响
		 * 美国法定节假日 ① 新年 （1月1日） ② 总统日 （2月第三个星期一） ③ 战争纪念日 （5月最后星期一） ④ 美国独立节 （7月4日） ⑤ 劳工节 （9月第一个星期一） ⑥ 感恩节 （11月最后一个星期四） ⑦ 圣诞节 （12月25日） 
		 * 
		 * 理论上美国黄金交易时间为: 
		 * (北京时间) 周一06:60 ~ 周六 03:30
		 * 冬令时期间 延后半小时
		 */
		boolean isUSWinterTime = localDateTimeHandler.isUSWinterTime(washtonNow.toLocalDate());
		if(isUSWinterTime) {
			beiJingDateTime = beiJingDateTime.minusMinutes(30);
		}
		
		if (dayOfWeek == 1) {
			return (beiJingDateTime.getHour() >= 6 && beiJingDateTime.getMinute() >= 30);
		} else if (dayOfWeek > 1 && dayOfWeek < 6) {
			return true;
		} else if (dayOfWeek == 6) {
			return (beiJingDateTime.getHour() <= 3 && beiJingDateTime.getMinute() <= 30);
		} else if (dayOfWeek > 6) {
			return false;
		}

		return false;
	}
	
	protected boolean isPreciousMetalsTransactionTime() {
		return isPreciousMetalsTransactionTime(LocalDateTime.now());
	}
	
}
