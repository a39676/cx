package demo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.base.organizations.pojo.dto.DeleteOrgDTO;
import demo.base.organizations.pojo.dto.OrgRegistDTO;
import demo.base.organizations.pojo.result.OrgRegistResult;
import demo.base.organizations.service.OrganizationService;
import demo.baseCommon.controller.CommonController;
import demo.baseCommon.pojo.result.CommonResultCX;
import demo.config.costom_component.SnowFlake;
import demo.test.pojo.constant.TestUrl;

@Controller
@RequestMapping(value = { TestUrl.root2 })
public class TestController2 extends CommonController {

	@Autowired
	private SnowFlake snowFlake;
	@Autowired
	protected RedisTemplate<String, String> redisTemplate;

	@GetMapping(value = "/snowFlake")
	@ResponseBody
	public String snowFlake() {
		return String.valueOf(snowFlake.getNextId());
	}
	
	@GetMapping(value = "/about")
	public ModelAndView about() {
		return new ModelAndView("cleanBlogJSP/about");
	}

	@Autowired
	private OrganizationService orgService;
	
	@PostMapping(value = "/topOrgRegist")
	@ResponseBody
	public OrgRegistResult topOrgRegist(@RequestBody OrgRegistDTO dto) {
		return orgService.topOrgRegist(dto);
	}

	@PostMapping(value = "/deleteOrg")
	@ResponseBody
	public CommonResultCX deleteOrg(@RequestBody DeleteOrgDTO dto) {
		return orgService.deleteOrg(dto);
	}
}
