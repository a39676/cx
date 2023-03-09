package demo.interaction.sms.service.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import aliCloud.sms.pojo.constant.SmsUrl;
import aliCloud.sms.pojo.dto.SendSmsDTO;
import auxiliaryCommon.pojo.dto.EncryptDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.system.pojo.po.Hostname;
import demo.base.system.pojo.result.HostnameType;
import demo.base.system.service.HostnameService;
import demo.base.system.service.impl.SystemOptionService;
import demo.common.service.CommonService;
import demo.interaction.sms.mapper.SmsSendingHistoryMapper;
import demo.interaction.sms.pojo.dto.QuerySendingFrequencyDTO;
import demo.interaction.sms.pojo.po.SmsSendingHistory;
import demo.interaction.sms.pojo.result.QuerySendingFrequencyResult;
import demo.interaction.sms.service.SmsService;
import demo.tool.telegram.service.TelegramService;
import net.sf.json.JSONObject;
import telegram.pojo.constant.TelegramBotType;
import telegram.pojo.constant.TelegramStaticChatID;
import toolPack.encryptHandle.EncryptUtil;
import toolPack.httpHandel.HttpUtil;

@Service
public class SmsServiceImpl extends CommonService implements SmsService {

	@Autowired
	private SmsSendingHistoryMapper smsSendingHistoryMapper;
	@Autowired
	private EncryptUtil encryptUtil;
	@Autowired
	private HostnameService hostnameService;
	@Autowired
	private SystemOptionService systemOptionService;
	@Autowired
	private TelegramService telegramService;

	private static final int MAX_NUMBER_OF_SENDS_PER_HOUR = 3;
	private static final int MAX_NUMBER_OF_SENDS_PER_DAY = 5;

	@Override
	public CommonResult sendVerificationCode(SendSmsDTO dto) {
		CommonResult r = smsSendingFrequencyVerification(dto);

		if (r.isFail()) {
			return r;
		}

		JSONObject json = JSONObject.fromObject(dto);
		try {
			String encryptStr = encryptUtil.aesEncrypt(systemOptionService.getAesKey(),
					systemOptionService.getAesInitVector(), json.toString());
			EncryptDTO parameterDTO = new EncryptDTO();
			parameterDTO.setEncryptedStr(encryptStr);
			json = JSONObject.fromObject(parameterDTO);

			Hostname hostname = hostnameService.findHostname(HostnameType.fdj);
			HttpUtil util = new HttpUtil();
			String urlStr = hostname.getHostname() + SmsUrl.ROOT + SmsUrl.SEND_VERIFICATION_CODE;

			String response = util.sendPost(urlStr, json.toString());
			r = new Gson().fromJson(response, CommonResult.class);

			if (r.isSuccess()) {
				CommonResult insertResult = insertSendingHistory(dto);
				if (insertResult.isFail()) {
					telegramService.sendMessageByChatRecordId(TelegramBotType.CX_MESSAGE,
							"Can NOT insert SMS sending history of: " + dto.toString(), TelegramStaticChatID.MY_ID);
				}
			}
			return r;
		} catch (Exception e) {
			r.setMessage(e.getLocalizedMessage());
			return r;
		}
	}

	private CommonResult smsSendingFrequencyVerification(SendSmsDTO dto) {
		CommonResult r = new CommonResult();
		QuerySendingFrequencyDTO mapperDTO = new QuerySendingFrequencyDTO();
		mapperDTO.setPhoneNumber(dto.getPhoneNumber());
		LocalDateTime now = LocalDateTime.now();
		mapperDTO.setDailyStartTime(now.with(LocalTime.MIN));
		mapperDTO.setDailyEndTime(now.with(LocalTime.MAX));
		mapperDTO.setHourlyStartTime(now.minusHours(1));
		mapperDTO.setHourlyEndTime(now);
		QuerySendingFrequencyResult result = smsSendingHistoryMapper.querySendingFrequency(mapperDTO);

		if (result.getHourlyCounting() > MAX_NUMBER_OF_SENDS_PER_HOUR
				|| result.getDailyCounting() > MAX_NUMBER_OF_SENDS_PER_DAY) {
			r.setMessage("超出发送次数上限, 请稍后再试");
		}

		r.setIsSuccess();
		return r;
	}

	private CommonResult insertSendingHistory(SendSmsDTO dto) {
		CommonResult r = new CommonResult();
		SmsSendingHistory po = new SmsSendingHistory();
		po.setId(snowFlake.getNextId());
		po.setPhoneNumber(dto.getPhoneNumber());
		po.setSmsContent("SignName:" + dto.getSignName() + ", TemplateCode:" + dto.getTemplateCode()
				+ ", TelplateParamJsonStr:" + dto.getTelplateParamJsonStr());
		int count = smsSendingHistoryMapper.insertSelective(po);
		if (count == 1) {
			r.setIsSuccess();
		}
		return r;
	}
}
