package demo.base.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.base.user.pojo.constant.AuthUrl;
import demo.base.user.pojo.dto.DeleteAuthDTO;
import demo.base.user.pojo.dto.EditAuthDTO;
import demo.base.user.pojo.dto.EditAuthRoleDTO;
import demo.base.user.pojo.dto.InsertAuthDTO;
import demo.base.user.pojo.result.InsertNewAuthResult;
import demo.base.user.service.AuthRoleService;
import demo.base.user.service.AuthService;
import demo.common.pojo.result.CommonResultCX;

@Controller
@RequestMapping(value = AuthUrl.root)
public class AuthController {

	@Autowired
	private AuthService authService;
	@Autowired
	private AuthRoleService authRoleService;
	
	@GetMapping(value = AuthUrl.authManager)
	public ModelAndView authManager(@RequestParam(value = "orgPK", defaultValue = "" ) String orgPK) {
		return authService.authManagerView(orgPK);
	}
	
	@GetMapping(value = AuthUrl.authEdit)
	public ModelAndView editAuth(@RequestParam(value = "authPK", defaultValue = "" ) String authPK) {
		return authService.authEditView(authPK);
	}
	
	@PostMapping(value = AuthUrl.authEdit)
	@ResponseBody
	public CommonResultCX editAuth(@RequestBody EditAuthDTO dto) {
		return authService.editAuth(dto);
	}
	
	@GetMapping(value = AuthUrl.insertAuth)
	public ModelAndView insertAuth(@RequestParam(value = "orgPK", defaultValue = "" ) String orgPK) {
		return authService.insertAuthView(orgPK);
	}
	
	@PostMapping(value = AuthUrl.insertAuth)
	@ResponseBody
	public InsertNewAuthResult insertAuth(@RequestBody InsertAuthDTO dto) {
		return authService.insertAuth(dto);
	}
	
	@PostMapping(value = AuthUrl.deleteAuth)
	@ResponseBody
	public CommonResultCX deleteAuth(@RequestBody DeleteAuthDTO dto) {
		return authService.deleteAuth(dto);
	}
	
	@PostMapping(value = AuthUrl.insertAuthRole)
	@ResponseBody
	public CommonResultCX insertAuthRole(@RequestBody EditAuthRoleDTO dto) {
		return authRoleService.insertAuthRole(dto);
	}
	
	@PostMapping(value = AuthUrl.deleteAuthRole)
	@ResponseBody
	public CommonResultCX deleteAuthRole(@RequestBody EditAuthRoleDTO dto) {
		return authRoleService.deleteAuthRole(dto);
	}
}
