package demo.finance.cryptoCoin.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
	public CommonResult insertNewCryptoCoinPriceNoticeSetting(InsertNewCryptoCoinPriceNoticeSettingDTO dto) {
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
		if (dto.getPricePercentage() != null) {
			newPO.setPricePercentage(new BigDecimal(dto.getPricePercentage()));
		}
		if (dto.getFluctuationSpeedPercentage() != null) {
			newPO.setFluctuationSpeedPercentage(new BigDecimal(dto.getFluctuationSpeedPercentage()));
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
		CurrencyType currencyTpye = CurrencyType.getType(dto.getCurrencyType());
		if (coinType == null || currencyTpye == null) {
			r.failWithMessage("error param");
			return r;
		}

		if (dto.getMinuteRange() != null && dto.getMinuteRange() > 120) {
			r.failWithMessage("minute range max 120");
			return r;
		}

		if (!validRegexToolService.validEmail(dto.getEmail())) {
			r.failWithMessage("error email");
			return r;
		}

		if (dto.getPricePercentage() != null && dto.getPricePercentage() == 0) {
			r.failWithMessage("percent can not equals to 0");
			return r;
		}

		if (dto.getFluctuationSpeedPercentage() != null && dto.getFluctuationSpeedPercentage() == 0) {
			r.failWithMessage("percent can not equals to 0");
			return r;
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

	private boolean priceConditionHadSet(InsertNewCryptoCoinPriceNoticeSettingDTO dto) {
		return dto.getMaxPrice() != null || dto.getMinPrice() != null;
	}

	private boolean priceRangeConditionHadSet(InsertNewCryptoCoinPriceNoticeSettingDTO dto) {
		return dto.getOriginalPrice() != null && dto.getPricePercentage() != null;
	}

	private boolean priceFluctuationSpeedConditionHadSet(InsertNewCryptoCoinPriceNoticeSettingDTO dto) {
		Double percentage = dto.getFluctuationSpeedPercentage();
		return (percentage != null && (percentage > 0.01 || percentage < -0.01))
				&& (dto.getMinuteRange() != null && dto.getMinuteRange() > 0);
	}

	private boolean priceConditionHadSet(CryptoCoinPriceNotice dto) {
		return dto.getMaxPrice() != null || dto.getMinPrice() != null;
	}

	private boolean priceRangeConditionHadSet(CryptoCoinPriceNotice dto) {
		return dto.getOriginalPrice() != null && dto.getPricePercentage() != null;
	}

	private boolean priceFluctuationSpeedConditionHadSet(CryptoCoinPriceNotice dto) {
		return dto.getFluctuationSpeedPercentage() != null
				&& dto.getFluctuationSpeedPercentage().compareTo(BigDecimal.ZERO) != 0 && dto.getMinuteRange() != null
				&& dto.getMinuteRange() > 0;
	}

	@Override
	public ModelAndView insertNewCryptoCoinPriceNoticeSetting() {
		ModelAndView view = new ModelAndView("finance/cryptoCoin/insertNewCryptoCoinPriceNoticeSetting");
		view.addObject("cryptoCoinType", CryptoCoinType.values());
//		view.addObject("currencyType", CurrencyType.values());
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
		CommonResult handleResult = null;
		if (priceConditionHadSet(noticeSetting)) {
			handleResult = priceConditionNoticeHandle(noticeSetting, coinType, currencyType);
			if (handleResult.isSuccess()) {
				content += handleResult.getMessage();
			}
		}
		if (priceRangeConditionHadSet(noticeSetting)) {
			handleResult = priceRangeNoticeHandle(noticeSetting, coinType, currencyType);
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

		if (StringUtils.isNotBlank(content)
//				&& !"dev".equals(constantService.getValByName("envName"))
		) {
			mailService.sendSimpleMail(noticeSetting.getEmail(), "价格提示", content, null, MailType.preciousMetalsNotice);
			noticeSetting.setNoticeTime(LocalDateTime.now());
			noticeSetting.setIsDelete(true);
			noticeMapper.updateByPrimaryKeySelective(noticeSetting);
		}

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
				content = coinType.getName() + ", " + currencyType + ", " + " price(range) had reach "
						+ lastMinuteMaxPrice + " at: " + maxMinPriceResult.getMaxPriceDateTime() + ";";
			}

		} else if (noticeSetting.getMinPrice() != null) {
			if (lastMinuteMinPrice.compareTo(noticeSetting.getMinPrice()) <= -1) {
				content = coinType.getName() + ", " + currencyType + ", " + " price(range) had reach "
						+ lastMinuteMinPrice + " at: " + maxMinPriceResult.getMinPriceDateTime() + ";";
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

		List<CryptoCoinPrice> lastMinutePOList = findHistoryDateByLastMinutes(coinType, currencyType, 5);

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

		if (noticeSetting.getPricePercentage().compareTo(BigDecimal.ZERO) > 0) {
			BigDecimal noticeMaxRange = noticeSetting.getPricePercentage().add(BigDecimal.ONE);
			BigDecimal noticeMaxPrice = settingOriginalPrice.multiply(noticeMaxRange);
			if (lastMinuteMaxPrice.compareTo(noticeMaxPrice) > 0) {
				content = coinType.getName() + ", " + currencyType + ", " + " had over range, trigger price: "
						+ noticeMaxPrice + " at: " + maxMinPriceResult.getMaxPriceDateTime();
			}
		} else {
			BigDecimal noticeMinRange = noticeSetting.getPricePercentage().add(BigDecimal.ONE);
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

		List<CryptoCoinPrice> historyPOList = findHistoryDateByLastMinutes(coinType, currencyType,
				noticeSetting.getMinuteRange());
		if (historyPOList == null || historyPOList.isEmpty()) {
			return r;
		}

		FindMaxMinPriceResult maxMinPriceResult = findMaxMinPrice(historyPOList);
		if (maxMinPriceResult.isFail()) {
			r.addMessage(maxMinPriceResult.getMessage());
			return r;
		}

		double lastMax = maxMinPriceResult.getMaxPrice().doubleValue();
		double lastMin = maxMinPriceResult.getMinPrice().doubleValue();

		Double upApmlitude = null;
		Double lowApmlitude = null;

		if (maxMinPriceResult.getMaxPriceDateTime().isBefore(maxMinPriceResult.getMinPriceDateTime())) {
			lowApmlitude = (lastMin / lastMax - 1) * 100;
		} else {
			upApmlitude = (lastMax / lastMin - 1) * 100;
		}
//		涨跌幅计算, 可能超出精度范围

		String content = null;
		Double trigerPercentage = noticeSetting.getFluctuationSpeedPercentage().doubleValue();
		if (trigerPercentage > 0) {
			if (upApmlitude != null && upApmlitude > trigerPercentage) {
				content = coinType.getName() + ", " + currencyType + ", " + " had reach highest amlitude, new price: "
						+ historyPOList.get(0).getPrice();
			}
		} else {
			if (lowApmlitude != null && lowApmlitude < trigerPercentage) {
				content = coinType.getName() + ", " + currencyType + ", " + " had reach lowest amlitude, new price: "
						+ historyPOList.get(0).getPrice();
			}
		}

		if (content != null) {
			r.successWithMessage(content);
		}

		return r;
	}

}
