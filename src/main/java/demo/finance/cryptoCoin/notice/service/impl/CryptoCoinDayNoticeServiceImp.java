package demo.finance.cryptoCoin.notice.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.pojo.dto.InsertCryptoCoinPriceNoticeSettingDTO;
import demo.finance.cryptoCoin.data.pojo.result.CryptoCoinNoticeDTOCheckResult;
import demo.finance.cryptoCoin.notice.mapper.CryptoCoinPriceNoticeMapper;
import demo.finance.cryptoCoin.notice.pojo.po.CryptoCoinPriceNotice;
import demo.finance.cryptoCoin.notice.service.CryptoCoinDayNoticeService;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

@Service
public class CryptoCoinDayNoticeServiceImp extends CryptoCoinCommonService implements CryptoCoinDayNoticeService {

	@Autowired
	private CryptoCoinPriceNoticeMapper noticeMapper;

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

	private CryptoCoinNoticeDTOCheckResult noticeDTOCheck(InsertCryptoCoinPriceNoticeSettingDTO dto) {
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
				&& (dto.getMinuteRange() != null && dto.getMinuteRange() > 0);
	}
}
