package demo.toyParts.educate.service;

import org.springframework.web.servlet.ModelAndView;

import demo.toyParts.educate.pojo.dto.GetExerciesLeaderboardDTO;

public interface EducateLeaderboardService {

	ModelAndView getLeaderboard(GetExerciesLeaderboardDTO dto);

}
