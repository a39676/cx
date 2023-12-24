package demo.finance.currencyExchangeRate.data.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.testEvent.common.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.scheduleClawing.currencyExchangeRate.pojo.dto.CurrencyExchangeRateCollectDTO;
import autoTest.testEvent.scheduleClawing.currencyExchangeRate.pojo.dto.CurrencyExchangeRatePairDTO;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import autoTest.testModule.pojo.type.TestModuleType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.automationTest.mq.producer.TestEventInsertAckProducer;
import demo.finance.common.service.impl.FinanceCommonService;
import demo.finance.currencyExchangeRate.data.mapper.CurrencyExchangeRate1dayMapper;
import demo.finance.currencyExchangeRate.data.pojo.po.CurrencyExchangeRate1day;
import demo.finance.currencyExchangeRate.data.pojo.po.CurrencyExchangeRate1dayExample;
import demo.finance.currencyExchangeRate.data.service.CurrencyExchangeRateService;
import finance.currencyExchangeRate.pojo.dto.CurrencyExchageRateDataDTO;
import finance.currencyExchangeRate.pojo.result.CurrencyExchageRateCollectResult;

@Service
public class CurrencyExchangeRateServiceImpl extends FinanceCommonService implements CurrencyExchangeRateService {

	@Autowired
	private CurrencyExchangeRateOptionService optionService;
	
	@Autowired
	private TestEventInsertAckProducer testEventInsertAckProducer;
	
	@Autowired
	private CurrencyExchangeRate1dayMapper mapper;
	
	@Override
	public void sendDailyDataQuery() {
		sendDataQuery(true);
	}
	
	@Override
	public void sendDataQuery(Boolean isDailyQuery) {
		AutomationTestInsertEventDTO eventDTO = new AutomationTestInsertEventDTO();
		eventDTO.setTestEventId(0L);
		eventDTO.setTestModuleType(TestModuleType.SCHEDULE_CLAWING.getId());
		eventDTO.setFlowType(ScheduleClawingType.CURRENCY_EXCHANGE_RAGE.getId());
		eventDTO.setTestEventId(snowFlake.getNextId());

		CurrencyExchangeRateCollectDTO paramDTO = new CurrencyExchangeRateCollectDTO();
		paramDTO.setIsDailyQuery(isDailyQuery);
		
		paramDTO.setExchangerateApiApiKey(optionService.getExchangerateApiApiKey());
		List<CurrencyExchangeRatePairDTO> currencyPairList = optionService.getPairList();
		
		CurrencyExchangeRatePairDTO dataPair = null;
		
		for(CurrencyExchangeRatePairDTO cp : currencyPairList) {
			dataPair = new CurrencyExchangeRatePairDTO();
			dataPair.setCurrencyFromCode(cp.getCurrencyFromCode());
			dataPair.setCurrencyToCode(cp.getCurrencyToCode());
			paramDTO.addDataPair(dataPair);
		}
		
		eventDTO = automationTestInsertEventDtoAddParamStr(eventDTO, paramDTO);
		
		testEventInsertAckProducer.send(eventDTO);
	}
	
	@Override
	public CommonResult receiveDailyData(CurrencyExchageRateCollectResult inputDataResult) {
		CommonResult r = new CommonResult();
		
		log.error("Receive currency exchange rate data");
		List<CurrencyExchageRateDataDTO> dataList = inputDataResult.getDataList();
		for(CurrencyExchageRateDataDTO dataDTO : dataList) {
			updateTodayDayData(dataDTO);
			if(inputDataResult.getIsDailyQuery()) {
				updateYesterDayData(dataDTO);
			}
		}
		
		r.setIsSuccess();
		return r;
	}
	
	private void updateYesterDayData(CurrencyExchageRateDataDTO dto) {
		Integer currencyCodeFrom = dto.getCurrencyCodeFrom();
		Integer currencyCodeTo = dto.getCurrencyCodeTo();
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime yesterday = now.with(LocalTime.MIN).minusDays(1);
		
		CurrencyExchangeRate1dayExample example = new CurrencyExchangeRate1dayExample();
		example.createCriteria().andCurrencyFromEqualTo(currencyCodeFrom).andCurrencyToEqualTo(currencyCodeTo).andStartTimeEqualTo(yesterday);
		List<CurrencyExchangeRate1day> poList = mapper.selectByExample(example);
		CurrencyExchangeRate1day po = null;
		if(poList == null || poList.isEmpty()) {
			po = new CurrencyExchangeRate1day();
			po.setCurrencyFrom(currencyCodeFrom);
			po.setCurrencyTo(currencyCodeTo);
			po.setId(snowFlake.getNextId());
			po.setStartTime(yesterday);
			po.setEndTime(yesterday.with(LocalTime.MAX));
		} else {
			po = poList.get(0);
		}
		
		po.setBuyHighPrice(dto.getYesterdayBuyHigh());
		po.setBuyLowPrice(dto.getYesterdayBuyLow());
		po.setSellHighPrice(dto.getYesterdaySellHigh());
		po.setSellLowPrice(dto.getYesterdaySellLow());
		po.setBuyAvgPrice(dto.getYesterdayBuyAvg());
		po.setSellAvgPrice(dto.getYesterdaySellAvg());
		
		if(poList == null || poList.isEmpty()) {
			mapper.insertSelective(po);
		} else {
			mapper.updateByPrimaryKeySelective(po);
		}
	}
	
	private void updateTodayDayData(CurrencyExchageRateDataDTO dto) {
		Integer currencyCodeFrom = dto.getCurrencyCodeFrom();
		Integer currencyCodeTo = dto.getCurrencyCodeTo();
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime todayStart = now.with(LocalTime.MIN);
		LocalDateTime todayEnd = now.with(LocalTime.MAX);
		
		CurrencyExchangeRate1dayExample example = new CurrencyExchangeRate1dayExample();
		example.createCriteria().andCurrencyFromEqualTo(currencyCodeFrom).andCurrencyToEqualTo(currencyCodeTo).andStartTimeEqualTo(todayStart);
		List<CurrencyExchangeRate1day> poList = mapper.selectByExample(example);
		CurrencyExchangeRate1day po = null;
		if(poList == null || poList.isEmpty()) {
			po = new CurrencyExchangeRate1day();
			po.setId(snowFlake.getNextId());
			po.setCurrencyFrom(currencyCodeFrom);
			po.setCurrencyTo(currencyCodeTo);
			po.setStartTime(todayStart);
			po.setEndTime(todayEnd);
		} else {
			po = poList.get(0);
		}
		
		if(po.getBuyHighPrice() == null || dto.getCurrencyAmountTo().compareTo(po.getBuyHighPrice()) > 0) {
			po.setBuyHighPrice(dto.getCurrencyAmountTo());
		}
		if(po.getBuyLowPrice() == null || dto.getCurrencyAmountTo().compareTo(po.getBuyLowPrice()) < 0) {
			po.setBuyLowPrice(dto.getCurrencyAmountTo());
		}
		if(po.getSellHighPrice() == null || dto.getCurrencyAmountTo().compareTo(po.getSellHighPrice()) > 0) {
			po.setSellHighPrice(dto.getCurrencyAmountTo());
		}
		if(po.getSellLowPrice() == null || dto.getCurrencyAmountTo().compareTo(po.getSellLowPrice()) < 0) {
			po.setSellLowPrice(dto.getCurrencyAmountTo());
		}
		po.setBuyAvgPrice(dto.getCurrencyAmountTo());
		
		po.setBuyAvgPrice(BigDecimal.ZERO);
		po.setSellAvgPrice(BigDecimal.ZERO);
		
		if(poList == null || poList.isEmpty()) {
			mapper.insertSelective(po);
		} else {
			mapper.updateByPrimaryKeySelective(po);
		}
	}

}
