package demo.toyParts.educate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.common.controller.CommonController;
import demo.toyParts.educate.pojo.constant.EducateUrl;
import demo.toyParts.educate.service.EducateLeaderboardService;

@Controller
@RequestMapping(value = EducateUrl.ROOT)
public class EducateLeaderboardController extends CommonController {

	@Autowired
	private EducateLeaderboardService educateLeaderboardService;
	
	@GetMapping(value = EducateUrl.LEADERBOARD)
	@ResponseBody
	public ModelAndView leaderboard(@RequestParam("orderType") Integer orderType, @RequestParam ("days") Integer days) {
		return educateLeaderboardService.getLeaderboard(days, orderType);
	}
}
