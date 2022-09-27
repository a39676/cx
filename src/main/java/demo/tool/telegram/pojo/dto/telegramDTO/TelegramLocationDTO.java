package demo.tool.telegram.pojo.dto.telegramDTO;

public class TelegramLocationDTO {

	private Float longitude;
	private Float latitude;
	private Float horizontal_accuracy;

	private Long live_period;
	private Long heading;
	private Long proximity_alert_radius;

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getHorizontal_accuracy() {
		return horizontal_accuracy;
	}

	public void setHorizontal_accuracy(Float horizontal_accuracy) {
		this.horizontal_accuracy = horizontal_accuracy;
	}

	public Long getLive_period() {
		return live_period;
	}

	public void setLive_period(Long live_period) {
		this.live_period = live_period;
	}

	public Long getHeading() {
		return heading;
	}

	public void setHeading(Long heading) {
		this.heading = heading;
	}

	public Long getProximity_alert_radius() {
		return proximity_alert_radius;
	}

	public void setProximity_alert_radius(Long proximity_alert_radius) {
		this.proximity_alert_radius = proximity_alert_radius;
	}

	@Override
	public String toString() {
		return "TelegramLocationDTO [longitude=" + longitude + ", latitude=" + latitude + ", horizontal_accuracy="
				+ horizontal_accuracy + ", live_period=" + live_period + ", heading=" + heading
				+ ", proximity_alert_radius=" + proximity_alert_radius + "]";
	}

}
