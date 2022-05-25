package demo.toyParts.educate.pojo.dto;

import demo.toyParts.educate.pojo.type.EducateLeaderboardOrderType;

public class GetExerciesLeaderboardDTO {

	/** {@link EducateLeaderboardOrderType} */
	private Integer orderType;

	private Integer days;

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	@Override
	public String toString() {
		return "GetExerciesLeaderboardDTO [orderType=" + orderType + ", days=" + days + "]";
	}

}
