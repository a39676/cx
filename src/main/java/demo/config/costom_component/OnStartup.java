package demo.config.costom_component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import demo.ai.aiArt.service.AiArtService;
import demo.base.task.service.TaskHandlerService;
import demo.base.user.pojo.po.Users;
import demo.base.user.pojo.type.SystemRolesType;
import demo.base.user.service.RoleService;
import demo.base.user.service.UserRegistService;
import demo.base.user.service.UsersService;
import demo.base.user.service.impl.UserRoleConstantService;
import demo.common.service.CommonService;
import demo.joy.scene.service.JoySceneManagerService;
import demo.pmemo.service.UrgeNoticeManagerService;

@Component
//public class DatabaseFillerOnStartup implements ApplicationListener<ContextStartedEvent> {
public class OnStartup extends CommonService implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private RoleService roleService;
	@Autowired
	private UserRoleConstantService userRoleConstantService;
	@Autowired
	private UsersService userService;
	@Autowired
	private UserRegistService userRegistService;
	@Autowired
	private JoySceneManagerService joySceneOperationService;
	@Autowired
	private TaskHandlerService taskHandlerServiceImpl;
	@Autowired
	private UrgeNoticeManagerService urgeNoticeService;
	@Autowired
	private AiArtService aiArtService;

	/*
	 * ContextStartedEvent ContextStoppedEvent ContextRefreshedEvent
	 * ContextClosedEvent RequestHandleEvent
	 */

	@Override
//	public void onApplicationEvent(ContextStartedEvent event) {
	public void onApplicationEvent(ApplicationReadyEvent event) {

		log.error("starting database filler");

//		if (event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")) {}
		if (event.getApplicationContext().getParent() == null) {
			roleService.__initBaseRole();
			log.error("after base role init");

			userRoleConstantService.refresh();

			/* 如无超级管理员角色, 初始化 */
			log.error("query auth auth");
			List<Users> superUserList = userService.findUserListByRole(SystemRolesType.ROLE_SUPER_ADMIN);
			if (superUserList.isEmpty()) {
				log.error("NO super admin");
			} else {
				log.error("Has super admin");
			}

			if (superUserList.isEmpty()) {
				userRegistService.__baseSuperAdminRegist().getNewSuperAdminId();
				log.error("can NOT find super admin");
			} else {
				log.error("super admin found");
			}

			log.error("load joy option");
			joySceneOperationService.defaultSceneInit();
			log.error("after load joy option");

		}

		log.error("data base filler end");

		log.error("Check OS");
		if (isWindows()) {
			log.error("OS: Windows, will set proxy");
			String proxyHost = "127.0.0.1";
			String proxyPort = "10809";

			System.setProperty("http.proxyHost", proxyHost);
			System.setProperty("http.proxyPort", proxyPort);

			System.setProperty("https.proxyHost", proxyHost);
			System.setProperty("https.proxyPort", proxyPort);

			log.error("Had set proxy");
		}

		log.error("taskHandlerServiceImpl.fixRuningEventStatus();");
		taskHandlerServiceImpl.fixRuningTaskStatus();

		log.error("taskHandlerServiceImpl.setBreakFlag(0);");
		taskHandlerServiceImpl.setBreakFlag(0);

		log.error("urge notice service, update message web hook secret token");
		urgeNoticeService.setUpdateMsgWebhook();

		log.error("AI art service load image wall to cache");
		aiArtService.loadImageWallToCache();

		log.error("DatabaseFillerOnStartup end");
	}

}
