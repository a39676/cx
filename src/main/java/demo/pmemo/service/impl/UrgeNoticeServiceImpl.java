package demo.pmemo.service.impl;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.article.article.service.impl.ArticleCommonService;
import demo.pmemo.pojo.constant.UrgeNoticeConstant;
import demo.pmemo.pojo.dto.UpdateMessageResponseStoreDTO;
import demo.pmemo.pojo.dto.UpdateMessageStoreDTO;
import demo.pmemo.pojo.type.UrgeNoticeBotCommandType;
import demo.pmemo.service.UrgeNoticeManagerService;
import demo.pmemo.service.UrgeNoticeService;
import demo.tool.telegram.pojo.dto.TelegramUpdateMessageDTO;
import demo.tool.telegram.pojo.dto.telegramDTO.TelegramMessageDTO;
import demo.tool.telegram.service.TelegramService;
import net.sf.json.JSONObject;
import telegram.pojo.constant.TelegramBotType;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class UrgeNoticeServiceImpl extends ArticleCommonService implements UrgeNoticeService {

	/*
	 * TODO
	 * 
	 * 2. Need file max length
	 */

	@Autowired
	private TelegramService telegramService;

	@Autowired
	private UrgeNoticeManagerService managerService;

	@Override
	public void receiveUpdateMsgWebhook(HttpServletRequest request, TelegramUpdateMessageDTO unknowContent) {
		String inputToken = request.getHeader("X-Telegram-Bot-Api-Secret-Token");
		if (StringUtils.isBlank(inputToken) || !managerService.getSecretToken().equals(inputToken)) {
			log.error("fake msg: " + unknowContent.toString());
			return;
		}

		TelegramMessageDTO message = unknowContent.getMessage();

		UrgeNoticeBotCommandType commandType = findBotCommand(message);
		if (commandType == null) {
			return;
		}

		Long chatId = message.getFrom().getId();
		if (!telegramService.hasThisChatId(chatId)) {
			return;
		}

		if (UrgeNoticeBotCommandType.ADD.equals(commandType)) {
			UpdateMessageResponseStoreDTO oldDTO = readUrgeNoticeFile(chatId);
			UpdateMessageResponseStoreDTO newDTO = addNotice(unknowContent, oldDTO);
			JSONObject json = JSONObject.fromObject(newDTO);
			rewriteUrgeNotice(chatId, json.toString());

		} else if (UrgeNoticeBotCommandType.DELETE.equals(commandType)) {
			String text = message.getText();
			String numberStr = text.replaceFirst(UrgeNoticeBotCommandType.DELETE.getName() + " ", "");
			try {
				Integer number = Integer.parseInt(numberStr);
				UpdateMessageResponseStoreDTO oldDTO = readUrgeNoticeFile(chatId);
				UpdateMessageResponseStoreDTO newDTO = deleteNotice(number, oldDTO);
				JSONObject json = JSONObject.fromObject(newDTO);
				rewriteUrgeNotice(chatId, json.toString());
			} catch (Exception e) {
			}
			
		} else if (UrgeNoticeBotCommandType.SHOW.equals(commandType)) {
			UpdateMessageResponseStoreDTO oldDTO = readUrgeNoticeFile(chatId);
			StringBuffer sb = new StringBuffer();
			for(UpdateMessageStoreDTO notice : oldDTO.getNoticeList()) {
				sb.append("" + notice.getOrderNumber() + ". " + notice.getMessage().getText() + "\n");
			}
			telegramService.sendMessage(TelegramBotType.URGE_NOTICE, sb.toString(), chatId);
		
		} else if (UrgeNoticeBotCommandType.CLEAR.equals(commandType)) {
			UpdateMessageResponseStoreDTO newDTO = new UpdateMessageResponseStoreDTO();
			JSONObject json = JSONObject.fromObject(newDTO);
			rewriteUrgeNotice(chatId, json.toString());
		}
	}

	private UrgeNoticeBotCommandType findBotCommand(TelegramMessageDTO message) {
		if (message.getEntities() == null) {
			return null;
		}

		String text = message.getText();
		if (StringUtils.isBlank(text)) {
			return null;
		}

		for (int i = 0; i < UrgeNoticeBotCommandType.values().length; i++) {
			if (text.startsWith(UrgeNoticeBotCommandType.values()[i].getName())) {
				return UrgeNoticeBotCommandType.values()[i];
			}
		}
		return null;
	}

	private UpdateMessageResponseStoreDTO deleteNotice(Integer targetNumber, UpdateMessageResponseStoreDTO oldDTO) {
		List<UpdateMessageStoreDTO> noticeList = oldDTO.getNoticeList();
		for (int i = 0; i < noticeList.size(); i++) {
			if (noticeList.get(i).getOrderNumber().equals(targetNumber)) {
				noticeList.remove(i);
				oldDTO.setNoticeList(noticeList);
				return oldDTO;
			}
		}

		return oldDTO;
	}

	private UpdateMessageResponseStoreDTO addNotice(TelegramUpdateMessageDTO newMsg,
			UpdateMessageResponseStoreDTO oldDTO) {
		for (int i = 0; i < oldDTO.getNoticeList().size(); i++) {
			if (oldDTO.getNoticeList().get(i).getUpdate_id() <= newMsg.getUpdate_id()) {
				continue;
			}
			UpdateMessageStoreDTO storeMsgDTO = new UpdateMessageStoreDTO();
			BeanUtils.copyProperties(newMsg, storeMsgDTO);
			List<UpdateMessageStoreDTO> noticeList = oldDTO.getNoticeList();
			noticeList.add(storeMsgDTO);

			oldDTO.setNoticeList(sortAndRefreshNoticeNumber(noticeList));
		}

		return oldDTO;
	}

	private List<UpdateMessageStoreDTO> sortAndRefreshNoticeNumber(List<UpdateMessageStoreDTO> noticeList) {
		Collections.sort(noticeList);
		for (int i = 1; i < noticeList.size(); i++) {
			noticeList.get(i).setOrderNumber(i);
		}
		return noticeList;
	}

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
			return "d:" + UrgeNoticeConstant.URGE_NOTE_SAVING_FOLDER;
		} else {
			return UrgeNoticeConstant.URGE_NOTE_SAVING_FOLDER;
		}
	}
}