package demo.finance.metal.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.pojo.result.CommonResultCX;
import demo.finance.metal.mapper.MetalPrice5minuteMapper;
import demo.finance.metal.pojo.po.MetalPrice;
import demo.finance.metal.pojo.po.MetalPrice5minute;
import demo.finance.metal.pojo.po.MetalPrice5minuteExample;
import demo.finance.metal.service.PreciousMetal5MinuteDataSummaryService;
import precious_metal.pojo.type.MetalType;

@Service
public class PreciousMetal5MinuteDataSummaryServiceImpl extends PreciousMetalCommonService implements PreciousMetal5MinuteDataSummaryService {

	@Autowired
	private MetalPrice5minuteMapper metalPrice5minuteMapper;
	
	@Override
	public CommonResultCX cacheDataTo5Minute(MetalType metalType, LocalDateTime startTime, LocalDateTime endTime) {
		
		CommonResultCX r = new CommonResultCX();
		
		int minuteStep = 5;
		
		MetalPrice5minute thisSummaryPO = findSummaryPO(metalType, startTime, endTime);
		
		boolean newPOFlag = false;
		
//		当前时间区间无汇总数据, 汇总后更新, 否则直接更新
		if(thisSummaryPO == null) {
			newPOFlag = true;
			LocalDateTime previousStartTime = startTime.minusMinutes(minuteStep);
			List<MetalPrice> oldCachePOList = findCacheDataByTime(metalType, previousStartTime, startTime);
			
			// 可能是新开盘区间, 前区间未必有数据
			if(oldCachePOList != null && !oldCachePOList.isEmpty()) {
				MetalPrice5minute oldSummaryPO = findSummaryPO(metalType, startTime, endTime);
				oldSummaryPO = updateOrCreate5MinteSummaryPOByCache(oldSummaryPO, oldCachePOList);
				metalPrice5minuteMapper.updateByPrimaryKeySelective(oldSummaryPO);
			}
		}

		List<MetalPrice> cachePOList = findCacheDataByTime(metalType, startTime, endTime);
		thisSummaryPO = updateOrCreate5MinteSummaryPOByCache(thisSummaryPO, cachePOList);
		
		// 收盘后, 会再触发一次汇总收集, 此时当前区间无数据
		if(thisSummaryPO != null) {
			if(newPOFlag) {
				metalPrice5minuteMapper.insertSelective(thisSummaryPO);
			} else {
				metalPrice5minuteMapper.updateByPrimaryKeySelective(thisSummaryPO);
			}
		}
		r.setIsSuccess();
		return r;
	}

	private MetalPrice5minute findSummaryPO(MetalType metalType, LocalDateTime startTime, LocalDateTime endTime) {
		MetalPrice5minuteExample example = new MetalPrice5minuteExample();
		example.createCriteria()
		.andIsDeleteEqualTo(false)
		.andMetalTypeEqualTo(metalType.getCode())
		.andStartTimeGreaterThanOrEqualTo(startTime)
		.andEndTimeLessThanOrEqualTo(endTime)
		;
		List<MetalPrice5minute> summaryPOList = metalPrice5minuteMapper.selectByExample(example);
		if(summaryPOList != null) {
			return summaryPOList.get(0);
		}
		return null;
	}
	
	private MetalPrice5minute updateOrCreate5MinteSummaryPOByCache(MetalPrice5minute po, List<MetalPrice> cachePOList) {
		
		if(cachePOList == null || cachePOList.isEmpty()) {
			return null;
		}
		
		if(po == null) {
			po = new MetalPrice5minute();
			po.setId(snowFlake.getNextId());
			po.setCreateTime(LocalDateTime.now());
		}
		
		MetalPrice tmpCachePO = null;
		for(int i = 0; i < cachePOList.size(); i++) {
			tmpCachePO = cachePOList.get(i);
			
			if(po.getStartPrice() == null) {
				po.setStartPrice(tmpCachePO.getPrice());
				po.setMetalType(tmpCachePO.getMetalType());
			}
			if(po.getHighPrice() == null || po.getHighPrice().compareTo(tmpCachePO.getPrice()) == -1) {
				po.setHighPrice(tmpCachePO.getPrice());
			}
			if(po.getLowPrice() == null || po.getLowPrice().compareTo(tmpCachePO.getPrice()) == 1) {
				po.setLowPrice(tmpCachePO.getPrice());
			}
			po.setEndPrice(tmpCachePO.getPrice());
		}
		
		return po;
	}
}
