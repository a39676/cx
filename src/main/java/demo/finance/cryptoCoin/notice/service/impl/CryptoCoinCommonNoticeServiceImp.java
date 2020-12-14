package demo.finance.cryptoCoin.notice.service.impl;

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
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice1minuteMapper;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1minute;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1minuteExample;
import demo.finance.cryptoCoin.notice.mapper.CryptoCoinPriceNoticeMapper;
import demo.finance.cryptoCoin.notice.pojo.po.CryptoCoinPriceNotice;
import demo.finance.cryptoCoin.notice.pojo.po.CryptoCoinPriceNoticeExample;
import demo.finance.cryptoCoin.notice.pojo.po.CryptoCoinPriceNoticeExample.Criteria;
import demo.finance.cryptoCoin.notice.service.CryptoCoinCommonNoticeService;
import demo.tool.pojo.type.MailType;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

@Service
public class CryptoCoinCommonNoticeServiceImp extends CryptoCoinCommonService implements CryptoCoinCommonNoticeService {

	@Autowired
	protected CryptoCoinPriceNoticeMapper noticeMapper;
	@Autowired
	private CryptoCoinPrice1minuteMapper cache1MinMapper;
	
	@Override
	public ModelAndView insertNewCryptoCoinPriceNoticeSetting() {
		ModelAndView view = new ModelAndView("finance/cryptoCoin/insertNewCryptoCoinPriceNoticeSetting");
		view.addObject("cryptoCoinType", CryptoCoinType.values());
//		view.addObject("currencyType", CurrencyType.values());
		view.addObject("currencyType", CurrencyType.values());
		return view;
	}
	
	private boolean priceConditionHadSet(CryptoCoinPriceNotice po) {
		return po.getMaxPrice() != null || po.getMinPrice() != null;
	}

	private boolean priceFluctuationSpeedConditionHadSet(CryptoCoinPriceNotice po) {
		return po.getFluctuationSpeedPercentage() != null
				&& po.getFluctuationSpeedPercentage().compareTo(BigDecimal.ZERO) != 0 && po.getMinuteRange() != null
				&& po.getMinuteRange() > 0;
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
