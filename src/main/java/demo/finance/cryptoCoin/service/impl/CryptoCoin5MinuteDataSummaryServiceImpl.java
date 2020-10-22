package demo.finance.cryptoCoin.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.mapper.CryptoCoinPrice5minuteMapper;
import demo.finance.cryptoCoin.pojo.bo.CryptoCoinPriceDataSummaryBO;
import demo.finance.cryptoCoin.pojo.bo.StepTimeBO;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPrice5minute;
import demo.finance.cryptoCoin.pojo.po.CryptoCoinPrice5minuteExample;
import demo.finance.cryptoCoin.service.CryptoCoin5MinuteDataSummaryService;
import finance.cryptoCoin.pojo.dto.CryptoCoinHistoryPriceDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinHistoryPriceSubDTO;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

@Service
public class CryptoCoin5MinuteDataSummaryServiceImpl extends CryptoCoinCommonService
		implements CryptoCoin5MinuteDataSummaryService {

	@Autowired
	private CryptoCoinPrice5minuteMapper summaryMapper;

	@Override
	public CommonResult reciveCoinHistoryPrice(CryptoCoinHistoryPriceDTO dto) {
		CommonResult r = new CommonResult();

		List<CryptoCoinPriceDataSummaryBO> boList = handleHistoryDataList(dto);
		if (boList == null || boList.isEmpty()) {
			return r;
		}

		for (CryptoCoinPriceDataSummaryBO bo : boList) {
			updateSummaryData(bo);
		}

		return r;
	}

	private List<CryptoCoinPriceDataSummaryBO> handleHistoryDataList(CryptoCoinHistoryPriceDTO dto) {
		List<CryptoCoinPriceDataSummaryBO> list = new ArrayList<>();

		List<CryptoCoinHistoryPriceSubDTO> dataList = dto.getPriceHistoryData();

		if (dataList == null || dataList.isEmpty()) {
			return list;
		}

		CryptoCoinType coinType = CryptoCoinType.getType(dto.getCryptoCoinTypeName());
		CurrencyType currencyType = CurrencyType.getType(dto.getCurrencyName());
		if (coinType == null || currencyType == null) {
			return list;
		}

		CryptoCoinPriceDataSummaryBO tmpSummaryDataBO = null;
		int minuteStep = 5;
		StepTimeBO stepTimeBO = null;
		LocalDateTime tmpDataTime = null;
		for (CryptoCoinHistoryPriceSubDTO tmpSubData : dataList) {
			tmpDataTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(tmpSubData.getTime());

			if (stepTimeBO == null) {
				stepTimeBO = findStepTime(tmpSubData, minuteStep);
				tmpSummaryDataBO = new CryptoCoinPriceDataSummaryBO();
				tmpSummaryDataBO.setCurrencyCode(currencyType.getCode());
				tmpSummaryDataBO.setCoinTypeCode(coinType.getCode());
				tmpSummaryDataBO.setStartTime(stepTimeBO.getStepStartTime());
				tmpSummaryDataBO.setEndTime(stepTimeBO.getStepEndTime());
				tmpSummaryDataBO.setStart(tmpSubData.getStart());
				tmpSummaryDataBO.setEnd(tmpSubData.getEnd());
				tmpSummaryDataBO.setHigh(tmpSubData.getHigh());
				tmpSummaryDataBO.setLow(tmpSubData.getLow());

			} else {
				if (!tmpDataTime.equals(stepTimeBO.getStepEndTime())) {

					if (tmpSubData.getHigh() > tmpSummaryDataBO.getHigh()) {
						tmpSummaryDataBO.setHigh(tmpSubData.getHigh());
					}
					if (tmpSubData.getLow() < tmpSummaryDataBO.getLow()) {
						tmpSummaryDataBO.setLow(tmpSubData.getLow());
					}

				} else {
					tmpSummaryDataBO.setEnd(tmpSubData.getEnd());
					list.add(tmpSummaryDataBO);

					stepTimeBO = null;
					tmpSummaryDataBO = null;
				}
			}

		}

		return list;
	}

	private StepTimeBO findStepTime(CryptoCoinHistoryPriceSubDTO dto, int minuteStep) {
		StepTimeBO bo = new StepTimeBO();
		LocalDateTime tmpDataTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getTime());
		LocalDateTime tmpStepEndTime = nextStepTime(tmpDataTime, minuteStep);
		LocalDateTime tmpStepStartTime = tmpStepEndTime.minusMinutes(minuteStep);
		bo.setStepStartTime(tmpStepStartTime);
		bo.setStepEndTime(tmpStepEndTime);
		return bo;
	}

	private void updateSummaryData(CryptoCoinPriceDataSummaryBO bo) {
		CryptoCoinPrice5minuteExample example = new CryptoCoinPrice5minuteExample();
		example.createCriteria().andCoinTypeEqualTo(bo.getCoinTypeCode()).andCurrencyTypeEqualTo(bo.getCurrencyCode())
				.andEndTimeEqualTo(bo.getEndTime());
		List<CryptoCoinPrice5minute> poList = summaryMapper.selectByExample(example);

		CryptoCoinPrice5minute po = null;
		if (poList == null || poList.isEmpty()) {
			po = new CryptoCoinPrice5minute();
			po.setId(snowFlake.getNextId());
			po.setCoinType(bo.getCoinTypeCode());
			po.setCurrencyType(bo.getCurrencyCode());
			po.setStartPrice(new BigDecimal(bo.getStart()));
			po.setEndPrice(new BigDecimal(bo.getEnd()));
			po.setHighPrice(new BigDecimal(bo.getHigh()));
			po.setLowPrice(new BigDecimal(bo.getLow()));
			po.setStartTime(bo.getStartTime());
			po.setEndTime(bo.getEndTime());

			summaryMapper.insertSelective(po);

		} else {
			po = poList.get(0);

			if (po.getHighPrice().doubleValue() < bo.getHigh()) {
				po.setHighPrice(new BigDecimal(bo.getHigh()));
			}
			if (po.getLowPrice().doubleValue() > bo.getLow()) {
				po.setLowPrice(new BigDecimal(bo.getLow()));
			}
			po.setEndPrice(new BigDecimal(bo.getEnd()));

			summaryMapper.updateByPrimaryKeySelective(po);
		}

	}
}
