package demo.tool.other.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.base.user.pojo.vo.UserIpVO;
import demo.base.user.service.UserIpService;
import demo.tool.other.pojo.constant.TmpToolUrl;
import demo.tool.other.service.ComplexToolService;

@Controller
@RequestMapping(value = TmpToolUrl.root)
public class TmpToolController {
	
	@Autowired
	private UserIpService userIpService;
	@Autowired
	private ComplexToolService complexToolService;
	
	@GetMapping(value = TmpToolUrl.ipHistory)
	@ResponseBody
	public List<UserIpVO> ipHistory() {
		return userIpService.findIpRecordLastMonth();
	}
	
	@GetMapping(value = "/compoundInterest")
	@ResponseBody
	public ModelAndView compoundInterest() {
		return complexToolService.compoundInterest();
	}

}
