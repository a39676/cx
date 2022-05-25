package demo.automationTest.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;

public class InsertSearchingDemoEventResult extends CommonResult {
	
	private Long eventId;
	private Integer waitingEventCount = 0;
	private Integer hasInsertCount;
	private Integer maxInsertCount;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Integer getWaitingEventCount() {
		return waitingEventCount;
	}

	public void setWaitingEventCount(Integer waitingEventCount) {
		this.waitingEventCount = waitingEventCount;
	}

	public Integer getHasInsertCount() {
		return hasInsertCount;
	}

	public void setHasInsertCount(Integer hasInsertCount) {
		this.hasInsertCount = hasInsertCount;
	}

	public Integer getMaxInsertCount() {
		return maxInsertCount;
	}

	public void setMaxInsertCount(Integer maxInsertCount) {
		this.maxInsertCount = maxInsertCount;
	}

	@Override
	public String toString() {
		return "InsertBingDemoEventResult [eventId=" + eventId + ", waitingEventCount=" + waitingEventCount
				+ ", hasInsertCount=" + hasInsertCount + ", maxInsertCount=" + maxInsertCount + "]";
	}
}
