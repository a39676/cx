package demo.base.user.service;

import javax.servlet.http.HttpServletRequest;

import demo.base.user.pojo.dto.UserRegistDTO;
import demo.base.user.pojo.vo.__baseSuperAdminRegistVO;
import demo.baseCommon.pojo.result.CommonResultCX;

public interface UserRegistService {

	CommonResultCX newUserRegist(UserRegistDTO param, String ip, HttpServletRequest request);

	CommonResultCX modifyRegistEmail(Long userId, String email);

	CommonResultCX sendForgotUsernameMail(String email, HttpServletRequest request);

	CommonResultCX sendForgotPasswordMail(String email, HttpServletRequest request);

	CommonResultCX resetPasswordByMailKey(String mailKey, String newPassword, String newPasswordRepeat);

	CommonResultCX resetPasswordByLoginUser(Long userId, String oldPassword, String newPassword,
			String newPasswordRepeat);

	boolean isUserExists(String userName);

	CommonResultCX registActivation(String mailKey, String activeEMail);

	__baseSuperAdminRegistVO __baseSuperAdminRegist();

	CommonResultCX resendRegistMail(Long userId); //2018-06-28 暂停向注册用户发送激活邮件,改由用户发回激活码.

	
}
