package demo.tool.textMessageForward.telegram.pojo.dto.telegramDTO;

public class TelegramWebAppDataDTO {

	private String data;
	private String button_text;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getButton_text() {
		return button_text;
	}

	public void setButton_text(String button_text) {
		this.button_text = button_text;
	}

	@Override
	public String toString() {
		return "TelegramWebAppDataDTO [data=" + data + ", button_text=" + button_text + "]";
	}

}
