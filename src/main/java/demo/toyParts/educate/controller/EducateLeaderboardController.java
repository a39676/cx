package demo.toyParts.educate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.common.controller.CommonController;
import demo.toyParts.educate.pojo.constant.EducateUrl;
import demo.toyParts.educate.pojo.dto.GetExerciesLeaderboardDTO;
import demo.toyParts.educate.service.EducateLeaderboardService;

@Controller
@RequestMapping(value = EducateUrl.ROOT)
public class EducateLeaderboardController extends CommonController {

	@Autowired
	private EducateLeaderboardService educateLeaderboardService;
	
	@PostMapping(value = EducateUrl.LEADERBOARD)
	@ResponseBody
	public ModelAndView leaderboard(@RequestBody GetExerciesLeaderboardDTO dto) {
		return educateLeaderboardService.getLeaderboard(dto);
	}
}
