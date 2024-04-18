package demo.tool.textMessageForward.telegram.pojo.dto.telegramDTO;

public class TelegramOrderInfoDTO {

	private String name;
	private String phone_number;
	private String email;
	private TelegramShippingAddressDTO shipping_address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public TelegramShippingAddressDTO getShipping_address() {
		return shipping_address;
	}

	public void setShipping_address(TelegramShippingAddressDTO shipping_address) {
		this.shipping_address = shipping_address;
	}

	@Override
	public String toString() {
		return "TelegramOrderInfoDTO [name=" + name + ", phone_number=" + phone_number + ", email=" + email
				+ ", shipping_address=" + shipping_address + "]";
	}

}
