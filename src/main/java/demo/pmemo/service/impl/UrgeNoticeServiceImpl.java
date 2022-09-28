package demo.pmemo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.article.article.service.impl.ArticleCommonService;
import demo.pmemo.pojo.constant.PMemoConstant;
import demo.pmemo.service.UrgeNoticeService;
import demo.tool.telegram.pojo.dto.TelegramGetUpdateMessageFeedbackDTO;
import demo.tool.telegram.pojo.dto.TelegramGetUpdateMessageResultDTO;
import demo.tool.telegram.pojo.dto.telegramDTO.TelegramMessageDTO;
import demo.tool.telegram.service.TelegramService;
import telegram.pojo.constant.TelegramBotType;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class UrgeNoticeServiceImpl extends ArticleCommonService implements UrgeNoticeService {
	
	/*
	 * TODO
	 * 1. Need white name list
	 * 2. Need file max length
	 */

	@Autowired
	private TelegramService telegramService;
	
	public TelegramGetUpdateMessageFeedbackDTO getMessageUpdate() {
		TelegramGetUpdateMessageFeedbackDTO result = telegramService.getUpdateMessage(TelegramBotType.URGE_NOTICE.getName());
		return result;
	}
	
	private List<TelegramGetUpdateMessageResultDTO> filterMsgUpdate(TelegramGetUpdateMessageFeedbackDTO dto) {
		List<TelegramGetUpdateMessageResultDTO> newMsgList = new ArrayList<>();
		
		String lastUpdateMsgIdStr = redisOriginalConnectService.getValByName(PMemoConstant.LAST_UPDATE_TELEGRAM_BOT_URGE_NOTICE_MSG_ID_KEY);
		Long lastUpdateMsgId = 0L;
		try {
			lastUpdateMsgId = Long.parseLong(lastUpdateMsgIdStr);
		} catch (Exception e) {
		}
		
		if(!dto.getOk() || dto.getResult() == null || dto.getResult().isEmpty()) {
			return newMsgList;
		}
		
		for(TelegramGetUpdateMessageResultDTO msg : dto.getResult()) {
			if(msg.getUpdate_id() <= lastUpdateMsgId) {
//				TODO need white name list
				continue;
			}
			lastUpdateMsgId = msg.getUpdate_id();
			newMsgList.add(msg);
		}
		
		redisOriginalConnectService.setValByName(PMemoConstant.LAST_UPDATE_TELEGRAM_BOT_URGE_NOTICE_MSG_ID_KEY, lastUpdateMsgId.toString());
		
		return newMsgList;
	}

	private void msgStore(List<TelegramGetUpdateMessageResultDTO> msgList) {
		if (msgList == null || msgList.isEmpty()) {
			return;
		}
		
		Map<Long, List<TelegramGetUpdateMessageResultDTO>> userMsgMap = new HashMap<>();
		List<TelegramGetUpdateMessageResultDTO> tmpMsgList = null;
		Long tmpTelegramUserId = null;
		for(TelegramGetUpdateMessageResultDTO udpateMsg : msgList) {
			tmpTelegramUserId = udpateMsg.getMessage().getFrom().getId();
			if(!userMsgMap.containsKey(tmpTelegramUserId)) {
				tmpMsgList = new ArrayList<>();
			} else {
				tmpMsgList = userMsgMap.get(tmpTelegramUserId);
			}
			tmpMsgList.add(udpateMsg);
			userMsgMap.put(tmpTelegramUserId, tmpMsgList);
		}
		
		
	}
	
	private void handleUrgeNoticeUpdate(Long telegramUserId, List<TelegramMessageDTO> newMsgList) {
		/*
		 * TODO
		 */
		
		
	}
	
//	TODO delete notice
	
//	TODO clear notice
	
//	TODO add notice 
	
//	TODO show notice 
	
//	TODO read file
	private List<TelegramMessageDTO> readUrgeNoticeFile(Long telegramUserId) {
//		TODO
		
	}
	
	private String getUrgeNoticeFilePath(Long telegramUserId) {
		if(telegramUserId == null) {
			return null;
		}
		return getUrgeNoticeStorePrefixPath() + "/" + telegramUserId + ".txt";
	}
	
	private void rewiriteUrgeNotice(Long telegramUserId, String content) {
		String pathStr = getUrgeNoticeFilePath(telegramUserId);
		if(pathStr == null) {
			return;
		}
		
		FileUtilCustom io = new FileUtilCustom();
		io.byteToFile(content.getBytes(), pathStr);
	}
	

	private String getUrgeNoticeStorePrefixPath() {
		if (isWindows()) {
			return "d:" + PMemoConstant.URGE_NOTE_SAVING_FOLDER;
		} else {
			return PMemoConstant.URGE_NOTE_SAVING_FOLDER;
		}
	}
}