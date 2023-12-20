package demo.tool.textMessageForward.telegram.service;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.textMessageForward.telegram.pojo.dto.TelegramGetUpdatesDTO;
import demo.tool.textMessageForward.telegram.pojo.po.TelegramChatId;
import demo.tool.textMessageForward.telegram.pojo.vo.TelegramChatIdVO;
import telegram.pojo.type.TelegramBotType;

public interface TelegramService {

	List<TelegramChatId> getChatIDList();

	boolean chatIdExists(String pk);

	TelegramChatIdVO buildChatIdVO(TelegramChatId po);

	TelegramChatId getChatID(Long id);
	
	CommonResult sendMessageByChatRecordId(TelegramBotType botType, String msg, Long id);

	CommonResult sendMessage(String msg, Long id);

	void telegramSendingCheck();

	TelegramGetUpdatesDTO getUpdateMessage(String botIDKey);

	TelegramGetUpdatesDTO getUpdateMessage(String botIDKey, Long lastUpdateMsgId);

	void setWebhook(String botIDKey, String webhookUrl, String secretToken);

	boolean hasThisChatId(Long chatId);

	CommonResult sendMessageByTelegramChatId(TelegramBotType botType, String msg, Long telegramChatId);

}
