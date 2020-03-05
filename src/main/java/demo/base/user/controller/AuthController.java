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
import demo.base.user.pojo.dto.InsertAuthDTO;
import demo.base.user.pojo.result.InsertNewAuthResult;
import demo.base.user.service.AuthService;
import demo.baseCommon.pojo.result.CommonResultCX;

@Controller
@RequestMapping(value = AuthUrl.root)
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@GetMapping(value = AuthUrl.authManager)
	public ModelAndView authManager(@RequestParam(value = "orgPK", defaultValue = "" ) String orgPK) {
		return authService.authManagerView(orgPK);
	}
	
	@PostMapping(value = AuthUrl.insertSysAuth)
	@ResponseBody
	public InsertNewAuthResult insertSysAuth(@RequestBody InsertAuthDTO dto) {
		return authService.insertSysAuth(dto);
	}
	
	@PostMapping(value = AuthUrl.insertOrgAuth)
	@ResponseBody
	public InsertNewAuthResult insertOrgAuth(@RequestBody InsertAuthDTO dto) {
		return authService.insertOrgAuth(dto);
	}
	
	@PostMapping(value = AuthUrl.deleteAuth)
	@ResponseBody
	public CommonResultCX deleteAuth(@RequestBody DeleteAuthDTO dto) {
		return authService.deleteAuth(dto);
	}
}
