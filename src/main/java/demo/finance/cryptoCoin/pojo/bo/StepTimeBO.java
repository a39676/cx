package demo.finance.cryptoCoin.pojo.bo;

import java.time.LocalDateTime;

public class StepTimeBO {

	private LocalDateTime stepStartTime;
	private LocalDateTime stepEndTime;

	@Override
	public String toString() {
		return "StepTimeBO [stepStartTime=" + stepStartTime + ", stepEndTime=" + stepEndTime + "]";
	}

	public LocalDateTime getStepStartTime() {
		return stepStartTime;
	}

	public void setStepStartTime(LocalDateTime stepStartTime) {
		this.stepStartTime = stepStartTime;
	}

	public LocalDateTime getStepEndTime() {
		return stepEndTime;
	}

	public void setStepEndTime(LocalDateTime stepEndTime) {
		this.stepEndTime = stepEndTime;
	}
	
	
}
