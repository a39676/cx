package demo.tool.telegram.pojo.dto.telegramDTO;

import java.util.List;

public class TelegramInlineKeyboardMarkupDTO {

	private List<List<TelegramInlineKeyboardButtonDTO>> inline_keyboard;

	public List<List<TelegramInlineKeyboardButtonDTO>> getInline_keyboard() {
		return inline_keyboard;
	}

	public void setInline_keyboard(List<List<TelegramInlineKeyboardButtonDTO>> inline_keyboard) {
		this.inline_keyboard = inline_keyboard;
	}

	@Override
	public String toString() {
		return "TelegramInlineKeyboardMarkupDTO [inline_keyboard=" + inline_keyboard + "]";
	}

}
