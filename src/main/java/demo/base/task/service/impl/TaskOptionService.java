package demo.base.task.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;

@Scope("singleton")
@Service
public class TaskOptionService extends CommonService {

	private boolean breakFlag = false;

	private boolean runningTask = false;

	private String runningTaskName;

	public boolean getBreakFlag() {
		return breakFlag;
	}

	public void setBreakFlag(boolean breakFlag) {
		this.breakFlag = breakFlag;
	}

	public boolean getRunningTask() {
		return runningTask;
	}

	public void setRunningTask(boolean runningTask) {
		this.runningTask = runningTask;
	}

	public String getRunningTaskName() {
		return runningTaskName;
	}

	public void setRunningTaskName(String runningTaskName) {
		this.runningTaskName = runningTaskName;
	}

	@Override
	public String toString() {
		return "TaskOptionService [breakFlag=" + breakFlag + ", runningTask=" + runningTask + ", runningTaskName="
				+ runningTaskName + "]";
	}

}
