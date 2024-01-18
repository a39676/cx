package demo.finance.cashFlow.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.type.CurrencyType;
import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.common.service.CommonService;
import demo.finance.cashFlow.mapper.CashFlowRecordMapper;
import demo.finance.cashFlow.pojo.po.CashFlowRecord;
import demo.finance.cashFlow.pojo.po.CashFlowRecordExample;
import demo.finance.cashFlow.pojo.result.CashFlowSummaryResult;
import demo.finance.cashFlow.pojo.vo.CashFlowRecordVO;
import demo.finance.cashFlow.service.CashFlowService;

@Service
public class CashFlowServiceImpl extends CommonService implements CashFlowService {

	@Autowired
	private CashFlowRecordMapper cashFlowRecordMapper;

	private Integer dayToWeek = 7;
	private Double dayToMonth = 30.42;
	private Double dayToYear = 365.25;
	private Double weekToMonth = 4.5;
	private Double weekToYear = 52.14;
	private Integer monthToYear = 12;

	@Override
	public CashFlowSummaryResult getSummary() {
		CashFlowSummaryResult r = new CashFlowSummaryResult();

		Long userId = baseUtilCustom.getUserId();
		if (userId == null) {
			return r;
		}

		LocalDateTime now = LocalDateTime.now();
		CashFlowRecordExample example = new CashFlowRecordExample();
		example.createCriteria().andUserIdEqualTo(userId).andIsDeleteEqualTo(false).andActiveTimeLessThanOrEqualTo(now)
				.andExpiredTimeGreaterThanOrEqualTo(now);
		List<CashFlowRecord> recordList = cashFlowRecordMapper.selectByExample(example);

		Double dailyFlow = getDailyFlowSummary(recordList);
		r.setDailyFlowSummary(dailyFlow);

		Double monthlyFlow = getMonthlyFlowSummary(recordList);
		r.setMonthlyFlowSummary(monthlyFlow);

		Double annualFlow = getAnnualFlowSummary(recordList);
		r.setAnnualFlowSummary(annualFlow);

		List<CashFlowRecordVO> voList = new ArrayList<>();
		CashFlowRecordVO vo = null;
		for (CashFlowRecord po : recordList) {
			vo = recordToVO(po);
			voList.add(vo);
		}

		r.setRecordList(voList);
		r.setIsSuccess();
		return r;
	}

	private Double getDailyFlowSummary(List<CashFlowRecord> list) {
		Double summary = 0D;

		BigDecimal tmp = null;
		for (CashFlowRecord po : list) {
			tmp = po.getFlowAmount();
			if (TimeUnitType.DAY.getCode().equals(po.getTimeUnitCode())) {
				tmp = tmp.divide(new BigDecimal(po.getTimeCounting()), 2, RoundingMode.HALF_UP);
			} else if (TimeUnitType.WEEK.getCode().equals(po.getTimeUnitCode())) {
				tmp = tmp.divide(new BigDecimal(po.getTimeCounting() * dayToWeek), 2, RoundingMode.HALF_UP);
			} else if (TimeUnitType.MONTH.getCode().equals(po.getTimeUnitCode())) {
				tmp = tmp.divide(new BigDecimal(po.getTimeCounting() * dayToMonth), 2, RoundingMode.HALF_UP);
			} else if (TimeUnitType.YEAR.getCode().equals(po.getTimeUnitCode())) {
				tmp = tmp.divide(new BigDecimal(po.getTimeCounting() * dayToYear), 2, RoundingMode.HALF_UP);
			}

			summary += summary + tmp.doubleValue();
		}

		return summary;
	}

	private Double getMonthlyFlowSummary(List<CashFlowRecord> list) {
		Double summary = 0D;

		BigDecimal tmp = null;
		for (CashFlowRecord po : list) {
			tmp = po.getFlowAmount();
			if (TimeUnitType.DAY.getCode().equals(po.getTimeUnitCode())) {
				tmp = tmp.divide(new BigDecimal(po.getTimeCounting()), 2, RoundingMode.HALF_UP)
						.multiply(new BigDecimal(dayToMonth));
			} else if (TimeUnitType.WEEK.getCode().equals(po.getTimeUnitCode())) {
				tmp = tmp.divide(new BigDecimal(po.getTimeCounting()), 2, RoundingMode.HALF_UP)
						.multiply(new BigDecimal(weekToMonth));
			} else if (TimeUnitType.MONTH.getCode().equals(po.getTimeUnitCode())) {
				tmp = tmp.divide(new BigDecimal(po.getTimeCounting()), 2, RoundingMode.HALF_UP);
			} else if (TimeUnitType.YEAR.getCode().equals(po.getTimeUnitCode())) {
				tmp = tmp.divide(new BigDecimal(po.getTimeCounting() * monthToYear), 2, RoundingMode.HALF_UP);
			}

			summary += summary + tmp.doubleValue();
		}

		return summary;
	}

	private Double getAnnualFlowSummary(List<CashFlowRecord> list) {
		Double summary = 0D;

		BigDecimal tmp = null;
		for (CashFlowRecord po : list) {
			tmp = po.getFlowAmount();
			if (TimeUnitType.DAY.getCode().equals(po.getTimeUnitCode())) {
				tmp = tmp.divide(new BigDecimal(po.getTimeCounting()), 2, RoundingMode.HALF_UP)
						.multiply(new BigDecimal(dayToYear));
			} else if (TimeUnitType.WEEK.getCode().equals(po.getTimeUnitCode())) {
				tmp = tmp.divide(new BigDecimal(po.getTimeCounting()), 2, RoundingMode.HALF_UP)
						.multiply(new BigDecimal(weekToYear));
			} else if (TimeUnitType.MONTH.getCode().equals(po.getTimeUnitCode())) {
				tmp = tmp.divide(new BigDecimal(po.getTimeCounting()), 2, RoundingMode.HALF_UP)
						.multiply(new BigDecimal(monthToYear));
			} else if (TimeUnitType.YEAR.getCode().equals(po.getTimeUnitCode())) {
				tmp = tmp.divide(new BigDecimal(po.getTimeCounting()), 2, RoundingMode.HALF_UP);
			}

			summary += summary + tmp.doubleValue();
		}

		return summary;
	}

	private CashFlowRecordVO recordToVO(CashFlowRecord po) {
		CashFlowRecordVO vo = new CashFlowRecordVO();

		TimeUnitType timeUnit = TimeUnitType.getType(po.getTimeUnitCode());
		if (timeUnit != null) {
			vo.setTimeUnitCode(timeUnit.getCode());
			vo.setTimeUnitName(timeUnit.getName());
		}
		vo.setTimeCounting(po.getTimeCounting());

		CurrencyType currencyType = CurrencyType.getType(po.getCurrencyCode());
		if (currencyType != null) {
			vo.setCurrencyCode(currencyType.getCode());
			vo.setCurrencyName(currencyType.getName());
		}

		vo.setFlowAmount(po.getFlowAmount());

		return vo;
	}
}
