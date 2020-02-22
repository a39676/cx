package demo.base.user.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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

import demo.base.system.pojo.constant.BaseStatusCode;
import demo.base.user.pojo.constant.UserRegistView;
import demo.base.user.pojo.constant.UsersUrl;
import demo.base.user.pojo.dto.UserRegistDTO;
import demo.base.user.pojo.result.NewUserRegistResult;
import demo.base.user.pojo.type.SystemRolesType;
import demo.base.user.service.UserRegistService;
import demo.baseCommon.controller.CommonController;
import demo.baseCommon.pojo.result.CommonResultCX;
import demo.baseCommon.pojo.type.ResultTypeCX;
import demo.config.costom_component.BaseUtilCustom;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = UsersUrl.root)
public class UsersRegistController extends CommonController {

	@Autowired
	private UserRegistService userRegistService;
	@Autowired
	private BaseUtilCustom baseUtilCustom;

	@GetMapping(value = { UsersUrl.userNameExistCheck })
	public void userNameExistCheck(@RequestBody String jsonStrInput, HttpServletResponse response) throws IOException {
		// 建输出流
		PrintWriter out = response.getWriter();

		JSONObject jsonInput = JSONObject.fromObject(jsonStrInput);
		String userName = jsonInput.getString("userName");
		JSONObject json = new JSONObject();
		boolean result = userRegistService.isUserExists(userName);
		if (result) {
			json.put("result", BaseStatusCode.fail);
		} else {
			json.put("exception", "user name not exist");
		}

		out.print(json);
	}

	@PostMapping(value = UsersUrl.modifyRegistMail)
	public void modifyRegistMail(@RequestBody String data, HttpServletResponse response) {
		JSONObject jsonInput = getJson(data);

		CommonResultCX result = new CommonResultCX();
		JSONObject jsonOutput;
		if (!baseUtilCustom.isLoginUser() || !jsonInput.containsKey("modifyRegistMail")) {
			result.fillWithResult(ResultTypeCX.errorParam);
			jsonOutput = JSONObject.fromObject(result);
			outputJson(response, jsonOutput);
			return;
		}

		result = userRegistService.modifyRegistEmail(baseUtilCustom.getUserId(),
				jsonInput.getString("modifyRegistMail"));
		if (result.isSuccess()) {
			result.successWithMessage("邮件已发送,因网络原因,可能存在延迟,请稍后至邮箱查收.请留意邮箱拦截规则,如果邮件被拦截,可能存放于邮箱垃圾箱内...");
		}
		jsonOutput = JSONObject.fromObject(result);
		outputJson(response, jsonOutput);
	}

	@ApiOperation(value = "创建用户页面", notes = "返回创建用户页面")
	@GetMapping(value = UsersUrl.userRegist)
	public ModelAndView userRegistView(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		view.setViewName(UserRegistView.userRegist);
		view.addObject("title", "Sign up");
		return view;
	}

	@ApiOperation(value = "用户注册", notes = "用户注册请求")
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
	public void forgotPassword(@RequestBody String data, HttpServletResponse response, HttpServletRequest request) {
		CommonResultCX result = new CommonResultCX();
		JSONObject jsonOutput;
		JSONObject jsonInput = getJson(data);

		if (!jsonInput.containsKey("email")) {
			result.fillWithResult(ResultTypeCX.errorParam);
			outputJson(response, JSONObject.fromObject(result));
			return;
		}

		result = userRegistService.sendForgotPasswordMail(jsonInput.getString("email"), request);
		if (result.isSuccess()) {
			result.successWithMessage("邮件已发送,因网络原因,可能存在延迟,请稍后至邮箱查收.请留意邮箱拦截规则,如果邮件被拦截,可能存放于邮箱垃圾箱内...");
			outputJson(response, JSONObject.fromObject(result));
			return;
		}

		jsonOutput = JSONObject.fromObject(result);
		outputJson(response, jsonOutput);
		return;
	}

	@PostMapping(value = UsersUrl.forgotUsername)
	public void forgotUsername(@RequestBody String data, HttpServletResponse response, HttpServletRequest request) {
		CommonResultCX result = new CommonResultCX();
		JSONObject jsonOutput;
		JSONObject jsonInput = getJson(data);

		if (!jsonInput.containsKey("email")) {
			result.fillWithResult(ResultTypeCX.errorParam);
			outputJson(response, JSONObject.fromObject(result));
			return;
		}

		result = userRegistService.sendForgotUsernameMail(jsonInput.getString("email"), request);
		if (result.isSuccess()) {
			result.successWithMessage("邮件已发送,因网络原因,可能存在延迟,请稍后至邮箱查收.请留意邮箱拦截规则,如果邮件被拦截,可能存放于邮箱垃圾箱内...");
			outputJson(response, JSONObject.fromObject(result));
			return;
		}

		jsonOutput = JSONObject.fromObject(result);
		outputJson(response, jsonOutput);
		return;
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
	public void resetPassword(@RequestBody String data, HttpServletResponse response) {
		JSONObject jsonInput = getJson(data);
		CommonResultCX result = new CommonResultCX();

		if (!jsonInput.containsKey("newPassword") || !jsonInput.containsKey("newPasswordRepeat")) {
			result.fillWithResult(ResultTypeCX.nullParam);
			outputJson(response, JSONObject.fromObject(result));
			return;
		}

		if (jsonInput.containsKey("mailKey")
				&& StringUtils.isNotBlank(String.valueOf(jsonInput.getString("mailKey")))) {
			result = userRegistService.resetPasswordByMailKey(jsonInput.getString("mailKey"),
					jsonInput.getString("newPassword"), jsonInput.getString("newPasswordRepeat"));
		} else if (baseUtilCustom.isLoginUser()) {
			if (!jsonInput.containsKey("oldPassword")) {
				result.fillWithResult(ResultTypeCX.nullParam);
				outputJson(response, JSONObject.fromObject(result));
				return;
			}
			result = userRegistService.resetPasswordByLoginUser(baseUtilCustom.getUserId(),
					jsonInput.getString("oldPassword"), jsonInput.getString("newPassword"),
					jsonInput.getString("newPasswordRepeat"));
		} else {
			result.successWithMessage("");
			outputJson(response, JSONObject.fromObject(result));
			return;
		}

		outputJson(response, JSONObject.fromObject(result));
	}

}
