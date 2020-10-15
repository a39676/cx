package demo.finance.metal.service;

import java.time.LocalDateTime;

import demo.common.pojo.result.CommonResultCX;
import precious_metal.pojo.type.MetalType;

public interface PreciousMetal5MinuteDataSummaryService {

	CommonResultCX cacheDataTo5Minute(MetalType metalType, LocalDateTime startTime, LocalDateTime endTime);

	void cacheDataTo5Minute();

}
