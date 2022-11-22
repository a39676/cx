package demo.base.task.service;

import auxiliaryCommon.pojo.result.CommonResult;

public interface TaskHandlerService {

	String getRunningTaskName();

	void fixRuningTaskStatus();

	boolean setBreakFlag(Integer flag);

	boolean setBreakFlag(boolean flag);

	CommonResult startEvent(String message);

}
