package demo.toyParts.educate.service.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.base.user.pojo.vo.UsersDetailVO;
import demo.base.user.service.UsersService;
import demo.toyParts.educate.pojo.dto.GetExerciesLeaderboardDTO;
import demo.toyParts.educate.pojo.po.StudentExerciesHistory;
import demo.toyParts.educate.pojo.po.StudentExerciesHistoryExample;
import demo.toyParts.educate.pojo.type.EducateLeaderboardOrderType;
import demo.toyParts.educate.pojo.type.MatchGradeType;
import demo.toyParts.educate.pojo.vo.RankingVO;
import demo.toyParts.educate.service.EducateCommonService;
import demo.toyParts.educate.service.EducateLeaderboardService;

@Service
public class EducateLeaderboardServiceImpl extends EducateCommonService implements EducateLeaderboardService {

	@Autowired
	private UsersService usersService;

	@Override
	public ModelAndView getLeaderboard(GetExerciesLeaderboardDTO dto) {
		ModelAndView view = new ModelAndView("toyJSP/educateJSP/leaderboard");
		if (dto == null || dto.getDays() == null || dto.getOrderType() == null) {
			return view;
		}
		EducateLeaderboardOrderType orderType = EducateLeaderboardOrderType.getType(dto.getOrderType());
		if(orderType == null) {
			return view;
		}
		
		if (dto.getDays() < 1) {
			dto.setDays(7);
		}
		if (dto.getDays() > 30) {
			dto.setDays(30);
		}

		LocalDateTime now = LocalDateTime.now();
		StudentExerciesHistoryExample historyExample = new StudentExerciesHistoryExample();
		historyExample.createCriteria().andCreateTimeBetween(now.minusDays(dto.getDays() - 1).with(LocalTime.MIN), now).andMatchGradeTypeGreaterThanOrEqualTo(MatchGradeType.CURRENT_GRADE.getCode());
		List<StudentExerciesHistory> exerciesHistoryPOList = exerciesHistoryMapper.selectByExample(historyExample);

		List<RankingVO> voList = null;
		if(EducateLeaderboardOrderType.ORDER_BY_SCORE.equals(orderType)) {
			view.addObject("orderType", "按累计成绩排名");
			voList = orderByScore(exerciesHistoryPOList);
		} else {
			view.addObject("orderType", "按累计积分排名");
			voList = orderByPoint(exerciesHistoryPOList);
		}
		view.addObject("calculateDays", dto.getDays());
		view.addObject("leaderboardOrderTypeList", EducateLeaderboardOrderType.values());
		view.addObject("currentLeaderboardOrderType", orderType);
		
		
		view.addObject("leaderboard", voList);
		return view;
	}

	private List<RankingVO> orderByPoint(List<StudentExerciesHistory> exerciesHistoryPOList) {
		Set<Long> userIdSet = new HashSet<>();
		Map<Long, Double> pointMap = new HashMap<>();
		for(StudentExerciesHistory po : exerciesHistoryPOList) {
			if(po.getUserId() == null) {
				continue;
			}
			userIdSet.add(po.getUserId());
			if(pointMap.containsKey(po.getUserId())) {
				pointMap.put(po.getUserId(), pointMap.get(po.getUserId()).doubleValue() + po.getPoints().doubleValue());
			} else {
				pointMap.put(po.getUserId(), po.getPoints().doubleValue());
			}
		}
		
		Map<Long, String> nicknameMap = new HashMap<>();
		for(Long userId : userIdSet) {
			UsersDetailVO userVO = usersService.findUserDetail(userId);
			nicknameMap.put(userId, userVO.getNickName());
		}
		
		List<RankingVO> voList = new ArrayList<>();
		RankingVO vo = null;
		for(Long userId : userIdSet) {
			vo = new RankingVO();
			vo.setNickname(nicknameMap.get(userId));
			vo.setNumber(pointMap.get(userId));
			voList.add(vo);
		}
		
		Collections.sort(voList);
		return voList;
	}
	
	private List<RankingVO> orderByScore(List<StudentExerciesHistory> exerciesHistoryPOList) {
		Set<Long> userIdSet = new HashSet<>();
		Map<Long, Double> scoreMap = new HashMap<>();
		for(StudentExerciesHistory po : exerciesHistoryPOList) {
			if(po.getUserId() == null || po.getScore() == null) {
				continue;
			}
			userIdSet.add(po.getUserId());
			if(scoreMap.containsKey(po.getUserId())) {
				scoreMap.put(po.getUserId(), scoreMap.get(po.getUserId()).doubleValue() + po.getScore().doubleValue());
			} else {
				scoreMap.put(po.getUserId(), po.getScore().doubleValue());
			}
		}
		
		Map<Long, String> nicknameMap = new HashMap<>();
		for(Long userId : userIdSet) {
			UsersDetailVO userVO = usersService.findUserDetail(userId);
			nicknameMap.put(userId, userVO.getNickName());
		}
		
		List<RankingVO> voList = new ArrayList<>();
		RankingVO vo = null;
		for(Long userId : userIdSet) {
			vo = new RankingVO();
			vo.setNickname(nicknameMap.get(userId));
			vo.setNumber(scoreMap.get(userId));
			voList.add(vo);
		}
		
		Collections.sort(voList);
		return voList;
	}
}
