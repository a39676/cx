package demo.pmemo.service;

import demo.tool.telegram.pojo.dto.TelegramUpdateMessageDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface UrgeNoticeService {

	void receiveUpdateMsgWebhook(HttpServletRequest request, TelegramUpdateMessageDTO unknowContent);

	void sendAllUrgeNoticeList();

}
