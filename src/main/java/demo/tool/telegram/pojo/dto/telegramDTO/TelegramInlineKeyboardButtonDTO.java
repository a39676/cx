package demo.tool.telegram.pojo.dto.telegramDTO;

public class TelegramInlineKeyboardButtonDTO {

	private String text;
	private String url;
	private String callback_data;
	private TelegramWebAppInfoDTO web_app;
	private TelegramLoginUrlDTO login_url;
	private String switch_inline_query;
	private String switch_inline_query_current_chat;
	private TelegramCallbackGameDTO callback_game;
	private Boolean pay;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCallback_data() {
		return callback_data;
	}

	public void setCallback_data(String callback_data) {
		this.callback_data = callback_data;
	}

	public TelegramWebAppInfoDTO getWeb_app() {
		return web_app;
	}

	public void setWeb_app(TelegramWebAppInfoDTO web_app) {
		this.web_app = web_app;
	}

	public TelegramLoginUrlDTO getLogin_url() {
		return login_url;
	}

	public void setLogin_url(TelegramLoginUrlDTO login_url) {
		this.login_url = login_url;
	}

	public String getSwitch_inline_query() {
		return switch_inline_query;
	}

	public void setSwitch_inline_query(String switch_inline_query) {
		this.switch_inline_query = switch_inline_query;
	}

	public String getSwitch_inline_query_current_chat() {
		return switch_inline_query_current_chat;
	}

	public void setSwitch_inline_query_current_chat(String switch_inline_query_current_chat) {
		this.switch_inline_query_current_chat = switch_inline_query_current_chat;
	}

	public TelegramCallbackGameDTO getCallback_game() {
		return callback_game;
	}

	public void setCallback_game(TelegramCallbackGameDTO callback_game) {
		this.callback_game = callback_game;
	}

	public Boolean getPay() {
		return pay;
	}

	public void setPay(Boolean pay) {
		this.pay = pay;
	}

	@Override
	public String toString() {
		return "TelegramInlineKeyboardButtonDTO [text=" + text + ", url=" + url + ", callback_data=" + callback_data
				+ ", web_app=" + web_app + ", login_url=" + login_url + ", switch_inline_query=" + switch_inline_query
				+ ", switch_inline_query_current_chat=" + switch_inline_query_current_chat + ", callback_game="
				+ callback_game + ", pay=" + pay + "]";
	}

}
