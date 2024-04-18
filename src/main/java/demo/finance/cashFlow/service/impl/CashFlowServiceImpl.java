package demo.finance.cashFlow.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.type.CurrencyType;
import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.common.service.CommonService;
import demo.finance.cashFlow.mapper.CashFlowRecordMapper;
import demo.finance.cashFlow.pojo.bo.CashFlowSubSummaryBO;
import demo.finance.cashFlow.pojo.po.CashFlowRecord;
import demo.finance.cashFlow.pojo.po.CashFlowRecordExample;
import demo.finance.cashFlow.pojo.po.CashFlowRecordExample.Criteria;
import demo.finance.cashFlow.pojo.result.CashFlowSummaryResult;
import demo.finance.cashFlow.pojo.vo.CashFlowRecordVO;
import demo.finance.cashFlow.service.CashFlowService;
import demo.finance.currencyExchangeRate.data.service.CurrencyExchangeRateService;

@Service
public class CashFlowServiceImpl extends CommonService implements CashFlowService {

	@Autowired
	private CashFlowRecordMapper cashFlowRecordMapper;
	@Autowired
	private CurrencyExchangeRateService currencyExchangeRateService;

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
		Criteria criteria1 = example.createCriteria();
		criteria1.andUserIdEqualTo(userId).andIsDeleteEqualTo(false).andActiveTimeLessThanOrEqualTo(now)
				.andExpiredTimeGreaterThanOrEqualTo(now);
		Criteria criteria2 = example.createCriteria();
		criteria2.andUserIdEqualTo(userId).andIsDeleteEqualTo(false).andActiveTimeLessThanOrEqualTo(now)
				.andExpiredTimeIsNull();
		example.or(criteria2);
		List<CashFlowRecord> recordList = cashFlowRecordMapper.selectByExample(example);

		log.error("Find " + recordList.size() + " cash flow records");
		CashFlowSubSummaryBO summary = new CashFlowSubSummaryBO();
		CashFlowSubSummaryBO tmpSummary = null;
		for (CashFlowRecord po : recordList) {
			tmpSummary = getFlowSummary(po);
			summary.setDailyFlow(summary.getDailyFlow() + tmpSummary.getDailyFlow());
			summary.setMonthlyFlow(summary.getMonthlyFlow() + tmpSummary.getMonthlyFlow());
			summary.setAnnualFlow(summary.getAnnualFlow() + tmpSummary.getAnnualFlow());
			log.error("Tmp summary: " + tmpSummary.toString());
		}

		r.setDailyFlowSummary(summary.getDailyFlow());
		r.setMonthlyFlowSummary(summary.getMonthlyFlow());
		r.setAnnualFlowSummary(summary.getAnnualFlow());

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

	private CashFlowSubSummaryBO getFlowSummary(CashFlowRecord po) {
		CashFlowSubSummaryBO bo = new CashFlowSubSummaryBO();

		bo.setDailyFlow(getDailyFlowSummary(po).doubleValue());
		bo.setMonthlyFlow(getMonthlyFlowSummary(po).doubleValue());
		bo.setAnnualFlow(getAnnualFlowSummary(po).doubleValue());

		return bo;
	}

	private BigDecimal getDailyFlowSummary(CashFlowRecord po) {
		if (po.getCurrencyCode() == null) {
			return BigDecimal.ZERO;
		}
		CurrencyType currencyType = CurrencyType.getType(po.getCurrencyCode());
		if (currencyType == null) {
			return BigDecimal.ZERO;
		}

		LocalDateTime todayStart = LocalDateTime.now().with(LocalTime.MIN);
		if (po.getExpiredTime() != null && po.getExpiredTime().isBefore(todayStart)) {
			return BigDecimal.ZERO;
		}
		BigDecimal amount = po.getFlowAmount();
		if (TimeUnitType.DAY.getCode().equals(po.getTimeUnitCode())) {
			amount = amount.divide(new BigDecimal(po.getTimeCounting()), 2, RoundingMode.HALF_UP);

		} else if (TimeUnitType.WEEK.getCode().equals(po.getTimeUnitCode())) {
			int days = dayToWeek * po.getTimeCounting();
			amount = amount.divide(new BigDecimal(days), 2, RoundingMode.HALF_UP);

		} else if (TimeUnitType.MONTH.getCode().equals(po.getTimeUnitCode())) {
			double days = dayToMonth * po.getTimeCounting();
			amount = amount.divide(new BigDecimal(days), 2, RoundingMode.HALF_UP);

		} else if (TimeUnitType.YEAR.getCode().equals(po.getTimeUnitCode())) {
			double days = dayToYear * po.getTimeCounting();
			amount = amount.divide(new BigDecimal(days), 2, RoundingMode.HALF_UP);

//		} else if (po.getTimeUnitCode() == null) {
		} else {
			long days = ChronoUnit.DAYS.between(po.getActiveTime(), LocalDateTime.now());
			amount = amount.divide(new BigDecimal(days), 2, RoundingMode.HALF_UP);
		}

		if (!CurrencyType.CNY.equals(currencyType)) {
			Double rate = currencyExchangeRateService.getRate(currencyType, CurrencyType.CNY);
			if (rate == null) {
				return BigDecimal.ZERO;
			}
			amount = amount.multiply(new BigDecimal(rate));
		}

		return amount;
	}

	private BigDecimal getMonthlyFlowSummary(CashFlowRecord po) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime thisMonthStart = now.withDayOfMonth(1).with(LocalTime.MIN);
		if (po.getExpiredTime() != null && po.getExpiredTime().isBefore(thisMonthStart)) {
			return BigDecimal.ZERO;
		}

		LocalDateTime expiredTimeForCalculate = getExpiredTimeForCalculate(po);
		Long steps = getSteps(po, expiredTimeForCalculate);
		if (steps == 0) {
			return BigDecimal.ZERO;
		}

		BigDecimal amount = null;
		if (TimeUnitType.DAY.getCode().equals(po.getTimeUnitCode())) {
			BigDecimal totalAmount = po.getFlowAmount().multiply(new BigDecimal(steps));
			amount = totalAmount.divide(new BigDecimal(dayToMonth), 2, RoundingMode.HALF_UP);

		} else if (TimeUnitType.WEEK.getCode().equals(po.getTimeUnitCode())) {
			BigDecimal totalAmount = po.getFlowAmount().multiply(new BigDecimal(steps));
			amount = totalAmount.divide(new BigDecimal(weekToMonth), 2, RoundingMode.HALF_UP);

		} else if (TimeUnitType.MONTH.getCode().equals(po.getTimeUnitCode())) {
			amount = po.getFlowAmount().divide(new BigDecimal(po.getTimeCounting()), 2, RoundingMode.HALF_UP);

		} else if (TimeUnitType.YEAR.getCode().equals(po.getTimeUnitCode())) {
			amount = po.getFlowAmount().divide((new BigDecimal(po.getTimeCounting() * monthToYear)), 2,
					RoundingMode.HALF_UP);

//		} else if (po.getTimeUnitCode() == null) {
		} else {
			long months = ChronoUnit.MONTHS.between(po.getActiveTime(), LocalDateTime.now());
			if (months == 0) {
				months++;
			}
			amount = po.getFlowAmount().divide(new BigDecimal(months), 2, RoundingMode.HALF_UP);
		}

		return amount;
	}

	private BigDecimal getAnnualFlowSummary(CashFlowRecord po) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime thisYearStart = now.withMonth(1).withDayOfMonth(1);
		if (po.getExpiredTime() != null && po.getExpiredTime().isBefore(thisYearStart)) {
			return BigDecimal.ZERO;
		}

		LocalDateTime expiredTimeForCalculate = getExpiredTimeForCalculate(po);
		Long steps = getSteps(po, expiredTimeForCalculate);
		if (steps == 0) {
			return BigDecimal.ZERO;
		}

		BigDecimal amount = null;
		if (TimeUnitType.DAY.getCode().equals(po.getTimeUnitCode())) {
			BigDecimal totalAmount = po.getFlowAmount().multiply(new BigDecimal(steps));
			amount = totalAmount.divide(new BigDecimal(dayToYear), 2, RoundingMode.HALF_UP);

		} else if (TimeUnitType.WEEK.getCode().equals(po.getTimeUnitCode())) {
			BigDecimal totalAmount = po.getFlowAmount().multiply(new BigDecimal(steps));
			amount = totalAmount.divide(new BigDecimal(weekToYear), 2, RoundingMode.HALF_UP);

		} else if (TimeUnitType.MONTH.getCode().equals(po.getTimeUnitCode())) {
			BigDecimal totalAmount = po.getFlowAmount().multiply(new BigDecimal(steps));
			amount = totalAmount.divide(new BigDecimal(monthToYear), 2, RoundingMode.HALF_UP);

		} else if (TimeUnitType.YEAR.getCode().equals(po.getTimeUnitCode())) {
			amount = po.getFlowAmount().divide(new BigDecimal(po.getTimeCounting()), 2, RoundingMode.HALF_UP);

//		} else if (po.getTimeUnitCode() == null) {
		} else {
			long years = ChronoUnit.YEARS.between(po.getActiveTime(), LocalDateTime.now());
			if (years == 0) {
				years++;
			}
			amount = po.getFlowAmount().divide(new BigDecimal(years), 2, RoundingMode.HALF_UP);
		}

		return amount;
	}

	private LocalDateTime getExpiredTimeForCalculate(CashFlowRecord po) {
		LocalDateTime expiredTime = po.getExpiredTime();
		if (expiredTime == null) {
			LocalDateTime now = LocalDateTime.now();
			if (TimeUnitType.DAY.getCode().equals(po.getTimeUnitCode())) {
				expiredTime = now;
			} else if (TimeUnitType.WEEK.getCode().equals(po.getTimeUnitCode())) {
				expiredTime = now;
				while (!DayOfWeek.SUNDAY.equals(expiredTime.getDayOfWeek())) {
					expiredTime = expiredTime.plusDays(1);
				}
			} else if (TimeUnitType.MONTH.getCode().equals(po.getTimeUnitCode())) {
				expiredTime = now.plusMonths(1).withDayOfMonth(1).minusDays(1);
			} else if (TimeUnitType.YEAR.getCode().equals(po.getTimeUnitCode())) {
				expiredTime = now.withMonth(12).withDayOfMonth(31);
			} else {
				expiredTime = now;
			}
			expiredTime = expiredTime.plusDays(1).with(LocalTime.MIN);
		}

		return expiredTime;
	}

	private Long getSteps(CashFlowRecord po, LocalDateTime expiredTime) {
		Long steps = 0L;
		if (po.getIsPrepaid()) {
			steps++;
		}
		Long gaps = null;
		if (TimeUnitType.DAY.getCode().equals(po.getTimeUnitCode())) {
			gaps = ChronoUnit.DAYS.between(po.getActiveTime(), expiredTime);
		} else if (TimeUnitType.WEEK.getCode().equals(po.getTimeUnitCode())) {
			gaps = ChronoUnit.WEEKS.between(po.getActiveTime(), expiredTime);
		} else if (TimeUnitType.MONTH.getCode().equals(po.getTimeUnitCode())) {
			gaps = ChronoUnit.MONTHS.between(po.getActiveTime(), expiredTime);
		} else if (TimeUnitType.YEAR.getCode().equals(po.getTimeUnitCode())) {
			gaps = ChronoUnit.YEARS.between(po.getActiveTime(), expiredTime);
		} else {
			gaps = ChronoUnit.DAYS.between(po.getActiveTime(), expiredTime);
		}
		if (po.getTimeCounting() != null) {
			steps += gaps.intValue() / po.getTimeCounting();
		} else {
			steps += gaps.intValue();
		}
		return steps;
	}

	private CashFlowRecordVO recordToVO(CashFlowRecord po) {
		CashFlowRecordVO vo = new CashFlowRecordVO();

		TimeUnitType timeUnit = TimeUnitType.getType(po.getTimeUnitCode());
		if (timeUnit != null) {
			vo.setTimeUnitCode(timeUnit.getCode());
			vo.setTimeUnitName(timeUnit.getName());
			vo.setTimeCounting(po.getTimeCounting());
		} else {
			timeUnit = TimeUnitType.DAY;
			vo.setTimeUnitCode(timeUnit.getCode());
			vo.setTimeUnitName(timeUnit.getName());
			Long days = ChronoUnit.DAYS.between(po.getActiveTime(), LocalDateTime.now());
			vo.setTimeCounting(days.intValue());
		}

		CurrencyType currencyType = CurrencyType.getType(po.getCurrencyCode());
		if (currencyType != null) {
			vo.setCurrencyCode(currencyType.getCode());
			vo.setCurrencyName(currencyType.getName());
		}

		vo.setFlowAmount(po.getFlowAmount());
		vo.setRemark(po.getRemark());

		return vo;
	}
}
