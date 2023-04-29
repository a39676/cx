package demo.ai.aiArt.pojo.dto;

public class AiUserDetailInRedisDTO {

	private Long userId;

	private String rechargeMarkThisWeek;
	private Integer freeJobCountingToday;
	private Integer freeJobCountingLastThreeDays;

	public String getRechargeMarkThisWeek() {
		return rechargeMarkThisWeek;
	}

	public void setRechargeMarkThisWeek(String rechargeMarkThisWeek) {
		this.rechargeMarkThisWeek = rechargeMarkThisWeek;
	}

	public Integer getFreeJobCountingToday() {
		return freeJobCountingToday;
	}

	public void setFreeJobCountingToday(Integer freeJobCountingToday) {
		this.freeJobCountingToday = freeJobCountingToday;
	}

	public Integer getFreeJobCountingLastThreeDays() {
		return freeJobCountingLastThreeDays;
	}

	public void setFreeJobCountingLastThreeDays(Integer freeJobCountingLastThreeDays) {
		this.freeJobCountingLastThreeDays = freeJobCountingLastThreeDays;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "AiUserDetailInRedisDTO [userId=" + userId + ", rechargeMarkThisWeek=" + rechargeMarkThisWeek
				+ ", freeJobCountingToday=" + freeJobCountingToday + ", freeJobCountingLastThreeDays="
				+ freeJobCountingLastThreeDays + "]";
	}

}
