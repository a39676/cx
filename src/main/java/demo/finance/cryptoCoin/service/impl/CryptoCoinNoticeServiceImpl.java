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
import demo.finance.cryptoCoin.mapper.CryptoCoinPrice1minuteMapper;
import demo.finance.cryptoCoin.mapper.CryptoCoinPriceNoticeMapper;
import demo.finance.cryptoCoin.pojo.dto.InsertNewCryptoCoinPriceNoticeSettingDTO;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPrice1minute;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPrice1minuteExample;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPriceNotice;
import demo.finance.cryptoCoin.pojo.result.CryptoCoinNoticeDTOCheckResult;
import demo.finance.cryptoCoin.service.CryptoCoinNoticeService;
import demo.tool.pojo.type.MailType;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

@Service
public class CryptoCoinNoticeServiceImpl extends CryptoCoinCommonService implements CryptoCoinNoticeService {

	@Autowired
	private CryptoCoinPriceNoticeMapper noticeMapper;
	@Autowired
	private CryptoCoinPrice1minuteMapper cache1MinMapper;

	@Override
	public CommonResult insertNewCryptoCoinPriceNoticeSetting(InsertNewCryptoCoinPriceNoticeSettingDTO dto) {
		CommonResult r = new CommonResult();
		CryptoCoinNoticeDTOCheckResult checkResult = noticeDTOCheck(dto);
		if (checkResult.isFail()) {
			r.setMessage(checkResult.getMessage());
			return r;
		}

		dto = dtoPrefixHandle(dto);

		CryptoCoinPriceNotice newPO = new CryptoCoinPriceNotice();
		newPO.setCoinType(dto.getCoinType());
		newPO.setCurrencyType(dto.getCurrencyType());
		newPO.setEmail(dto.getEmail());
		newPO.setMaxPrice(dto.getMaxPrice());
		newPO.setMinPrice(dto.getMinPrice());
		newPO.setMinuteRange(dto.getMinuteRange());
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

		if (dto.getMinuteRange() != null && dto.getMinuteRange() > 300) {
			r.failWithMessage("minute range max 300");
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

	private InsertNewCryptoCoinPriceNoticeSettingDTO dtoPrefixHandle(InsertNewCryptoCoinPriceNoticeSettingDTO dto) {
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

	private boolean priceConditionHadSet(CryptoCoinPriceNotice po) {
		return po.getMaxPrice() != null || po.getMinPrice() != null;
	}

	private boolean priceFluctuationSpeedConditionHadSet(CryptoCoinPriceNotice po) {
		return po.getFluctuationSpeedPercentage() != null
				&& po.getFluctuationSpeedPercentage().compareTo(BigDecimal.ZERO) != 0 && po.getMinuteRange() != null
				&& po.getMinuteRange() > 0;
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
		List<CryptoCoinPrice1minute> historyPOList = findHistoryDateByLastMinutes(coinType, currencyType, 1);

		if (historyPOList == null || historyPOList.isEmpty()) {
			return r;
		}

		FindMaxMinPriceResult maxMinPriceResult = findMaxMinPrice(historyPOList);
		if (maxMinPriceResult.isFail()) {
			r.addMessage(maxMinPriceResult.getMessage());
			return r;
		}

		BigDecimal lastMaxPrice = maxMinPriceResult.getMaxPrice();
		BigDecimal lastMinPrice = maxMinPriceResult.getMinPrice();
		String content = null;

		if (noticeSetting.getMaxPrice() != null) {
			if (lastMaxPrice.compareTo(noticeSetting.getMaxPrice()) >= 0) {
				content = coinType.getName() + ", " + currencyType + ", " + " price(range) had reach " + lastMaxPrice
						+ " at: " + maxMinPriceResult.getMaxPriceDateTime() + ";";
			}

		} else if (noticeSetting.getMinPrice() != null) {
			if (lastMinPrice.compareTo(noticeSetting.getMinPrice()) <= 0) {
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

		List<CryptoCoinPrice1minute> historyPOList = findHistoryDateByLastMinutes(coinType, currencyType,
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

		upApmlitude = (lastMax / lastMin - 1) * 100;
		lowApmlitude = (lastMin / lastMax - 1) * 100;

		String content = null;
		Double trigerPercentage = noticeSetting.getFluctuationSpeedPercentage().doubleValue();
		if (trigerPercentage < 0) {
			trigerPercentage = 0 - trigerPercentage;
		}

		if (upApmlitude >= trigerPercentage) {
			content = coinType.getName() + ", " + currencyType + ", " + " had reach highest amlitude, new price: "
					+ historyPOList.get(0).getEndPrice();
		} else if (lowApmlitude <= trigerPercentage) {
			content = coinType.getName() + ", " + currencyType + ", " + " had reach lowest amlitude, new price: "
					+ historyPOList.get(0).getEndPrice();
		}

		if (content != null) {
			r.successWithMessage(content);
		}

		return r;
	}

	private List<CryptoCoinPrice1minute> findHistoryDateByLastMinutes(CryptoCoinType coinType,
			CurrencyType currencyType, Integer minutes) {
		CryptoCoinPrice1minuteExample example = new CryptoCoinPrice1minuteExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getCode()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andCreateTimeGreaterThanOrEqualTo(LocalDateTime.now().minusMinutes(minutes));
		;
		example.setOrderByClause("create_time desc");

		return cache1MinMapper.selectByExample(example);
	}

	protected FindMaxMinPriceResult findMaxMinPrice(List<CryptoCoinPrice1minute> list) {
		FindMaxMinPriceResult r = new FindMaxMinPriceResult();

		if (list == null || list.isEmpty()) {
			r.setMessage("empty history data");
			return r;
		}

		CryptoCoinPrice1minute defaultData = list.get(0);
		BigDecimal maxPrice = defaultData.getHighPrice();
		BigDecimal minPrice = defaultData.getLowPrice();
		LocalDateTime maxPriceDateTime = defaultData.getCreateTime();
		LocalDateTime minPriceDateTime = defaultData.getCreateTime();
		for (CryptoCoinPrice1minute po : list) {
			if (maxPrice.compareTo(po.getHighPrice()) < 0) {
				maxPrice = po.getHighPrice();
				maxPriceDateTime = po.getStartTime();
			} else if (minPrice.compareTo(po.getLowPrice()) > 0) {
				minPrice = po.getLowPrice();
				minPriceDateTime = po.getStartTime();
			}
		}

		r.setMaxPrice(maxPrice);
		r.setMinPrice(minPrice);
		r.setMaxPriceDateTime(maxPriceDateTime);
		r.setMinPriceDateTime(minPriceDateTime);
		r.setIsSuccess();
		return r;
	}
}
