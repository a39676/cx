package demo.finance.cnStockMarket.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.config.costom_component.OptionFilePathConfigurer;
import demo.finance.cnStockMarket.pojo.dto.CnStockMarketNoticeSettingDTO;
import demo.finance.cnStockMarket.service.CnStockMarketService;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.tool.textMessageForward.telegram.service.TelegramService;
import finance.cnStockMarket.pojo.bo.CnStockMarketDataBO;
import finance.cnStockMarket.pojo.dto.CnStockMarketDataDTO;
import net.sf.json.JSONObject;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.type.TelegramBotType;
import toolPack.ioHandle.FileUtilCustom;

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
			msg += ", higheset: " + lastData.getHighPrice().setScale(2, RoundingMode.HALF_UP) + ", at "
					+ lastData.getStartTime() + "; ";
		}
		if (noticeSetting.getMinPrice() != null
				&& lastData.getLowPrice().doubleValue() < noticeSetting.getMinPrice().doubleValue()) {
			msg += ", lowest: " + lastData.getLowPrice().setScale(2, RoundingMode.HALF_UP) + ", at "
					+ lastData.getStartTime() + "; ";
		}

		if (msg.length() < 1) {
			return r;
		}

		msg = dto.getStockCode() + ", now: " + lastData.getEndPrice() + ", " + msg;
		telegramService.sendMessageByChatRecordId(TelegramBotType.CX_MESSAGE, msg, TelegramStaticChatID.MY_ID);
//		noticeSetting.setNoticed(true);

		noticeSetting.setMaxPrice(
				lastData.getEndPrice().multiply(new BigDecimal(1.02)).setScale(2, RoundingMode.HALF_UP).doubleValue());
		noticeSetting.setMinPrice(
				lastData.getEndPrice().multiply(new BigDecimal(0.98)).setScale(2, RoundingMode.HALF_UP).doubleValue());

		FileUtilCustom ioUtil = new FileUtilCustom();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JSONObject newJson = new JSONObject();
		JSONObject subNoticeJson = new JSONObject();
		subNoticeJson.put(noticeSetting.getStockCode(), noticeSetting);
		newJson.put("noticeSettings", subNoticeJson);
		String jsonString = gson.toJson(newJson);
		ioUtil.byteToFile(jsonString.toString().getBytes(StandardCharsets.UTF_8),
				OptionFilePathConfigurer.CN_STOCK_MARKET);
		;

		r.setMessage(msg);
		r.setIsSuccess();
		return r;
	}
}
