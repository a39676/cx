package demo.base.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.user.pojo.constant.UserRegistView;
import demo.base.user.pojo.constant.UsersUrl;
import demo.base.user.pojo.dto.ForgotPasswordDTO;
import demo.base.user.pojo.dto.ForgotUsernameDTO;
import demo.base.user.pojo.dto.ModifyRegistMailDTO;
import demo.base.user.pojo.dto.ResetPasswordDTO;
import demo.base.user.pojo.dto.UserNameExistCheckDTO;
import demo.base.user.pojo.dto.UserRegistDTO;
import demo.base.user.pojo.result.NewUserRegistResult;
import demo.base.user.pojo.type.SystemRolesType;
import demo.base.user.service.UserRegistService;
import demo.common.controller.CommonController;
import demo.common.pojo.result.CommonResultCX;
import demo.common.pojo.type.ResultTypeCX;
import demo.config.costom_component.BaseUtilCustom;
import io.swagger.v3.oas.annotations.Operation;

@Controller
@RequestMapping(value = UsersUrl.root)
public class UsersRegistController extends CommonController {

	@Autowired
	private UserRegistService userRegistService;
	@Autowired
	private BaseUtilCustom baseUtilCustom;

	@GetMapping(value = { UsersUrl.userNameExistCheck })
	@ResponseBody
	public CommonResult userNameExistCheck(@RequestBody UserNameExistCheckDTO dto) {
		CommonResult r = new CommonResult();

		boolean checkExistResult = userRegistService.isUserExists(dto.getUserName());
		if (!checkExistResult) {
			r.addMessage("user name not exist");
		}

		return r;
	}

	@PostMapping(value = UsersUrl.modifyRegistMail)
	@ResponseBody
	public CommonResultCX modifyRegistMail(@RequestBody ModifyRegistMailDTO dto) {

		CommonResultCX result = new CommonResultCX();
		if (!baseUtilCustom.isLoginUser() || StringUtils.isBlank(dto.getModifyRegistMail())) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}

		result = userRegistService.modifyRegistEmail(baseUtilCustom.getUserId(), dto.getModifyRegistMail());
		if (result.isSuccess()) {
			result.successWithMessage("邮件已发送,因网络原因,可能存在延迟,请稍后至邮箱查收.请留意邮箱拦截规则,如果邮件被拦截,可能存放于邮箱垃圾箱内...");
		}
		return result;
	}

	@Operation(summary = "创建用户页面", description = "返回创建用户页面")
	@GetMapping(value = UsersUrl.userRegist)
	public ModelAndView userRegistView(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		view.setViewName(UserRegistView.userRegist);
		view.addObject("title", "Sign up");
		return view;
	}

	@Operation(summary = "用户注册", description = "用户注册请求")
	@PostMapping(value = UsersUrl.userRegist)
	@ResponseBody
	public NewUserRegistResult userRegistHandler(@RequestBody UserRegistDTO dto, HttpServletRequest request) {
		String ip = request.getHeader("X-FORWARDED-FOR");

		return userRegistService.newUserRegist(dto, ip, request);
	}

	@GetMapping(value = UsersUrl.registActivation)
	public ModelAndView registActivation(@RequestParam(value = "mailKey", defaultValue = "") String mailKey) {
		CommonResultCX serviceResult = userRegistService.registActivation(mailKey);
		ModelAndView view = new ModelAndView(UserRegistView.userRegistActivationResult);
		view.addObject("message", serviceResult.getMessage());
		return view;
	}

	@PostMapping(value = UsersUrl.resendRegistMail)
	@ResponseBody
	public CommonResultCX resendRegistMail(HttpServletRequest request) {
		CommonResultCX result = new CommonResultCX();
		if (!baseUtilCustom.isLoginUser()
				|| baseUtilCustom.getRoles().contains(SystemRolesType.ROLE_USER_ACTIVE.getName())) {
			result.fillWithResult(ResultTypeCX.notLoginUser);
			return result;
		}
		Long userId = baseUtilCustom.getUserId();
		result = userRegistService.resendRegistMail(userId, request);
		if (!result.getResult().equals(ResultTypeCX.success.getCode())) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		result.successWithMessage("邮件已发送,因网络原因,可能存在延迟,请稍后至邮箱查收.请留意邮箱拦截规则,如果邮件被拦截,可能存放于邮箱垃圾箱内...");
		return result;
	}

	@GetMapping(value = UsersUrl.forgotPasswordOrUsername)
	public ModelAndView forgotPasswordOrUsername() {
		return new ModelAndView(UserRegistView.forgotPasswordOrUsername);
	}

	@PostMapping(value = UsersUrl.forgotPassword)
	@ResponseBody
	public CommonResultCX forgotPassword(@RequestBody ForgotPasswordDTO dto, HttpServletRequest request) {
		CommonResultCX result = new CommonResultCX();

		if (StringUtils.isBlank(dto.getEmail())) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}

		result = userRegistService.sendForgotPasswordMail(dto.getEmail(), request);
		if (result.isSuccess()) {
			result.successWithMessage("邮件已发送,因网络原因,可能存在延迟,请稍后至邮箱查收.请留意邮箱拦截规则,如果邮件被拦截,可能存放于邮箱垃圾箱内...");
			return result;
		}

		return result;
	}

	@PostMapping(value = UsersUrl.forgotUsername)
	@ResponseBody
	public CommonResultCX forgotUsername(@RequestBody ForgotUsernameDTO dto, HttpServletRequest request) {
		CommonResultCX result = new CommonResultCX();

		if (StringUtils.isBlank(dto.getEmail())) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}

		result = userRegistService.sendForgotUsernameMail(dto.getEmail(), request);
		if (result.isSuccess()) {
			result.successWithMessage("邮件已发送,因网络原因,可能存在延迟,请稍后至邮箱查收.请留意邮箱拦截规则,如果邮件被拦截,可能存放于邮箱垃圾箱内...");
			return result;
		}

		return result;
	}

	@GetMapping(value = UsersUrl.resetPassword)
	public ModelAndView resetPassword(@RequestParam(value = "mailKey", defaultValue = "") String mailKey,
			RedirectAttributes redirectAttr) {
		/**
		 * 仅接受通过重置密码邮件url的访问, 用户登录后重设密码不经过此处
		 */
		ModelAndView view = new ModelAndView("userJSP/resetPassword");

		if (StringUtils.isBlank(mailKey)) {
			view.addObject("errorMessage", ResultTypeCX.errorParam.getName());
			return view;
		}

		view.addObject("mailKey", mailKey);

		return view;
	}

	@PostMapping(value = UsersUrl.resetPassword)
	@ResponseBody
	public CommonResultCX resetPassword(@RequestBody ResetPasswordDTO dto) {
		CommonResultCX r = new CommonResultCX();

		if (StringUtils.isAnyBlank(dto.getNewPassword(), dto.getNewPasswordRepeat())) {
			r.fillWithResult(ResultTypeCX.nullParam);
			return r;
		}

		if (StringUtils.isNotBlank(dto.getMailKey())) {
			r = userRegistService.resetPasswordByMailKey(dto.getMailKey(), dto.getNewPassword(),
					dto.getNewPasswordRepeat());
		} else if (baseUtilCustom.isLoginUser()) {
			if (StringUtils.isBlank(dto.getOldPassword())) {
				r.fillWithResult(ResultTypeCX.nullParam);
				return r;
			}
			r = userRegistService.resetPasswordByLoginUser(baseUtilCustom.getUserId(), dto.getOldPassword(),
					dto.getNewPassword(), dto.getNewPasswordRepeat());
		} else {
			r.successWithMessage("");
			return r;
		}
		return r;
	}

}
