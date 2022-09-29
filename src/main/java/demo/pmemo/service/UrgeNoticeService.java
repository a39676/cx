package demo.pmemo.service;

import javax.servlet.http.HttpServletRequest;

import demo.tool.telegram.pojo.dto.TelegramGetUpdatesDTO;
import demo.tool.telegram.pojo.dto.TelegramUpdateMessageDTO;

public interface UrgeNoticeService {

	void receiveUpdateMsgWebhook(HttpServletRequest request, TelegramUpdateMessageDTO unknowContent);

	TelegramGetUpdatesDTO getMessageUpdate();
}
