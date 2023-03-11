package demo.interaction.sms.service;

import auxiliaryCommon.pojo.result.CommonResult;

public interface SmsSendingService {

	CommonResult sendVerificationCode(String phoneNumber);


}
