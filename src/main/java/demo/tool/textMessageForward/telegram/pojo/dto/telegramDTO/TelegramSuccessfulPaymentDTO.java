package demo.tool.textMessageForward.telegram.pojo.dto.telegramDTO;

public class TelegramSuccessfulPaymentDTO {

	private String currency;
	private Long total_amount;
	private String invoice_payload;
	private String shipping_option_id;
	private TelegramOrderInfoDTO order_info;
	private String telegram_payment_charge_id;
	private String provider_payment_charge_id;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Long getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(Long total_amount) {
		this.total_amount = total_amount;
	}

	public String getInvoice_payload() {
		return invoice_payload;
	}

	public void setInvoice_payload(String invoice_payload) {
		this.invoice_payload = invoice_payload;
	}

	public String getShipping_option_id() {
		return shipping_option_id;
	}

	public void setShipping_option_id(String shipping_option_id) {
		this.shipping_option_id = shipping_option_id;
	}

	public TelegramOrderInfoDTO getOrder_info() {
		return order_info;
	}

	public void setOrder_info(TelegramOrderInfoDTO order_info) {
		this.order_info = order_info;
	}

	public String getTelegram_payment_charge_id() {
		return telegram_payment_charge_id;
	}

	public void setTelegram_payment_charge_id(String telegram_payment_charge_id) {
		this.telegram_payment_charge_id = telegram_payment_charge_id;
	}

	public String getProvider_payment_charge_id() {
		return provider_payment_charge_id;
	}

	public void setProvider_payment_charge_id(String provider_payment_charge_id) {
		this.provider_payment_charge_id = provider_payment_charge_id;
	}

	@Override
	public String toString() {
		return "TelegramSuccessfulPaymentDTO [currency=" + currency + ", total_amount=" + total_amount
				+ ", invoice_payload=" + invoice_payload + ", shipping_option_id=" + shipping_option_id
				+ ", order_info=" + order_info + ", telegram_payment_charge_id=" + telegram_payment_charge_id
				+ ", provider_payment_charge_id=" + provider_payment_charge_id + "]";
	}

}
