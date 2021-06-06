package demo.config.costom_component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import demo.article.article.service.ArticleOptionService;
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
import demo.common.service.CommonService;
import demo.joy.image.icon.service.JoyIconService;
import demo.joy.scene.service.JoySceneManagerService;
import demo.test.mp.producer.TestingQueueAckProducer;

@Component
//public class DatabaseFillerOnStartup implements ApplicationListener<ContextStartedEvent> {
public class DatabaseFillerOnStartup extends CommonService implements ApplicationListener<ApplicationReadyEvent> {
	
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
	private ArticleOptionService articleOptionService;
	
	@Autowired
	private JoySceneManagerService joySceneOperationService;
	@Autowired
	private JoyIconService joyIconService;
	
	@Autowired
	private TestingQueueAckProducer testingQueueAckProducer;
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
		
		log.error("starting database filler");
		testingQueueAckProducer.send();
		
//		if (event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")) {}
		if (event.getApplicationContext().getParent() == null) {
			roleService.__initBaseRole();
			log.error("after base role init");
			
			/* 如无超级管理员角色, 初始化 */
			log.error("query auth auth");
			FindAuthsResult authsResult = authService.findSuperAdministratorAuth();
			log.error("auths result: " + authsResult.isSuccess());
			List<Auth> superAdminAuthList = authsResult.getAuthList();
			log.error("super admin auth list size: " + superAdminAuthList.size());
			Long superAdminAuthId = null;
			if(superAdminAuthList == null || superAdminAuthList.size() < 1) {
				superAdminAuthId = authService.__createBaseSuperAdminAuth(UserConstant.noneUserId);
				log.error("can NOT find super admin auth ID");
			} else {
				superAdminAuthId = superAdminAuthList.get(0).getId();
				log.error("super admin auth id found");
			}
			
			List<Users> superAdminUserList = userService.findUserListByAuthId(superAdminAuthId);
			log.error("super admin user list size: " + superAdminUserList.size());
			Long superAdminId = null;
			if(superAdminUserList.size() < 1) {
				superAdminId = userRegistService.__baseSuperAdminRegist().getNewSuperAdminId();
				log.error("can NOT find super admin ID");
			} else {
				superAdminId = superAdminUserList.get(0).getUserId();
				log.error("super admin ID found");
			}
			
			Organizations baseOrg = orgService.getOrgById(InitSystemConstant.ORIGINAL_BASE_ORG_ID);
			if(baseOrg == null || baseOrg.getIsDelete()) {
				log.error("can NOT find base org, rebuild it");
				__systemOrgService.__initBaseOrg(superAdminId);
			}
			
			log.error("load joy option");
			joySceneOperationService.defaultSceneInit();
			joyIconService.loadAllIconToRedis();
			log.error("after load joy option");
			
			log.error("load article option");
			articleOptionService.loadAllOption();
		}
		
		log.error("data base filler end");
	}


}
