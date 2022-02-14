package demo.toyParts.educate.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.toyParts.educate.pojo.dto.EducateHomepageDataSummaryDTO;
import demo.toyParts.educate.pojo.dto.EducateHomepageSubjectDataSummaryDTO;
import demo.toyParts.educate.pojo.po.StudentDetail;
import demo.toyParts.educate.pojo.po.StudentExerciesHistory;
import demo.toyParts.educate.pojo.po.StudentExerciesHistoryExample;
import demo.toyParts.educate.pojo.type.EducateLeaderboardOrderType;
import demo.toyParts.educate.pojo.type.ExerciesSubjectType;
import demo.toyParts.educate.pojo.type.GradeType;
import demo.toyParts.educate.pojo.type.MatchGradeType;
import demo.toyParts.educate.service.EducateCommonService;
import demo.toyParts.educate.service.ExerciesHomepageService;

@Service
public class ExerciesHomepageServiceImpl extends EducateCommonService implements ExerciesHomepageService {

	@Override
	public ModelAndView homepage() {
		ModelAndView view = new ModelAndView("toyJSP/educateJSP/EducateIndex");

		Long userId = baseUtilCustom.getUserId();
		if (userId == null) {
			return view;
		}

		view.addObject("nickname", baseUtilCustom.getUserPrincipal().getNickName());

		LocalDateTime now = LocalDateTime.now();
		StudentExerciesHistoryExample exerciesHistoryExample = new StudentExerciesHistoryExample();
		exerciesHistoryExample.createCriteria().andCreateTimeBetween(now.minusDays(30).with(LocalTime.MIN), now)
				.andMatchGradeTypeGreaterThanOrEqualTo(MatchGradeType.CURRENT_GRADE.getCode()).andUserIdEqualTo(userId);
		List<StudentExerciesHistory> exerciesHistoryPOList = exerciesHistoryMapper
				.selectByExample(exerciesHistoryExample);

		EducateHomepageDataSummaryDTO exerciesData = dataSummary(exerciesHistoryPOList);
		view.addObject("exerciesData", exerciesData);

		StudentDetail detailPO = studentDetailMapper.selectByPrimaryKey(userId);
		GradeType gradeType = GradeType.getType(detailPO.getGradeType().intValue());
		view.addObject("gradeType", gradeType);
		view.addObject("points", detailPO.getPointsSummary());
		view.addObject("gradeTypeList", GradeType.values());
		List<ExerciesSubjectType> subjectTypeList = new ArrayList<>();
		subjectTypeList.add(ExerciesSubjectType.math);
		subjectTypeList.add(ExerciesSubjectType.english);
		subjectTypeList.add(ExerciesSubjectType.chinese);
		view.addObject("subjectList", subjectTypeList);
		view.addObject("leaderboardOrderTypeList", EducateLeaderboardOrderType.values());

		return view;
	}

	private EducateHomepageDataSummaryDTO dataSummary(List<StudentExerciesHistory> exerciesHistoryPOList) {
		EducateHomepageDataSummaryDTO dto = new EducateHomepageDataSummaryDTO();

		EducateHomepageSubjectDataSummaryDTO subData = null;
		for (ExerciesSubjectType subject : ExerciesSubjectType.values()) {
			subData = subjectDataSummary(exerciesHistoryPOList, subject);
			dto.getSubjectDataList().add(subData);
		}

		return dto;
	}

	private EducateHomepageSubjectDataSummaryDTO subjectDataSummary(List<StudentExerciesHistory> exerciesHistoryPOList,
			ExerciesSubjectType subject) {
		EducateHomepageSubjectDataSummaryDTO dto = new EducateHomepageSubjectDataSummaryDTO();
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime todayStart = now.with(LocalTime.MIN);
		LocalDateTime sevenDayStart = now.minusDays(7).with(LocalTime.MIN);

		Double totayScoreTotal = 0D;
		Integer todayCounting = 0;
		Double sevenDayScoreTotal = 0D;
		Integer sevenDayCounting = 0;
		Double thirtyDayScoreTotal = 0D;
		Integer thirtyDayCounting = 0;

		dto.setSubjectType(subject);
		for (StudentExerciesHistory po : exerciesHistoryPOList) {
			if (po.getSubjectType().equals(subject.getCode().longValue()) && po.getScore() != null) {
				if (po.getCreateTime().isAfter(sevenDayStart)) {
					sevenDayScoreTotal = sevenDayScoreTotal + po.getScore().doubleValue();
					sevenDayCounting++;
					if (po.getCreateTime().isAfter(todayStart)) {
						totayScoreTotal = totayScoreTotal + po.getScore().doubleValue();
						todayCounting++;
					}
				}
				thirtyDayScoreTotal = thirtyDayScoreTotal + po.getScore().doubleValue();
				thirtyDayCounting++;

				dto.setLastScore(po.getScore().doubleValue());
			}
		}

		if (todayCounting == 0) {
			dto.setAvgScoreToday(0D);
		} else {
			dto.setAvgScoreToday(BigDecimal.valueOf(totayScoreTotal / todayCounting).setScale(3, RoundingMode.HALF_UP)
					.doubleValue());
		}

		if (sevenDayCounting == 0) {
			dto.setAvgScoreSevenDays(0D);
		} else {
			dto.setAvgScoreSevenDays(BigDecimal.valueOf(sevenDayScoreTotal / sevenDayCounting)
					.setScale(3, RoundingMode.HALF_UP).doubleValue());
		}

		if (thirtyDayCounting == 0) {
			dto.setAvgScoreThirtyDays(0D);
		} else {
			dto.setAvgScoreThirtyDays(BigDecimal.valueOf(thirtyDayScoreTotal / thirtyDayCounting)
					.setScale(3, RoundingMode.HALF_UP).doubleValue());
		}
		

		dto.setExerciesCountToday(todayCounting);
		dto.setExerciesCountSevenDays(sevenDayCounting);
		dto.setExerciesCountThirtyDays(thirtyDayCounting);

		dto.setTotalScoreToday(totayScoreTotal);
		dto.setTotalScoreSevenDays(sevenDayScoreTotal);
		dto.setTotalScoreThirtyDays(thirtyDayScoreTotal);
		return dto;
	}

	public ModelAndView personDetail() {
		/*
		 * TODO 提供近半年的统计
		 */

		return null;
	}
}
