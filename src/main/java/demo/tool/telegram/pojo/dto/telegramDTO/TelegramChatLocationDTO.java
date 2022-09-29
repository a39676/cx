package demo.tool.telegram.pojo.dto.telegramDTO;

public class TelegramChatLocationDTO {

	private TelegramLocationDTO location;

	private String address;

	public TelegramLocationDTO getLocation() {
		return location;
	}

	public void setLocation(TelegramLocationDTO location) {
		this.location = location;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "TelegramChatLocationDTO [location=" + location + ", address=" + address + "]";
	}

}
