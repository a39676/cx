package demo.tool.telegram.service;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.telegram.pojo.vo.TelegramChatIdVO;

public interface TelegramService {

	CommonResult sendMessage(String msg, String chatID);

	List<TelegramChatIdVO> getChatIDList();

	boolean chatIdExists(String pk);

}
