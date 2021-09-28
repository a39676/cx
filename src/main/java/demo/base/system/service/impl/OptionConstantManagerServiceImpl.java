package demo.base.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.article.article.service.impl.ArticleConstantService;
import demo.article.articleComment.service.impl.ArticleCommentConstantService;
import demo.automationTest.service.impl.AutomationTestConstantService;
import demo.base.system.service.OptionConstantManagerService;
import demo.common.service.CommonService;
import demo.finance.cryptoCoin.common.service.CryptoCoinConstantService;
import demo.thirdPartyAPI.cloudinary.service.impl.CloudinaryConstantService;
import demo.tool.mail.service.impl.MailConstantService;
import demo.tool.telegram.service.impl.TelegramConstantService;

@Service
public class OptionConstantManagerServiceImpl extends CommonService implements OptionConstantManagerService {

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
	
	
	@Override
	public void refreshSystemConstant() {
		systemConstantService.refreshConstant();
	}
	
	@Override
	public void refreshArticleConstant() {
		articleConstantService.refreshConstant();
	}
	
	@Override
	public void refreshArticleCommentConstant() {
		articleCommentConstantService.refreshConstant();
	}
	
	@Override
	public void refreshMailConstant() {
		mailConstantService.refreshConstant();
	}
	
	@Override
	public void refreshCloudinaryConstant() {
		cloudinaryConstantService.refreshConstant();
	}
	
	@Override
	public void refreshAutomationTestConstant() {
		automationTestConstantService.refreshConstant();
	}
	
	@Override
	public void refreshCryptoCoinConstant() {
		cryptoCoinConstantService.refreshConstant();
	}
	
	@Override
	public void refreshTelegramConstant() {
		telegramConstantService.refreshConstant();
	}
}
