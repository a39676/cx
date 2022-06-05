package demo.base.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.article.article.service.impl.ArticleOptionService;
import demo.article.articleComment.service.impl.ArticleCommentOptionService;
import demo.automationTest.service.impl.AutomationTestOptionService;
import demo.base.system.service.OptionConstantManagerService;
import demo.common.service.CommonService;
import demo.finance.cryptoCoin.common.service.CryptoCoinOptionService;
import demo.joy.common.service.JoyOptionService;
import demo.joy.garden.service.impl.JoyGardenOptionService;
import demo.thirdPartyAPI.cloudinary.service.impl.CloudinaryOptionService;
import demo.tool.mail.service.impl.MailOptionService;
import demo.tool.telegram.service.impl.TelegramOptionService;
import demo.toyParts.educate.service.impl.EducateOptionService;

@Service
public class OptionServiceManagerServiceImpl extends CommonService implements OptionConstantManagerService {

	@Autowired
	private SystemOptionService systemConstantService;
	@Autowired
	private ArticleOptionService articleConstantService;
	@Autowired
	private ArticleCommentOptionService articleCommentConstantService;
	@Autowired
	private MailOptionService mailConstantService;
	@Autowired
	private CloudinaryOptionService cloudinaryConstantService;
	@Autowired
	private AutomationTestOptionService automationTestOptionService;
	@Autowired
	private CryptoCoinOptionService cryptoCoinOptionService;
	@Autowired
	private TelegramOptionService telegramOptionService;
	@Autowired
	private EducateOptionService educateOptionService;
	@Autowired
	private JoyOptionService joyOptionService;
	@Autowired
	private JoyGardenOptionService joyGardenOptionService;
	
	
	@Override
	public void refreshSystemOption() {
		systemConstantService.refreshOption();
	}
	
	@Override
	public void refreshArticleOption() {
		articleConstantService.refreshOption();
	}
	
	@Override
	public void refreshArticleCommentOption() {
		articleCommentConstantService.refreshOption();
	}
	
	@Override
	public void refreshMailOption() {
		mailConstantService.refreshOption();
	}
	
	@Override
	public void refreshCloudinaryOption() {
		cloudinaryConstantService.refreshOption();
	}
	
	@Override
	public void refreshAutomationTestOption() {
		automationTestOptionService.refreshOption();
	}
	
	@Override
	public void refreshCryptoCoinOption() {
		cryptoCoinOptionService.refreshOption();
	}
	
	@Override
	public void refreshTelegramOption() {
		telegramOptionService.refreshOption();
	}
	
	@Override
	public void refreshJoyOption() {
		joyOptionService.refreshOption();
	}
	
	@Override
	public void refreshJoyGardenOption() {
		joyGardenOptionService.refreshOption();
	}
	
	@Override
	public String refreshEducateOption() {
		return educateOptionService.refreshOption();
	}
	
}
