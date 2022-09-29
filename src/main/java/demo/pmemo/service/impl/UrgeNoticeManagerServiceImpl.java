package demo.pmemo.service.impl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.article.article.service.impl.ArticleCommonService;
import demo.pmemo.pojo.constant.UrgeNoticeConstant;
import demo.pmemo.pojo.constant.UrgeNoticeUrl;
import demo.pmemo.service.UrgeNoticeManagerService;
import demo.tool.telegram.service.TelegramService;
import telegram.pojo.constant.TelegramBotType;

@Service
public class UrgeNoticeManagerServiceImpl extends ArticleCommonService implements UrgeNoticeManagerService {

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
	public void setUpdateMsgWebhook() {
		String secretToken = generateNewSecretToken();
		String tokenUrlEncode = URLEncoder.encode(secretToken, StandardCharsets.UTF_8);

		String hostname = hostnameService.findMainHostname();
		String webhookUrl = "https://" + hostname + UrgeNoticeUrl.ROOT + UrgeNoticeUrl.RECEIVE_URGE_NOTICE_MSG;
		
		String webhookUrlEncode = URLEncoder.encode(webhookUrl, StandardCharsets.UTF_8);
		telegramService.setWebhook(TelegramBotType.URGE_NOTICE.getName(), webhookUrlEncode, tokenUrlEncode);
	}

	private String generateNewSecretToken() {
		String str = systemOptionService.encryptId(snowFlake.getNextId());
		str = str.replaceAll("[^a-zA-Z0-9_]", "");
		return str;
	}

	private void storeSecretToken(String secretToken) {
		redisOriginalConnectService.setValByName(UrgeNoticeConstant.URGE_NOTICE_SECRET_TOKEN_KEY, secretToken);
	}
	
	@Override
	public String getSecretToken() {
		String token = redisOriginalConnectService.getValByName(UrgeNoticeConstant.URGE_NOTICE_SECRET_TOKEN_KEY);
		if(StringUtils.isBlank(token)) {
			token = generateNewSecretToken();
			storeSecretToken(token);
		}
		return token;
	}
}