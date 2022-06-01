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

	@GetMapping(value = "/refreshSystemConstant")
	@ResponseBody
	public String refreshSystemConstant() {
		optionConstantManagerService.refreshSystemConstant();
		return "done";
	}
	
	@GetMapping(value = "/refreshArticleConstant")
	@ResponseBody
	public String refreshArticleConstant() {
		optionConstantManagerService.refreshArticleOption();
		return "done";
	}

	@GetMapping(value = "/refreshArticleCommentConstant")
	@ResponseBody
	public String refreshArticleCommentConstant() {
		optionConstantManagerService.refreshArticleCommentOption();
		return "done";
	}

	@GetMapping(value = "/refreshMailConstant")
	@ResponseBody
	public String refreshMailConstant() {
		optionConstantManagerService.refreshMailOption();
		return "done";
	}

	@GetMapping(value = "/refreshCloudinaryConstant")
	@ResponseBody
	public String refreshCloudinaryConstant() {
		optionConstantManagerService.refreshCloudinaryOption();
		return "done";
	}

	@GetMapping(value = "/refreshAutomationTestConstant")
	@ResponseBody
	public String refreshAutomationTestConstant() {
		optionConstantManagerService.refreshAutomationTestOption();
		return "done";
	}

	@GetMapping(value = "/refreshCryptoCoinConstant")
	@ResponseBody
	public String refreshCryptoCoinConstant() {
		optionConstantManagerService.refreshCryptoCoinOption();
		return "done";
	}

	@GetMapping(value = "/refreshTelegramConstant")
	@ResponseBody
	public String refreshTelegramConstant() {
		optionConstantManagerService.refreshTelegramOption();
		return "done";
	}
	
	@GetMapping(value = "/refreshEducateOption")
	@ResponseBody
	public String refreshEducateOption() {
		optionConstantManagerService.refreshEducateOption();
		return "done";
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
