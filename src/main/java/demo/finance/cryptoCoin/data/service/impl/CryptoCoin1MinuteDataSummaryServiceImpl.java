package demo.finance.cryptoCoin.data.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice1minuteMapper;
import demo.finance.cryptoCoin.data.pojo.bo.CryptoCoinPriceCommonDataBO;
import demo.finance.cryptoCoin.data.pojo.constant.CryptoCoinDataConstant;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1minute;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1minuteExample;
import demo.finance.cryptoCoin.data.service.CryptoCoin1MinuteDataSummaryService;
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
				if(data.getVolume() != null && data.getVolume() > 0) {
					if(tmpPO.getVolume().doubleValue() < data.getVolume()) {
						tmpPO.setVolume(new BigDecimal(data.getVolume()));
					}
				}

				summaryMapper.updateByPrimaryKeySelective(tmpPO);
			}
		}

	}

	@Override
	public CommonResult deleteExpiredCacheData() {
		CommonResult r = new CommonResult();

		LocalDateTime expriedTime = LocalDateTime.now()
				.minusHours(CryptoCoinDataConstant.CRYPTO_COIN_1MINUTE_DATA_LIVE_HOURS);

		CryptoCoinPrice1minuteExample example = new CryptoCoinPrice1minuteExample();
		example.createCriteria().andCreateTimeLessThan(expriedTime);
		summaryMapper.deleteByExample(example);
		r.setIsSuccess();
		return r;
	}

	@Override
	public List<CryptoCoinPrice1minute> getData(CryptoCoinType coinType, CurrencyType currencyType, LocalDateTime startTime) {
		CryptoCoinPrice1minuteExample example = new CryptoCoinPrice1minuteExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getCode()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeGreaterThanOrEqualTo(startTime);
		;
		example.setOrderByClause("create_time desc");

		return summaryMapper.selectByExample(example);
	}
	
	@Override
	public List<CryptoCoinPriceCommonDataBO> getCommonData(CryptoCoinType coinType, CurrencyType currencyType, LocalDateTime startTime) {
		List<CryptoCoinPrice1minute> poList = getData(coinType, currencyType, startTime);
		
		CryptoCoinPriceCommonDataBO tmpCommonData = null;
		List<CryptoCoinPriceCommonDataBO> commonDataList = new ArrayList<>();
		for(CryptoCoinPrice1minute po : poList) {
			tmpCommonData = new CryptoCoinPriceCommonDataBO();
			BeanUtils.copyProperties(po, tmpCommonData);
			commonDataList.add(tmpCommonData);
		}
		
		return commonDataList;
	}
}
