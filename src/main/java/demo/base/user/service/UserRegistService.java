package demo.base.user.service;

import demo.base.user.pojo.dto.UserRegistDTO;
import demo.base.user.pojo.vo.__baseSuperAdminRegistVO;
import demo.baseCommon.pojo.result.CommonResultCX;

public interface UserRegistService {

	CommonResultCX newUserRegist(UserRegistDTO param, String ip);

	CommonResultCX modifyRegistEmail(Long userId, String email);

	CommonResultCX sendForgotPasswordMail(String email, String hostName);

	CommonResultCX sendForgotUsernameMail(String email, String hostName);

	CommonResultCX resetPasswordByMailKey(String mailKey, String newPassword, String newPasswordRepeat);

	CommonResultCX resetPasswordByLoginUser(Long userId, String oldPassword, String newPassword,
			String newPasswordRepeat);

	boolean isUserExists(String userName);

	CommonResultCX registActivation(String mailKey, String activeEMail);

	void handleMails();

	__baseSuperAdminRegistVO __baseSuperAdminRegist();

//	CommonResult resendRegistMail(Long userId); //2018-06-28 暂停向注册用户发送激活邮件,改由用户发回激活码.
}
