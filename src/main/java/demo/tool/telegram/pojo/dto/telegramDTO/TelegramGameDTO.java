package demo.tool.telegram.pojo.dto.telegramDTO;

import java.util.List;

public class TelegramGameDTO {

	private String title;
	private String description;
	private List<TelegramPhotoSizeDTO> photo;
	private String text;
	private List<TelegramMessageEntityDTO> text_entities;
	private TelegramAnimationDTO animation;

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

	public List<TelegramPhotoSizeDTO> getPhoto() {
		return photo;
	}

	public void setPhoto(List<TelegramPhotoSizeDTO> photo) {
		this.photo = photo;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<TelegramMessageEntityDTO> getText_entities() {
		return text_entities;
	}

	public void setText_entities(List<TelegramMessageEntityDTO> text_entities) {
		this.text_entities = text_entities;
	}

	public TelegramAnimationDTO getAnimation() {
		return animation;
	}

	public void setAnimation(TelegramAnimationDTO animation) {
		this.animation = animation;
	}

	@Override
	public String toString() {
		return "TelegramGameDTO [title=" + title + ", description=" + description + ", text=" + text + ", animation="
				+ animation + "]";
	}

}
