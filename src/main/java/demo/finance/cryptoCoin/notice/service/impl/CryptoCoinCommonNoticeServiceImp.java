package demo.finance.cryptoCoin.notice.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.pojo.dto.InsertCryptoCoinPriceNoticeSettingDTO;
import demo.finance.cryptoCoin.data.pojo.result.CryptoCoinNoticeDTOCheckResult;
import demo.finance.cryptoCoin.data.pojo.result.FilterBODataResult;
import demo.finance.cryptoCoin.data.service.CryptoCoin1DayDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin1MinuteDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin1MonthDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin1WeekDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin5MinuteDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin60MinuteDataSummaryService;
import demo.finance.cryptoCoin.mq.producer.TelegramCryptoCoinMessageAckProducer;
import demo.finance.cryptoCoin.notice.mapper.CryptoCoinPriceNoticeMapper;
import demo.finance.cryptoCoin.notice.pojo.dto.NoticeUpdateDTO;
import demo.finance.cryptoCoin.notice.pojo.dto.SearchCryptoCoinConditionDTO;
import demo.finance.cryptoCoin.notice.pojo.po.CryptoCoinPriceNotice;
import demo.finance.cryptoCoin.notice.pojo.po.CryptoCoinPriceNoticeExample;
import demo.finance.cryptoCoin.notice.pojo.po.CryptoCoinPriceNoticeExample.Criteria;
import demo.finance.cryptoCoin.notice.pojo.vo.CryptoCoinNoticeVO;
import demo.finance.cryptoCoin.notice.service.CryptoCoinCommonNoticeService;
import demo.tool.telegram.pojo.po.TelegramChatId;
import demo.tool.telegram.pojo.vo.TelegramChatIdVO;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.constant.CryptoCoinDataConstant;
import finance.cryptoCoin.pojo.type.CryptoCoinType;
import telegram.pojo.dto.TelegramMessageDTO;

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

	@Autowired
	private TelegramCryptoCoinMessageAckProducer telegramMessageAckProducer;

	@Override
	public ModelAndView cryptoCoinPriceNoticeSettingManager() {
		ModelAndView view = new ModelAndView("finance/cryptoCoin/CryptoCoinPriceNoticeSettingManager");
		view.addObject("cryptoCoinType", CryptoCoinType.values());
		view.addObject("currencyType", CurrencyType.values());
		TimeUnitType[] timeUnitTypes = new TimeUnitType[] { TimeUnitType.minute, TimeUnitType.hour, TimeUnitType.day,
				TimeUnitType.week, TimeUnitType.month };
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
		newPO.setTelegramChatId(decryptPrivateKey(dto.getTelegramChatPK()));
		newPO.setNoticeCount(dto.getNoticeCount());
		newPO.setMaxPrice(dto.getMaxPrice());
		newPO.setMinPrice(dto.getMinPrice());
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

	private CryptoCoinNoticeDTOCheckResult noticeDTOCheck(InsertCryptoCoinPriceNoticeSettingDTO dto) {
		CryptoCoinNoticeDTOCheckResult r = new CryptoCoinNoticeDTOCheckResult();

		CryptoCoinType coinType = CryptoCoinType.getType(dto.getCoinType());
		CurrencyType currencyType = CurrencyType.getType(dto.getCurrencyType());

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
			TimeUnitType timeUnitTypeOfDataWatch = TimeUnitType.getType(dto.getTimeUnitOfDataWatch());
			if (timeUnitTypeOfDataWatch == null || dto.getTimeRangeOfDataWatch() == null
					|| dto.getTimeRangeOfDataWatch() < 0) {
				r.failWithMessage("data watch time range setting error");
				return r;
			}
		} else {
			dto.setTimeUnitOfDataWatch(null);
		}

		if (!priceConditionHadSet(dto) && !priceRangeConditionHadSet(dto)
				&& !priceFluctuationSpeedConditionHadSet(dto)) {
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
			validDate = validDate.withHour(23).withMinute(59).withSecond(59).withNano(999999);
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

		dto.setMaxPrice(new BigDecimal(dto.getOriginalPrice() * (1 + range / 100)));
		dto.setMinPrice(new BigDecimal(dto.getOriginalPrice() * (1 - range / 100)));

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
				&& (dto.getTimeUnitOfDataWatch() != null && dto.getTimeRangeOfDataWatch() > 0);
	}

	private boolean priceConditionHadSet(CryptoCoinPriceNotice po) {
		return po.getMaxPrice() != null || po.getMinPrice() != null;
	}

	private boolean priceFluctuationSpeedConditionHadSet(CryptoCoinPriceNotice po) {
		return po.getFluctuationSpeedPercentage() != null
				&& po.getFluctuationSpeedPercentage().compareTo(BigDecimal.ZERO) != 0
				&& po.getTimeUnitOfDataWatch() != null && po.getTimeRangeOfDataWatch() != null
				&& po.getTimeRangeOfDataWatch() > 0;
	}

	@Override
	public void noticeHandler() {
		List<CryptoCoinPriceNotice> noticeList = noticeMapper.selectValidNoticeSetting(LocalDateTime.now());
		if (noticeList == null || noticeList.isEmpty()) {
			return;
		}

		for (CryptoCoinPriceNotice notice : noticeList) {
			try {
				subNoticeHandler(notice);
			} catch (Exception e) {
				log.error(notice.getId() + ", hit error: " + e.getLocalizedMessage());
			}
		}

	}

	private CommonResult subNoticeHandler(CryptoCoinPriceNotice noticeSetting) {
		CommonResult r = new CommonResult();
		CryptoCoinType coinType = CryptoCoinType.getType(noticeSetting.getCoinType());
		if (coinType == null) {
			log.error(noticeSetting.getId() + ", coin type setting error");
			r.failWithMessage("coin type setting error");
			return r;
		}

		CurrencyType currencyType = CurrencyType.getType(noticeSetting.getCurrencyType());
		if (currencyType == null) {
			log.error(noticeSetting.getId() + ", currencty type setting error");
			r.failWithMessage("currencty type setting error");
			return r;
		}

		/*
		 * if it is REUSE notice, notice count should > 1, event in last round, notice
		 * time will not be null
		 */
		LocalDateTime nextNoticeTime = noticeSetting.getNextNoticeTime();
		if (nextNoticeTime == null || nextNoticeTime.isAfter(LocalDateTime.now())) {
			return r;
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
			TelegramMessageDTO dto = new TelegramMessageDTO();
			dto.setMsg(content);
			dto.setId(noticeSetting.getTelegramChatId());
			telegramMessageAckProducer.send(dto);

			noticeSetting.setNoticeTime(LocalDateTime.now());
			noticeSetting.setNoticeCount(noticeSetting.getNoticeCount() - 1);
			if (noticeSetting.getNoticeCount() < 1) {
				noticeSetting.setIsDelete(true);
			} else {
				TimeUnitType timeUnitTypeOfNoticeInterval = TimeUnitType
						.getType(noticeSetting.getTimeUnitOfNoticeInterval());
				if(noticeSetting.getNoticeTime() == null) {
					noticeSetting.setNoticeTime(LocalDateTime.now());
				}
				nextNoticeTime = getNextSettingTime(noticeSetting.getNoticeTime(), timeUnitTypeOfNoticeInterval,
						noticeSetting.getTimeRangeOfNoticeInterval().longValue());
				noticeSetting.setNextNoticeTime(nextNoticeTime);
			}
			noticeMapper.updateByPrimaryKeySelective(noticeSetting);
			r.successWithMessage("notice sended");
			return r;
		} else {
			r.failWithMessage("didn't hit any notice setting");
			return r;
		}

	}

	private CommonResult priceConditionNoticeHandle(CryptoCoinPriceNotice noticeSetting, CryptoCoinType coinType,
			CurrencyType currencyType) {
		CommonResult r = new CommonResult();
		List<CryptoCoinPriceCommonDataBO> historyDataList = _1MinuteDataSummaryService.getCommonDataFillWithCache(coinType,
				currencyType, LocalDateTime.now().minusMinutes(2).withSecond(0).withNano(0));

		if (historyDataList == null || historyDataList.isEmpty()) {
			log.error(noticeSetting.getId() + ", can NOT find any history data");
			return r;
		}

		FilterBODataResult maxMinPriceResult = filterData(historyDataList);
		if (maxMinPriceResult.isFail()) {
			r.addMessage(maxMinPriceResult.getMessage());
			return r;
		}

		BigDecimal lastMaxPrice = maxMinPriceResult.getMaxPrice();
		BigDecimal lastMinPrice = maxMinPriceResult.getMinPrice();
		String content = null;

		if (noticeSetting.getMaxPrice() != null) {
			if (lastMaxPrice.doubleValue() >= noticeSetting.getMaxPrice().doubleValue()) {
				content = coinType.getName() + ", " + currencyType + ", " + " price(range) had reach " + lastMaxPrice.setScale(2, RoundingMode.HALF_UP)
						+ " at: " + maxMinPriceResult.getMaxPriceDateTime() + ";";
			}

		}
		if (noticeSetting.getMinPrice() != null) {
			if (lastMinPrice.doubleValue() <= noticeSetting.getMinPrice().doubleValue()) {
				content = coinType.getName() + ", " + currencyType + ", " + " price(range) had reach " + lastMinPrice.setScale(2, RoundingMode.HALF_UP)
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
				noticeSetting.getTimeUnitOfDataWatch(), noticeSetting.getTimeRangeOfDataWatch());
		if (historyPOList == null || historyPOList.isEmpty()) {
			log.error(noticeSetting.getId() + ", can NOT find any history data");
			return r;
		}

		Collections.sort(historyPOList);
		
		FilterBODataResult maxMinPriceResult = filterData(historyPOList);
		if (maxMinPriceResult.isFail()) {
			r.addMessage(maxMinPriceResult.getMessage());
			return r;
		}

		double lastMax = maxMinPriceResult.getMaxPrice().doubleValue();
		double lastMin = maxMinPriceResult.getMinPrice().doubleValue();

		Double upApmlitude = (lastMax / lastMin - 1) * 100;
		Double lowApmlitude = (lastMin / lastMax - 1) * 100;

		String content = null;
		Double trigerPercentage = noticeSetting.getFluctuationSpeedPercentage().doubleValue();
		if (trigerPercentage < 0) {
			trigerPercentage = 0 - trigerPercentage;
		}

		if (maxMinPriceResult.getMinPriceDateTime().isBefore(maxMinPriceResult.getMaxPriceDateTime())
				|| maxMinPriceResult.getMinPriceDateTime().isEqual(maxMinPriceResult.getMaxPriceDateTime())) {

			if (upApmlitude >= trigerPercentage) {
				BigDecimal upApmlitudeBigDecimal = new BigDecimal(upApmlitude);
				content = coinType.getName() + ", " + currencyType.getName() 
						+ ", " + "最新价: " + historyPOList.get(historyPOList.size() - 1).getEndPrice().setScale(2, RoundingMode.HALF_UP)
						+ ", " + "最近" + noticeSetting.getTimeRangeOfDataWatch()
						+ TimeUnitType.getType(noticeSetting.getTimeUnitOfDataWatch()).getCnName() 
						+ ", " + "波幅达 " + upApmlitudeBigDecimal.setScale(2, RoundingMode.HALF_UP) + "%"
						+ ", " + maxMinPriceResult.getMinPriceDateTime() + " 时触及低价: " + lastMin + ", "
						+ maxMinPriceResult.getMaxPriceDateTime() + " 时触及高价: " + lastMax ;
			}
		} else {
			if ((0 - lowApmlitude) >= trigerPercentage) {
				BigDecimal lowApmlitubeBigDecimal = new BigDecimal(lowApmlitude);
				content = coinType.getName() + ", " + currencyType.getName() 
						+ ", " + "最新价: " + historyPOList.get(historyPOList.size() - 1).getEndPrice().setScale(2, RoundingMode.HALF_UP)
						+ ", " + "最近" + noticeSetting.getTimeRangeOfDataWatch()
						+ TimeUnitType.getType(noticeSetting.getTimeUnitOfDataWatch()).getCnName() 
						+ ", " + "波幅达 " + lowApmlitubeBigDecimal.setScale(2, RoundingMode.HALF_UP) + "%"
						+ ", " + maxMinPriceResult.getMaxPriceDateTime() + " 时触及高价: " + lastMax + ", "
						+ maxMinPriceResult.getMinPriceDateTime() + " 时触及低价: " + lastMin ;
			}
		}

		if (content != null) {
			r.successWithMessage(content);
		}

		return r;
	}

	/*
	 * TODO
	 * for debug
	 */
	@Override
	public List<CryptoCoinPriceCommonDataBO> findHistoryData(CryptoCoinType coinType, CurrencyType currencyType,
			Integer timeUnit, Integer timeRange) {
		LocalDateTime startTime = null;
		if (TimeUnitType.minute.getCode().equals(timeUnit)) {
			startTime = LocalDateTime.now().minusMinutes(timeRange).withSecond(0).withNano(0);
			if (CryptoCoinDataConstant.CRYPTO_COIN_1MINUTE_DATA_LIVE_HOURS * 60 > timeRange) {
				return _1MinuteDataSummaryService.getCommonDataFillWithCache(coinType, currencyType, startTime);
			} else if (CryptoCoinDataConstant.CRYPTO_COIN_5MINUTE_DATA_LIVE_HOURS * 60 > timeRange) {
				return _5MinuteDataSummaryService.getCommonDataFillWithCache(coinType, currencyType, startTime);
			}
		} else if (TimeUnitType.hour.getCode().equals(timeUnit)) {
			startTime = LocalDateTime.now().minusHours(timeRange).withSecond(0).withNano(0);
			return hourDataSummaryService.getCommonData(coinType, currencyType, startTime);
		} else if (TimeUnitType.day.getCode().equals(timeUnit)) {
			startTime = LocalDateTime.now().minusDays(timeRange).withSecond(0).withNano(0);
			return dailyDataSummaryService.getCommonData(coinType, currencyType, startTime);
		} else if (TimeUnitType.week.getCode().equals(timeUnit)) {
			LocalDateTime lastSunday = localDateTimeHandler.findLastDayOfWeek(LocalDateTime.now(), DayOfWeek.SUNDAY);
			startTime = lastSunday.minusDays((timeRange - 1) * 7).withSecond(0).withNano(0);
			return weeklyDataSummaryService.getCommonData(coinType, currencyType, startTime);
		} else if (TimeUnitType.month.getCode().equals(timeUnit)) {
			startTime = LocalDateTime.now().withDayOfMonth(1).minusMonths(timeRange - 1).withSecond(0).withNano(0);
			return monthlyDataSummaryService.getCommonData(coinType, currencyType, startTime);
		}

		return new ArrayList<CryptoCoinPriceCommonDataBO>();
	}

	@Override
	public void deleteOldNotice() {
		CryptoCoinPriceNoticeExample example = new CryptoCoinPriceNoticeExample();
		Criteria noticedCriteria = example.createCriteria();
		noticedCriteria.andNoticeTimeIsNotNull().andNoticeCountLessThanOrEqualTo(0);
		Criteria validTimeCriteria = example.createCriteria();
		validTimeCriteria.andValidTimeLessThan(LocalDateTime.now());
		example.or(noticedCriteria);
		CryptoCoinPriceNotice record = new CryptoCoinPriceNotice();
		record.setIsDelete(true);
		noticeMapper.updateByExampleSelective(record, example);
	}

	@Override
	public ModelAndView searchValidNotices(SearchCryptoCoinConditionDTO dto) {
		ModelAndView view = new ModelAndView("finance/cryptoCoin/CryptoCoinPriceNoticeSearchResult");
		
		view.addObject("cryptoCoinType", CryptoCoinType.values());
		view.addObject("currencyType", CurrencyType.values());
		TimeUnitType[] timeUnitTypes = new TimeUnitType[] { TimeUnitType.minute, TimeUnitType.hour, TimeUnitType.day,
				TimeUnitType.week, TimeUnitType.month };
		view.addObject("timeUnitType", timeUnitTypes);
		
		Long chatId = decryptPrivateKey(dto.getReciverPK());
		
		CryptoCoinPriceNoticeExample example = new CryptoCoinPriceNoticeExample();
		Criteria criteria = example.createCriteria();
		criteria.andIsDeleteEqualTo(false)
		.andNoticeCountGreaterThan(0)
		.andValidTimeGreaterThan(LocalDateTime.now())
		;
		if(chatId != null) {
			criteria.andTelegramChatIdEqualTo(chatId);
		}
		if(dto.getCryptoCoinCode() != null) {
			criteria.andCoinTypeEqualTo(dto.getCryptoCoinCode());
		}
		if(dto.getCurrencyCode() != null) {
			criteria.andCurrencyTypeEqualTo(dto.getCurrencyCode());
		}
		
		List<CryptoCoinPriceNotice> noticeList = noticeMapper.selectByExample(example);
		List<CryptoCoinNoticeVO> noticeVOList = new ArrayList<>();
		for (CryptoCoinPriceNotice po : noticeList) {
			noticeVOList.add(poToVO(po));
		}
		
		view.addObject("noticeVOList", noticeVOList);
		return view;
	}

	private CryptoCoinNoticeVO poToVO(CryptoCoinPriceNotice po) {
		CryptoCoinNoticeVO vo = new CryptoCoinNoticeVO();
		vo.setPk(encryptId(po.getId()));
		vo.setCryptoCoinCode(po.getCoinType());
		vo.setCryptoCoinName(CryptoCoinType.getType(po.getCoinType()).getName());
		vo.setCurrencyCode(po.getCurrencyType());
		vo.setCurrencyName(CurrencyType.getType(po.getCurrencyType()).getName());
		if (po.getMaxPrice() != null) {
			vo.setMaxPrice(po.getMaxPrice().doubleValue());
		}
		if (po.getMinPrice() != null) {
			vo.setMinPrice(po.getMinPrice().doubleValue());
		}
		if (po.getTimeUnitOfDataWatch() != null) {
			vo.setTimeUnitOfDataWatch(po.getTimeUnitOfDataWatch());
			vo.setTimeUnitOfDataWatchName(TimeUnitType.getType(po.getTimeUnitOfDataWatch()).getCnName());
			vo.setTimeRangeOfDataWatch(po.getTimeRangeOfDataWatch());
		}
		if (po.getTimeUnitOfNoticeInterval() != null) {
			vo.setTimeUnitOfNoticeInterval(po.getTimeUnitOfNoticeInterval());
			vo.setTimeUnitOfNoticeIntervalName(TimeUnitType.getType(po.getTimeUnitOfNoticeInterval()).getCnName());
			vo.setTimeRangeOfNoticeInterval(po.getTimeRangeOfNoticeInterval());
		}
		if (po.getFluctuationSpeedPercentage() != null) {
			vo.setFluctuactionSpeedPercentage(po.getFluctuationSpeedPercentage().doubleValue());
		}
		vo.setNoticeCount(po.getNoticeCount());
		vo.setValidTime(localDateTimeHandler.dateToStr(po.getValidTime()));
		vo.setNoticeTime(localDateTimeHandler.dateToStr(po.getNoticeTime()));
		vo.setNextNoticeTime(localDateTimeHandler.dateToStr(po.getNextNoticeTime()));
		TelegramChatId chatIdPO = telegramService.getChatID(po.getTelegramChatId());
		TelegramChatIdVO chatIdVO = telegramService.buildChatIdVO(chatIdPO);
		vo.setNoticeReciver(chatIdVO.getUsername());
		vo.setNoticeReciverPK(chatIdVO.getPk());
		return vo;
	}

	@Override
	public CommonResult deleteNotice(String pk) {
		CommonResult r = new CommonResult();
		Long id = decryptPrivateKey(pk);
		if (id == null) {
			r.failWithMessage("param error");
			return r;
		}

		CryptoCoinPriceNotice po = noticeMapper.selectByPrimaryKey(id);
		po.setIsDelete(true);

		int updateCount = noticeMapper.updateByPrimaryKeySelective(po);

		if (updateCount == 1) {
			r.successWithMessage("delete success");
			return r;
		} else {
			r.failWithMessage("update error");
			return r;
		}
	}

	@Override
	public CommonResult updateNotice(NoticeUpdateDTO dto) {
		CommonResult r = new CommonResult();

		Long id = decryptPrivateKey(dto.getPk());
		if (id == null) {
			r.failWithMessage("param error");
			return r;
		}

		CryptoCoinPriceNotice po = noticeMapper.selectByPrimaryKey(id);
		if (po == null) {
			return r;
		}

		CryptoCoinType coinType = CryptoCoinType.getType(dto.getCryptoCoinCode());
		CurrencyType currencyType = CurrencyType.getType(dto.getCurrencyCode());
		if (coinType == null || currencyType == null) {
			r.failWithMessage("param error");
			return r;
		}
		po.setCoinType(coinType.getCode());
		po.setCurrencyType(currencyType.getCode());
		
		if(StringUtils.isNotBlank(dto.getNextNoticeTime())) {
			LocalDateTime nextNoticeTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getNextNoticeTime());
			if(nextNoticeTime == null) {
				r.failWithMessage("next notice time setting error");
			}
			po.setNextNoticeTime(nextNoticeTime);
		}
		
		if(StringUtils.isNotBlank(dto.getValidTime())) {
			LocalDateTime validTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getValidTime());
			if(validTime == null) {
				r.failWithMessage("valid time setting error");
			}
			po.setValidTime(validTime);
		}

		if (dto.getMaxPrice() != null) {
			po.setMaxPrice(new BigDecimal(dto.getMaxPrice()));
		} else {
			po.setMaxPrice(null);
		}
		if (dto.getMinPrice() != null) {
			po.setMinPrice(new BigDecimal(dto.getMinPrice()));
		} else {
			po.setMinPrice(null);
		}

		po.setTimeUnitOfDataWatch(dto.getTimeUnitOfDataWatch());
		po.setTimeRangeOfDataWatch(dto.getTimeRangeOfDataWatch());

		po.setTimeUnitOfNoticeInterval(dto.getTimeUnitOfNoticeInterval());
		po.setTimeRangeOfNoticeInterval(dto.getTimeRangeOfNoticeInterval());

		if (dto.getFluctuactionSpeedPercentage() != null) {
			po.setFluctuationSpeedPercentage(new BigDecimal(dto.getFluctuactionSpeedPercentage()));
		} else {
			po.setFluctuationSpeedPercentage(null);
		}

		po.setNoticeCount(dto.getNoticeCount());

		if (!priceConditionHadSet(po) && !priceFluctuationSpeedConditionHadSet(po)) {
			r.failWithMessage("please check notice condition");
			return r;
		}

		int updateCount = noticeMapper.updateByPrimaryKey(po);
		if (updateCount == 1) {
			r.successWithMessage("udpate success");
		}

		return r;
	}
}
