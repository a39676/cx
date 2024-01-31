package demo.finance.cnStockMarket.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cnStockMarket.pojo.dto.CnStockMarketNoticeSettingDTO;
import demo.finance.cnStockMarket.service.CnStockMarketService;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.tool.textMessageForward.telegram.service.TelegramService;
import finance.cnStockMarket.pojo.bo.CnStockMarketDataBO;
import finance.cnStockMarket.pojo.dto.CnStockMarketDataDTO;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.type.TelegramBotType;

@Service
public class CnStockMarketServiceImpl extends CryptoCoinCommonService implements CnStockMarketService {

	@Autowired
	private CnStockMarketOptionService cnStockMarketOptionService;
	@Autowired
	private TelegramService telegramService;

	@Override
	public CommonResult receiveCnStockMarketData(CnStockMarketDataDTO dto) {
		CommonResult r = new CommonResult();
		if (StringUtils.isBlank(dto.getStockCode())) {
			r.setMessage("Stock code EMPTY");
			return r;
		}

		CnStockMarketNoticeSettingDTO noticeSetting = cnStockMarketOptionService.getNoticeSettings()
				.get(dto.getStockCode());

		if (noticeSetting == null || noticeSetting.getNoticed()) {
			r.setIsSuccess();
			return r;
		}

		List<CnStockMarketDataBO> datas = dto.getData();
		if (datas == null || datas.isEmpty()) {
			r.setMessage("NO datas found");
			return r;
		}

//		FilterBODataResult maxMinPriceResult = filterData(datas);
//		if (maxMinPriceResult.isFail()) {
//			return r;
//		}
//		String msg = "";
//		if (noticeSetting.getMaxPrice() != null
//				&& maxMinPriceResult.getMaxPrice().doubleValue() > noticeSetting.getMaxPrice().doubleValue()) {
//			msg += ", higher than: " + maxMinPriceResult.getMaxPrice() + ", at "
//					+ maxMinPriceResult.getMaxPriceDateTime() + "; ";
//		}
//		if (noticeSetting.getMinPrice() != null
//				&& maxMinPriceResult.getMinPrice().doubleValue() < noticeSetting.getMinPrice().doubleValue()) {
//			msg += ", lower than: " + maxMinPriceResult.getMinPrice() + ", at "
//					+ maxMinPriceResult.getMinPriceDateTime() + "; ";
//		}

		CnStockMarketDataBO lastData = datas.get(datas.size() - 1);
		String msg = "";
		if (noticeSetting.getMaxPrice() != null
				&& lastData.getHighPrice().doubleValue() > noticeSetting.getMaxPrice().doubleValue()) {
			msg += ", higheset: " + lastData.getHighPrice() + ", at " + lastData.getStartTime() + "; ";
		}
		if (noticeSetting.getMinPrice() != null
				&& lastData.getLowPrice().doubleValue() < noticeSetting.getMinPrice().doubleValue()) {
			msg += ", lowest: " + lastData.getLowPrice() + ", at " + lastData.getStartTime() + "; ";
		}

		if (msg.length() > 0) {
			msg = dto.getStockCode() + ", now: " + lastData.getEndPrice() + ", " + msg;
			telegramService.sendMessageByChatRecordId(TelegramBotType.CX_MESSAGE, msg, TelegramStaticChatID.MY_ID);
			noticeSetting.setNoticed(true);
			r.setMessage(msg);
		}
		r.setIsSuccess();
		return r;
	}
}
