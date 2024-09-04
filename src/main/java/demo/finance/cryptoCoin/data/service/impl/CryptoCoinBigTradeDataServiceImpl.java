package demo.finance.cryptoCoin.data.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinBigTradeMapper;
import demo.finance.cryptoCoin.data.pojo.constant.CryptoCoinDataUrl;
import demo.finance.cryptoCoin.data.pojo.dto.CryptoCoinBigTradeQueryDTO;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinBigTrade;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinBigTradeExample;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinBigTradeExample.Criteria;
import demo.finance.cryptoCoin.data.pojo.vo.CryptoCoinBigTradeBubbleChartVO;
import demo.finance.cryptoCoin.data.pojo.vo.CryptoCoinBigTradeSummaryVO;
import demo.finance.cryptoCoin.data.service.CryptoCoinBigTradeDataService;
import finance.cryptoCoin.pojo.bo.CryptoCoinBigTradeDataBO;
import finance.cryptoCoin.pojo.type.CryptoCoinBigMoveDataType;

@Service
public class CryptoCoinBigTradeDataServiceImpl extends CryptoCoinCommonService
		implements CryptoCoinBigTradeDataService {

	@Autowired
	private CryptoCoinBigTradeMapper cryptoCoinBigTradeMapper;

	@Override
	public void receiveNewBigTradeFutureUmDataMessage(String msg) {
		if (StringUtils.isBlank(msg)) {
			return;
		}
		CryptoCoinBigTradeDataBO bo = buildObjFromJsonCustomization(msg, CryptoCoinBigTradeDataBO.class);
		if (StringUtils.isBlank(bo.getSymbol())) {
			return;
		}
		if (CryptoCoinBigMoveDataType.getType(bo.getDataType()) == null) {
			return;
		}

		CryptoCoinBigTrade po = new CryptoCoinBigTrade();

		try {
			po.setEventTime(localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(bo.getBigTradeTimeStr()));
		} catch (Exception e) {
			return;
		}
		CryptoCoinBigTradeExample example = new CryptoCoinBigTradeExample();
		example.createCriteria().andSymbolEqualTo(bo.getSymbol()).andEventTimeEqualTo(po.getEventTime())
				.andAmountEqualTo(bo.getAmount()).andQuantityEqualTo(bo.getQuantity()).andPriceEqualTo(bo.getPrice())
				.andIsMakerEqualTo(bo.getIsMaker()).andDataTypeEqualTo(bo.getDataType());
		List<CryptoCoinBigTrade> oldDataList = cryptoCoinBigTradeMapper.selectByExample(example);
		if (!oldDataList.isEmpty()) {
			return;
		}

		po.setAmount(bo.getAmount());
		po.setPrice(bo.getPrice());
		po.setQuantity(bo.getQuantity());
		po.setIsMaker(bo.getIsMaker());
		po.setSymbol(bo.getSymbol());
		po.setDataType(bo.getDataType());

		try {
			cryptoCoinBigTradeMapper.insertSelective(po);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ModelAndView getBigTradeDataBubbleChartBySymbol(String symbol) {
		ModelAndView v = new ModelAndView("cryptoCoin/bigTradeChartBySymbolView");
		v.addObject("title", "Big trade");
		v.addObject("bubbleChartUrl", CryptoCoinDataUrl.BIG_TRADE_FUTURE_UM_BUBBLE_CHART_BY_SYMBOL);
		v.addObject("lineChartUrl", CryptoCoinDataUrl.BIG_TRADE_FUTURE_UM_LINE_CHART_BY_SYMBOL);
		if (StringUtils.isNotBlank(symbol)) {
			v.addObject("preSetSymbol", symbol);
			v.addObject("title", "Big trade" + symbol);
		}
		return v;
	}

	@Override
	public ModelAndView getBigTradeBubbleChartBySymbol(CryptoCoinBigTradeQueryDTO dto) {
		ModelAndView v = new ModelAndView("cryptoCoin/getBigTradeBubbleChartBySymbol");
		v.addObject("title", (dto.getSymbol() + ", Big trade"));
		v.addObject("symbol", dto.getSymbol());

		LocalDateTime now = LocalDateTime.now();
		CryptoCoinBigTradeExample example = new CryptoCoinBigTradeExample();
		example.createCriteria().andSymbolEqualTo(dto.getSymbol())
				.andEventTimeGreaterThanOrEqualTo(now.minusHours(dto.getStart()))
				.andEventTimeLessThanOrEqualTo(now.minusHours(dto.getEnd()));
		List<CryptoCoinBigTrade> dataList = cryptoCoinBigTradeMapper.selectByExample(example);

		List<CryptoCoinBigTradeBubbleChartVO> saleList = new ArrayList<>();
		List<CryptoCoinBigTradeBubbleChartVO> buyList = new ArrayList<>();
		CryptoCoinBigTrade data = null;
		CryptoCoinBigTradeBubbleChartVO vo = null;
		BigDecimal bigStep = optionService.getBinanceFutureUmSymbolBigStepMap().get(dto.getSymbol());
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			vo = new CryptoCoinBigTradeBubbleChartVO();
			vo.setPrice(data.getPrice().doubleValue());
			Long timeGap = ChronoUnit.MINUTES.between(now, data.getEventTime());
			vo.setTimeGap(timeGap.intValue());
			int r = data.getAmount().divide(bigStep, 0, RoundingMode.HALF_UP).intValue();
			r += 3;
			r *= 3;
			vo.setR(r);
			vo.setAmount(data.getAmount().doubleValue());
			vo.setEventTime(localDateTimeHandler.dateToStr(data.getEventTime()));
			vo.setQuantity(data.getQuantity().doubleValue());
			if (data.getIsMaker()) {
				saleList.add(vo);
			} else {
				buyList.add(vo);
			}
		}

		v.addObject("saleList", saleList);
		v.addObject("buyList", buyList);

		return v;
	}

	@Override
	public ModelAndView getBigTradeLineChartBySymbol(CryptoCoinBigTradeQueryDTO dto) {
		ModelAndView v = new ModelAndView("cryptoCoin/getBigTradeLineChartBySymbol");
		v.addObject("title", (dto.getSymbol() + ", Big trade"));
		v.addObject("symbol", dto.getSymbol());

		LocalDateTime now = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
		LocalDateTime startTime = now.minusHours(dto.getStart());
		LocalDateTime endTime = now.minusHours(dto.getEnd() - 1);
		CryptoCoinBigTradeExample example = new CryptoCoinBigTradeExample();
		example.createCriteria().andSymbolEqualTo(dto.getSymbol()).andEventTimeGreaterThanOrEqualTo(startTime)
				.andEventTimeLessThanOrEqualTo(endTime);
		List<CryptoCoinBigTrade> dataList = cryptoCoinBigTradeMapper.selectByExample(example);

		// for debug
//		BigDecimal total = BigDecimal.ZERO;
//		for (CryptoCoinBigTrade data : dataList) {
//			if (data.getIsMaker()) {
//				total = total.add(data.getAmount().negate());
//			} else {
//				total = total.add(data.getAmount());
//			}
//		}
//		System.out.println(total);

		List<String> timeStringXLine = new ArrayList<>();
		LocalDateTime indexTime = now.minusHours(dto.getStart());
		for (int i = 0; !indexTime.isAfter(endTime); i++) {
			indexTime = startTime.plusHours(i);
			timeStringXLine.add(localDateTimeHandler.dateToStr(indexTime, DATE_FORMAT_FOR_INDEX_CHART_IN_HOUR));
		}

		v.addObject("xValues", timeStringXLine);

		Map<LocalDateTime, CryptoCoinBigTrade> summaryDataMap = new HashMap<>();
		for (int i = 0; i < dataList.size(); i++) {
			CryptoCoinBigTrade dataInList = dataList.get(i);
			CryptoCoinBigTrade dataInMap = summaryDataMap
					.get(dataInList.getEventTime().withMinute(0).withSecond(0).withNano(0));

			if (dataInMap == null) {
				dataInMap = new CryptoCoinBigTrade();
				dataInMap.setIsMaker(dataInList.getIsMaker());
				if (dataInList.getIsMaker()) {
					dataInMap.setAmount(dataInList.getAmount().negate());
				} else {
					dataInMap.setAmount(dataInList.getAmount());
				}
				summaryDataMap.put(dataInList.getEventTime().withMinute(0).withSecond(0).withNano(0), dataInMap);
			} else {
				if (dataInList.getIsMaker()) {
					dataInMap.setAmount(dataInMap.getAmount().subtract(dataInList.getAmount()));
				} else {
					dataInMap.setAmount(dataInMap.getAmount().add(dataInList.getAmount()));
				}
			}
		}

//		for debug
//		total = BigDecimal.ZERO;
//		for(Entry<LocalDateTime, CryptoCoinBigTrade> dataInMap : summaryDataMap.entrySet()) {
//			CryptoCoinBigTrade data = dataInMap.getValue();
//			total = total.add(data.getAmount());
//		}
//		System.out.println(total);

		List<BigDecimal> totalList = new ArrayList<>();
		indexTime = now.minusHours(dto.getStart());
		BigDecimal lastAmount = BigDecimal.ZERO;
		for (int i = 0; !indexTime.isAfter(endTime); i++) {
			indexTime = startTime.plusHours(i);
			CryptoCoinBigTrade dataInMap = summaryDataMap.get(indexTime);
			if (dataInMap == null) {
				totalList.add(lastAmount);
			} else {
				BigDecimal amount = dataInMap.getAmount();
				lastAmount = lastAmount.add(amount);
				totalList.add(lastAmount);
			}
		}

		v.addObject("line", totalList);

		return v;
	}

	@Override
	public ModelAndView getBigTradeSummaryDataTable() {
		ModelAndView v = new ModelAndView("cryptoCoin/bigTradeDataTable");
		return v;
	}

	@Override
	public ModelAndView getBigTradeSummaryDataTable(CryptoCoinBigTradeQueryDTO dto) {
		ModelAndView v = new ModelAndView("cryptoCoin/getBigTradeDataTable");

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime startTime = now.minusHours(dto.getStart());
		LocalDateTime endTime = now.minusHours(dto.getEnd());
		CryptoCoinBigTradeExample example = new CryptoCoinBigTradeExample();
		Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(dto.getSymbol())) {
			criteria.andSymbolEqualTo(dto.getSymbol());
		}
		criteria.andEventTimeGreaterThanOrEqualTo(startTime).andEventTimeLessThanOrEqualTo(endTime);
		List<CryptoCoinBigTrade> sourceDataList = cryptoCoinBigTradeMapper.selectByExample(example);
		if (sourceDataList == null || sourceDataList.isEmpty()) {
			return v;
		}

		Map<String, CryptoCoinBigTradeSummaryVO> summaryMap = new HashMap<>();
		for (CryptoCoinBigTrade data : sourceDataList) {
			CryptoCoinBigTradeSummaryVO dataInMap = summaryMap.get(data.getSymbol());
			if (dataInMap == null) {
				dataInMap = new CryptoCoinBigTradeSummaryVO();
				dataInMap.setSymbol(data.getSymbol());
				summaryMap.put(data.getSymbol(), dataInMap);
			}
			dataInMap.setCountingTotal(dataInMap.getCountingTotal() + 1);
			if (data.getIsMaker()) {
				dataInMap.setAmountSell(data.getAmount());
				dataInMap.setCountingSell(dataInMap.getCountingSell() + 1);
				dataInMap.setAmountTotal(dataInMap.getAmountTotal().subtract(data.getAmount()));
			} else {
				dataInMap.setAmountBuy(data.getAmount());
				dataInMap.setCountingBuy(dataInMap.getCountingBuy() + 1);
				dataInMap.setAmountTotal(dataInMap.getAmountTotal().add(data.getAmount()));
			}
		}

		List<CryptoCoinBigTradeSummaryVO> resultList = new ArrayList<>();
		for (Entry<String, CryptoCoinBigTradeSummaryVO> entry : summaryMap.entrySet()) {
			resultList.add(entry.getValue());
		}

		Collections.sort(resultList);
		v.addObject("dataList", resultList);
		return v;
	}
}
