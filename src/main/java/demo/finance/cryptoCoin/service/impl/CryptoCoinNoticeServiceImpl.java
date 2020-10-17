package demo.finance.cryptoCoin.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.common.pojo.result.FindMaxMinPriceResult;
import demo.finance.cryptoCoin.mapper.CryptoCoinPriceNoticeMapper;
import demo.finance.cryptoCoin.pojo.dto.InsertNewCryptoCoinPriceNoticeSettingDTO;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPrice;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPriceNotice;
import demo.finance.cryptoCoin.pojo.result.CryptoCoinNoticeDTOCheckResult;
import demo.finance.cryptoCoin.service.CryptoCoinNoticeService;
import demo.tool.pojo.type.MailType;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

@Service
public class CryptoCoinNoticeServiceImpl extends CryptoCoinCommonService implements CryptoCoinNoticeService {

	@Autowired
	private CryptoCoinPriceNoticeMapper noticeMapper;

	@Override
	public CommonResult insertNewMetalPriceNoticeSetting(InsertNewCryptoCoinPriceNoticeSettingDTO dto) {
		CommonResult r = new CommonResult();
		CryptoCoinNoticeDTOCheckResult checkResult = noticeDTOCheck(dto);
		if (checkResult.isFail()) {
			r.setMessage(checkResult.getMessage());
			return r;
		}

		CryptoCoinPriceNotice newPO = new CryptoCoinPriceNotice();
		newPO.setCoinType(dto.getCoinType());
		newPO.setCurrencyType(dto.getCurrencyType());
		newPO.setEmail(dto.getEmail());
		newPO.setMaxPrice(dto.getMaxPrice());
		newPO.setMinPrice(dto.getMinPrice());
		newPO.setMinuteRange(dto.getMinuteRange());
		if (dto.getOriginalPrice() != null) {
			newPO.setOriginalPrice(new BigDecimal(dto.getOriginalPrice()));
		}
		if (dto.getPercentage() != null) {
			newPO.setPercentage(new BigDecimal(dto.getPercentage()));
		}
		newPO.setValidTime(checkResult.getValidTime());
		newPO.setId(snowFlake.getNextId());
		newPO.setCreateTime(LocalDateTime.now());
		int count = noticeMapper.insertSelective(newPO);

		if (count > 0) {
			r.successWithMessage("insert notice setting: " + dto.toString());
		}
		return r;
	}

	private CryptoCoinNoticeDTOCheckResult noticeDTOCheck(InsertNewCryptoCoinPriceNoticeSettingDTO dto) {
		CryptoCoinNoticeDTOCheckResult r = new CryptoCoinNoticeDTOCheckResult();

		CryptoCoinType coinType = CryptoCoinType.getType(dto.getCoinType());
		CurrencyType currencyTpye = CurrencyType.getType(dto.getCoinType());
		if (coinType == null || currencyTpye == null) {
			r.failWithMessage("error param");
			return r;
		}

		if (!validRegexToolService.validEmail(dto.getEmail())) {
			r.failWithMessage("error email");
			return r;
		}

		if (dto.getPercentage() != null && dto.getPercentage() == 0) {
			r.failWithMessage("percent can not equals to 0");
			return r;
		}

		if (!priceConditionHadSet(dto) || !priceRangeConditionHadSet(dto)
				|| !priceFluctuationSpeedConditionHadSet(dto)) {
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

	private boolean priceConditionHadSet(InsertNewCryptoCoinPriceNoticeSettingDTO dto) {
		return dto.getMaxPrice() == null || dto.getMinPrice() == null;
	}

	private boolean priceRangeConditionHadSet(InsertNewCryptoCoinPriceNoticeSettingDTO dto) {
		return dto.getOriginalPrice() != null && dto.getPercentage() != null;
	}

	private boolean priceFluctuationSpeedConditionHadSet(InsertNewCryptoCoinPriceNoticeSettingDTO dto) {
		return dto.getPercentage() != null && dto.getMinuteRange() != null;
	}

	private boolean priceConditionHadSet(CryptoCoinPriceNotice dto) {
		return dto.getMaxPrice() == null || dto.getMinPrice() == null;
	}

	private boolean priceRangeConditionHadSet(CryptoCoinPriceNotice dto) {
		return dto.getOriginalPrice() != null && dto.getPercentage() != null;
	}

	private boolean priceFluctuationSpeedConditionHadSet(CryptoCoinPriceNotice dto) {
		return dto.getPercentage() != null && dto.getMinuteRange() != null;
	}

	@Override
	public ModelAndView insertNewCryptoCoinPriceNoticeSetting() {
		ModelAndView view = new ModelAndView("finance/cryptoCoin/insertNewCryptoCoinPriceNoticeSetting");
		view.addObject("cryptoCoinType", CryptoCoinType.values());
		view.addObject("currencyType", CurrencyType.values());
		return view;
	}

	@Override
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

		String content = "";
		if (priceConditionHadSet(noticeSetting)) {
			content += priceConditionNoticeHandle(noticeSetting, coinType, currencyType).getMessage();
		}
		if (priceRangeConditionHadSet(noticeSetting)) {
			content += priceRangeNoticeHandle(noticeSetting, coinType, currencyType).getMessage();
		}
		if (priceFluctuationSpeedConditionHadSet(noticeSetting)) {
			content += priceFluctuationSpeedNoticeHandle(noticeSetting, coinType, currencyType).getMessage();
		}

		if (content != null && !"dev".equals(constantService.getValByName("envName"))) {
			mailService.sendSimpleMail(noticeSetting.getEmail(), "价格提示", content, null, MailType.preciousMetalsNotice);
		}

		noticeSetting.setNoticeTime(LocalDateTime.now());
		noticeSetting.setIsDelete(true);
		noticeMapper.updateByPrimaryKeySelective(noticeSetting);
	}

	private CommonResult priceConditionNoticeHandle(CryptoCoinPriceNotice noticeSetting, CryptoCoinType coinType,
			CurrencyType currencyType) {
		CommonResult r = new CommonResult();
		List<CryptoCoinPrice> historyPOList = findHistoryDateByLastMinutes(coinType, currencyType, 1);

		if (historyPOList == null || historyPOList.isEmpty()) {
			return r;
		}

		FindMaxMinPriceResult maxMinPriceResult = findMaxMinPrice(historyPOList);
		if (maxMinPriceResult.isFail()) {
			r.addMessage(maxMinPriceResult.getMessage());
			return r;
		}

		BigDecimal lastMinuteMaxPrice = maxMinPriceResult.getMaxPrice();
		BigDecimal lastMinuteMinPrice = maxMinPriceResult.getMinPrice();
		String content = null;

		if (noticeSetting.getMaxPrice() != null) {
			if (lastMinuteMaxPrice.compareTo(noticeSetting.getMaxPrice()) >= 1) {
				content = coinType.getName() + ", " + currencyType + ", " + " price had reach " + lastMinuteMaxPrice
						+ " at: " + maxMinPriceResult.getMaxPriceDateTime() + ";";
			}

		} else if (noticeSetting.getMinPrice() != null) {
			if (lastMinuteMinPrice.compareTo(noticeSetting.getMinPrice()) <= -1) {
				content = coinType.getName() + ", " + currencyType + ", " + " price had reach " + lastMinuteMinPrice
						+ " at: " + maxMinPriceResult.getMinPriceDateTime() + ";";
			}
		}

		if (content != null) {
			r.successWithMessage(content);
		}

		return r;
	}

	private CommonResult priceRangeNoticeHandle(CryptoCoinPriceNotice noticeSetting, CryptoCoinType coinType,
			CurrencyType currencyType) {

		CommonResult r = new CommonResult();

		List<CryptoCoinPrice> lastMinutePOList = findHistoryDateByLastMinutes(coinType, currencyType,
				noticeSetting.getMinuteRange());

		if (lastMinutePOList == null || lastMinutePOList.isEmpty()) {
			return r;
		}

		FindMaxMinPriceResult maxMinPriceResult = findMaxMinPrice(lastMinutePOList);
		if (maxMinPriceResult.isFail()) {
			r.addMessage(maxMinPriceResult.getMessage());
			return r;
		}

		BigDecimal lastMinuteMaxPrice = maxMinPriceResult.getMaxPrice();
		BigDecimal lastMinuteMinPrice = maxMinPriceResult.getMinPrice();
		BigDecimal settingOriginalPrice = noticeSetting.getOriginalPrice();
		String content = null;
		
		if (noticeSetting.getPercentage().compareTo(BigDecimal.ZERO) > 0) {
			BigDecimal noticeMaxRange = noticeSetting.getPercentage().add(BigDecimal.ONE);
			BigDecimal noticeMaxPrice = settingOriginalPrice.multiply(noticeMaxRange);
			if (lastMinuteMaxPrice.compareTo(noticeMaxPrice) > 0) {
				content = coinType.getName() + ", " + currencyType + ", " + " had over range, trigger price: "
						+ noticeMaxPrice + " at: " + maxMinPriceResult.getMaxPriceDateTime();
			}
		} else {
			BigDecimal noticeMinRange = noticeSetting.getPercentage().add(BigDecimal.ONE);
			BigDecimal noticeMinPrice = settingOriginalPrice.multiply(noticeMinRange);
			if (lastMinuteMinPrice.compareTo(noticeMinPrice) < 0) {
				content = coinType.getName() + ", " + currencyType + ", " + " had over range, trigger price: "
						+ noticeMinPrice + " at: " + maxMinPriceResult.getMinPriceDateTime();
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

		List<CryptoCoinPrice> historyPOList = findHistoryDateByLastMinutes(coinType, currencyType, noticeSetting.getMinuteRange());
		if (historyPOList == null || historyPOList.isEmpty()) {
			r.setMessage("no history data");
			return r;
		}
		
		FindMaxMinPriceResult maxMinPriceResult = findMaxMinPrice(historyPOList);
		if(maxMinPriceResult.isFail()) {
			r.addMessage(maxMinPriceResult.getMessage());
			return r;
		}

		BigDecimal lastMax = maxMinPriceResult.getMaxPrice();
		BigDecimal lastMin = maxMinPriceResult.getMinPrice();
		
		BigDecimal upApmlitude = null;
		BigDecimal lowApmlitude = null;
		if(maxMinPriceResult.getMaxPriceDateTime().isBefore(maxMinPriceResult.getMinPriceDateTime())) {
			lowApmlitude = lastMin.divide(lastMax).subtract(BigDecimal.ONE);
		} else {
			upApmlitude = lastMax.divide(lastMin).subtract(BigDecimal.ONE);
		}
		
		String content = null;
		BigDecimal trigerPercentage = noticeSetting.getPercentage();
		if (upApmlitude.compareTo(trigerPercentage) > 0) {
			content = coinType.getName() + ", " + currencyType + ", " + " had reach highest amlitude, new price: "
					+ historyPOList.get(0).getPrice();
		} else if(lowApmlitude.compareTo(trigerPercentage) < 0) {
			content = coinType.getName() + ", " + currencyType + ", " + " had reach lowest amlitude, new price: "
					+ historyPOList.get(0).getPrice();
		}

		if(content != null) {
			r.setIsSuccess();
		}
		
		return r;
	}

}
