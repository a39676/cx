package demo.base.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.user.pojo.constant.UsersUrl;
import demo.base.user.pojo.dto.FindUserByConditionDTO;
import demo.base.user.pojo.dto.OtherUserInfoDTO;
import demo.base.user.pojo.po.Users;
import demo.base.user.pojo.result.FindUserByConditionResult;
import demo.base.user.pojo.vo.UsersDetailVO;
import demo.base.user.service.UsersService;
import demo.baseCommon.controller.CommonController;
import demo.config.costom_component.BaseUtilCustom;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = UsersUrl.root)
public class UsersController extends CommonController {
	
	@Autowired
	private UsersService usersService;
	@Autowired
	private BaseUtilCustom baseUtilCustom;
	
	@PostMapping(value = UsersUrl.userInfo)
	public ModelAndView userInfo() {
		return usersService.findUserInfo();
	}
	
	@PostMapping(value = UsersUrl.otherUserInfo)
	public ModelAndView otherUserInfo(@RequestBody OtherUserInfoDTO param, HttpServletRequest request, HttpServletResponse response) {
//		TODO
		ModelAndView view = new ModelAndView("userJSP/otherUserInfo");
		
		UsersDetailVO ud = usersService.findOtherUserDetail(param);
		
		view.addObject("userDetail", ud);
		view.addObject("pk", param.getPk());
		
		return view;
	}
	
	public Long getCurrentUserId() {
		String userName = baseUtilCustom.getCurrentUserName();
		if(userName == null || userName.length() == 0) {
			return null;
		} 
		
		Users currentUser = usersService.getUserbyUserName(userName);
		if(currentUser == null) {
			return null;
		}
		return currentUser.getUserId();
	}
	
	@PostMapping(value = UsersUrl.isLogin)
	public void isLogin(HttpServletResponse response) {
		CommonResult result = new CommonResult();
		if(baseUtilCustom.isLoginUser()) {
			result.setIsSuccess();
		}
		outputJson(response, JSONObject.fromObject(result));
	}
	
	public String findHeadImageUrl(Long userId) {
		return usersService.findHeadImageUrl(userId);
	}

	@PostMapping(value = UsersUrl.findUserByCondition)
	@ResponseBody
	public FindUserByConditionResult findUserByCondition(@RequestBody FindUserByConditionDTO dto) {
		return usersService.findUserByCondition(dto);
	}
}
