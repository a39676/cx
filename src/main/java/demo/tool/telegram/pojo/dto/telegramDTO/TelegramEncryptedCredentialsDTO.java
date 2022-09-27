package demo.tool.telegram.pojo.dto.telegramDTO;

public class TelegramEncryptedCredentialsDTO {

	private String data;
	private String hash;
	private String secret;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	@Override
	public String toString() {
		return "TelegramEncryptedCredentialsDTO [data=" + data + ", hash=" + hash + ", secret=" + secret + "]";
	}

}
