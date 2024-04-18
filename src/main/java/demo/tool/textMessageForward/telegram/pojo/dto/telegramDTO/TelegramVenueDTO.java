package demo.tool.textMessageForward.telegram.pojo.dto.telegramDTO;

public class TelegramVenueDTO {

	private TelegramLocationDTO location;
	private String title;
	private String address;
	private String foursquare_id;
	private String foursquare_type;
	private String google_place_id;
	private String google_place_type;

	public TelegramLocationDTO getLocation() {
		return location;
	}

	public void setLocation(TelegramLocationDTO location) {
		this.location = location;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFoursquare_id() {
		return foursquare_id;
	}

	public void setFoursquare_id(String foursquare_id) {
		this.foursquare_id = foursquare_id;
	}

	public String getFoursquare_type() {
		return foursquare_type;
	}

	public void setFoursquare_type(String foursquare_type) {
		this.foursquare_type = foursquare_type;
	}

	public String getGoogle_place_id() {
		return google_place_id;
	}

	public void setGoogle_place_id(String google_place_id) {
		this.google_place_id = google_place_id;
	}

	public String getGoogle_place_type() {
		return google_place_type;
	}

	public void setGoogle_place_type(String google_place_type) {
		this.google_place_type = google_place_type;
	}

	@Override
	public String toString() {
		return "TelegramVenueDTO [location=" + location + ", title=" + title + ", address=" + address
				+ ", foursquare_id=" + foursquare_id + ", foursquare_type=" + foursquare_type + ", google_place_id="
				+ google_place_id + ", google_place_type=" + google_place_type + "]";
	}

}
