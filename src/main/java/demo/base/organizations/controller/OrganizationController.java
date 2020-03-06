package demo.base.organizations.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.base.organizations.po.constant.OrgManagerView;
import demo.base.organizations.po.constant.OrgUrl;
import demo.base.organizations.pojo.result.FindOrgListResult;
import demo.base.organizations.service.OrganizationService;
import demo.base.user.pojo.dto.FindOrgByConditionDTO;

@Controller
@RequestMapping(value = OrgUrl.root)
public class OrganizationController {

	@Autowired
	private OrganizationService orgService;
	
	@PostMapping(value = OrgUrl.findOrgList)
	@ResponseBody
	public FindOrgListResult findOrgList(@RequestBody FindOrgByConditionDTO dto) {
		return orgService.findOrgList(dto);
	}
	
	@GetMapping(value = OrgUrl.findOrgList)
	public ModelAndView findOrgList() {
		return new ModelAndView(OrgManagerView.orgSearch);
	}
	
	@GetMapping(value = OrgUrl.orgManager)
	public ModelAndView orgManager() {
		return new ModelAndView(OrgManagerView.orgManager);
	}
}
