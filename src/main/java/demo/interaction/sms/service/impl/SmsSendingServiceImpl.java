package demo.interaction.sms.service.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import aliCloud.sms.pojo.constant.SmsUrl;
import aliCloud.sms.pojo.dto.SendSmsDTO;
import aliCloud.sms.pojo.dto.SendVerificationCodeSmsDTO;
import auxiliaryCommon.pojo.dto.EncryptDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.system.pojo.po.Hostname;
import demo.base.system.pojo.result.HostnameType;
import demo.common.service.ToolCommonService;
import demo.interaction.sms.mapper.SmsSendingHistoryMapper;
import demo.interaction.sms.pojo.dto.QuerySendingFrequencyDTO;
import demo.interaction.sms.pojo.po.SmsSendingHistory;
import demo.interaction.sms.pojo.result.QuerySendingFrequencyResult;
import demo.interaction.sms.service.SmsSendingService;
import demo.interaction.sms.service.SmsVerificationService;
import demo.tool.telegram.service.TelegramService;
import net.sf.json.JSONObject;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.type.TelegramBotType;
import toolPack.encryptHandle.EncryptUtil;
import toolPack.httpHandel.HttpUtil;

@Service
public class SmsSendingServiceImpl extends ToolCommonService implements SmsSendingService {

	@Autowired
	private SmsSendingHistoryMapper smsSendingHistoryMapper;
	@Autowired
	private EncryptUtil encryptUtil;
	@Autowired
	private TelegramService telegramService;
	@Autowired
	private SmsVerificationService smsVerificationService;

	private static final int MAX_NUMBER_OF_SENDS_PER_HOUR = 3;
	private static final int MAX_NUMBER_OF_SENDS_PER_DAY = 5;
	private static final int MAX_TOTAL_OF_SENDS_PER_DAY = 500;

	@Override
	public CommonResult sendVerificationCode(String phoneNumber) {
		CommonResult r = smsSendingFrequencyVerification(phoneNumber);
		if (r.isFail()) {
			return r;
		}

		r = checkSmsSendedDailyCounting();
		if (r.isFail()) {
			return r;
		}
		Integer code = ThreadLocalRandom.current().nextInt(100000, 999999);
		SendVerificationCodeSmsDTO dto = new SendVerificationCodeSmsDTO();
		dto.setVerificationCode(code.toString());
		dto.setPhoneNumber(phoneNumber);
		JSONObject telplateParamJson = new JSONObject();
		telplateParamJson.put("code", code.toString());
		dto.setTemplateCode(telplateParamJson.toString());
		dto.setTemplateCode("SMS_272605730");
		/* 
		 * TODO
		 * 未设置签名
		 */
		
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
				smsVerificationService.insertData(dto);
			}
			return r;
		} catch (Exception e) {
			r.setMessage(e.getLocalizedMessage());
			return r;
		}
	}

	private CommonResult smsSendingFrequencyVerification(String phoneNumber) {
		CommonResult r = new CommonResult();
		QuerySendingFrequencyDTO mapperDTO = new QuerySendingFrequencyDTO();
		mapperDTO.setPhoneNumber(phoneNumber);
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
	
	private CommonResult checkSmsSendedDailyCounting() {
		CommonResult r = new CommonResult();
		LocalDateTime now = LocalDateTime.now();
		int count = smsSendingHistoryMapper.querySendedCounting(now.with(LocalTime.MIN), now.with(LocalTime.MAX));
		if(count > MAX_TOTAL_OF_SENDS_PER_DAY) {
			r.setMessage("超出今日发送上限");
			return r;
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
