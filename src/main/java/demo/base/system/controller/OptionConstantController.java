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

	@GetMapping(value = "/refreshArticleConstant")
	@ResponseBody
	public String refreshArticleConstant() {
		optionConstantManagerService.refreshArticleConstant();
		return "done";
	}

	@GetMapping(value = "/refreshArticleCommentConstant")
	@ResponseBody
	public String refreshArticleCommentConstant() {
		optionConstantManagerService.refreshArticleCommentConstant();
		return "done";
	}

	@GetMapping(value = "/refreshMailConstant")
	@ResponseBody
	public String refreshMailConstant() {
		optionConstantManagerService.refreshMailConstant();
		return "done";
	}

	@GetMapping(value = "/refreshCloudinaryConstant")
	@ResponseBody
	public String refreshCloudinaryConstant() {
		optionConstantManagerService.refreshCloudinaryConstant();
		return "done";
	}

	@GetMapping(value = "/refreshAutomationTestConstant")
	@ResponseBody
	public String refreshAutomationTestConstant() {
		optionConstantManagerService.refreshAutomationTestConstant();
		return "done";
	}

	@GetMapping(value = "/refreshCryptoCoinConstant")
	@ResponseBody
	public String refreshCryptoCoinConstant() {
		optionConstantManagerService.refreshCryptoCoinConstant();
		return "done";
	}

}
