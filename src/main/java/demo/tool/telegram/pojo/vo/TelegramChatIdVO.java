package demo.tool.telegram.pojo.vo;

public class TelegramChatIdVO {

	private String pk;
	private String username;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "TelegramChatIdVO [pk=" + pk + ", username=" + username + "]";
	}

}
