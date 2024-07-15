package demo.finance.cryptoCoin.trading.sevice.impl;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.service.CommonService;
import demo.finance.cryptoCoin.trading.mq.producer.CryptoCoinBinanceUmBtcArbitrageWithBatchProducer;
import demo.finance.cryptoCoin.trading.mq.producer.CryptoCoinBinanceUmFutureOrderModifyProducer;
import demo.finance.cryptoCoin.trading.mq.producer.CryptoCoinBinanceUmFutureOrderProducer;
import demo.finance.cryptoCoin.trading.sevice.CryptoCoinBinanceFutureTradingService;
import finance.cryptoCoin.binance.pojo.dto.BinanceUpdateOrderDTO;
import finance.cryptoCoin.binance.pojo.dto.CryptoCoinBinanceBtArbitrageWithBatchDTO;
import finance.cryptoCoin.binance.pojo.dto.CryptoCoinBinanceFutureBatchOrderDTO;
import finance.cryptoCoin.binance.pojo.type.BinanceOrderSideType;
import finance.cryptoCoin.binance.pojo.type.BinanceOrderTypeType;
import finance.cryptoCoin.binance.pojo.type.BinancePositionSideType;

@Service
public class CryptoCoinBinanceFutureTradingServiceImpl extends CommonService
		implements CryptoCoinBinanceFutureTradingService {

//	@Autowired
//	private CryptoCoinComplexToolMapper complexToolMapper;
	@Autowired
	private CryptoCoinBinanceUmFutureOrderProducer umFutureOrderProducer;
	@Autowired
	private CryptoCoinBinanceUmBtcArbitrageWithBatchProducer umBtcArbitrageWithBatchProducer;
	@Autowired
	private CryptoCoinBinanceUmFutureOrderModifyProducer umFutureOrderModifyProducer;

	@Override
	public ModelAndView tradingView() {
		ModelAndView v = new ModelAndView("cryptoCoin/setFutureOrder");

//		LocalDateTime defaultStartTime = LocalDateTime.now().minusHours(8);
//
//		List<CryptoCoinBtcAndLowIndexGapDTO> dataList = complexToolMapper.selectGaps(defaultStartTime);
//		List<String> xValues = new ArrayList<>();
//		List<Double> gap = new ArrayList<>();
//		for (CryptoCoinBtcAndLowIndexGapDTO dto : dataList) {
//			xValues.add(localDateTimeHandler.dateToStr(dto.getStartTime()));
//			gap.add(dto.getGap());
//		}
//
//		v.addObject("xValues", xValues);
//		v.addObject("gap", gap);

		return v;
	}

	@Override
	public CommonResult sendFutureOrder(CryptoCoinBinanceFutureBatchOrderDTO dto) {
		CommonResult r = new CommonResult();
		if (dto.getSymbols() == null || dto.getSymbols().isEmpty()) {
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
		if (BinanceOrderTypeType.getType(dto.getOrderTypeCode()) == null) {
			dto.setOrderSideCode(BinanceOrderTypeType.MARKET.getCode());
		} else if (BinanceOrderTypeType.getType(dto.getOrderTypeCode()).equals(BinanceOrderTypeType.LIMIT)
				&& dto.getPreOrderRatio() == null) {
			r.failWithMessage("Order ratio invalid");
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

	@Override
	public CommonResult batchOrderModify(BinanceUpdateOrderDTO dto) {
		CommonResult r = new CommonResult();
		BinanceOrderSideType orderSide = BinanceOrderSideType.getType(dto.getTargetOrderSideCode());
		BinancePositionSideType positionType = BinancePositionSideType.getType(dto.getTargetPositionSideCode());

		if (orderSide == null || positionType == null) {
			String msg = "Can NOT update order, " + dto.toString();
			log.error(msg);
			r.failWithMessage(msg);
			return r;
		}

		if (dto.getQuantityRatio() != null && (dto.getQuantityRatio().compareTo(BigDecimal.ZERO) <= 0
				|| dto.getQuantityRatio().compareTo(new BigDecimal(100)) > 0)) {
			String msg = "Can NOT update order, quantity ratio invalid, " + dto.toString();
			log.error(msg);
			r.failWithMessage(msg);
			return r;
		}

		if (dto.getPriceRatio() == null || (dto.getPriceRatio().compareTo(new BigDecimal(-100)) <= 0
				|| dto.getPriceRatio().compareTo(new BigDecimal(100)) > 0)) {
			String msg = "Can NOT update order, price ratio invalid, " + dto.toString();
			log.error(msg);
			r.failWithMessage(msg);
			return r;
		}

		if (StringUtils.isBlank(dto.getOrderId())) {
			dto.setOrderId(null);
		}

		if (dto.getSymbols() != null) {
			for (int i = 0; i < dto.getSymbols().size(); i++) {
				if (StringUtils.isBlank(dto.getSymbols().get(i))) {
					dto.getSymbols().remove(i);
					i--;
				}
			}
		}

		umFutureOrderModifyProducer.updateOrder(dto);
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult closePositionByRatio(CryptoCoinBinanceFutureBatchOrderDTO dto) {
		CommonResult r = new CommonResult();

		if (BinanceOrderSideType.getType(dto.getOrderSideCode()) == null) {
			r.failWithMessage("Order side invalid");
			return r;
		}
		if (BinancePositionSideType.getType(dto.getPositionSideCode()) == null) {
			r.failWithMessage("Position side invalid");
			return r;
		}
		if (dto.getSymbols() == null || dto.getSymbols().isEmpty()) {
			r.failWithMessage("Symbol list invalid");
			return r;
		}
		if (BinanceOrderTypeType.getType(dto.getOrderTypeCode()) == null) {
			r.failWithMessage("Order type invalid; Please set \"LIMIT\" or \"MARKET\"");
			return r;
		}
		if (BinanceOrderTypeType.LIMIT.equals(BinanceOrderTypeType.getType(dto.getOrderTypeCode()))
				&& dto.getPreOrderRatio() == null) {
			r.failWithMessage("Order ratio invalid");
			return r;
		}
		if (dto.getClosePositionQuantityRatio() == null
				|| dto.getClosePositionQuantityRatio().compareTo(BigDecimal.ZERO) < 0
				|| dto.getClosePositionQuantityRatio().compareTo(new BigDecimal(100)) > 100) {
			r.failWithMessage("Quantity ratio invalid");
			return r;
		}

		umFutureOrderProducer.binanceUmFutureOrder(dto);
		r.setIsSuccess();
		return r;
	}
}
