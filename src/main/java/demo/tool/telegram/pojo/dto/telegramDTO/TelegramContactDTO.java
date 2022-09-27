package demo.tool.telegram.pojo.dto.telegramDTO;

public class TelegramContactDTO {

	private String phone_number;
	private String first_name;
	private String last_name;
	private Long user_id;
	private String vcard;

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getVcard() {
		return vcard;
	}

	public void setVcard(String vcard) {
		this.vcard = vcard;
	}

	@Override
	public String toString() {
		return "TelegramContact [phone_number=" + phone_number + ", first_name=" + first_name + ", last_name="
				+ last_name + ", user_id=" + user_id + ", vcard=" + vcard + "]";
	}

}
