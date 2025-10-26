package demo.base.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.ai.aiArt.service.AiArtService;
import demo.ai.aiArt.service.impl.AiArtCacheService;
import demo.ai.aiArt.service.impl.AiArtOptionService;
import demo.ai.aiChat.service.impl.AiChatOptionService;
import demo.ai.common.service.impl.AiCommonOptionService;
import demo.article.article.service.impl.ArticleOptionService;
import demo.article.articleComment.service.impl.ArticleCommentOptionService;
import demo.automationTest.service.impl.AutomationTestOptionService;
import demo.base.system.pojo.constant.BaseUrl;
import demo.base.system.service.impl.SystemOptionService;
import demo.finance.cnStockMarket.service.impl.CnStockMarketOptionService;
import demo.finance.cryptoCoin.common.service.CryptoCoinOptionService;
import demo.finance.cryptoCoin.data.service.CryptoCoinDataComplexService;
import demo.finance.currencyExchangeRate.data.service.impl.CurrencyExchangeRateOptionService;
import demo.interaction.wechat.service.impl.WechatOptionService;
import demo.interaction.wechatPay.service.impl.WechatPayOptionService;
import demo.joy.common.service.JoyOptionService;
import demo.joy.garden.service.impl.JoyGardenOptionService;
import demo.promote.service.impl.PromoteOptionService;
import demo.thirdPartyAPI.cloudFlare.service.impl.CloudFlareOptionService;
import demo.thirdPartyAPI.cloudinary.service.impl.CloudinaryOptionService;
import demo.thirdPartyAPI.openAI.service.impl.OpenAiOptionService;
import demo.tool.educate.service.impl.EducateOptionService;
import demo.tool.mail.service.impl.MailOptionService;
import demo.tool.service.impl.ToolOptionService;
import demo.tool.taobao.service.impl.TaobaoProductSourceOptionService;
import demo.tool.textMessageForward.telegram.service.impl.TelegramOptionService;
import demo.tool.zulip.service.impl.ZulipOptionService;

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
	private CryptoCoinDataComplexService cryptoCoinDataComplexService;
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
	@Autowired
	private OpenAiOptionService openAiOptionService;
	@Autowired
	private WechatOptionService wechatOptionService;
	@Autowired
	private CloudFlareOptionService cloudFlareOptionService;
	@Autowired
	private WechatPayOptionService wechatPayOptionService;
	@Autowired
	private AiCommonOptionService aiCommonOptionService;
	@Autowired
	private AiChatOptionService aiChatOptionService;
	@Autowired
	private AiArtOptionService aiArtOptionService;
	@Autowired
	private AiArtService aiArtService;
	@Autowired
	private AiArtCacheService aiArtCacheService;
	@Autowired
	private ToolOptionService toolOptionService;
	@Autowired
	private PromoteOptionService promoteOptionService;
	@Autowired
	private ZulipOptionService zulipOptionService;
	@Autowired
	private CnStockMarketOptionService cnStockMarketOptionService;
	@Autowired
	private TaobaoProductSourceOptionService taobaoProductSourceOptionService;

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
		cryptoCoinDataComplexService.loadSymbolMaxLeverageInfoToCache();
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

	@GetMapping(value = "/refreshOpenAiOption")
	@ResponseBody
	public String refreshOpenAiOption() {
		openAiOptionService.refreshOption();
		return "done";
	}

	@GetMapping(value = "/refreshWechatOption")
	@ResponseBody
	public String refreshWechatOption() {
		wechatOptionService.refreshOption();
		return "done";
	}

	@GetMapping(value = "/refreshAiChatOption")
	@ResponseBody
	public String refreshAiChatOption() {
		aiChatOptionService.refreshOption();
		return aiChatOptionService.toString();
	}

	@GetMapping(value = "/refreshAiCommonOption")
	@ResponseBody
	public String aiCommonOptionService() {
		aiCommonOptionService.refreshOption();
		return "done";
	}

	@GetMapping(value = "/refreshCloudFlareOption")
	@ResponseBody
	public String refreshCloudFlareOption() {
		cloudFlareOptionService.refreshOption();
		return "done";
	}

	@GetMapping(value = "/refreshWechatPayOption")
	@ResponseBody
	public String refreshWechatPayOption() {
		wechatPayOptionService.refreshOption();
		return "done";
	}

	@GetMapping(value = "/refreshAiArtOption")
	@ResponseBody
	public String refreshAiArtOption() {
		aiArtOptionService.refreshOption();
		aiArtService.loadingCache();
		return aiArtOptionService.toString() + ", " + aiArtCacheService.toString();
	}

	@GetMapping(value = "/refreshToolOption")
	@ResponseBody
	public String refreshToolOption() {
		toolOptionService.refreshOption();
		return "done";
	}

	@GetMapping(value = "/refreshPromoteOption")
	@ResponseBody
	public String refreshPromoteOption() {
		promoteOptionService.refreshOption();
		return "done";
	}

	@GetMapping(value = "/refreshZulipOption")
	@ResponseBody
	public String zulipOptionService() {
		zulipOptionService.refreshOption();
		return "done";
	}

	@GetMapping(value = "/refreshCnStockMarketOption")
	@ResponseBody
	public String cnStockMarketOptionService() {
		cnStockMarketOptionService.refreshOption();
		return "done";
	}

	@GetMapping(value = "/refreshTaobaoProductSourceOptionService")
	@ResponseBody
	public String taobaoProductSourceOptionService() {
		taobaoProductSourceOptionService.refreshOption();
		return "done";
	}

}
