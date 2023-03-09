package demo.interaction.sms.pojo.dto;

import java.time.LocalDateTime;

public class QuerySendingFrequencyDTO {

	private LocalDateTime dailyStartTime;
	private LocalDateTime dailyEndTime;
	private LocalDateTime hourlyStartTime;
	private LocalDateTime hourlyEndTime;
	private String phoneNumber;

	public LocalDateTime getDailyStartTime() {
		return dailyStartTime;
	}

	public void setDailyStartTime(LocalDateTime dailyStartTime) {
		this.dailyStartTime = dailyStartTime;
	}

	public LocalDateTime getDailyEndTime() {
		return dailyEndTime;
	}

	public void setDailyEndTime(LocalDateTime dailyEndTime) {
		this.dailyEndTime = dailyEndTime;
	}

	public LocalDateTime getHourlyStartTime() {
		return hourlyStartTime;
	}

	public void setHourlyStartTime(LocalDateTime hourlyStartTime) {
		this.hourlyStartTime = hourlyStartTime;
	}

	public LocalDateTime getHourlyEndTime() {
		return hourlyEndTime;
	}

	public void setHourlyEndTime(LocalDateTime hourlyEndTime) {
		this.hourlyEndTime = hourlyEndTime;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "QuerySendingFrequencyResult [dailyStartTime=" + dailyStartTime + ", dailyEndTime=" + dailyEndTime
				+ ", hourlyStartTime=" + hourlyStartTime + ", hourlyEndTime=" + hourlyEndTime + ", phoneNumber="
				+ phoneNumber + "]";
	}

}
