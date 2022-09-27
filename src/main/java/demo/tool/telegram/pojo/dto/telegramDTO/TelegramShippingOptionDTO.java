package demo.tool.telegram.pojo.dto.telegramDTO;

import java.util.List;

public class TelegramShippingOptionDTO {

	private String id;
	private String title;
	private List<TelegramLabeledPriceDTO> prices;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<TelegramLabeledPriceDTO> getPrices() {
		return prices;
	}

	public void setPrices(List<TelegramLabeledPriceDTO> prices) {
		this.prices = prices;
	}

	@Override
	public String toString() {
		return "TelegramShippingOptionDTO [id=" + id + ", title=" + title + ", prices=" + prices + "]";
	}

}
