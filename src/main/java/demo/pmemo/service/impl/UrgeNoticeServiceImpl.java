package demo.pmemo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.article.article.service.impl.ArticleCommonService;
import demo.pmemo.pojo.constant.PMemoConstant;
import demo.pmemo.pojo.constant.UrgeNoticeUrl;
import demo.pmemo.service.UrgeNoticeService;
import demo.tool.telegram.pojo.dto.TelegramGetUpdatesDTO;
import demo.tool.telegram.pojo.dto.TelegramUpdateMessageDTO;
import demo.tool.telegram.pojo.dto.UpdateMessageResponseStoreDTO;
import demo.tool.telegram.service.TelegramService;
import telegram.pojo.constant.TelegramBotType;
import telegram.pojo.constant.TelegramStaticChatID;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class UrgeNoticeServiceImpl extends ArticleCommonService implements UrgeNoticeService {

	/*
	 * TODO
	 * 
	 * Update set web hook instead
	 * 
	 * 1. Need white name list 2. Need file max length
	 */

	@Autowired
	private TelegramService telegramService;

	@Override
	public void receiveUpdateMsgWebhook(String unknowContent) {
		/*
		 * TODO For telegram HTTP POST, receive new msg from bot
		 */
		telegramService.sendMessage(TelegramBotType.BOT_2, unknowContent, TelegramStaticChatID.MY_ID);
	}

	@Override
	public void setUpdateMsgWebhook(String secretToken) {
		String hostname = hostnameService.findMainHostname();
		String webhookUrl = "https://" + hostname + UrgeNoticeUrl.ROOT + UrgeNoticeUrl.RECEIVE_URGE_NOTICE_MSG;
		log.error("Set web hook: " + webhookUrl);
		telegramService.setWebhook(TelegramBotType.URGE_NOTICE.getName(), webhookUrl, secretToken);
	}

	@Override
	public TelegramGetUpdatesDTO getMessageUpdate() {
		TelegramGetUpdatesDTO result = telegramService.getUpdateMessage(TelegramBotType.URGE_NOTICE.getName(),
				getLastUpdateMsgId());
		return result;
	}

	private Long getLastUpdateMsgId() {
		Long lastUpdateMsgId = 0L;
		String lastUpdateMsgIdStr = redisOriginalConnectService
				.getValByName(PMemoConstant.LAST_UPDATE_TELEGRAM_BOT_URGE_NOTICE_MSG_ID_KEY);
		try {
			lastUpdateMsgId = Long.parseLong(lastUpdateMsgIdStr);
		} catch (Exception e) {
		}
		return lastUpdateMsgId;
	}

	private List<TelegramUpdateMessageDTO> filterMsgUpdate(TelegramGetUpdatesDTO dto) {
		List<TelegramUpdateMessageDTO> newMsgList = new ArrayList<>();

		Long lastUpdateMsgId = getLastUpdateMsgId();

		if (!dto.getOk() || dto.getResult() == null || dto.getResult().isEmpty()) {
			return newMsgList;
		}

		for (TelegramUpdateMessageDTO msg : dto.getResult()) {
			if (msg.getUpdate_id() <= lastUpdateMsgId) {
//				TODO need white name list
				continue;
			}
			lastUpdateMsgId = msg.getUpdate_id();
			newMsgList.add(msg);
		}

		redisOriginalConnectService.setValByName(PMemoConstant.LAST_UPDATE_TELEGRAM_BOT_URGE_NOTICE_MSG_ID_KEY,
				lastUpdateMsgId.toString());

		return newMsgList;
	}

	private void msgHandle(List<TelegramUpdateMessageDTO> msgList) {
		if (msgList == null || msgList.isEmpty()) {
			return;
		}

		Map<Long, List<TelegramUpdateMessageDTO>> userMsgMap = new HashMap<>();
		List<TelegramUpdateMessageDTO> tmpMsgList = null;
		Long tmpTelegramUserId = null;
		for (TelegramUpdateMessageDTO udpateMsg : msgList) {
			tmpTelegramUserId = udpateMsg.getMessage().getFrom().getId();
			if (!userMsgMap.containsKey(tmpTelegramUserId)) {
				tmpMsgList = new ArrayList<>();
			} else {
				tmpMsgList = userMsgMap.get(tmpTelegramUserId);
			}
			tmpMsgList.add(udpateMsg);
			userMsgMap.put(tmpTelegramUserId, tmpMsgList);
		}

		for (Entry<Long, List<TelegramUpdateMessageDTO>> entry : userMsgMap.entrySet()) {
			handleUrgeNoticeUpdate(entry.getKey(), entry.getValue());
		}
	}

	private void handleUrgeNoticeUpdate(Long telegramUserId, List<TelegramUpdateMessageDTO> newMsgList) {
		/*
		 * TODO
		 */

	}

//	TODO delete notice

//	TODO clear notice

//	TODO add notice 
	private UpdateMessageResponseStoreDTO addNotice(TelegramUpdateMessageDTO newMsg, UpdateMessageResponseStoreDTO oldDTO) {
		for (int i = 0; i < oldDTO.getResult().size(); i++) {
			if (oldDTO.getResult().get(i).getUpdate_id() <= newMsg.getUpdate_id()) {
				continue;
			} else {
				oldDTO.getResult().add(newMsg);
			}
		}

		return oldDTO;
	}

//	TODO show notice 

//	TODO read file
	private UpdateMessageResponseStoreDTO readUrgeNoticeFile(Long telegramUserId) {
		String content = getUrgeNoticeFilePath(telegramUserId);
		UpdateMessageResponseStoreDTO dto = buildObjFromJsonCustomization(content, UpdateMessageResponseStoreDTO.class);
		return dto;
	}

	private String getUrgeNoticeFilePath(Long telegramUserId) {
		if (telegramUserId == null) {
			return null;
		}
		return getUrgeNoticeStorePrefixPath() + "/" + telegramUserId + ".txt";
	}

	private void rewriteUrgeNotice(Long telegramUserId, String content) {
		String pathStr = getUrgeNoticeFilePath(telegramUserId);
		if (pathStr == null) {
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