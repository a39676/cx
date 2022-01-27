package demo.config.costom_component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import demo.article.article.service.impl.ArticleOptionService;
import demo.article.articleComment.service.impl.ArticleCommentOptionService;
import demo.automationTest.service.impl.AutomationTestOptionService;
import demo.base.system.service.impl.SystemOptionService;
import demo.base.user.pojo.po.Users;
import demo.base.user.pojo.type.SystemRolesType;
import demo.base.user.service.RoleService;
import demo.base.user.service.UserRegistService;
import demo.base.user.service.UsersService;
import demo.base.user.service.impl.UserRoleConstantService;
import demo.common.service.CommonService;
import demo.finance.cryptoCoin.common.service.CryptoCoinOptionService;
import demo.joy.image.icon.service.JoyIconService;
import demo.joy.scene.service.JoySceneManagerService;
import demo.thirdPartyAPI.cloudinary.service.impl.CloudinaryOptionService;
import demo.tool.mail.service.impl.MailOptionService;
import demo.tool.service.impl.ToolConstantService;
import demo.tool.telegram.service.impl.TelegramOptionService;

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
	private SystemOptionService systemOptionService;
	@Autowired
	private ArticleOptionService articleOptionService;
	@Autowired
	private ArticleCommentOptionService articleCommentOptionService;
	@Autowired
	private MailOptionService mailOptionService;
	@Autowired
	private CloudinaryOptionService cloudinaryOptionService;
	@Autowired
	private AutomationTestOptionService automationTestOptionService;
	@Autowired
	private CryptoCoinOptionService cryptoCoinOptionService;
	@Autowired
	private TelegramOptionService telegramOptionService;
	@Autowired
	private ToolConstantService toolOptionService;
	
	
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
			systemOptionService.refreshOption();
			
			log.error("loading tool option");
			toolOptionService.refreshConstant();
			
			log.error("loading article option");
			articleOptionService.refreshOption();
			
			log.error("loading article comment option");
			articleCommentOptionService.refreshOption();
			
			log.error("loading mail option");
			mailOptionService.refreshOption();
			
			log.error("loading cloudinary option");
			cloudinaryOptionService.refreshOption();
			
			log.error("loading automation test option");
			automationTestOptionService.refreshOption();
			
			log.error("loading crypto coin option");
			cryptoCoinOptionService.refreshOption();
			
			log.error("loading telegram option");
			telegramOptionService.refreshOption();
			
		}
		
		log.error("data base filler end");
	}


}
