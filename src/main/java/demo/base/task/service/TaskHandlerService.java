package demo.base.task.service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.task.pojo.dto.SendTaskDTO;

public interface TaskHandlerService {

	CommonResult startEvent(SendTaskDTO dto);

	boolean existsRuningEvent();

	void fixRuningEventStatus();

	boolean setBreakFlag(Integer flag);

	boolean setBreakFlag(boolean flag);

}
