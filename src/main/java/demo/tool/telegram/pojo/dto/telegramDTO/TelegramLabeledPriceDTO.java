package demo.tool.telegram.pojo.dto.telegramDTO;

public class TelegramLabeledPriceDTO {

	private String label;
	private Long amount;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "TelegramLabeledPriceDTO [label=" + label + ", amount=" + amount + "]";
	}

}
