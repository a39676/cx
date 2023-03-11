package demo.interaction.sms.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aliCloud.sms.pojo.dto.SendVerificationCodeSmsDTO;
import demo.base.system.service.impl.RedisOriginalConnectService;
import demo.common.service.CommonService;
import demo.interaction.sms.service.SmsVerificationService;

@Service
public class SmsVerificationServiceImpl extends CommonService implements SmsVerificationService {

	@Autowired
	private RedisOriginalConnectService redisConnectService;
	
	@Override
	public void insertData(SendVerificationCodeSmsDTO dto) {
		redisConnectService.setValByName(dto.getPhoneNumber(), dto.getVerificationCode(), 5, TimeUnit.MINUTES);
	}
	
	@Override
	public boolean verify(String code, String phone) {
		String codeInCache = redisConnectService.getValByName(phone);
		
		Boolean flag = codeInCache.equals(code);
		if(flag) {
			redisConnectService.deleteValByName(phone);
		}
		return flag;
	}
	
}
