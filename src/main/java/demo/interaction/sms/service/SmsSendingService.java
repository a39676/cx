package demo.interaction.sms.service;

import aliCloud.sms.pojo.dto.SendVerificationCodeSmsDTO;
import auxiliaryCommon.pojo.result.CommonResult;

public interface SmsSendingService {

	CommonResult sendVerificationCode(SendVerificationCodeSmsDTO dto);

}
