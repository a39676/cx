package demo.base.user.controller;



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
import demo.base.user.pojo.dto.StudentRegistDTO;
import demo.base.user.pojo.dto.UserNameExistCheckDTO;
import demo.base.user.pojo.dto.UserRegistDTO;
import demo.base.user.pojo.result.NewUserRegistResult;
import demo.base.user.pojo.type.SystemRolesType;
import demo.base.user.service.UserRegistService;
import demo.common.controller.CommonController;
import demo.config.costom_component.BaseUtilCustom;
import demo.toyParts.educate.pojo.type.GradeType;
import jakarta.servlet.http.HttpServletRequest;

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
	public CommonResult modifyRegistMail(@RequestBody ModifyRegistMailDTO dto) {

		CommonResult result = new CommonResult();
		if (!baseUtilCustom.isLoginUser() || StringUtils.isBlank(dto.getModifyRegistMail())) {
			result.setMessage("Error param");
			return result;
		}

		result = userRegistService.modifyRegistEmail(baseUtilCustom.getUserId(), dto.getModifyRegistMail());
		if (result.isSuccess()) {
			result.successWithMessage("邮件已发送,因网络原因,可能存在延迟,请稍后至邮箱查收.请留意邮箱拦截规则,如果邮件被拦截,可能存放于邮箱垃圾箱内...");
		}
		return result;
	}

	@GetMapping(value = UsersUrl.userRegist)
	public ModelAndView userRegistView(HttpServletRequest request) {
		return userRegistService.userRegistView(request);
	}

	@PostMapping(value = UsersUrl.userRegist)
	@ResponseBody
	public NewUserRegistResult userRegistHandler(@RequestBody UserRegistDTO dto, HttpServletRequest request) {
		return userRegistService.newUserRegist(dto, request);
	}
	
	@GetMapping(value = UsersUrl.STUDENT_REGIST)
	public ModelAndView studentRegistView(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		view.setViewName("toyJSP/educateJSP/studentRegist");
		view.addObject("gradeTypeList", GradeType.values());
		view.addObject("title", "Student sign up");
		return view;
	}
	
	@PostMapping(value = UsersUrl.STUDENT_REGIST)
	@ResponseBody
	public NewUserRegistResult studentRegist(@RequestBody StudentRegistDTO dto, HttpServletRequest request) {
		String ip = request.getHeader("X-FORWARDED-FOR");
		return userRegistService.newStudentRegist(dto, ip, request);
	}

	@GetMapping(value = UsersUrl.registActivation)
	public ModelAndView registActivation(@RequestParam(value = "mailKey", defaultValue = "") String mailKey) {
		CommonResult serviceResult = userRegistService.registActivation(mailKey);
		ModelAndView view = new ModelAndView(UserRegistView.userRegistActivationResult);
		view.addObject("message", serviceResult.getMessage());
		return view;
	}

	@PostMapping(value = UsersUrl.resendRegistMail)
	@ResponseBody
	public CommonResult resendRegistMail(HttpServletRequest request) {
		CommonResult result = new CommonResult();
		if (!baseUtilCustom.isLoginUser()
				|| baseUtilCustom.getRoles().contains(SystemRolesType.ROLE_USER_ACTIVE.getName())) {
			result.setMessage("Please login");
			return result;
		}
		Long userId = baseUtilCustom.getUserId();
		result = userRegistService.resendRegistMail(userId, request);
		if (result.isFail()) {
			result.setMessage("Error param");
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
	public CommonResult forgotPassword(@RequestBody ForgotPasswordDTO dto, HttpServletRequest request) {
		CommonResult result = new CommonResult();

		if (StringUtils.isBlank(dto.getEmail())) {
			result.setMessage("Error param");
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
	public CommonResult forgotUsername(@RequestBody ForgotUsernameDTO dto, HttpServletRequest request) {
		CommonResult result = new CommonResult();

		if (StringUtils.isBlank(dto.getEmail())) {
			result.setMessage("Error param");
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
			view.addObject("errorMessage", "Error param");
			return view;
		}

		view.addObject("mailKey", mailKey);

		return view;
	}

	@PostMapping(value = UsersUrl.resetPassword)
	@ResponseBody
	public CommonResult resetPassword(@RequestBody ResetPasswordDTO dto) {
		CommonResult r = new CommonResult();

		if (StringUtils.isAnyBlank(dto.getNewPassword(), dto.getNewPasswordRepeat())) {
			r.setMessage("Null param");
			return r;
		}

		if (StringUtils.isNotBlank(dto.getMailKey())) {
			r = userRegistService.resetPasswordByMailKey(dto.getMailKey(), dto.getNewPassword(),
					dto.getNewPasswordRepeat());
		} else if (baseUtilCustom.isLoginUser()) {
			if (StringUtils.isBlank(dto.getOldPassword())) {
				r.setMessage("Null param");
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
