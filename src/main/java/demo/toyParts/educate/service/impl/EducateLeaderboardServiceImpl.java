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
import demo.toyParts.educate.pojo.po.StudentExerciesHistory;
import demo.toyParts.educate.pojo.po.StudentExerciesHistoryExample;
import demo.toyParts.educate.pojo.vo.RankingVO;
import demo.toyParts.educate.service.EducateCommonService;
import demo.toyParts.educate.service.EducateLeaderboardService;

@Service
public class EducateLeaderboardServiceImpl extends EducateCommonService implements EducateLeaderboardService {

	@Autowired
	private UsersService usersService;

	@Override
	public ModelAndView getLeaderboard(Integer days, Integer orderType) {
		ModelAndView view = new ModelAndView("toyJSP/educateJSP/leaderboard");
		if (days == null || orderType == null) {
			return view;
		}
		if (days < 7) {
			days = 7;
		}
		if (days > 30) {
			days = 30;
		}
		if (orderType != 1 && orderType != 2) {
			return view;
		}

		LocalDateTime now = LocalDateTime.now();
		StudentExerciesHistoryExample example = new StudentExerciesHistoryExample();
		example.createCriteria().andCreateTimeBetween(now.minusDays(days - 1).with(LocalTime.MIN), now);
		List<StudentExerciesHistory> exerciesHistoryPOList = exerciesHistoryMapper.selectByExample(example);

		List<RankingVO> voList = null;
		if(orderType == 1) {
			view.addObject("orderType", "按积分排名");
			voList = orderByPoint(exerciesHistoryPOList);
		} else {
			view.addObject("orderType", "按获得成绩排名");
			voList = orderByScore(exerciesHistoryPOList);
		}

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
