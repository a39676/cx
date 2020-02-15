package demo.base.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.base.user.pojo.constant.UserAuthUrl;
import demo.base.user.pojo.constant.UserAuthView;
import demo.base.user.pojo.dto.EditUserAuthDTO;
import demo.base.user.pojo.dto.FindAuthsConditionDTO;
import demo.base.user.pojo.dto.FindUserAuthDTO;
import demo.base.user.pojo.result.FindAuthsResult;
import demo.base.user.pojo.result.FindUserAuthResult;
import demo.base.user.service.AuthService;
import demo.base.user.service.UserAuthService;
import demo.baseCommon.controller.CommonController;
import demo.baseCommon.pojo.result.CommonResultCX;

@Controller
@RequestMapping(value = UserAuthUrl.root)
public class UserAuthController extends CommonController {

	@Autowired
	private UserAuthService userAuthService;
	@Autowired
	private AuthService authService;
	
	@GetMapping(value = UserAuthUrl.userAuthManager)
	public ModelAndView userAuthManager() {
		return new ModelAndView(UserAuthView.userAuthManager);
	}
	
	@PostMapping(value = UserAuthUrl.editUserAuth)
	@ResponseBody
	public CommonResultCX editUserAuth(@RequestBody EditUserAuthDTO dto) {
		return userAuthService.editUserAuth(dto);
	}
	
	@PostMapping(value = UserAuthUrl.findUserAuth)
	@ResponseBody
	public FindUserAuthResult findUserAuth(@RequestBody FindUserAuthDTO dto) {
		return userAuthService.findUserAuth(dto);
	}
	
	@PostMapping(value = UserAuthUrl.findAuth)
	@ResponseBody
	public FindAuthsResult findAuth() {
		return authService.findAuthsByCondition(new FindAuthsConditionDTO());
	}
}
