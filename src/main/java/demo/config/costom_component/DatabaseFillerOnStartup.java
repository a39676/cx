package demo.config.costom_component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import demo.article.article.service.impl.ArticleConstantService;
import demo.article.articleComment.service.impl.ArticleCommentConstantService;
import demo.automationTest.service.impl.AutomationTestConstantService;
import demo.base.system.service.impl.SystemConstantService;
import demo.base.user.pojo.po.Users;
import demo.base.user.pojo.type.SystemRolesType;
import demo.base.user.service.RoleService;
import demo.base.user.service.UserRegistService;
import demo.base.user.service.UsersService;
import demo.base.user.service.impl.UserRoleConstantService;
import demo.common.service.CommonService;
import demo.finance.cryptoCoin.common.service.CryptoCoinConstantService;
import demo.joy.image.icon.service.JoyIconService;
import demo.joy.scene.service.JoySceneManagerService;
import demo.thirdPartyAPI.cloudinary.service.impl.CloudinaryConstantService;
import demo.tool.mail.service.impl.MailConstantService;
import demo.tool.telegram.service.impl.TelegramConstantService;

@Component
//public class DatabaseFillerOnStartup implements ApplicationListener<ContextStartedEvent> {
public class DatabaseFillerOnStartup extends CommonService implements ApplicationListener<ApplicationReadyEvent> {
	
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
	private JoyIconService joyIconService;
	@Autowired
	private SystemConstantService systemConstantService;
	@Autowired
	private ArticleConstantService articleConstantService;
	@Autowired
	private ArticleCommentConstantService articleCommentConstantService;
	@Autowired
	private MailConstantService mailConstantService;
	@Autowired
	private CloudinaryConstantService cloudinaryConstantService;
	@Autowired
	private AutomationTestConstantService automationTestConstantService;
	@Autowired
	private CryptoCoinConstantService cryptoCoinConstantService;
	@Autowired
	private TelegramConstantService telegramConstantService;
	
	
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
		
//		if (event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")) {}
		if (event.getApplicationContext().getParent() == null) {
			roleService.__initBaseRole();
			log.error("after base role init");
			
			userRoleConstantService.refresh();
			
			/* 如无超级管理员角色, 初始化 */
			log.error("query auth auth");
			List<Users> superUserList = userService.findUserListByRole(SystemRolesType.ROLE_SUPER_ADMIN);
			if(superUserList.isEmpty()) {
				log.error("NO super admin");
			} else {
				log.error("Has super admin");
			}
			
			
			if(superUserList.isEmpty()) {
				userRegistService.__baseSuperAdminRegist().getNewSuperAdminId();
				log.error("can NOT find super admin");
			} else {
				log.error("super admin found");
			}
			
			log.error("load joy option");
			joySceneOperationService.defaultSceneInit();
			joyIconService.loadAllIconToRedis();
			log.error("after load joy option");
			
			log.error("loading system option");
			systemConstantService.refreshConstant();
			
			log.error("loading article option");
			articleConstantService.refreshConstant();
			
			log.error("loading article comment option");
			articleCommentConstantService.refreshConstant();
			
			log.error("loading mail option");
			mailConstantService.refreshConstant();
			
			log.error("loading cloudinary option");
			cloudinaryConstantService.refreshConstant();
			
			log.error("loading automation test option");
			automationTestConstantService.refreshConstant();
			
			log.error("loading crypto coin option");
			cryptoCoinConstantService.refreshConstant();
			
			log.error("loading telegram option");
			telegramConstantService.refreshConstant();
			
		}
		
		log.error("data base filler end");
	}


}
