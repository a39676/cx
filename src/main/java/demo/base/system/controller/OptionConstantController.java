package demo.base.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.base.system.pojo.constant.BaseUrl;
import demo.base.system.service.OptionConstantManagerService;

@Controller
@RequestMapping(value = BaseUrl.OPTION_CONSTANT)
public class OptionConstantController {

	@Autowired
	private OptionConstantManagerService optionConstantManagerService;

	@GetMapping(value = "/refreshSystemOption")
	@ResponseBody
	public String refreshSystemOption() {
		optionConstantManagerService.refreshSystemOption();
		return "done";
	}
	
	@GetMapping(value = "/refreshArticleOption")
	@ResponseBody
	public String refreshArticleOption() {
		optionConstantManagerService.refreshArticleOption();
		return "done";
	}

	@GetMapping(value = "/refreshArticleCommentOption")
	@ResponseBody
	public String refreshArticleCommentOption() {
		optionConstantManagerService.refreshArticleCommentOption();
		return "done";
	}

	@GetMapping(value = "/refreshMailOption")
	@ResponseBody
	public String refreshMailOption() {
		optionConstantManagerService.refreshMailOption();
		return "done";
	}

	@GetMapping(value = "/refreshCloudinaryOption")
	@ResponseBody
	public String refreshCloudinaryOption() {
		optionConstantManagerService.refreshCloudinaryOption();
		return "done";
	}

	@GetMapping(value = "/refreshAutomationTestOption")
	@ResponseBody
	public String refreshAutomationTestOption() {
		optionConstantManagerService.refreshAutomationTestOption();
		return "done";
	}

	@GetMapping(value = "/refreshCryptoCoinOption")
	@ResponseBody
	public String refreshCryptoCoinOption() {
		optionConstantManagerService.refreshCryptoCoinOption();
		return "done";
	}

	@GetMapping(value = "/refreshTelegramOption")
	@ResponseBody
	public String refreshTelegramOption() {
		optionConstantManagerService.refreshTelegramOption();
		return "done";
	}
	
	@GetMapping(value = "/refreshEducateOption")
	@ResponseBody
	public String refreshEducateOption() {
		return optionConstantManagerService.refreshEducateOption();
	}
	
	@GetMapping(value = "/refreshJoyOption")
	@ResponseBody
	public String refreshJoyOption() {
		optionConstantManagerService.refreshJoyOption();
		return "done";
	}
	
	@GetMapping(value = "/refreshJoyGardenOption")
	@ResponseBody
	public String refreshJoyGardenOption() {
		optionConstantManagerService.refreshJoyGardenOption();
		return "done";
	}
	
}
