package demo.tool.educate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.common.controller.CommonController;
import demo.tool.educate.pojo.constant.EducateUrl;
import demo.tool.educate.pojo.dto.GetExerciseLeaderboardDTO;
import demo.tool.educate.service.EducateLeaderboardService;

@Controller
@RequestMapping(value = EducateUrl.ROOT)
public class EducateLeaderboardController extends CommonController {

	@Autowired
	private EducateLeaderboardService educateLeaderboardService;
	
	@PostMapping(value = EducateUrl.LEADERBOARD)
	@ResponseBody
	public ModelAndView leaderboard(@RequestBody GetExerciseLeaderboardDTO dto) {
		return educateLeaderboardService.getLeaderboard(dto);
	}
}
