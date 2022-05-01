package demo.finance.cryptoCoin.tool.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.type.CurrencyType;
import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.finance.cryptoCoin.common.service.CryptoCoinAnalysisService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinCatalogMapper;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice1dayMapper;
import demo.finance.cryptoCoin.data.pojo.bo.CryptoCoinMABO;
import demo.finance.cryptoCoin.data.pojo.dto.InsertCryptoCoinLowPriceNoticeSettingDTO;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalogExample;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1day;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1dayExample;
import demo.finance.cryptoCoin.data.pojo.result.CryptoCoinMAResult;
import demo.finance.cryptoCoin.data.pojo.vo.CryptoCoinCatalogVO;
import demo.finance.cryptoCoin.data.service.CryptoCoin1DayDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoinCatalogService;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;
import demo.finance.cryptoCoin.notice.service.CryptoCoinCommonNoticeService;
import demo.finance.cryptoCoin.tool.service.CryptoCoinLowPriceNoticeService;
import demo.tool.telegram.pojo.constant.TelegramStaticChatID;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import telegram.pojo.constant.TelegramBotType;

@Service
public class CryptoCoinLowPriceNoticeServiceImpl extends CryptoCoinAnalysisService
		implements CryptoCoinLowPriceNoticeService {

	@Autowired
	private CryptoCoinPrice1dayMapper dailyDataMaper;
	@Autowired
	private CryptoCoin1DayDataSummaryService dailyDataService;
	@Autowired
	private CryptoCoinCatalogMapper catalogMapper;
	@Autowired
	private CryptoCoinPriceCacheService cacheService;
	@Autowired
	private CryptoCoinCatalogService coinCatalogService;

	@Autowired
	private CryptoCoinCommonNoticeService noticeService;

	@Override
	public void setNewLowPriceSubscription() {
		List<CryptoCoinCatalog> resultList = new ArrayList<>();
		List<CryptoCoinCatalog> coinTypeList = findLowPriceCoinType();
		for (CryptoCoinCatalog catalog : coinTypeList) {
			if (judgement(catalog)) {
				resultList.add(catalog);
			}
		}
		List<String> catalogNameList = resultList.stream().map(po -> po.getCoinNameEnShort())
				.collect(Collectors.toList());
		setLowPriceCatalogRedisList(catalogNameList);
	}

	private List<CryptoCoinCatalog> findLowPriceCoinType() {
		LocalDateTime yesterday = LocalDateTime.now().with(LocalTime.MIN).minusDays(1);
		BigDecimal lowPrice = new BigDecimal(0.5);

		CryptoCoinPrice1dayExample example = new CryptoCoinPrice1dayExample();
		example.createCriteria().andIsDeleteEqualTo(false).andStartTimeEqualTo(yesterday).andEndPriceLessThan(lowPrice);
		List<CryptoCoinPrice1day> dataList = dailyDataMaper.selectByExample(example);
		if(dataList.isEmpty()) {
			return new ArrayList<>();
		}
		List<Long> coinTypeIdList = dataList.stream().map(po -> po.getCoinType()).collect(Collectors.toList());

		CryptoCoinCatalogExample catalogExample = new CryptoCoinCatalogExample();
		catalogExample.createCriteria().andIsDeleteEqualTo(false).andIdIn(coinTypeIdList);
		List<CryptoCoinCatalog> catalogList = catalogMapper.selectByExample(catalogExample);

		return catalogList;
	}

	private boolean judgement(CryptoCoinCatalog catalog) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime twoMonthAgo = now.with(LocalTime.MIN).minusMonths(2);

		CryptoCoinPrice1dayExample example = new CryptoCoinPrice1dayExample();
		example.createCriteria().andIsDeleteEqualTo(false).andCoinTypeEqualTo(catalog.getId())
				.andStartTimeGreaterThan(twoMonthAgo);
		List<CryptoCoinPrice1day> oldData = dailyDataMaper.selectByExample(example);
		if (oldData == null || oldData.isEmpty()) {
			return false;
		}

		List<CryptoCoinPriceCommonDataBO> dataList = dailyDataService.getCommonDataList(catalog, CurrencyType.USD,
				twoMonthAgo);
		if (continuousRiseSixDay(dataList) || priceDoubledInTwoWeek(dataList) || rise30Percentage(dataList)
				|| breakAwayMA(dataList)) {
			return true;
		}

		return false;
	}

	private boolean continuousRiseSixDay(List<CryptoCoinPriceCommonDataBO> dataList) {
		if (dataList == null || dataList.size() < 6) {
			return false;
		}

		BigDecimal tmpPrice = BigDecimal.ZERO;
		CryptoCoinPriceCommonDataBO tmpData = null;
		for (int i = dataList.size() - 1 - 6; i < dataList.size(); i++) {
			tmpData = dataList.get(i);
			if (tmpPrice.compareTo(tmpData.getEndPrice()) > 0) {
				return false;
			} else {
				tmpPrice = tmpData.getEndPrice();
			}
		}
		return true;
	}

	private boolean priceDoubledInTwoWeek(List<CryptoCoinPriceCommonDataBO> dataList) {
		try {
			CryptoCoinPriceCommonDataBO startData = dataList.get(dataList.size() - 15);
			CryptoCoinPriceCommonDataBO endData = dataList.get(dataList.size() - 1);
			if (startData.getLowPrice().multiply(new BigDecimal(2)).compareTo(endData.getHighPrice()) <= 0) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	private boolean rise30Percentage(List<CryptoCoinPriceCommonDataBO> dataList) {
		try {
			CryptoCoinPriceCommonDataBO startData = dataList.get(dataList.size() - 2);
			CryptoCoinPriceCommonDataBO endData = dataList.get(dataList.size() - 1);
			if (startData.getLowPrice().multiply(new BigDecimal(1.3)).compareTo(endData.getHighPrice()) <= 0) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	private boolean breakAwayMA(List<CryptoCoinPriceCommonDataBO> dataList) {
		CryptoCoinMAResult maList = getMAList(dataList, 5, 10, 20, 40);

		CryptoCoinMABO tmpMA = null;
		CryptoCoinMABO nextMA = null;
		for (int i = 0; i < maList.getMaList().size() - 1; i++) {
			tmpMA = maList.getMaList().get(i);
			nextMA = maList.getMaList().get(i + 1);
			try {
				if (tmpMA == null || tmpMA.getMaValue() == null || tmpMA.getMaValue() == 0 || tmpMA.getMaValue() < nextMA.getMaValue()) {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
		}

		CryptoCoinPriceCommonDataBO lastData = dataList.get(dataList.size() - 1);
		CryptoCoinMABO lastMA = maList.getMaList().get(maList.getMaList().size() - 1);
		return (lastData.getEndPrice().doubleValue() >= (lastMA.getMaValue()));
	}

	private void setLowPriceCatalogRedisList(List<String> list) {
		cleanLowPriceCatalogRedisList();

		if (list.isEmpty()) {
			return;
		}
		
		constantService.getLowPriceSubscriptionSet().addAll(list);
	}

	private void cleanLowPriceCatalogRedisList() {
		constantService.getLowPriceSubscriptionSet().clear();
	}

	@Override
	public List<CryptoCoinCatalogVO> getLowPriceSubscriptionCatalogVOList() {
		List<CryptoCoinCatalogVO> voList = new ArrayList<>();
		List<CryptoCoinCatalog> catalogPOList = getLowPriceSubscriptionCatalogList();
		if (catalogPOList.isEmpty()) {
			return voList;
		}

		for (CryptoCoinCatalog po : catalogPOList) {
			voList.add(cryptoCoinCatalogPOToVO(po));
		}

		return voList;
	}

	@Override
	public List<CryptoCoinCatalog> getLowPriceSubscriptionCatalogList() {
		return coinCatalogService.findCatalog(new ArrayList<>(constantService.getLowPriceSubscriptionSet()));
	}

	@Override
	public void setLowPriceCoinWatching() {
		List<CryptoCoinCatalog> lowPriceSubscriptionCatalogPOList = getLowPriceSubscriptionCatalogList();
		if (lowPriceSubscriptionCatalogPOList.isEmpty()) {
			return;
		}

		InsertCryptoCoinLowPriceNoticeSettingDTO dto = null;
		CryptoCoinPriceCommonDataBO newPrice = null;
		String telegramChatPK = systemOptionService.encryptId(TelegramStaticChatID.MY_ID);
		for (CryptoCoinCatalog po : lowPriceSubscriptionCatalogPOList) {
			dto = new InsertCryptoCoinLowPriceNoticeSettingDTO();
			dto.setTelegramChatPK(telegramChatPK);
			dto.setBotName(TelegramBotType.CRYPTO_COIN_LOW_PRICE_NOTICE_BOT.getName());
			dto.setCoinType(po.getCoinNameEnShort());
			dto.setCurrencyType(CurrencyType.USD.getCode());
			dto.setNoticeCount(999999);
			dto.setFluctuationSpeedPercentage(1.2);
			dto.setTimeRangeOfDataWatch(3);
			dto.setTimeUnitOfDataWatch(TimeUnitType.minute.getCode());
			dto.setTimeRangeOfNoticeInterval(10);
			dto.setTimeUnitOfNoticeInterval(TimeUnitType.minute.getCode());
			dto.setStartNoticeTime(localDateTimeHandler.dateToStr(LocalDateTime.now()));
			dto.setValidTime(localDateTimeHandler.dateToStr(LocalDateTime.now().plusHours(8)));
			noticeService.insertNewCryptoCoinLowPriceNoticeSetting(dto);

			newPrice = cacheService.getNewPrice(po, CurrencyType.USD);
			if (newPrice != null) {
				dto.setFluctuationSpeedPercentage(null);
				dto.setTimeRangeOfDataWatch(null);
				dto.setTimeUnitOfDataWatch(null);
				dto.setNoticeCount(1);
				dto.setMaxPrice(newPrice.getEndPrice().multiply(new BigDecimal(1.06)));
				dto.setMinPrice(newPrice.getEndPrice().multiply(new BigDecimal(0.94)));
				noticeService.insertNewCryptoCoinPriceNoticeSetting(dto);
			}
		}

	}

}
