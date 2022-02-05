package demo.toyParts.educate.service;

import org.springframework.web.servlet.ModelAndView;

public interface EducateLeaderboardService {

	/**
	 * 
	 * @param days
	 * @param orderType 1 = order by points, 2 = order by score
	 * @return
	 */
	ModelAndView getLeaderboard(Integer days, Integer orderType);

}
