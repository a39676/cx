package demo.tool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.base.user.pojo.vo.UserIpVO;
import demo.base.user.service.UserIpService;
import demo.tool.pojo.constant.TmpToolUrl;

@Controller
@RequestMapping(value = TmpToolUrl.root)
public class TmpToolController {
	
	@Autowired
	private UserIpService userIpService;
	
	@GetMapping(value = TmpToolUrl.ipHistory)
	@ResponseBody
	public List<UserIpVO> ipHistory() {
		return userIpService.findIpRecordLastMonth();
	}

}
