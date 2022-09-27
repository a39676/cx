package demo.pmemo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.article.article.service.impl.ArticleCommonService;
import demo.pmemo.pojo.constant.PMemoConstant;
import demo.pmemo.service.UrgeNoticeService;
import demo.tool.telegram.pojo.dto.TelegramGetUpdateMessageResultDTO;
import demo.tool.telegram.pojo.dto.TelegramGetUpdateMessageFeedbackDTO;
import demo.tool.telegram.pojo.dto.telegramDTO.TelegramMessageDTO;
import demo.tool.telegram.service.TelegramService;
import telegram.pojo.constant.TelegramBotType;

@Service
public class UrgeNoticeServiceImpl extends ArticleCommonService implements UrgeNoticeService {

	@Autowired
	private TelegramService telegramService;

	public TelegramGetUpdateMessageFeedbackDTO getMessageUpdate() {
		TelegramGetUpdateMessageFeedbackDTO result = telegramService.getUpdateMessage(TelegramBotType.URGE_NOTICE.getName());

		return result;
	}

	private void msgStore(TelegramGetUpdateMessageResultDTO dto) {
		if (dto == null || dto.getMessage() == null || dto.getMessage().isEmpty()) {
			return;
		}
		List<TelegramMessageDTO> msgList = dto.getMessage();
		Map<Long, List<TelegramMessageDTO>> userMsgList = new HashMap<>();
		for(TelegramMessageDTO msg : msgList) {
//			TODO
		}
	}
	
//	TODO read file
	private void readUrgeNoticeFile(String path) {
//		TODO
	}
	
	
//	TODO rewrite file
	
	

	private String getUrgeNoticeStorePrefixPath() {
		if (isWindows()) {
			return "d:" + PMemoConstant.URGE_NOTE_SAVING_FOLDER;
		} else {
			return PMemoConstant.URGE_NOTE_SAVING_FOLDER;
		}
	}
}