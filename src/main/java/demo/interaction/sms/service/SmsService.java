package demo.interaction.sms.service;

import aliCloud.sms.pojo.dto.SendSmsDTO;
import auxiliaryCommon.pojo.result.CommonResult;

public interface SmsService {

	CommonResult sendVerificationCode(SendSmsDTO dto);

}
