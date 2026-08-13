package demo.tool.textMessageForward.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;
import demo.tool.textMessageForward.service.TelegramMeetingMsgForwardService;
import demo.tool.textMessageForward.telegram.pojo.dto.TelegramGetUpdatesDTO;
import demo.tool.textMessageForward.telegram.pojo.dto.TelegramUpdateMessageDTO;
import demo.tool.textMessageForward.telegram.service.TelegramService;
import demo.tool.textMessageForward.telegram.service.impl.TelegramOptionService;
import telegram.pojo.type.TelegramBotType;

@Service
public class TelegramMeetingMsgForwardServiceImpl extends CommonService implements TelegramMeetingMsgForwardService {

	@Autowired
	private TelegramOptionService optionService;
	@Autowired
	private TelegramService telegramService;

	@Override
	public void findMeetingMsgAndForward() {
		Long lastMsgId = 0L;
		Long lastMsgIdInMap = optionService.getBotCodeMsgIdMap().get(TelegramBotType.ARK_ASSISTANT_2.getCode());
		if (lastMsgIdInMap != null) {
			lastMsgId = optionService.getBotCodeMsgIdMap().get(TelegramBotType.ARK_ASSISTANT_2.getCode());
		}
		TelegramGetUpdatesDTO updateMsg = telegramService.getUpdateMessage(TelegramBotType.ARK_ASSISTANT_2.getName(),
				9L);
		List<TelegramUpdateMessageDTO> resultList = updateMsg.getResult();
		if (resultList == null || resultList.size() < 1) {
			return;
		}
		for (int i = 0; i < resultList.size(); i++) {
			TelegramUpdateMessageDTO msg = resultList.get(i);
			if (msg.getUpdate_id() <= lastMsgId) {
				continue;
			}
			try {
				String text = msg.getMessage().getCaption();
				if (StringUtils.containsAny(text, "meeting.tencent.com", "fchat.vip", "test")) {
					telegramService.sendMessageByTelegramChatId(TelegramBotType.ARK_ASSISTANT_2, text,
							optionService.getTargetGroupId());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		lastMsgId = resultList.get(resultList.size() - 1).getUpdate_id();
		optionService.getBotCodeMsgIdMap().put(TelegramBotType.ARK_ASSISTANT_2.getCode(), lastMsgId);
	}
}
