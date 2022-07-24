package demo.toyParts.educate.service;

import org.springframework.web.servlet.ModelAndView;

import demo.toyParts.educate.pojo.dto.GetExerciseLeaderboardDTO;

public interface EducateLeaderboardService {

	ModelAndView getLeaderboard(GetExerciseLeaderboardDTO dto);

}
