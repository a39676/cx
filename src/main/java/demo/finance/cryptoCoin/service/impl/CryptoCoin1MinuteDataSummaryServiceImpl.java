package demo.finance.cryptoCoin.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.mapper.CryptoCoinPrice1minuteMapper;
import demo.finance.cryptoCoin.pojo.constant.CryptoCoinConstant;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPrice1minute;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPrice1minuteExample;
import demo.finance.cryptoCoin.service.CryptoCoin1MinuteDataSummaryService;
import finance.cryptoCoin.pojo.dto.CryptoCoinHistoryPriceDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinHistoryPriceSubDTO;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

@Service
public class CryptoCoin1MinuteDataSummaryServiceImpl extends CryptoCoinCommonService
		implements CryptoCoin1MinuteDataSummaryService {

	@Autowired
	private CryptoCoinPrice1minuteMapper summaryMapper;

	@Override
	public CommonResult reciveCoinHistoryPrice(CryptoCoinHistoryPriceDTO dto) {
		CommonResult r = new CommonResult();

		List<CryptoCoinHistoryPriceSubDTO> dataList = dto.getPriceHistoryData();
		if (dataList == null || dataList.isEmpty()) {
			return r;
		}

		CryptoCoinType coinType = CryptoCoinType.getType(dto.getCryptoCoinTypeName());
		CurrencyType currencyType = CurrencyType.getType(dto.getCurrencyName());
		if (coinType == null || currencyType == null) {
			return r;
		}

		updateSummaryData(dataList, coinType, currencyType);

		return r;
	}

	private void updateSummaryData(List<CryptoCoinHistoryPriceSubDTO> dataList, CryptoCoinType coinType,
			CurrencyType currencyType) {

		LocalDateTime dataStartTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dataList.get(0).getTime());
		LocalDateTime dataEndime = localDateTimeHandler
				.stringToLocalDateTimeUnkonwFormat(dataList.get(dataList.size() - 1).getTime());

		CryptoCoinPrice1minuteExample example = new CryptoCoinPrice1minuteExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getCode()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeBetween(dataStartTime, dataEndime);

		List<CryptoCoinPrice1minute> poList = summaryMapper.selectByExample(example);

		Map<LocalDateTime, CryptoCoinPrice1minute> datetimeMapPo = new HashMap<>();
		poList.stream().forEach(po -> datetimeMapPo.put(po.getStartTime(), po));

		CryptoCoinPrice1minute tmpPO = null;
		LocalDateTime tmpTime = null;
		for (CryptoCoinHistoryPriceSubDTO data : dataList) {
			tmpTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(data.getTime());
			tmpPO = datetimeMapPo.get(tmpTime);
			if (tmpPO == null) {
				tmpPO = new CryptoCoinPrice1minute();
				tmpPO.setId(snowFlake.getNextId());
				tmpPO.setCoinType(coinType.getCode());
				tmpPO.setCurrencyType(currencyType.getCode());
				tmpPO.setStartTime(tmpTime);
				tmpPO.setEndTime(tmpTime.plusMinutes(1));
				tmpPO.setStartPrice(new BigDecimal(data.getStart()));
				tmpPO.setEndPrice(new BigDecimal(data.getEnd()));
				tmpPO.setHighPrice(new BigDecimal(data.getHigh()));
				tmpPO.setLowPrice(new BigDecimal(data.getLow()));
				tmpPO.setVolume(new BigDecimal(data.getVolume()));

				summaryMapper.insertSelective(tmpPO);
			} else {
				if (tmpPO.getHighPrice().doubleValue() < data.getHigh()) {
					tmpPO.setHighPrice(new BigDecimal(data.getHigh()));
				}
				if (tmpPO.getLowPrice().doubleValue() > data.getLow()) {
					tmpPO.setLowPrice(new BigDecimal(data.getLow()));
				}
				tmpPO.setEndPrice(new BigDecimal(data.getEnd()));

				summaryMapper.updateByPrimaryKeySelective(tmpPO);
			}
		}

	}

	@Override
	public CommonResult deleteExpiredCacheData() {
		CommonResult r = new CommonResult();

		LocalDateTime expriedTime = LocalDateTime.now()
				.minusHours(CryptoCoinConstant.CRYPTO_COIN_1MINUTE_DATA_LIVE_HOURS);

		CryptoCoinPrice1minuteExample example = new CryptoCoinPrice1minuteExample();
		example.createCriteria().andCreateTimeLessThan(expriedTime);
		summaryMapper.deleteByExample(example);
		r.setIsSuccess();
		return r;
	}
}
