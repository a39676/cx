package demo.finance.metal.service;

import java.time.LocalDateTime;

import auxiliaryCommon.pojo.result.CommonResult;
import finance.precious_metal.pojo.type.MetalType;

public interface PreciousMetal5MinuteDataSummaryService {

	CommonResult cacheDataTo5Minute(MetalType metalType, LocalDateTime startTime, LocalDateTime endTime);

	void cacheDataTo5Minute();

}
