package demo.config.costom_component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import demo.base.organizations.pojo.po.Organizations;
import demo.base.organizations.service.OrganizationService;
import demo.base.organizations.service.__SystemOrganizationService;
import demo.base.system.pojo.constant.InitSystemConstant;
import demo.base.user.pojo.constant.UserConstant;
import demo.base.user.pojo.po.Auth;
import demo.base.user.pojo.po.Users;
import demo.base.user.pojo.result.FindAuthsResult;
import demo.base.user.service.AuthService;
import demo.base.user.service.RoleService;
import demo.base.user.service.UserRegistService;
import demo.base.user.service.UsersService;
import demo.joy.image.icon.service.JoyIconService;
import demo.joy.scene.service.JoySceneOperationService;

@Component
//public class DatabaseFillerOnStartup implements ApplicationListener<ContextStartedEvent> {
public class DatabaseFillerOnStartup implements ApplicationListener<ApplicationReadyEvent> {
	
	@Autowired
	private AuthService authService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UsersService userService;
	@Autowired
	private UserRegistService userRegistService;
	@Autowired
	private OrganizationService orgService;
	@Autowired
	private __SystemOrganizationService __systemOrgService;
	
	@Autowired
	private JoySceneOperationService joySceneOperationService;
	@Autowired
	private JoyIconService joyIconService;
/*
 * ContextStartedEvent
 * ContextStoppedEvent
 * ContextRefreshedEvent
 * ContextClosedEvent
 * RequestHandleEvent
 */
	
	@Override
//	public void onApplicationEvent(ContextStartedEvent event) {
	public void onApplicationEvent(ApplicationReadyEvent event) {
		
//		if (event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")) {}
		if (event.getApplicationContext().getParent() == null) {
			roleService.__initBaseRole();
			
			/* 如无超级管理员角色, 初始化 */
			FindAuthsResult authsResult = authService.findSuperAdministratorAuth();
			List<Auth> superAdminAuthList = authsResult.getAuthList();
			Long superAdminAuthId = null;
			if(superAdminAuthList == null || superAdminAuthList.size() < 1) {
				superAdminAuthId = authService.__createBaseSuperAdminAuth(UserConstant.noneUserId);
			} else {
				superAdminAuthId = superAdminAuthList.get(0).getId();
			}
			
			List<Users> superAdminUserList = userService.findUserListByAuthId(superAdminAuthId);
			Long superAdminId = null;
			if(superAdminUserList.size() < 1) {
				superAdminId = userRegistService.__baseSuperAdminRegist().getNewSuperAdminId();
			} else {
				superAdminId = superAdminUserList.get(0).getUserId();
			}
			
			Organizations baseOrg = orgService.getOrgById(InitSystemConstant.ORIGINAL_BASE_ORG_ID);
			if(baseOrg == null || baseOrg.getIsDelete()) {
				__systemOrgService.__initBaseOrg(superAdminId);
			}
			
			joySceneOperationService.defaultSceneInit();
			joyIconService.loadAllIconToRedis();
		}
	}


}
