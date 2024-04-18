package demo.finance.currencyExchangeRate.notice.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.finance.common.service.impl.FinanceCommonService;
import demo.finance.currencyExchangeRate.data.mapper.CurrencyExchangeRate1dayMapper;
import demo.finance.currencyExchangeRate.data.pojo.po.CurrencyExchangeRate1day;
import demo.finance.currencyExchangeRate.data.pojo.po.CurrencyExchangeRate1dayExample;
import demo.finance.currencyExchangeRate.data.pojo.result.FilterDataResult;
import demo.finance.currencyExchangeRate.notice.mapper.CurrencyExchangeRateNoticeMapper;
import demo.finance.currencyExchangeRate.notice.pojo.dto.InsertCurrencyExchangeRateNoticeSettingDTO;
import demo.finance.currencyExchangeRate.notice.pojo.po.CurrencyExchangeRateNotice;
import demo.finance.currencyExchangeRate.notice.pojo.po.CurrencyExchangeRateNoticeExample;
import demo.finance.currencyExchangeRate.notice.pojo.po.CurrencyExchangeRateNoticeExample.Criteria;
import demo.finance.currencyExchangeRate.notice.pojo.result.CurrencyExchangeRateNoticeDTOCheckResult;
import demo.finance.currencyExchangeRate.notice.service.CurrencyExchangeRateNoticeService;
import demo.tool.textMessageForward.telegram.pojo.po.TelegramChatId;
import demo.tool.textMessageForward.telegram.pojo.vo.TelegramChatIdVO;
import demo.tool.textMessageForward.telegram.service.TelegramService;
import telegram.pojo.type.TelegramBotType;

@Service
public class CurrencyExchangeRateNoticeServiceImpl extends FinanceCommonService
		implements CurrencyExchangeRateNoticeService {

	@Autowired
	private TelegramService telegramService;

	@Autowired
	private CurrencyExchangeRateNoticeMapper noticeMapper;
	@Autowired
	private CurrencyExchangeRate1dayMapper dataMapper;

	@Override
	public ModelAndView currencyExhchangeRateNoticeSettingManager() {
		ModelAndView view = new ModelAndView("finance/currencyExchangeRate/CurrencyExchangeRateNoticeSettingManager");
		List<CurrencyType> currencyTypeList = new ArrayList<>();
		currencyTypeList.add(CurrencyType.USD);
		currencyTypeList.addAll(Arrays.asList(CurrencyType.values()));
		view.addObject("currencyType", currencyTypeList);

		TimeUnitType[] timeUnitTypes = new TimeUnitType[] { TimeUnitType.MINUTE, TimeUnitType.HOUR, TimeUnitType.DAY,
				TimeUnitType.WEEK, TimeUnitType.MONTH };
		view.addObject("timeUnitType", timeUnitTypes);

		List<TelegramChatId> chatIDPOList = telegramService.getChatIDList();
		List<TelegramChatIdVO> chatIdVOList = new ArrayList<>();
		for (TelegramChatId po : chatIDPOList) {
			chatIdVOList.add(telegramService.buildChatIdVO(po));
		}
		view.addObject("chatVOList", chatIdVOList);
		return view;
	}

	@Override
	public CommonResult insertNewCryptoCoinPriceNoticeSetting(InsertCurrencyExchangeRateNoticeSettingDTO dto) {
		CommonResult r = new CommonResult();
		CurrencyExchangeRateNoticeDTOCheckResult checkResult = noticeDTOCheck(dto);
		if (checkResult.isFail()) {
			r.setMessage(checkResult.getMessage());
			return r;
		}

		dto = dtoPrefixHandle(dto);

		CurrencyType currencyFrom = CurrencyType.getType(dto.getFromCurrencyType());
		CurrencyType currencyTo = CurrencyType.getType(dto.getToCurrencyType());

		CurrencyExchangeRateNotice newPO = new CurrencyExchangeRateNotice();
		newPO.setId(snowFlake.getNextId());
		newPO.setTelegramBotName(TelegramBotType.BBT_MESSAGE.getName());
		newPO.setCurrencyFrom(currencyFrom.getCode());
		newPO.setCurrencyTo(currencyTo.getCode());
		newPO.setTelegramChatId(systemOptionService.decryptPrivateKey(dto.getTelegramChatPK()));
		newPO.setNoticeCount(dto.getNoticeCount());
		newPO.setMaxRate(dto.getMaxRate());
		newPO.setMinRate(dto.getMinRate());
		newPO.setTimeUnitOfDataWatch(dto.getTimeUnitOfDataWatch());
		newPO.setTimeRangeOfDataWatch(dto.getTimeRangeOfDataWatch());
		if (dto.getFluctuationSpeedPercentage() != null) {
			newPO.setFluctuationSpeedPercentage(new BigDecimal(dto.getFluctuationSpeedPercentage()));
		}
		newPO.setTimeUnitOfNoticeInterval(dto.getTimeUnitOfNoticeInterval());
		newPO.setTimeRangeOfNoticeInterval(dto.getTimeRangeOfNoticeInterval());
		newPO.setValidTime(checkResult.getValidTime());
		newPO.setCreateTime(LocalDateTime.now());
		try {
			newPO.setNextNoticeTime(localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getStartNoticeTime()));
		} catch (Exception e) {
		}
		int count = noticeMapper.insertSelective(newPO);

		if (count > 0) {
			r.successWithMessage("insert notice setting: " + dto.toString());
		}
		return r;
	}

	private CurrencyExchangeRateNoticeDTOCheckResult noticeDTOCheck(InsertCurrencyExchangeRateNoticeSettingDTO dto) {
		CurrencyExchangeRateNoticeDTOCheckResult r = new CurrencyExchangeRateNoticeDTOCheckResult();

		if (dto.getFromCurrencyType() == null || dto.getToCurrencyType() == null) {
			r.setMessage("Please set correct currency type");
			return r;
		}

		dto.setFromCurrencyType(dto.getFromCurrencyType().toUpperCase());
		dto.setToCurrencyType(dto.getToCurrencyType().toUpperCase());

		CurrencyType fromCurrencyType = CurrencyType.getType(dto.getFromCurrencyType());
		CurrencyType toCurrencyType = CurrencyType.getType(dto.getToCurrencyType());

		if (dto.getNoticeCount() == null || dto.getNoticeCount() < 0) {
			dto.setNoticeCount(1);
		} else if (dto.getNoticeCount() > 1) {
			TimeUnitType timeUnitTypeOfNoticeInterval = TimeUnitType.getType(dto.getTimeUnitOfNoticeInterval());
			if (timeUnitTypeOfNoticeInterval == null || dto.getTimeRangeOfNoticeInterval() == null
					|| dto.getTimeRangeOfNoticeInterval() < 1) {
				r.failWithMessage("please set notice interval");
				return r;
			}
		}

		if (fromCurrencyType == null || toCurrencyType == null || dto.getTelegramChatPK() == null) {
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
			TimeUnitType timeUnitTypeOfDataWatch = TimeUnitType.getType(dto.getTimeUnitOfDataWatch());
			if (timeUnitTypeOfDataWatch == null || dto.getTimeRangeOfDataWatch() == null
					|| dto.getTimeRangeOfDataWatch() < 0) {
				r.failWithMessage("data watch time range setting error");
				return r;
			}
		} else {
			dto.setTimeUnitOfDataWatch(null);
		}

		if (!rateConditionHadSet(dto) && !rateRangeConditionHadSet(dto) && !rateFluctuationSpeedConditionHadSet(dto)) {
			r.failWithMessage("please set a condition");
			return r;
		}

		LocalDateTime validDate = null;
		if (StringUtils.isBlank(dto.getValidTime())) {
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
			if (validDate.getHour() == 0 || validDate.getMinute() == 0 || validDate.getSecond() == 0) {
				validDate = validDate.with(LocalTime.MAX);
			}
			if (validDate.isBefore(LocalDateTime.now())) {
				r.failWithMessage("please select a valid date");
				return r;
			}
		}

		r.setValidTime(validDate);
		r.setIsSuccess();
		return r;
	}

	private boolean rateConditionHadSet(InsertCurrencyExchangeRateNoticeSettingDTO dto) {
		return dto.getMaxRate() != null || dto.getMinRate() != null;
	}

	private boolean rateRangeConditionHadSet(InsertCurrencyExchangeRateNoticeSettingDTO dto) {
		return dto.getOriginalRate() != null && dto.getPricePercentage() != null;
	}

	private boolean rateFluctuationSpeedConditionHadSet(InsertCurrencyExchangeRateNoticeSettingDTO dto) {
		Double percentage = dto.getFluctuationSpeedPercentage();
		return (percentage != null && (percentage > 0.01 || percentage < -0.01))
				&& (dto.getTimeUnitOfDataWatch() != null && dto.getTimeRangeOfDataWatch() > 0);
	}

	private boolean rateConditionHadSet(CurrencyExchangeRateNotice po) {
		return po.getMaxRate() != null || po.getMinRate() != null;
	}

	private boolean rateFluctuationSpeedConditionHadSet(CurrencyExchangeRateNotice po) {
		return po.getFluctuationSpeedPercentage() != null
				&& po.getFluctuationSpeedPercentage().compareTo(BigDecimal.ZERO) != 0
				&& po.getTimeUnitOfDataWatch() != null && po.getTimeRangeOfDataWatch() != null
				&& po.getTimeRangeOfDataWatch() > 0;
	}

	private InsertCurrencyExchangeRateNoticeSettingDTO dtoPrefixHandle(InsertCurrencyExchangeRateNoticeSettingDTO dto) {
		if (!rateRangeConditionHadSet(dto)) {
			return dto;
		}
		Double range = dto.getPricePercentage();
		if (range < 0) {
			range = 1 - range;
		}

		dto.setMaxRate(new BigDecimal(dto.getOriginalRate() * (1 + range / 100)));
		dto.setMinRate(new BigDecimal(dto.getOriginalRate() * (1 - range / 100)));

		dto.setOriginalRate(null);
		dto.setPricePercentage(null);

		return dto;
	}

	@Override
	public void deleteOldNotice() {
		CurrencyExchangeRateNoticeExample example = new CurrencyExchangeRateNoticeExample();
		Criteria noticedCriteria = example.createCriteria();
		noticedCriteria.andNoticeTimeIsNotNull().andNoticeCountLessThanOrEqualTo(0);
		Criteria validTimeCriteria = example.createCriteria();
		validTimeCriteria.andValidTimeLessThan(LocalDateTime.now());
		example.or(noticedCriteria);
		CurrencyExchangeRateNotice record = new CurrencyExchangeRateNotice();
		record.setIsDelete(true);
		noticeMapper.updateByExampleSelective(record, example);
	}

	@Override
	public void noticeHandler() {
		LocalDateTime now = LocalDateTime.now();
		CurrencyExchangeRateNoticeExample noticeExample = new CurrencyExchangeRateNoticeExample();
		noticeExample.createCriteria().andIsDeleteEqualTo(false).andNoticeCountGreaterThan(0)
				.andValidTimeGreaterThan(now).andNextNoticeTimeLessThan(now);

		List<CurrencyExchangeRateNotice> noticeList = noticeMapper.selectByExample(noticeExample);
		log.error("Find " + noticeList.size() + " currency exchange rate notice setting.");
		if (noticeList == null || noticeList.isEmpty()) {
			return;
		}

		for (CurrencyExchangeRateNotice notice : noticeList) {
			try {
				subNoticeHandler(notice);
			} catch (Exception e) {
				log.error(notice.getId() + ", hit error: " + e.getLocalizedMessage());
			}
		}

	}

	private CommonResult subNoticeHandler(CurrencyExchangeRateNotice notice) {
		CommonResult r = new CommonResult();

		CurrencyType currencyFrom = CurrencyType.getType(notice.getCurrencyFrom());
		CurrencyType currencyTo = CurrencyType.getType(notice.getCurrencyTo());
		if (currencyFrom == null || currencyTo == null) {
			log.error(notice.getId() + ", currency from: " + notice.getCurrencyFrom() + ", currency to: "
					+ notice.getCurrencyTo() + ", currency type setting error");
			r.failWithMessage("currency type setting error");
			return r;
		}

		/*
		 * if it is REUSE notice, notice count should > 1, event in last round, notice
		 * time will not be null
		 */
		LocalDateTime nextNoticeTime = notice.getNextNoticeTime();
		if (nextNoticeTime == null || nextNoticeTime.isAfter(LocalDateTime.now())) {
			log.error(notice.getId() + ", currency from: " + notice.getCurrencyFrom() + ", currency to: "
					+ notice.getCurrencyTo() + ", next notice time NOT match");
			return r;
		}

		String content = "";
		CommonResult handleResult = null;

		if (rateConditionHadSet(notice)) {
			log.error(notice.getId() + ", currency from: " + notice.getCurrencyFrom() + ", currency to: "
					+ notice.getCurrencyTo() + ", rate condition had set");
			handleResult = rateConditionNoticeHandle(notice, currencyFrom, currencyTo);
			if (handleResult.isSuccess()) {
				content += handleResult.getMessage();
			} else {
				log.error(notice.getId() + ", currency from: " + notice.getCurrencyFrom() + ", currency to: "
						+ notice.getCurrencyTo() + ", error: " + handleResult.getMessage());
			}
		}
		if (rateFluctuationSpeedConditionHadSet(notice)) {
			log.error(notice.getId() + ", currency from: " + notice.getCurrencyFrom() + ", currency to: "
					+ notice.getCurrencyTo() + ", rate fluctuation speed condition had set");
			handleResult = rateFluctuationSpeedNoticeHandle(notice, currencyFrom, currencyTo);
			if (handleResult.isSuccess()) {
				content += handleResult.getMessage();
			} else {
				log.error(notice.getId() + ", currency from: " + notice.getCurrencyFrom() + ", currency to: "
						+ notice.getCurrencyTo() + ", error: " + handleResult.getMessage());
			}
		}

		if (StringUtils.isNotBlank(content)) {
			if (!"dev".equals(systemOptionService.getEnvName())) {
				telegramService.sendMessageByChatRecordId(TelegramBotType.getType(notice.getTelegramBotName()), content,
						notice.getTelegramChatId());
			} else {
				log.error("In dev env, will NOT send msg: " + content + ", chat ID: " + notice.getTelegramChatId()
						+ ", bot name: " + notice.getTelegramBotName());
			}

			notice.setNoticeTime(LocalDateTime.now());
			notice.setNoticeCount(notice.getNoticeCount() - 1);
			if (notice.getNoticeCount() < 1) {
				notice.setIsDelete(true);
			} else {
				TimeUnitType timeUnitTypeOfNoticeInterval = TimeUnitType.getType(notice.getTimeUnitOfNoticeInterval());
				if (notice.getNoticeTime() == null) {
					notice.setNoticeTime(LocalDateTime.now());
				}
				nextNoticeTime = getNextSettingTime(notice.getNoticeTime(), timeUnitTypeOfNoticeInterval,
						notice.getTimeRangeOfNoticeInterval().longValue());
				notice.setNextNoticeTime(nextNoticeTime);
			}
			noticeMapper.updateByPrimaryKeySelective(notice);
			r.successWithMessage("notice sended");
			return r;
		} else {
			log.error(notice.getId() + ", currency from: " + notice.getCurrencyFrom() + ", currency to: "
					+ notice.getCurrencyTo() + ", didn't hit any notice setting");
			r.failWithMessage("Didn't hit any notice setting");
			return r;
		}

	}

	private CommonResult rateConditionNoticeHandle(CurrencyExchangeRateNotice noticeSetting, CurrencyType currencyFrom,
			CurrencyType currencyTo) {
		CommonResult r = new CommonResult();
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime todayStart = now.with(LocalTime.MIN);
		CurrencyExchangeRate1dayExample dataExample = new CurrencyExchangeRate1dayExample();
		dataExample.createCriteria().andCurrencyFromEqualTo(currencyFrom.getCode())
				.andCurrencyToEqualTo(currencyTo.getCode()).andStartTimeEqualTo(todayStart);
		List<CurrencyExchangeRate1day> historyDataList = dataMapper.selectByExample(dataExample);
//		TODO need order by?

		if (historyDataList == null || historyDataList.isEmpty()) {
			log.error(noticeSetting.getId() + ", can NOT find any history data of: from " + currencyFrom.getName()
					+ ", to " + currencyTo.getName());
			return r;
		}

		FilterDataResult maxMinPriceResult = filterDataForCurrencyExchangeRate(historyDataList);
		log.error(noticeSetting.getId() + ", currency from: " + noticeSetting.getCurrencyFrom() + ", currency to: "
				+ noticeSetting.getCurrencyTo() + ", max min price result: " + maxMinPriceResult.toString());
		if (maxMinPriceResult.isFail()) {
			r.addMessage(maxMinPriceResult.getMessage());
			return r;
		}

		BigDecimal lastMaxRate = maxMinPriceResult.getMaxRate();
		BigDecimal lastMinRate = maxMinPriceResult.getMinRate();
		String content = null;

		if (noticeSetting.getMaxRate() != null) {
			if (lastMaxRate.doubleValue() >= noticeSetting.getMaxRate().doubleValue()) {
				content = "From " + currencyFrom.getName() + ", to " + currencyTo.getName() + ", had RISE to "
						+ numberSetScale(lastMaxRate);
			}

		}
		if (noticeSetting.getMinRate() != null) {
			if (lastMinRate.doubleValue() <= noticeSetting.getMinRate().doubleValue()) {
				content = "From " + currencyFrom.getName() + ", to " + currencyTo.getName() + ", had FELL to "
						+ numberSetScale(lastMinRate);
			}
		}

		if (content != null) {
			r.successWithMessage(content);
		}

		return r;
	}

	private CommonResult rateFluctuationSpeedNoticeHandle(CurrencyExchangeRateNotice noticeSetting,
			CurrencyType currencyFrom, CurrencyType currencyTo) {
		CommonResult r = new CommonResult();

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime todayStart = now.with(LocalTime.MIN);
		CurrencyExchangeRate1dayExample dataExample = new CurrencyExchangeRate1dayExample();
		dataExample.createCriteria().andCurrencyFromEqualTo(currencyFrom.getCode())
				.andCurrencyToEqualTo(currencyTo.getCode()).andStartTimeEqualTo(todayStart);
		List<CurrencyExchangeRate1day> historyDataList = dataMapper.selectByExample(dataExample);
//		TODO need order by?
		if (historyDataList == null || historyDataList.isEmpty()) {
			log.error(noticeSetting.getId() + ", can NOT find any history data of: from " + currencyFrom.getName()
					+ ", to " + currencyTo.getName());
			return r;
		}

		FilterDataResult maxMinPriceResult = filterDataForCurrencyExchangeRate(historyDataList);
		log.error(noticeSetting.getId() + ", currency from: " + noticeSetting.getCurrencyFrom() + ", currency to: "
				+ noticeSetting.getCurrencyTo() + ", max min price result: " + maxMinPriceResult.toString());
		if (maxMinPriceResult.isFail()) {
			r.addMessage(maxMinPriceResult.getMessage());
			return r;
		}

		double lastMax = maxMinPriceResult.getMaxRate().doubleValue();
		double lastMin = maxMinPriceResult.getMinRate().doubleValue();

		Double upApmlitude = (lastMax / lastMin - 1) * 100;
		Double lowApmlitude = (lastMin / lastMax - 1) * 100;

		String content = null;
		Double trigerPercentage = noticeSetting.getFluctuationSpeedPercentage().doubleValue();
		if (trigerPercentage < 0) {
			trigerPercentage = 0 - trigerPercentage;
		}

		if (!maxMinPriceResult.getMinRateDateTime().isAfter(maxMinPriceResult.getMaxRateDateTime())) {
			if (upApmlitude >= trigerPercentage) {
				BigDecimal upApmlitudeBigDecimal = new BigDecimal(upApmlitude);
				content = "From " + currencyFrom.getName() + ", to " + currencyTo.getName() + ", " + " rate: "
						+ numberSetScale(historyDataList.get(historyDataList.size() - 1).getBuyAvgPrice()) + "\n" + ", "
						+ "最近" + noticeSetting.getTimeRangeOfDataWatch()
						+ TimeUnitType.getType(noticeSetting.getTimeUnitOfDataWatch()).getCnName() + ", " + "波幅达 "
						+ upApmlitudeBigDecimal.setScale(2, RoundingMode.HALF_UP) + "%" + "\n" + ", "
						+ maxMinPriceResult.getMinRateDateTime() + " 时触及低价: " + lastMin + "\n" + ", "
						+ maxMinPriceResult.getMaxRateDateTime() + " 时触及高价: " + lastMax;
			}
		} else {
			if ((0 - lowApmlitude) >= trigerPercentage) {
				BigDecimal lowApmlitubeBigDecimal = new BigDecimal(lowApmlitude);
				content = "From " + currencyFrom.getName() + ", to " + currencyTo.getName() + ", " + " rate: "
						+ numberSetScale(historyDataList.get(historyDataList.size() - 1).getBuyAvgPrice()) + "\n" + ", "
						+ "最近" + noticeSetting.getTimeRangeOfDataWatch()
						+ TimeUnitType.getType(noticeSetting.getTimeUnitOfDataWatch()).getCnName() + ", " + "波幅达 "
						+ lowApmlitubeBigDecimal.setScale(2, RoundingMode.HALF_UP) + "%" + "\n" + ", "
						+ maxMinPriceResult.getMaxRateDateTime() + " 时触及高价: " + lastMax + "\n" + ", "
						+ maxMinPriceResult.getMinRateDateTime() + " 时触及低价: " + lastMin;
			}
		}

		if (content != null) {
			r.successWithMessage(content);
		}

		return r;
	}

	private FilterDataResult filterDataForCurrencyExchangeRate(List<CurrencyExchangeRate1day> list) {
		FilterDataResult r = new FilterDataResult();

		if (list == null || list.isEmpty()) {
			r.setMessage("empty history data");
			return r;
		}

		double maxRate = Double.MIN_VALUE;
		double minRate = Double.MAX_VALUE;
		LocalDateTime maxRateDateTime = null;
		LocalDateTime minRateDateTime = null;
		LocalDateTime startTime = null;
		LocalDateTime endTime = null;
		for (CurrencyExchangeRate1day po : list) {
			if (po.getBuyHighPrice() != null && po.getBuyHighPrice().doubleValue() > maxRate) {
				maxRate = po.getBuyHighPrice().doubleValue();
				maxRateDateTime = po.getStartTime();
			}

			if (po.getSellHighPrice() != null && po.getSellHighPrice().doubleValue() > maxRate) {
				maxRate = po.getSellHighPrice().doubleValue();
				maxRateDateTime = po.getStartTime();
			}

			if (po.getSellLowPrice() != null && po.getSellLowPrice().doubleValue() < minRate) {
				minRate = po.getSellLowPrice().doubleValue();
				minRateDateTime = po.getStartTime();
			}

			if (po.getBuyLowPrice() != null && po.getBuyLowPrice().doubleValue() < minRate) {
				minRate = po.getBuyLowPrice().doubleValue();
				minRateDateTime = po.getStartTime();
			}

			if (po.getStartTime() != null) {
				if (startTime == null || startTime.isAfter(po.getStartTime())) {
					startTime = po.getStartTime();
				}
			}
			if (po.getEndTime() != null) {
				if (endTime == null || endTime.isBefore(po.getEndTime())) {
					endTime = po.getEndTime();
				}
			}

		}

		r.setMaxRate(new BigDecimal(maxRate));
		r.setMinRate(new BigDecimal(minRate));
		r.setMaxRateDateTime(maxRateDateTime);
		r.setMinRateDateTime(minRateDateTime);
		r.setIsSuccess();
		return r;
	}

	protected BigDecimal numberSetScale(BigDecimal num) {
		if (num.compareTo(new BigDecimal(100)) >= 0) {
			return num.setScale(2, RoundingMode.HALF_UP);
		} else {
			return num.setScale(6, RoundingMode.HALF_UP);
		}
	}
}
