package demo.base.admin.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import demo.base.admin.pojo.constant.AdminView;
import demo.base.admin.pojo.dto.LoadHomepageAnnouncementStrDTO;
import demo.base.admin.service.AdminService;
import demo.base.system.pojo.constant.BaseStatusCode;
import demo.base.system.service.impl.SystemConstantService;
import demo.base.user.pojo.constant.UserManagerView;
import demo.base.user.pojo.dto.UserIpDeleteDTO;
import demo.base.user.pojo.po.Users;
import demo.base.user.service.UsersService;
import demo.common.controller.CommonController;
import demo.common.pojo.result.CommonResultCX;
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
		view.setViewName(UserManagerView.userManager);

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
		tmpUser.setEnabled(Boolean.parseBoolean(formData.get("enable").get(0)));
		tmpUser.setAccountNonLocked(Boolean.parseBoolean(formData.get("accountNonLocked").get(0)));
		tmpUser.setAccountNonExpired(Boolean.parseBoolean(formData.get("accountNonExpired").get(0)));
		tmpUser.setCredentialsNonExpired(Boolean.parseBoolean(formData.get("credentialsNonExpired").get(0)));
		userDetailDao.setLockeds(tmpUser);

		view.addObject("message", tmpUser.getUserName());

		view.setViewName(UserManagerView.userManager);

		return view;
	}
	
	@GetMapping(value = AdminUrlConstant.manager)
	public ModelAndView manager() {
		return new ModelAndView(AdminView.manager);
	}

	@GetMapping(value = AdminUrlConstant.updateAccountMarker)
	public ModelAndView updateAccountMarker() {
		return new ModelAndView(AdminView.updateAccountMarker);

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

	@PostMapping(value = AdminUrlConstant.deleteUserIpRecord)
	public void deleteUserIpRecord(@RequestBody UserIpDeleteDTO param, HttpServletRequest request, HttpServletResponse response) {
		CommonResultCX result = adminService.deleteUserIpRecord(param);
		outputJson(response, JSONObject.fromObject(result));
	}
	
	@PostMapping(value = AdminUrlConstant.setHomepageAnnouncementStr)
	@ResponseBody
	public JSONObject setHomepageAnnouncementStr(@RequestBody LoadHomepageAnnouncementStrDTO dto) {
		String strContent = dto.getHomepageAnnouncementStr();
		adminService.setTempHomepageAnnouncement(strContent);
		JSONObject jsonOutput = new JSONObject();
		jsonOutput.put("homepageAnnouncementStr", systemConstantService.getHomepageAnnouncementStr());
		return jsonOutput;
	}
	
	@PostMapping(value = AdminUrlConstant.createFakeEvaluationStore)
	public void createFakeEvaluationStore(HttpServletResponse response) {
		int createCount = fakePostService.createFakeEvaluationStore();
		JSONObject jsonOutput = new JSONObject();
		jsonOutput.put("message", createCount);
		outputJson(response, jsonOutput);
	}
	
}