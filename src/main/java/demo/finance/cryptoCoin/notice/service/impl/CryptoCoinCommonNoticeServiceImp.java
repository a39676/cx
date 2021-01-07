package demo.finance.cryptoCoin.notice.service.impl;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.pojo.bo.CryptoCoinPriceCommonDataBO;
import demo.finance.cryptoCoin.data.pojo.constant.CryptoCoinDataConstant;
import demo.finance.cryptoCoin.data.pojo.dto.InsertCryptoCoinPriceNoticeSettingDTO;
import demo.finance.cryptoCoin.data.pojo.result.CryptoCoinNoticeDTOCheckResult;
import demo.finance.cryptoCoin.data.pojo.result.FilterBODataResult;
import demo.finance.cryptoCoin.data.service.CryptoCoin1DayDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin1MinuteDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin1MonthDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin1WeekDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin5MinuteDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin60MinuteDataSummaryService;
import demo.finance.cryptoCoin.notice.mapper.CryptoCoinPriceNoticeMapper;
import demo.finance.cryptoCoin.notice.pojo.po.CryptoCoinPriceNotice;
import demo.finance.cryptoCoin.notice.pojo.po.CryptoCoinPriceNoticeExample;
import demo.finance.cryptoCoin.notice.pojo.po.CryptoCoinPriceNoticeExample.Criteria;
import demo.finance.cryptoCoin.notice.service.CryptoCoinCommonNoticeService;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

@Service
public class CryptoCoinCommonNoticeServiceImp extends CryptoCoinCommonService implements CryptoCoinCommonNoticeService {

	@Autowired
	protected CryptoCoinPriceNoticeMapper noticeMapper;
	@Autowired
	private CryptoCoin1MinuteDataSummaryService _1MinuteDataSummaryService;
	@Autowired
	private CryptoCoin5MinuteDataSummaryService _5MinuteDataSummaryService;
	@Autowired
	private CryptoCoin60MinuteDataSummaryService hourDataSummaryService;
	@Autowired
	private CryptoCoin1DayDataSummaryService dailyDataSummaryService;
	@Autowired
	private CryptoCoin1WeekDataSummaryService weeklyDataSummaryService;
	@Autowired
	private CryptoCoin1MonthDataSummaryService monthlyDataSummaryService;

	@Override
	public ModelAndView insertNewCryptoCoinPriceNoticeSetting() {
		ModelAndView view = new ModelAndView("finance/cryptoCoin/insertNewCryptoCoinPriceNoticeSetting");
		view.addObject("cryptoCoinType", CryptoCoinType.values());
		view.addObject("currencyType", CurrencyType.values());
		TimeUnitType[] timeUnitTypes = new TimeUnitType[] { TimeUnitType.minute, TimeUnitType.hour, TimeUnitType.day,
				TimeUnitType.week, TimeUnitType.month };
		view.addObject("timeUnitType", timeUnitTypes);
		view.addObject("chatVOList", telegramService.getChatIDList());
		return view;
	}

	@Override
	public CommonResult insertNewCryptoCoinPriceNoticeSetting(InsertCryptoCoinPriceNoticeSettingDTO dto) {
		CommonResult r = new CommonResult();
		CryptoCoinNoticeDTOCheckResult checkResult = noticeDTOCheck(dto);
		if (checkResult.isFail()) {
			r.setMessage(checkResult.getMessage());
			return r;
		}

		dto = dtoPrefixHandle(dto);

		CryptoCoinPriceNotice newPO = new CryptoCoinPriceNotice();
		newPO.setId(snowFlake.getNextId());
		newPO.setCoinType(dto.getCoinType());
		newPO.setCurrencyType(dto.getCurrencyType());
		newPO.setTelegramChatPk(dto.getTelegramChatPK());
		newPO.setNoticeCount(dto.getNoticeCount());
		newPO.setMaxPrice(dto.getMaxPrice());
		newPO.setMinPrice(dto.getMinPrice());
		newPO.setTimeUnit(dto.getTimeUnit());
		newPO.setTimeRange(dto.getTimeRange());
		if (dto.getFluctuationSpeedPercentage() != null) {
			newPO.setFluctuationSpeedPercentage(new BigDecimal(dto.getFluctuationSpeedPercentage()));
		}
		newPO.setValidTime(checkResult.getValidTime());
		newPO.setCreateTime(LocalDateTime.now());
		int count = noticeMapper.insertSelective(newPO);

		if (count > 0) {
			r.successWithMessage("insert notice setting: " + dto.toString());
		}
		return r;
	}

	private CryptoCoinNoticeDTOCheckResult noticeDTOCheck(InsertCryptoCoinPriceNoticeSettingDTO dto) {
		CryptoCoinNoticeDTOCheckResult r = new CryptoCoinNoticeDTOCheckResult();

		CryptoCoinType coinType = CryptoCoinType.getType(dto.getCoinType());
		CurrencyType currencyType = CurrencyType.getType(dto.getCurrencyType());
		TimeUnitType timeUnitType = TimeUnitType.getType(dto.getTimeUnit());
		if (dto.getNoticeCount() == null || dto.getNoticeCount() < 0) {
			dto.setNoticeCount(1);
		}

		if (coinType == null || currencyType == null || dto.getTelegramChatPK() == null) {
			r.failWithMessage("error param");
			return r;
		}

		if (!telegramService.chatIdExists(dto.getTelegramChatPK())) {
			r.failWithMessage("error chatPK");
			return r;
		}

		if (dto.getPricePercentage() != null && dto.getPricePercentage() == 0) {
			r.failWithMessage("percent can not equals to 0");
			return r;
		}

		if (dto.getFluctuationSpeedPercentage() != null) {
			if (dto.getFluctuationSpeedPercentage() == 0) {
				r.failWithMessage("percent can not equals to 0");
				return r;
			}
			if (timeUnitType == null || dto.getTimeRange() == null || dto.getTimeRange() < 0) {
				r.failWithMessage("time range setting error");
				return r;
			}
		} else {
			dto.setTimeUnit(null);
		}

		if (!priceConditionHadSet(dto) && !priceRangeConditionHadSet(dto)
				&& !priceFluctuationSpeedConditionHadSet(dto)) {
			r.failWithMessage("please set a condition");
			return r;
		}

		LocalDateTime validDate = null;
		if (dto.getValidTime() == null) {
			validDate = LocalDateTime.now().plusMonths(6);
		} else {
			if (!dateHandler.isDateValid(dto.getValidTime())) {
				r.failWithMessage("please select a valid date");
				return r;
			}
			validDate = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getValidTime());
			/*
			 * FIXME 输入框未有精确到时分秒的, 权宜之计
			 */
			validDate = validDate.withHour(23).withMinute(59).withSecond(59);
			if (validDate.isBefore(LocalDateTime.now())) {
				r.failWithMessage("please select a valid date");
				return r;
			}
		}

		r.setValidTime(validDate);
		r.setIsSuccess();
		return r;
	}

	private InsertCryptoCoinPriceNoticeSettingDTO dtoPrefixHandle(InsertCryptoCoinPriceNoticeSettingDTO dto) {
		if (!priceRangeConditionHadSet(dto)) {
			return dto;
		}
		Double range = dto.getPricePercentage();
		if (range < 0) {
			range = 1 - range;
		}

		dto.setMaxPrice(new BigDecimal(dto.getOriginalPrice() * (1 + range)));
		dto.setMinPrice(new BigDecimal(dto.getOriginalPrice() * (1 - range)));

		dto.setOriginalPrice(null);
		dto.setPricePercentage(null);

		return dto;
	}

	private boolean priceConditionHadSet(InsertCryptoCoinPriceNoticeSettingDTO dto) {
		return dto.getMaxPrice() != null || dto.getMinPrice() != null;
	}

	private boolean priceRangeConditionHadSet(InsertCryptoCoinPriceNoticeSettingDTO dto) {
		return dto.getOriginalPrice() != null && dto.getPricePercentage() != null;
	}

	private boolean priceFluctuationSpeedConditionHadSet(InsertCryptoCoinPriceNoticeSettingDTO dto) {
		Double percentage = dto.getFluctuationSpeedPercentage();
		return (percentage != null && (percentage > 0.01 || percentage < -0.01))
				&& (dto.getTimeUnit() != null && dto.getTimeRange() > 0);
	}

	private boolean priceConditionHadSet(CryptoCoinPriceNotice po) {
		return po.getMaxPrice() != null || po.getMinPrice() != null;
	}

	private boolean priceFluctuationSpeedConditionHadSet(CryptoCoinPriceNotice po) {
		return po.getFluctuationSpeedPercentage() != null
				&& po.getFluctuationSpeedPercentage().compareTo(BigDecimal.ZERO) != 0 && po.getTimeUnit() != null
				&& po.getTimeRange() != null && po.getTimeRange() > 0;
	}

	public void noticeHandler() {
		List<CryptoCoinPriceNotice> noticeList = noticeMapper.selectValidNoticeSetting(LocalDateTime.now());
		if (noticeList == null || noticeList.isEmpty()) {
			return;
		}

		for (CryptoCoinPriceNotice notice : noticeList) {
			subNoticeHandler(notice);
		}

	}

	private void subNoticeHandler(CryptoCoinPriceNotice noticeSetting) {
		CryptoCoinType coinType = CryptoCoinType.getType(noticeSetting.getCoinType());
		if (coinType == null) {
			return;
		}

		CurrencyType currencyType = CurrencyType.getType(noticeSetting.getCurrencyType());
		if (currencyType == null) {
			return;
		}

		TimeUnitType timeUnitType = TimeUnitType.getType(noticeSetting.getTimeUnit());
		if (timeUnitType != null && noticeSetting.getTimeRange() != null && noticeSetting.getNoticeTime() != null) {
			LocalDateTime nextNoticeTime = null;
			if (timeUnitType.equals(TimeUnitType.minute)) {
				nextNoticeTime = noticeSetting.getNoticeTime().plusMinutes(noticeSetting.getTimeRange());
			} else if (timeUnitType.equals(TimeUnitType.hour)) {
				nextNoticeTime = noticeSetting.getNoticeTime().plusHours(noticeSetting.getTimeRange());
			} else if (timeUnitType.equals(TimeUnitType.day)) {
				nextNoticeTime = noticeSetting.getNoticeTime().plusDays(noticeSetting.getTimeRange());
			} else if (timeUnitType.equals(TimeUnitType.week)) {
				nextNoticeTime = noticeSetting.getNoticeTime().plusDays(noticeSetting.getTimeRange() * 7);
			} else if (timeUnitType.equals(TimeUnitType.month)) {
				nextNoticeTime = noticeSetting.getNoticeTime().plusMonths(noticeSetting.getTimeRange());
			}

			if (nextNoticeTime.isAfter(LocalDateTime.now())) {
				return;
			}
		}

		String content = "";
		CommonResult handleResult = null;
		if (priceConditionHadSet(noticeSetting)) {
			handleResult = priceConditionNoticeHandle(noticeSetting, coinType, currencyType);
			if (handleResult.isSuccess()) {
				content += handleResult.getMessage();
			}
		}
		if (priceFluctuationSpeedConditionHadSet(noticeSetting)) {
			handleResult = priceFluctuationSpeedNoticeHandle(noticeSetting, coinType, currencyType);
			if (handleResult.isSuccess()) {
				content += handleResult.getMessage();
			}
		}

		
		if (StringUtils.isNotBlank(content)) {
			CommonResult sendResult = telegramService.sendMessage(content, noticeSetting.getTelegramChatPk());
			if (sendResult.isSuccess()) {
				noticeSetting.setNoticeTime(LocalDateTime.now());
				noticeSetting.setNoticeCount(noticeSetting.getNoticeCount() - 1);
				if (noticeSetting.getNoticeCount() < 1) {
					noticeSetting.setIsDelete(true);
				}
				noticeMapper.updateByPrimaryKeySelective(noticeSetting);
			}
		}

	}

	private CommonResult priceConditionNoticeHandle(CryptoCoinPriceNotice noticeSetting, CryptoCoinType coinType,
			CurrencyType currencyType) {
		CommonResult r = new CommonResult();
		List<CryptoCoinPriceCommonDataBO> historyPOList = _1MinuteDataSummaryService.getCommonData(coinType,
				currencyType, LocalDateTime.now().minusMinutes(2));

		if (historyPOList == null || historyPOList.isEmpty()) {
			return r;
		}

		FilterBODataResult maxMinPriceResult = filterData(historyPOList);
		if (maxMinPriceResult.isFail()) {
			r.addMessage(maxMinPriceResult.getMessage());
			return r;
		}
		
		BigDecimal lastMaxPrice = maxMinPriceResult.getMaxPrice();
		BigDecimal lastMinPrice = maxMinPriceResult.getMinPrice();
		String content = null;

		if (noticeSetting.getMaxPrice() != null) {
			if (lastMaxPrice.doubleValue() >= noticeSetting.getMaxPrice().doubleValue()) {
				content = coinType.getName() + ", " + currencyType + ", " + " price(range) had reach " + lastMaxPrice
						+ " at: " + maxMinPriceResult.getMaxPriceDateTime() + ";";
			}

		}
		if (noticeSetting.getMinPrice() != null) {
			if (lastMinPrice.doubleValue() <= noticeSetting.getMinPrice().doubleValue()) {
				content = coinType.getName() + ", " + currencyType + ", " + " price(range) had reach " + lastMinPrice
						+ " at: " + maxMinPriceResult.getMinPriceDateTime() + ";";
			}
		}

		if (content != null) {
			r.successWithMessage(content);
		}

		return r;
	}

	private CommonResult priceFluctuationSpeedNoticeHandle(CryptoCoinPriceNotice noticeSetting, CryptoCoinType coinType,
			CurrencyType currencyType) {
		CommonResult r = new CommonResult();

		List<CryptoCoinPriceCommonDataBO> historyPOList = findHistoryData(coinType, currencyType,
				noticeSetting.getTimeUnit(), noticeSetting.getTimeRange());
		if (historyPOList == null || historyPOList.isEmpty()) {
			return r;
		}

		FilterBODataResult maxMinPriceResult = filterData(historyPOList);
		if (maxMinPriceResult.isFail()) {
			r.addMessage(maxMinPriceResult.getMessage());
			return r;
		}

		double lastMax = maxMinPriceResult.getMaxPrice().doubleValue();
		double lastMin = maxMinPriceResult.getMinPrice().doubleValue();

		Double upApmlitude = null;
		Double lowApmlitude = null;

		upApmlitude = (lastMax / lastMin - 1) * 100;
		lowApmlitude = (lastMin / lastMax - 1) * 100;

		String content = null;
		Double trigerPercentage = noticeSetting.getFluctuationSpeedPercentage().doubleValue();
		if (trigerPercentage < 0) {
			trigerPercentage = 0 - trigerPercentage;
		}

		if (upApmlitude >= trigerPercentage) {
//			String pattern = "虚拟币名, 标价货币名, 最近 x 小时/分钟, 升幅达 %s, 
//			%s时触及最低价 %s, %s时触及最高价 %s, 最新价为 %s";
			String pattern = "%s, %s, 最近 %s %s, 升幅达 %s%, %s时触及低价 %s, %s时触及高价 %s, 最新价为 %s";
			content = String.format(pattern, coinType.getName(), currencyType, noticeSetting.getTimeRange(),
					TimeUnitType.getType(noticeSetting.getTimeUnit()).getCnName(), String.valueOf(trigerPercentage),
					maxMinPriceResult.getMinPriceDateTime(), lastMin, maxMinPriceResult.getMaxPriceDateTime(), lastMax,
					historyPOList.get(0).getEndPrice());
		} else if (trigerPercentage < 0 && lowApmlitude <= trigerPercentage) {
			String pattern = "%s, %s, 最近 %s %s, 跌幅达 %s%, %s时触及高价 %s, %s时触及低价 %s, 最新价为 %s";
			content = String.format(pattern, coinType.getName(), currencyType, noticeSetting.getTimeRange(),
					TimeUnitType.getType(noticeSetting.getTimeUnit()).getCnName(), String.valueOf(trigerPercentage),
					maxMinPriceResult.getMaxPriceDateTime(), lastMax, maxMinPriceResult.getMinPriceDateTime(), lastMin,
					historyPOList.get(0).getEndPrice());
		}

		if (content != null) {
			r.successWithMessage(content);
		}

		return r;
	}

	private List<CryptoCoinPriceCommonDataBO> findHistoryData(CryptoCoinType coinType, CurrencyType currencyType,
			Integer timeUnit, Integer timeRange) {
		LocalDateTime startTime = null;
		if (TimeUnitType.minute.getCode().equals(timeUnit)) {
			startTime = LocalDateTime.now().minusMinutes(timeRange);
			if (CryptoCoinDataConstant.CRYPTO_COIN_1MINUTE_DATA_LIVE_HOURS * 60 > timeRange) {
				return _1MinuteDataSummaryService.getCommonData(coinType, currencyType, startTime);
			} else if (CryptoCoinDataConstant.CRYPTO_COIN_5MINUTE_DATA_LIVE_HOURS * 60 > timeRange) {
				return _5MinuteDataSummaryService.getCommonData(coinType, currencyType, startTime);
			}
		} else if (TimeUnitType.hour.getCode().equals(timeUnit)) {
			startTime = LocalDateTime.now().minusHours(timeRange);
			return hourDataSummaryService.getCommonData(coinType, currencyType, startTime);
		} else if (TimeUnitType.day.getCode().equals(timeUnit)) {
			startTime = LocalDateTime.now().minusDays(timeRange);
			return dailyDataSummaryService.getCommonData(coinType, currencyType, startTime);
		} else if (TimeUnitType.week.getCode().equals(timeUnit)) {
			LocalDateTime lastSunday = localDateTimeHandler.findLastDayOfWeek(LocalDateTime.now(), DayOfWeek.SUNDAY);
			startTime = lastSunday.minusDays((timeRange - 1) * 7);
			return weeklyDataSummaryService.getCommonData(coinType, currencyType, startTime);
		} else if (TimeUnitType.month.getCode().equals(timeUnit)) {
			startTime = LocalDateTime.now().withDayOfMonth(1).minusMonths(timeRange - 1);
			return monthlyDataSummaryService.getCommonData(coinType, currencyType, startTime);
		}

		return new ArrayList<CryptoCoinPriceCommonDataBO>();
	}

	@Override
	public void deleteOldNotice() {
		CryptoCoinPriceNoticeExample example = new CryptoCoinPriceNoticeExample();
		Criteria noticedCriteria = example.createCriteria();
		noticedCriteria.andNoticeTimeIsNotNull();
		Criteria validTimeCriteria = example.createCriteria();
		validTimeCriteria.andValidTimeLessThan(LocalDateTime.now());
		example.or(noticedCriteria);
		noticeMapper.deleteByExample(example);
	}
}
