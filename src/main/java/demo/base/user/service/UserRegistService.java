package demo.base.user.service;

import javax.servlet.http.HttpServletRequest;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.user.pojo.dto.StudentRegistDTO;
import demo.base.user.pojo.dto.UserRegistDTO;
import demo.base.user.pojo.result.NewUserRegistResult;
import demo.base.user.pojo.result.__baseSuperAdminRegistResult;

public interface UserRegistService {

	NewUserRegistResult newUserRegist(UserRegistDTO param, String ip, HttpServletRequest request);

	NewUserRegistResult newStudentRegist(StudentRegistDTO registDTO, String ip, HttpServletRequest request);

	CommonResult modifyRegistEmail(Long userId, String email);

	CommonResult sendForgotUsernameMail(String email, HttpServletRequest request);

	CommonResult sendForgotPasswordMail(String email, HttpServletRequest request);

	CommonResult resetPasswordByMailKey(String mailKey, String newPassword, String newPasswordRepeat);

	CommonResult resetPasswordByLoginUser(Long userId, String oldPassword, String newPassword,
			String newPasswordRepeat);

	boolean isUserExists(String userName);

	CommonResult registActivation(String mailKey);

	__baseSuperAdminRegistResult __baseSuperAdminRegist();

	CommonResult resendRegistMail(Long userId, HttpServletRequest request);

}
