package demo.tool.telegram.pojo.dto.telegramDTO;

import java.util.List;

public class TelegramEncryptedPassportElementDTO {

	private String type;
	private String data;
	private String phone_number;
	private String email;
	private List<TelegramPassportFileDTO> files;
	private TelegramPassportFileDTO front_side;
	private TelegramPassportFileDTO reverse_side;
	private TelegramPassportFileDTO selfie;
	private List<TelegramPassportFileDTO> translation;
	private String hash;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<TelegramPassportFileDTO> getFiles() {
		return files;
	}

	public void setFiles(List<TelegramPassportFileDTO> files) {
		this.files = files;
	}

	public TelegramPassportFileDTO getFront_side() {
		return front_side;
	}

	public void setFront_side(TelegramPassportFileDTO front_side) {
		this.front_side = front_side;
	}

	public TelegramPassportFileDTO getReverse_side() {
		return reverse_side;
	}

	public void setReverse_side(TelegramPassportFileDTO reverse_side) {
		this.reverse_side = reverse_side;
	}

	public TelegramPassportFileDTO getSelfie() {
		return selfie;
	}

	public void setSelfie(TelegramPassportFileDTO selfie) {
		this.selfie = selfie;
	}

	public List<TelegramPassportFileDTO> getTranslation() {
		return translation;
	}

	public void setTranslation(List<TelegramPassportFileDTO> translation) {
		this.translation = translation;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	@Override
	public String toString() {
		return "TelegramEncryptedPassportElementDTO [type=" + type + ", data=" + data + ", phone_number=" + phone_number
				+ ", email=" + email + ", front_side=" + front_side + ", reverse_side=" + reverse_side + ", selfie="
				+ selfie + ", hash=" + hash + "]";
	}

}
