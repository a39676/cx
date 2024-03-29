package demo.finance.cryptoCoin.notice.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.pojo.dto.InsertCryptoCoinLowPriceNoticeSettingDTO;
import demo.finance.cryptoCoin.data.pojo.dto.InsertCryptoCoinPriceNoticeSettingDTO;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.result.CryptoCoinNoticeDTOCheckResult;
import demo.finance.cryptoCoin.data.service.CryptoCoin1MinuteDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoinCatalogService;
import demo.finance.cryptoCoin.data.service.CryptoCoinHistoryDataService;
import demo.finance.cryptoCoin.notice.mapper.CryptoCoinPriceNoticeMapper;
import demo.finance.cryptoCoin.notice.pojo.dto.NoticeUpdateDTO;
import demo.finance.cryptoCoin.notice.pojo.dto.SearchCryptoCoinConditionDTO;
import demo.finance.cryptoCoin.notice.pojo.po.CryptoCoinPriceNotice;
import demo.finance.cryptoCoin.notice.pojo.po.CryptoCoinPriceNoticeExample;
import demo.finance.cryptoCoin.notice.pojo.po.CryptoCoinPriceNoticeExample.Criteria;
import demo.finance.cryptoCoin.notice.pojo.vo.CryptoCoinNoticeVO;
import demo.finance.cryptoCoin.notice.service.CryptoCoinCommonNoticeService;
import demo.tool.textMessageForward.telegram.pojo.po.TelegramChatId;
import demo.tool.textMessageForward.telegram.pojo.vo.TelegramChatIdVO;
import demo.tool.textMessageForward.telegram.service.TelegramService;
import finance.common.pojo.bo.FilterPriceResult;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.type.CurrencyTypeForCryptoCoin;
import telegram.pojo.type.TelegramBotType;

@Service
public class CryptoCoinCommonNoticeServiceImp extends CryptoCoinCommonService implements CryptoCoinCommonNoticeService {

	@Autowired
	private CryptoCoinCatalogService coinCatalogService;
	@Autowired
	private CryptoCoinHistoryDataService cryptoCoinHistoryDataService;
	@Autowired
	private CryptoCoin1MinuteDataSummaryService minuteDataService;

	@Autowired
	protected CryptoCoinPriceNoticeMapper noticeMapper;

	@Autowired
	private TelegramService telegramService;

	@Override
	public ModelAndView cryptoCoinPriceNoticeSettingManager() {
		ModelAndView view = new ModelAndView("finance/cryptoCoin/CryptoCoinPriceNoticeSettingManager");
		List<CurrencyTypeForCryptoCoin> currencyTypeList = new ArrayList<>();
		currencyTypeList.add(CurrencyTypeForCryptoCoin.USD);
		currencyTypeList.addAll(Arrays.asList(CurrencyTypeForCryptoCoin.values()));
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
		newPO.setTelegramBotName(TelegramBotType.BBT_MESSAGE.getName());
		newPO.setCoinType(dto.getCoinTypeCode());
		newPO.setCurrencyType(dto.getCurrencyType());
		newPO.setTelegramChatRecordId(systemOptionService.decryptPrivateKey(dto.getTelegramChatPK()));
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

	@Override
	public CommonResult insertNewCryptoCoinLowPriceNoticeSetting(InsertCryptoCoinLowPriceNoticeSettingDTO dto) {
		CommonResult r = new CommonResult();
		CryptoCoinNoticeDTOCheckResult checkResult = noticeDTOCheck(dto);
		if (checkResult.isFail()) {
			r.setMessage(checkResult.getMessage());
			return r;
		}

		dto = dtoPrefixHandle(dto);

		CryptoCoinPriceNotice newPO = new CryptoCoinPriceNotice();
		newPO.setId(snowFlake.getNextId());
		newPO.setTelegramBotName(TelegramBotType.CRYPTO_COIN_LOW_PRICE_NOTICE_BOT.getName());
		newPO.setCoinType(dto.getCoinTypeCode());
		newPO.setCurrencyType(dto.getCurrencyType());
		newPO.setTelegramChatRecordId(systemOptionService.decryptPrivateKey(dto.getTelegramChatPK()));
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

		CurrencyTypeForCryptoCoin currencyType = CurrencyTypeForCryptoCoin.getType(dto.getCurrencyType());

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

		if (StringUtils.isBlank(dto.getCoinType()) || currencyType == null || dto.getTelegramChatPK() == null) {
			r.failWithMessage("error param");
			return r;
		}

		CryptoCoinCatalog coinCatalog = coinCatalogService.findCatalog(dto.getCoinType());
		if (coinCatalog == null) {
			r.failWithMessage("error param");
			return r;
		}

		dto.setCoinTypeCode(coinCatalog.getId());

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

	private InsertCryptoCoinLowPriceNoticeSettingDTO dtoPrefixHandle(InsertCryptoCoinLowPriceNoticeSettingDTO dto) {
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
		if (noticeSetting.getCoinType() == null) {
			log.error(noticeSetting.getId() + ", coin type setting error");
			r.failWithMessage("coin type setting error");
			return r;
		}

		CurrencyTypeForCryptoCoin currencyType = CurrencyTypeForCryptoCoin.getType(noticeSetting.getCurrencyType());
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

		CryptoCoinCatalog coinType = coinCatalogService.findCatalog(noticeSetting.getCoinType());

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
			telegramService.sendMessageByChatRecordId(TelegramBotType.getType(noticeSetting.getTelegramBotName()),
					content, noticeSetting.getTelegramChatRecordId());

			noticeSetting.setNoticeTime(LocalDateTime.now());
			noticeSetting.setNoticeCount(noticeSetting.getNoticeCount() - 1);
			if (noticeSetting.getNoticeCount() < 1) {
				noticeSetting.setIsDelete(true);
			} else {
				TimeUnitType timeUnitTypeOfNoticeInterval = TimeUnitType
						.getType(noticeSetting.getTimeUnitOfNoticeInterval());
				if (noticeSetting.getNoticeTime() == null) {
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

	private CommonResult priceConditionNoticeHandle(CryptoCoinPriceNotice noticeSetting, CryptoCoinCatalog coinType,
			CurrencyTypeForCryptoCoin currencyType) {
		CommonResult r = new CommonResult();
		List<CryptoCoinPriceCommonDataBO> historyDataList = minuteDataService.getCommonDataListFillWithCache(coinType,
				currencyType, LocalDateTime.now().minusMinutes(2).withSecond(0).withNano(0));

		if (historyDataList == null || historyDataList.isEmpty()) {
			log.error(noticeSetting.getId() + ", can NOT find any history data of: " + coinType.getCoinNameEnShort());
			return r;
		}

		FilterPriceResult maxMinPriceResult = kLineToolUnit.filterData(historyDataList);
		if (maxMinPriceResult.isFail()) {
			r.addMessage(maxMinPriceResult.getMessage());
			return r;
		}

		BigDecimal lastMaxPrice = maxMinPriceResult.getMaxPrice();
		BigDecimal lastMinPrice = maxMinPriceResult.getMinPrice();
		String content = null;

		if (noticeSetting.getMaxPrice() != null) {
			if (lastMaxPrice.doubleValue() >= noticeSetting.getMaxPrice().doubleValue()) {
				content = coinType.getCoinNameEnShort() + ", " + currencyType + ", " + " price(range) had RISE to "
						+ numberSetScale(lastMaxPrice) + " at: " + maxMinPriceResult.getMaxPriceDateTime() + ";";
			}

		}
		if (noticeSetting.getMinPrice() != null) {
			if (lastMinPrice.doubleValue() <= noticeSetting.getMinPrice().doubleValue()) {
				content = coinType.getCoinNameEnShort() + ", " + currencyType + ", " + " price(range) had FELL to "
						+ numberSetScale(lastMinPrice) + " at: " + maxMinPriceResult.getMinPriceDateTime() + ";";
			}
		}

		if (content != null) {
			r.successWithMessage(content);
		}

		return r;
	}

	private CommonResult priceFluctuationSpeedNoticeHandle(CryptoCoinPriceNotice noticeSetting,
			CryptoCoinCatalog coinType, CurrencyTypeForCryptoCoin currencyType) {
		CommonResult r = new CommonResult();

		TimeUnitType timeUnit = TimeUnitType.getType(noticeSetting.getTimeUnitOfDataWatch());

		List<CryptoCoinPriceCommonDataBO> historyBOList = cryptoCoinHistoryDataService.getHistoryDataList(coinType,
				currencyType, timeUnit, noticeSetting.getTimeRangeOfDataWatch());
		if (historyBOList == null || historyBOList.isEmpty()) {
			log.error(noticeSetting.getId() + ", can NOT find any history data of: " + coinType.getCoinNameEnShort());
			return r;
		}

		Collections.sort(historyBOList);

		FilterPriceResult maxMinPriceResult = kLineToolUnit.filterData(historyBOList);
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

		if (!maxMinPriceResult.getMinPriceDateTime().isAfter(maxMinPriceResult.getMaxPriceDateTime())) {
			if (upApmlitude >= trigerPercentage) {
				BigDecimal upApmlitudeBigDecimal = new BigDecimal(upApmlitude);
				content = coinType.getCoinNameEnShort() + ", " + currencyType.getName() + ", " + "最新价: "
						+ numberSetScale(historyBOList.get(historyBOList.size() - 1).getEndPrice()) + "\n" + ", " + "最近"
						+ noticeSetting.getTimeRangeOfDataWatch()
						+ TimeUnitType.getType(noticeSetting.getTimeUnitOfDataWatch()).getCnName() + ", " + "波幅达 "
						+ upApmlitudeBigDecimal.setScale(2, RoundingMode.HALF_UP) + "%" + "\n" + ", "
						+ maxMinPriceResult.getMinPriceDateTime() + " 时触及低价: " + lastMin + "\n" + ", "
						+ maxMinPriceResult.getMaxPriceDateTime() + " 时触及高价: " + lastMax;
			}
		} else {
			if ((0 - lowApmlitude) >= trigerPercentage) {
				BigDecimal lowApmlitubeBigDecimal = new BigDecimal(lowApmlitude);
				content = coinType.getCoinNameEnShort() + ", " + currencyType.getName() + ", " + "最新价: "
						+ numberSetScale(historyBOList.get(historyBOList.size() - 1).getEndPrice()) + "\n" + ", " + "最近"
						+ noticeSetting.getTimeRangeOfDataWatch()
						+ TimeUnitType.getType(noticeSetting.getTimeUnitOfDataWatch()).getCnName() + ", " + "波幅达 "
						+ lowApmlitubeBigDecimal.setScale(2, RoundingMode.HALF_UP) + "%" + "\n" + ", "
						+ maxMinPriceResult.getMaxPriceDateTime() + " 时触及高价: " + lastMax + "\n" + ", "
						+ maxMinPriceResult.getMinPriceDateTime() + " 时触及低价: " + lastMin;
			}
		}

		if (content != null) {
			r.successWithMessage(content);
		}

		return r;
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

		view.addObject("currencyType", CurrencyTypeForCryptoCoin.values());
		TimeUnitType[] timeUnitTypes = new TimeUnitType[] { TimeUnitType.MINUTE, TimeUnitType.HOUR, TimeUnitType.DAY,
				TimeUnitType.WEEK, TimeUnitType.MONTH };
		view.addObject("timeUnitType", timeUnitTypes);

		Long chatId = systemOptionService.decryptPrivateKey(dto.getReciverPK());

		CryptoCoinPriceNoticeExample example = new CryptoCoinPriceNoticeExample();
		Criteria criteria = example.createCriteria();
		criteria.andIsDeleteEqualTo(false).andNoticeCountGreaterThan(0).andValidTimeGreaterThan(LocalDateTime.now());
		if (chatId != null) {
			criteria.andTelegramChatRecordIdEqualTo(chatId);
		}
		if (StringUtils.isNotBlank(dto.getCryptoCoinType())) {
			CryptoCoinCatalog catalog = coinCatalogService.findCatalog(dto.getCryptoCoinType());
			if (catalog != null) {
				criteria.andCoinTypeEqualTo(catalog.getId());
			} else {
				return view;
			}
		}
		if (dto.getCurrencyCode() != null) {
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
		vo.setPk(systemOptionService.encryptId(po.getId()));
		vo.setCryptoCoinCode(po.getCoinType());
		CryptoCoinCatalog coinType = coinCatalogService.findCatalog(po.getCoinType());
		vo.setCryptoCoinName(coinType.getCoinNameEnShort());
		vo.setCurrencyCode(po.getCurrencyType());
		vo.setCurrencyName(CurrencyTypeForCryptoCoin.getType(po.getCurrencyType()).getName());
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
		TelegramChatId chatIdPO = telegramService.getChatID(po.getTelegramChatRecordId());
		TelegramChatIdVO chatIdVO = telegramService.buildChatIdVO(chatIdPO);
		vo.setNoticeReciver(chatIdVO.getUsername());
		vo.setNoticeReciverPK(chatIdVO.getPk());
		return vo;
	}

	@Override
	public CommonResult deleteNotice(String pk) {
		CommonResult r = new CommonResult();
		Long id = systemOptionService.decryptPrivateKey(pk);
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

		Long id = systemOptionService.decryptPrivateKey(dto.getPk());
		if (id == null) {
			r.failWithMessage("param error");
			return r;
		}

		CryptoCoinPriceNotice po = noticeMapper.selectByPrimaryKey(id);
		if (po == null) {
			return r;
		}

		CryptoCoinCatalog coinType = coinCatalogService.findCatalog(dto.getCryptoCoinType());
		CurrencyTypeForCryptoCoin currencyType = CurrencyTypeForCryptoCoin.getType(dto.getCurrencyCode());
		if (coinType == null || currencyType == null) {
			r.failWithMessage("param error");
			return r;
		}
		po.setCoinType(coinType.getId());
		po.setCurrencyType(currencyType.getCode());

		if (StringUtils.isNotBlank(dto.getNextNoticeTime())) {
			LocalDateTime nextNoticeTime = localDateTimeHandler
					.stringToLocalDateTimeUnkonwFormat(dto.getNextNoticeTime());
			if (nextNoticeTime == null) {
				r.failWithMessage("next notice time setting error");
			}
			po.setNextNoticeTime(nextNoticeTime);
		}

		if (StringUtils.isNotBlank(dto.getValidTime())) {
			LocalDateTime validTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getValidTime());
			if (validTime == null) {
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
