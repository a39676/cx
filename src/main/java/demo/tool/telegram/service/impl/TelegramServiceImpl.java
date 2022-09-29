package demo.tool.telegram.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.service.impl.ToolCommonService;
import demo.tool.telegram.mapper.TelegramChatIdMapper;
import demo.tool.telegram.mapper.TelegramConstantMapper;
import demo.tool.telegram.pojo.bo.TelegramConstantBO;
import demo.tool.telegram.pojo.dto.TelegramGetUpdatesDTO;
import demo.tool.telegram.pojo.po.TelegramChatId;
import demo.tool.telegram.pojo.po.TelegramChatIdExample;
import demo.tool.telegram.pojo.po.TelegramConstant;
import demo.tool.telegram.pojo.po.TelegramConstantExample;
import demo.tool.telegram.pojo.vo.TelegramChatIdVO;
import demo.tool.telegram.service.TelegramService;
import telegram.pojo.constant.TelegramBotType;
import telegram.pojo.constant.TelegramStaticChatID;
import toolPack.httpHandel.HttpUtil;

@Service
public class TelegramServiceImpl extends ToolCommonService implements TelegramService {

	@Autowired
	private TelegramConstantMapper telegramConstantMapper;
	@Autowired
	private TelegramChatIdMapper chatIdMapper;
	@Autowired
	private TelegramOptionService telegramOptionService;

	private String botIDReady(String botIDKey) {
		if (StringUtils.isBlank(botIDKey)) {
			botIDKey = TelegramBotType.BOT_1.getName();
		}
		TelegramConstantBO botConstant = telegramOptionService.getTelegramConstantMap().get(botIDKey);

		if (botConstant != null) {
			return botConstant.getConstantvalue();
		}

		return botIDReset(botIDKey);
	}

	private String botIDReset(String botIDKey) {
		String bot1ID = null;

		TelegramConstantExample example = new TelegramConstantExample();
		example.createCriteria().andIsdeleteEqualTo(false).andConstantnameEqualTo(botIDKey);
		List<TelegramConstant> poList = telegramConstantMapper.selectByExample(example);

		if (poList != null && !poList.isEmpty()) {
			TelegramConstantBO bo = null;
			for (TelegramConstant po : poList) {
				bo = new TelegramConstantBO();
				BeanUtils.copyProperties(po, bo);
				telegramOptionService.putTelegramConstantMap(po.getConstantname(), bo);
			}
			bot1ID = poList.get(0).getConstantvalue();
		}

		return bot1ID;
	}

	@Override
	public CommonResult sendMessage(String msg, Long id) {
		return sendMessage(null, msg, id);
	}

	@Override
	public CommonResult sendMessage(TelegramBotType botType, String msg, Long id) {
		CommonResult r = new CommonResult();

		if (systemConstantService.isDev()) {
			log.error(msg);
			r.setIsSuccess();
			return r;
		}

		if (id == null) {
			r.failWithMessage("param error");
			return r;
		}

		if (StringUtils.isBlank(msg)) {
			r.failWithMessage("null msg");
			return r;
		}

		try {
			msg = URLEncoder.encode(msg, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e1) {
			msg = "msg trans error";
		}
		if (msg.length() > 512) {
			r.failWithMessage("msg too long");
			return r;
		}

		TelegramChatId po = chatIdMapper.selectByPrimaryKey(id);
		if (po == null) {
			r.failWithMessage("param error");
			return r;
		}

		if (botType == null) {
			botType = TelegramBotType.BOT_1;
		}
		String botID = botIDReady(botType.getName());
		if (StringUtils.isBlank(botID)) {
			r.failWithMessage("please set bot ID");
			return r;
		}

		String urlModel = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
		String url = String.format(urlModel, botID, po.getChatId(), msg);

		HttpUtil httpUtil = new HttpUtil();
		try {
			httpUtil.sendGet(url);
		} catch (Exception e) {
			log.error("telegram sending error: " + e.getMessage());
			log.error("telegram NOT sending message: " + msg);
			r.failWithMessage("net work error");
			return r;
		}

		r.normalSuccess();
		return r;
	}

	@Override
	public List<TelegramChatId> getChatIDList() {
		TelegramChatIdExample example = new TelegramChatIdExample();
		example.createCriteria().andIsdeleteEqualTo(false);
		List<TelegramChatId> poList = chatIdMapper.selectByExample(example);
		return poList;
	}

	@Override
	public TelegramChatId getChatID(Long id) {
		return chatIdMapper.selectByPrimaryKey(id);
	}

	@Override
	public TelegramChatIdVO buildChatIdVO(TelegramChatId po) {
		TelegramChatIdVO vo = new TelegramChatIdVO();
		vo.setPk(systemConstantService.encryptId(po.getId().longValue()));
		vo.setUsername(po.getChatUserName());
		return vo;
	}

	@Override
	public boolean chatIdExists(String pk) {
		Long id = systemConstantService.decryptPrivateKey(pk);
		if (id == null) {
			return false;
		}
		TelegramChatId po = chatIdMapper.selectByPrimaryKey(id);
		return po != null;
	}

	@Override
	public void telegramSendingCheck() {
		String msg = null;
		for (TelegramBotType botType : TelegramBotType.values()) {
			msg = "Testing msg, from: " + botType.getName();
			sendMessage(botType, msg, TelegramStaticChatID.MY_ID);
		}
	}

	@Override
	public TelegramGetUpdatesDTO getUpdateMessage(String botIDKey, Long lastUpdateMsgId) {
		TelegramGetUpdatesDTO dto = null;
		
		if(StringUtils.isBlank(botIDKey)) {
			return dto;
		}
		
		String urlModel = "https://api.telegram.org/bot%s/getUpdates?offset=%d";
		String botID = botIDReady(botIDKey);
		String url = String.format(urlModel, botID, lastUpdateMsgId);

		HttpUtil httpUtil = new HttpUtil();
		try {
			String response = httpUtil.sendGet(url);
			
			dto = buildObjFromJsonCustomization(response, TelegramGetUpdatesDTO.class);
			
		} catch (Exception e) {
			log.error("Get message update from telegram bot: " + botIDKey + ", error: " + e.getMessage());
		}
		return dto;
	}
	
	@Override
	public TelegramGetUpdatesDTO getUpdateMessage(String botIDKey) {
		return getUpdateMessage(botIDKey, 0L);
	}
	
	@Override
	public void setWebhook(String botIDKey,String webhookUrl, String secretToken) {
		
		if(StringUtils.isBlank(botIDKey)) {
			return ;
		}
		
		String urlModel = "https://api.telegram.org/bot%s/setWebhook?url=%s&secret_token=%s";
		String botID = botIDReady(botIDKey);
		String url = String.format(urlModel, botID, webhookUrl, secretToken);

		HttpUtil httpUtil = new HttpUtil();
		try {
			String response = httpUtil.sendGet(url);
			
			log.error("set web hook response: " + response);
			
		} catch (Exception e) {
			log.error("Telegram bot: " + botIDKey + ", set web hook error: " + e.getMessage());
		}
		return;
	}
}
