package demo.tool.textMessageForward.telegram.pojo.dto.telegramDTO;

public class TelegramProximityAlertTriggeredDTO {

	private TelegramUserDTO traveler;
	private TelegramUserDTO watcher;
	private Long distance;

	public TelegramUserDTO getTraveler() {
		return traveler;
	}

	public void setTraveler(TelegramUserDTO traveler) {
		this.traveler = traveler;
	}

	public TelegramUserDTO getWatcher() {
		return watcher;
	}

	public void setWatcher(TelegramUserDTO watcher) {
		this.watcher = watcher;
	}

	public Long getDistance() {
		return distance;
	}

	public void setDistance(Long distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "TelegramProximityAlertTriggeredDTO [traveler=" + traveler + ", watcher=" + watcher + ", distance="
				+ distance + "]";
	}

}
