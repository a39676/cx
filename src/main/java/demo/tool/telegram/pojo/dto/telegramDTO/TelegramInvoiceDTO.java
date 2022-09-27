package demo.tool.telegram.pojo.dto.telegramDTO;

public class TelegramInvoiceDTO {

	private String title;
	private String description;
	private String start_parameter;
	private String currency;
	private Long total_amount;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStart_parameter() {
		return start_parameter;
	}

	public void setStart_parameter(String start_parameter) {
		this.start_parameter = start_parameter;
	}

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

	@Override
	public String toString() {
		return "TelegramInvoiceDTO [title=" + title + ", description=" + description + ", start_parameter="
				+ start_parameter + ", currency=" + currency + ", total_amount=" + total_amount + "]";
	}

}
