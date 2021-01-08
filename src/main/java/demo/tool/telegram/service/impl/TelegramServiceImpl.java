package demo.tool.telegram.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.system.pojo.bo.SystemConstant;
import demo.common.service.CommonService;
import demo.tool.telegram.mapper.TelegramChatIdMapper;
import demo.tool.telegram.mapper.TelegramConstantMapper;
import demo.tool.telegram.pojo.constant.TelegramKeys;
import demo.tool.telegram.pojo.po.TelegramChatId;
import demo.tool.telegram.pojo.po.TelegramChatIdExample;
import demo.tool.telegram.pojo.po.TelegramConstant;
import demo.tool.telegram.pojo.po.TelegramConstantExample;
import demo.tool.telegram.pojo.vo.TelegramChatIdVO;
import demo.tool.telegram.service.TelegramService;
import toolPack.httpHandel.HttpUtil;

@Service
public class TelegramServiceImpl extends CommonService implements TelegramService {

	@Autowired
	private TelegramConstantMapper telegramConstantMapper; 
	@Autowired
	private TelegramChatIdMapper chatIdMapper;
	
	private String botIDReady() {
		return botIDReady(null);
	}
	
	private String botIDReady(String botIDKey) {
		if(botIDKey == null) {
			botIDKey = TelegramKeys.BOT1_ID_KEY;
		}
		String botID = constantService.getValByName(botIDKey);
		if(StringUtils.isNotBlank(botID)) {
			return botID;
		}
		return botIDReset(botIDKey);
	}
	
	private String botIDReset(String botIDKey) {
		if(botIDKey == null) {
			botIDKey = TelegramKeys.BOT1_ID_KEY;
		}
		String bot1ID = null;
		try {
			TelegramConstantExample example = new TelegramConstantExample();
			example.createCriteria().andIsdeleteEqualTo(false).andConstantnameEqualTo(botIDKey);
			List<TelegramConstant> poList = telegramConstantMapper.selectByExample(example);
			
			bot1ID = poList.get(0).getConstantvalue(); 
			SystemConstant systemConstant = new SystemConstant();
			systemConstant.setConstantName(botIDKey);
			systemConstant.setConstantValue(bot1ID);
			constantService.setValByName(systemConstant);
		} catch (Exception e) {
		}
		
		return bot1ID;
	}
	
	@Override
	public CommonResult sendMessage(String msg, String chatPK) {
		CommonResult r = new CommonResult();
		
		if(StringUtils.isAnyBlank(msg, chatPK)) {
			r.failWithMessage("param error");
			return r;
		}
		
		try {
			msg = URLEncoder.encode(msg, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e1) {
			msg = "msg trans error";
		}
		if(msg.length() > 512) {
			r.failWithMessage("msg too long");
			return r;
		}
		
		Long chatPOId = decryptPrivateKey(chatPK);
		if(chatPOId == null) {
			r.failWithMessage("chatPK error");
			return r;
		}
		
		TelegramChatId po = chatIdMapper.selectByPrimaryKey(chatPOId);
		if(po == null) {
			r.failWithMessage("chatPK error");
			return r;
		}
		
		String botID = botIDReady();
		if(StringUtils.isBlank(botID)) {
			r.failWithMessage("please set bot ID");
			return r;
		}
		
		String urlModel = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
		String url = String.format(urlModel, botID, po.getChatId(), msg);
		
		HttpUtil httpUtil = new HttpUtil();
		try {
			httpUtil.sendGet(url);
		} catch (Exception e) {
			log.error("telegram sending error" + e.getLocalizedMessage());
			log.error("telegram sending error" + e.getMessage());
			r.failWithMessage("net work error");
			return r;
		}
		
		r.normalSuccess();
		return r;
	}

	@Override
	public List<TelegramChatIdVO> getChatIDList() {
		TelegramChatIdExample example = new TelegramChatIdExample();
		example.createCriteria().andIsdeleteEqualTo(false);
		List<TelegramChatId> poList = chatIdMapper.selectByExample(example);
		List<TelegramChatIdVO> voList = new ArrayList<>();
		for(TelegramChatId po: poList) {
			voList.add(buildChatIdVO(po));
		}
		return voList;
	}
	
	private TelegramChatIdVO buildChatIdVO(TelegramChatId po) {
		TelegramChatIdVO vo = new TelegramChatIdVO();
		vo.setPk(encryptId(po.getId().longValue()));
//		try {
//			vo.setPk(URLEncoder.encode(encryptId(po.getId()), StandardCharsets.UTF_8.toString()));
//		} catch (UnsupportedEncodingException e) {
//		}
		vo.setUsername(po.getChatUserName());
		return vo;
	}
	
	@Override
	public boolean chatIdExists(String pk) {
		Long id = decryptPrivateKey(pk);
		if(id == null) {
			return false;
		}
		TelegramChatId po = chatIdMapper.selectByPrimaryKey(id);
		return po != null;
	}
	
}
