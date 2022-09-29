package demo.pmemo.service;

import demo.tool.telegram.pojo.dto.TelegramGetUpdatesDTO;

public interface UrgeNoticeService {

	void receiveUpdateMsgWebhook(String unknowContent);

	void setUpdateMsgWebhook(String secretToken);

	TelegramGetUpdatesDTO getMessageUpdate();


}
