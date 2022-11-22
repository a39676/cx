package demo.base.task.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;

@Scope("singleton")
@Service
public class TaskOptionService extends CommonService {

	private boolean breakFlag = false;

	private boolean runningTask = false;

	private String runningTaskName;

	private Map<Long, Integer> faildTaskCountingMap = new HashMap<Long, Integer>();

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

	public Map<Long, Integer> getFaildTaskCountingMap() {
		return faildTaskCountingMap;
	}

	public void setFaildTaskCountingMap(Map<Long, Integer> faildTaskCountingMap) {
		this.faildTaskCountingMap = faildTaskCountingMap;
	}
	
	public void addFailTaskCount(Long taskId) {
		if(faildTaskCountingMap.containsKey(taskId)) {
			faildTaskCountingMap.put(taskId, faildTaskCountingMap.get(taskId) + 1);
		} else {
			faildTaskCountingMap.put(taskId, 1);
		}
	}
	
	public void removeFailTaskCount(Long taskId) {
		faildTaskCountingMap.remove(taskId);
	}

	@Override
	public String toString() {
		return "TaskOptionService [breakFlag=" + breakFlag + ", runningTask=" + runningTask + ", runningTaskName="
				+ runningTaskName + ", faildTaskCountingMap=" + faildTaskCountingMap + "]";
	}

}
