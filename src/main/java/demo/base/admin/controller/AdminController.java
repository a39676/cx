package demo.base.admin.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.article.fakePost.service.FakePostService;
import demo.base.admin.pojo.constant.AdminUrlConstant;
import demo.base.admin.pojo.constant.AdminViewConstants;
import demo.base.admin.pojo.dto.RefreshSystemConstantDTO;
import demo.base.admin.service.AdminService;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.system.pojo.constant.BaseStatusCode;
import demo.base.system.service.impl.SystemConstantService;
import demo.base.user.pojo.dto.UserIpDeleteDTO;
import demo.base.user.pojo.po.Users;
import demo.base.user.service.UsersService;
import demo.baseCommon.controller.CommonController;
import demo.baseCommon.pojo.result.CommonResultCX;
import demo.finance.account_info.controller.AccountInfoController;
import net.sf.json.JSONObject;

/**
 * @author Acorn 2017年4月15日
 * 
 */
@Controller
@RequestMapping(value = AdminUrlConstant.root)
public class AdminController extends CommonController {

	@Autowired
	private UsersService userDetailDao;

	@Autowired
	private AccountInfoController accountInfoController;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private SystemConstantService systemConstantService;
	
	@Autowired
	private FakePostService fakePostService;
	
	/**
	 * 解锁/锁定用户
	 * 
	 * @param formData
	 * @return
	 */
	@GetMapping(value = AdminUrlConstant.userManager, produces = "text/html;charset=UTF-8")
	public ModelAndView userManager() {
		ModelAndView view = new ModelAndView();

		view.addObject("title", "User manger site");
		view.addObject("message", "Nothing yet");
		view.setViewName(AdminViewConstants.userManager);

		return view;
	}

	/**
	 * 解锁/锁定用户 请求处理
	 * 
	 * @param formData
	 * @return
	 */
	@PostMapping(value = AdminUrlConstant.userManager, produces = "text/html;charset=UTF-8")
	public ModelAndView userEdit(@RequestBody MultiValueMap<String, String> formData) {
		ModelAndView view = new ModelAndView();

		Users tmpUser = new Users();
		tmpUser.setUserName(formData.get("userName").get(0));
		tmpUser.setEnable(Boolean.parseBoolean(formData.get("enable").get(0)));
		tmpUser.setAccountNonLocked(Boolean.parseBoolean(formData.get("accountNonLocked").get(0)));
		tmpUser.setAccountNonExpired(Boolean.parseBoolean(formData.get("accountNonExpired").get(0)));
		tmpUser.setCredentialsNonExpired(Boolean.parseBoolean(formData.get("credentialsNonExpired").get(0)));
		userDetailDao.setLockeds(tmpUser);

		view.addObject("message", tmpUser.getUserName());

		view.setViewName(AdminViewConstants.userManager);

		return view;
	}
	
	@GetMapping(value = AdminUrlConstant.manager)
	public ModelAndView manager() {
		return new ModelAndView(AdminViewConstants.manager);
	}

	@GetMapping(value = AdminUrlConstant.updateAccountMarker, produces = "text/html;charset=UTF-8")
	public ModelAndView updateAccountMarker() {

		return new ModelAndView(AdminViewConstants.updateAccountMarker);

	}

	@PostMapping(value = AdminUrlConstant.updateAccountMarker, produces = "text/html;charset=UTF-8")
	public void updateAccountMarker(String accountNumber, HttpServletResponse response) {

		int updateCount = accountInfoController.updateAccountMarker(accountNumber);

		JSONObject json = new JSONObject();

		if (updateCount == 1) {
			json.put("result", BaseStatusCode.success);

		} else {
			json.put("result", BaseStatusCode.fail);

		}

		try {
			response.getWriter().println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@GetMapping(value = AdminUrlConstant.dba)
	public ModelAndView dbaPage() {
		ModelAndView view = new ModelAndView();
		view.addObject("title", "Spring Security Hello World");
		view.addObject("message", "This is protected page - Database Page!");
		view.setViewName(AdminViewConstants.adminView);

		return view;
	}

	@PostMapping(value = AdminUrlConstant.deleteUserIpRecord)
	public void deleteUserIpRecord(@RequestBody UserIpDeleteDTO param, HttpServletRequest request, HttpServletResponse response) {
		CommonResultCX result = adminService.deleteUserIpRecord(param);
		outputJson(response, JSONObject.fromObject(result));
	}

	@PostMapping(value = AdminUrlConstant.refreshSystemConstant)
	@ResponseBody
	public String refreshSystemConstant(@RequestBody RefreshSystemConstantDTO dto) {
		String result = systemConstantService.getValByName(dto.getKey(), true);
		return result;
	}
	
	@PostMapping(value = AdminUrlConstant.loadHomepageAnnouncementStr)
	public void loadHomepageAnnouncementStr(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonInput = getJson(data);
		String strContent = jsonInput.getString("homepageAnnouncementStr");
		if(StringUtils.isBlank(strContent) || "null".equals(strContent)) {
			adminService.loadHomepageAnnouncementStr();
		} else {
			adminService.loadHomepageAnnouncementStr(strContent);
		}
		JSONObject jsonOutput = new JSONObject();
		jsonOutput.put("homepageAnnouncementStr", systemConstantService.getValByName(SystemConstantStore.homepageAnnouncementStr));
		outputJson(response, jsonOutput);
	}
	
	@PostMapping(value = AdminUrlConstant.createFakeEvaluationStore)
	public void createFakeEvaluationStore(HttpServletResponse response) {
		int createCount = fakePostService.createFakeEvaluationStore();
		JSONObject jsonOutput = new JSONObject();
		jsonOutput.put("message", createCount);
		outputJson(response, jsonOutput);
	}
	
}