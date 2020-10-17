package demo.finance.cryptoCoin.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.mapper.CryptoCoinPriceNoticeMapper;
import demo.finance.cryptoCoin.pojo.dto.InsertNewCryptoCoinPriceNoticeSettingDTO;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPriceNotice;
import demo.finance.cryptoCoin.pojo.result.CryptoCoinNoticeDTOCheckResult;
import demo.finance.cryptoCoin.service.CryptoCoinNoticeService;
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
		newPO.setAbsoluteValueFalg(dto.getAbsoluteValueFlag());
		newPO.setMinuteRange(dto.getMinuteRange());
		if(dto.getOriginalPrice() != null) {
			newPO.setOriginalPrice(new BigDecimal(dto.getOriginalPrice()));
		}
		if(dto.getPercentage() != null) {
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

	public CryptoCoinNoticeDTOCheckResult noticeDTOCheck(InsertNewCryptoCoinPriceNoticeSettingDTO dto) {
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
		return dto.getOriginalPrice() != null && dto.getPercentage() != null && dto.getMinuteRange() != null;
	}

	@Override
	public ModelAndView insertNewCryptoCoinPriceNoticeSetting() {
		ModelAndView view = new ModelAndView("finance/cryptoCoin/insertNewCryptoCoinPriceNoticeSetting");
		view.addObject("cryptoCoinType", CryptoCoinType.values());
		view.addObject("currencyType", CurrencyType.values());
		return view;
	}
}
