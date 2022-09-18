package demo.base.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.article.article.service.impl.ArticleOptionService;
import demo.article.articleComment.service.impl.ArticleCommentOptionService;
import demo.automationTest.service.impl.AutomationTestOptionService;
import demo.base.system.pojo.constant.BaseUrl;
import demo.base.system.service.impl.SystemOptionService;
import demo.finance.cryptoCoin.common.service.CryptoCoinOptionService;
import demo.finance.currencyExchangeRate.data.service.impl.CurrencyExchangeRateOptionService;
import demo.joy.common.service.JoyOptionService;
import demo.joy.garden.service.impl.JoyGardenOptionService;
import demo.thirdPartyAPI.cloudinary.service.impl.CloudinaryOptionService;
import demo.tool.mail.service.impl.MailOptionService;
import demo.tool.telegram.service.impl.TelegramOptionService;
import demo.toyParts.educate.service.impl.EducateOptionService;

@Controller
@RequestMapping(value = BaseUrl.OPTION_CONSTANT)
public class OptionConstantController {

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
	@Autowired
	private CurrencyExchangeRateOptionService currencyExchangeRateOptionService;

	@GetMapping(value = "/refreshSystemOption")
	@ResponseBody
	public String refreshSystemOption() {
		systemConstantService.refreshOption();
		return "done";
	}
	
	@GetMapping(value = "/refreshArticleOption")
	@ResponseBody
	public String refreshArticleOption() {
		articleConstantService.refreshOption();
		return "done";
	}

	@GetMapping(value = "/refreshArticleCommentOption")
	@ResponseBody
	public String refreshArticleCommentOption() {
		articleCommentConstantService.refreshOption();
		return "done";
	}

	@GetMapping(value = "/refreshMailOption")
	@ResponseBody
	public String refreshMailOption() {
		mailConstantService.refreshOption();
		return "done";
	}

	@GetMapping(value = "/refreshCloudinaryOption")
	@ResponseBody
	public String refreshCloudinaryOption() {
		cloudinaryConstantService.refreshOption();
		return "done";
	}

	@GetMapping(value = "/refreshAutomationTestOption")
	@ResponseBody
	public String refreshAutomationTestOption() {
		automationTestOptionService.refreshOption();
		return "done";
	}

	@GetMapping(value = "/refreshCryptoCoinOption")
	@ResponseBody
	public String refreshCryptoCoinOption() {
		cryptoCoinOptionService.refreshOption();
		return "done";
	}

	@GetMapping(value = "/refreshTelegramOption")
	@ResponseBody
	public String refreshTelegramOption() {
		telegramOptionService.refreshOption();
		return "done";
	}
	
	@GetMapping(value = "/refreshEducateOption")
	@ResponseBody
	public String refreshEducateOption() {
		return educateOptionService.refreshOption();
	}
	
	@GetMapping(value = "/refreshJoyOption")
	@ResponseBody
	public String refreshJoyOption() {
		joyOptionService.refreshOption();
		return "done";
	}
	
	@GetMapping(value = "/refreshJoyGardenOption")
	@ResponseBody
	public String refreshJoyGardenOption() {
		joyGardenOptionService.refreshOption();
		return "done";
	}
	
	@GetMapping(value = "/refreshCurrencyExchangeRateOption")
	@ResponseBody
	public String refreshCurrencyExchangeRateOption() {
		currencyExchangeRateOptionService.refreshOption();
		return "done";
	}
	
}
