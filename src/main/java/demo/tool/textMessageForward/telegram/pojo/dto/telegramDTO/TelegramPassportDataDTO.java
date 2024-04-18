package demo.tool.textMessageForward.telegram.pojo.dto.telegramDTO;

import java.util.List;

public class TelegramPassportDataDTO {

	private List<TelegramEncryptedPassportElementDTO> data;
	private TelegramEncryptedCredentialsDTO credentials;

	public List<TelegramEncryptedPassportElementDTO> getData() {
		return data;
	}

	public void setData(List<TelegramEncryptedPassportElementDTO> data) {
		this.data = data;
	}

	public TelegramEncryptedCredentialsDTO getCredentials() {
		return credentials;
	}

	public void setCredentials(TelegramEncryptedCredentialsDTO credentials) {
		this.credentials = credentials;
	}

	@Override
	public String toString() {
		return "TelegramPassportDataDTO [credentials=" + credentials + "]";
	}

}
