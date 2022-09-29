package demo.pmemo.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
import telegram.pojo.constant.TelegramBotType;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class UrgeNoticeServiceImpl extends ArticleCommonService implements UrgeNoticeService {

	/*
	 * TODO
	 * 
	 * 2. Need file max length
	 * 3. Add UrgeNoticeManagerServiceImpl.setUpdateMsgWebhook() to url 
	 */

	@Autowired
	private TelegramService telegramService;

	@Autowired
	private UrgeNoticeManagerService managerService;

	@Override
	public void receiveUpdateMsgWebhook(HttpServletRequest request, TelegramUpdateMessageDTO receiveUpdateMessage) {
		if(!systemOptionService.isDev()) {
			String inputToken = request.getHeader("X-Telegram-Bot-Api-Secret-Token");
			if (StringUtils.isBlank(inputToken) || !managerService.getSecretToken().equals(inputToken)) {
				log.error("fake msg: " + receiveUpdateMessage.toString());
				return;
			}
		}

		TelegramMessageDTO message = receiveUpdateMessage.getMessage();

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
			if(oldDTO == null) {
				oldDTO = new UpdateMessageResponseStoreDTO();
			}
			UpdateMessageResponseStoreDTO newDTO = addNotice(receiveUpdateMessage, oldDTO);
			rewriteUrgeNotice(chatId, newDTO);
			showNotice(chatId);

		} else if (UrgeNoticeBotCommandType.DELETE.equals(commandType)) {
			deleteNotice(receiveUpdateMessage);
			showNotice(chatId);
			
		} else if (UrgeNoticeBotCommandType.SHOW.equals(commandType)) {
			showNotice(chatId);
		
		} else if (UrgeNoticeBotCommandType.CLEAR.equals(commandType)) {
			UpdateMessageResponseStoreDTO newDTO = new UpdateMessageResponseStoreDTO();
			rewriteUrgeNotice(chatId, newDTO);
			showNotice(chatId);
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

	private void deleteNotice(TelegramUpdateMessageDTO receiveUpdateMessage) {
		TelegramMessageDTO message = receiveUpdateMessage.getMessage();
		Long chatId = message.getFrom().getId();
		
		String text = message.getText();
		String numberStr = text.replaceFirst(UrgeNoticeBotCommandType.DELETE.getName() + " ", "");
		Integer targetNumber = null;
		try {
			targetNumber = Integer.parseInt(numberStr);
		} catch (Exception e) {
			return;
		}
		
		UpdateMessageResponseStoreDTO oldDTO = readUrgeNoticeFile(chatId);
		if(oldDTO == null) {
			return;
		}
		
		if(targetNumber > oldDTO.getNoticeList().size()) {
			return;
		}
		
		UpdateMessageResponseStoreDTO newDTO = deleteNoticeByNumber(targetNumber, oldDTO);
		rewriteUrgeNotice(chatId, newDTO);
	}
	
	private UpdateMessageResponseStoreDTO deleteNoticeByNumber(Integer targetNumber, UpdateMessageResponseStoreDTO oldDTO) {
		List<UpdateMessageStoreDTO> noticeList = oldDTO.getNoticeList();
		for (int i = 0; i < noticeList.size(); i++) {
			if (noticeList.get(i).getOrderNumber().equals(targetNumber)) {
				noticeList.remove(i);
				oldDTO.setNoticeList(sortAndRefreshNoticeNumber(noticeList));
				return oldDTO;
			}
		}

		return oldDTO;
	}

	private UpdateMessageResponseStoreDTO addNotice(TelegramUpdateMessageDTO newMsg,
			UpdateMessageResponseStoreDTO oldDTO) {
		List<UpdateMessageStoreDTO> noticeList = null;
		
		if(oldDTO.getNoticeList() == null || oldDTO.getNoticeList().isEmpty()) {
			noticeList = new ArrayList<>();
		} else {
			noticeList = oldDTO.getNoticeList();
		}
		
		
		String text = newMsg.getMessage().getText();
		String content = text.replaceFirst(UrgeNoticeBotCommandType.ADD.getName() + " ", "");
		
		UpdateMessageStoreDTO storeMsgDTO = new UpdateMessageStoreDTO();
		BeanUtils.copyProperties(newMsg, storeMsgDTO);
		storeMsgDTO.getMessage().setText(content);
		noticeList.add(storeMsgDTO);
		oldDTO.setNoticeList(sortAndRefreshNoticeNumber(noticeList));
		return oldDTO;
	}

	private void showNotice(Long chatId) {
		UpdateMessageResponseStoreDTO oldDTO = readUrgeNoticeFile(chatId);
		if(oldDTO == null || oldDTO.getNoticeList().isEmpty()) {
			telegramService.sendMessage(TelegramBotType.URGE_NOTICE, "Empty list", chatId);
			return;
		}
		StringBuffer sb = new StringBuffer();
		for(UpdateMessageStoreDTO notice : oldDTO.getNoticeList()) {
			sb.append("" + notice.getOrderNumber() + ". " + notice.getMessage().getText() + "\n");
		}
		telegramService.sendMessage(TelegramBotType.URGE_NOTICE, sb.toString(), chatId);
	}
	
	private List<UpdateMessageStoreDTO> sortAndRefreshNoticeNumber(List<UpdateMessageStoreDTO> noticeList) {
		Collections.sort(noticeList);
		UpdateMessageStoreDTO tmpDTO = null;
		for (int i = 0; i < noticeList.size(); i++) {
			tmpDTO = noticeList.get(i);
			tmpDTO.setOrderNumber(i + 1);
			noticeList.set(i, tmpDTO);
		}
		return noticeList;
	}

	private UpdateMessageResponseStoreDTO readUrgeNoticeFile(Long telegramUserId) {
		String filePath = getUrgeNoticeFilePath(telegramUserId);
		File file = new File(filePath);
		if(!file.exists()) {
			return new UpdateMessageResponseStoreDTO();
		}
		FileUtilCustom io = new FileUtilCustom();
		String content = io.getStringFromFile(filePath);
		UpdateMessageResponseStoreDTO dto = null;
		try {
			dto = buildObjFromJsonCustomization(content, UpdateMessageResponseStoreDTO.class);
		} catch (Exception e) {
			dto = new UpdateMessageResponseStoreDTO();
		}
		return dto;
	}

	private String getUrgeNoticeFilePath(Long telegramUserId) {
		if (telegramUserId == null) {
			return null;
		}
		return getUrgeNoticeStorePrefixPath() + "/" + telegramUserId + ".txt";
	}

	private void rewriteUrgeNotice(Long telegramUserId, UpdateMessageResponseStoreDTO dto) {
		String pathStr = getUrgeNoticeFilePath(telegramUserId);
		if (pathStr == null) {
			return;
		}
		
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, localDateTimeAdapter).setPrettyPrinting().create();
		String jsonString = gson.toJson(dto);
		
		FileUtilCustom io = new FileUtilCustom();
		io.byteToFile(jsonString.getBytes(), pathStr);
	}

	private String getUrgeNoticeStorePrefixPath() {
		if (isWindows()) {
			return "d:" + UrgeNoticeConstant.URGE_NOTE_SAVING_FOLDER;
		} else {
			return UrgeNoticeConstant.URGE_NOTE_SAVING_FOLDER;
		}
	}
}