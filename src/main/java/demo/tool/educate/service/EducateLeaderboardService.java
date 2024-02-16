package demo.tool.educate.service;

import org.springframework.web.servlet.ModelAndView;

import demo.tool.educate.pojo.dto.GetExerciseLeaderboardDTO;

public interface EducateLeaderboardService {

	ModelAndView getLeaderboard(GetExerciseLeaderboardDTO dto);

}
