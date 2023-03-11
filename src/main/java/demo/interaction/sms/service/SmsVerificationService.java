package demo.interaction.sms.service;

import aliCloud.sms.pojo.dto.SendVerificationCodeSmsDTO;

public interface SmsVerificationService {

	void insertData(SendVerificationCodeSmsDTO dto);

	boolean verify(String code, String phone);

}
