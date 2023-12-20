package demo.tool.textMessageForward.telegram.pojo.dto.telegramDTO;

public class TelegramLoginUrlDTO {

	private String url;
	private String forward_text;
	private String bot_username;
	private Boolean request_write_access;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getForward_text() {
		return forward_text;
	}

	public void setForward_text(String forward_text) {
		this.forward_text = forward_text;
	}

	public String getBot_username() {
		return bot_username;
	}

	public void setBot_username(String bot_username) {
		this.bot_username = bot_username;
	}

	public Boolean getRequest_write_access() {
		return request_write_access;
	}

	public void setRequest_write_access(Boolean request_write_access) {
		this.request_write_access = request_write_access;
	}

	@Override
	public String toString() {
		return "TelegramLoginUrlDTO [url=" + url + ", forward_text=" + forward_text + ", bot_username=" + bot_username
				+ ", request_write_access=" + request_write_access + "]";
	}

}
