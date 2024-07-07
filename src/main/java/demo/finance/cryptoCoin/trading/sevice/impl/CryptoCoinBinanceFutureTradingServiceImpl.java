package demo.finance.cryptoCoin.trading.sevice.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.service.CommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinCatalogMapper;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinComplexToolMapper;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice1minuteMapper;
import demo.finance.cryptoCoin.data.pojo.dto.CryptoCoinBtcAndLowIndexGapDTO;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalogExample;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1minute;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1minuteExample;
import demo.finance.cryptoCoin.trading.mq.producer.CryptoCoinBinanceUmBtcArbitrageWithBatchProducer;
import demo.finance.cryptoCoin.trading.mq.producer.CryptoCoinBinanceUmFutureOrderProducer;
import demo.finance.cryptoCoin.trading.sevice.CryptoCoinBinanceFutureTradingService;
import finance.cryptoCoin.binance.pojo.dto.CryptoCoinBinanceBtArbitrageWithBatchDTO;
import finance.cryptoCoin.binance.pojo.dto.CryptoCoinBinanceFutureOrderDTO;
import finance.cryptoCoin.binance.pojo.type.BinanceOrderSideType;
import finance.cryptoCoin.binance.pojo.type.BinancePositionSideType;
import finance.cryptoCoin.pojo.constant.CryptoCoinDataConstant;

@Service
public class CryptoCoinBinanceFutureTradingServiceImpl extends CommonService
		implements CryptoCoinBinanceFutureTradingService {

	@Autowired
	private CryptoCoinComplexToolMapper complexToolMapper;
	@Autowired
	private CryptoCoinBinanceUmFutureOrderProducer umFutureOrderProducer;
	@Autowired
	private CryptoCoinBinanceUmBtcArbitrageWithBatchProducer umBtcArbitrageWithBatchProducer;
	@Autowired
	private CryptoCoinPrice1minuteMapper data1minuteMapper;
	@Autowired
	private CryptoCoinCatalogMapper catalogMapper;

	@Override
	public ModelAndView tradingView() {
		ModelAndView v = new ModelAndView("cryptoCoin/setFutureOrder");

		LocalDateTime defaultStartTime = LocalDateTime.now().minusHours(8);

		List<CryptoCoinBtcAndLowIndexGapDTO> dataList = complexToolMapper.selectGaps(defaultStartTime);
		List<String> xValues = new ArrayList<>();
		List<Double> gap = new ArrayList<>();
		for (CryptoCoinBtcAndLowIndexGapDTO dto : dataList) {
			xValues.add(localDateTimeHandler.dateToStr(dto.getStartTime()));
			gap.add(dto.getGap());
		}

		v.addObject("xValues", xValues);
		v.addObject("gap", gap);

		CryptoCoinCatalogExample catalogExample = new CryptoCoinCatalogExample();
		catalogExample.createCriteria().andCoinNameEnShortEqualTo("BTC");
		List<CryptoCoinCatalog> catalogList = catalogMapper.selectByExample(catalogExample);
		if (catalogList == null || catalogList.isEmpty()) {
			return v;
		}
		CryptoCoinCatalog btcCatalog = catalogList.get(0);

		CryptoCoinPrice1minuteExample example = new CryptoCoinPrice1minuteExample();
		example.createCriteria().andCoinTypeEqualTo(btcCatalog.getId()).andStartTimeGreaterThan(defaultStartTime);
		List<CryptoCoinPrice1minute> btcDataList = data1minuteMapper.selectByExample(example);
		if (btcDataList == null || btcDataList.isEmpty()) {
			return v;
		}

		example = new CryptoCoinPrice1minuteExample();
		example.createCriteria().andCoinTypeEqualTo(CryptoCoinDataConstant.LOW_CAP_INDEX_CATALOG_ID.longValue())
				.andStartTimeGreaterThan(defaultStartTime);
		List<CryptoCoinPrice1minute> lowCapDataList = data1minuteMapper.selectByExample(example);
		if (lowCapDataList == null || lowCapDataList.isEmpty()) {
			return v;
		}

		List<BigDecimal> btcPriceDataList = new ArrayList<>();
		for (CryptoCoinPrice1minute data : btcDataList) {
			btcPriceDataList.add(data.getEndPrice());
		}
		
		List<BigDecimal> lowCapPriceDataList = new ArrayList<>();
		for (CryptoCoinPrice1minute data : lowCapDataList) {
			lowCapPriceDataList.add(data.getEndPrice());
		}
		
		v.addObject("btcPriceData", btcPriceDataList);
		v.addObject("lowCapPriceDataList", lowCapPriceDataList);

		return v;
	}

	@Override
	public CommonResult sendFutureOrder(CryptoCoinBinanceFutureOrderDTO dto) {
		CommonResult r = new CommonResult();
		if (StringUtils.isBlank(dto.getSymbol())) {
			r.failWithMessage("Symbol invalid");
			return r;
		}
		if (dto.getAmount() == null || dto.getAmount() < 0) {
			r.failWithMessage("Amount invalid");
			return r;
		}
		if (BinanceOrderSideType.getType(dto.getOrderSideCode()) == null) {
			r.failWithMessage("Order side invalid");
			return r;
		}
		if (BinancePositionSideType.getType(dto.getPositionSideCode()) == null) {
			r.failWithMessage("Position side invalid");
			return r;
		}
		umFutureOrderProducer.binanceUmFutureOrder(dto);
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult sendBtcArbitrageWithBatchOrder(CryptoCoinBinanceBtArbitrageWithBatchDTO dto) {
		CommonResult r = new CommonResult();
		if (dto.getSingleAmount() == null || dto.getSingleAmount() < 0) {
			r.failWithMessage("Amount invalid");
			return r;
		}
		if (dto.getSymbols() == null || dto.getSymbols().isEmpty()) {
			r.failWithMessage("Symbol invalid");
			return r;
		}
		umBtcArbitrageWithBatchProducer.binanceUmBtcArbitrageWithBatch(dto);

		r.setIsSuccess();
		return r;
	}
}
