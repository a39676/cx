package demo.interaction.ccm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.interaction.ccm.mq.producer.CcmManageProducer;
import demo.interaction.ccm.pojo.constant.CcmManageUrl;
import interaction.pojo.dto.CryptoCoinMonitorManagerDTO;
import interaction.pojo.type.CryptoCoinMonitorManagerType;

@Controller
@RequestMapping(value = CcmManageUrl.ROOT)
public class CcmManageController {

	@Autowired
	private CcmManageProducer ccmManageProducer;

	@GetMapping(value = CcmManageUrl.REFRESH_OPTION)
	@ResponseBody
	public String refreshOption() {
		CryptoCoinMonitorManagerDTO dto = new CryptoCoinMonitorManagerDTO();
		dto.setCode(CryptoCoinMonitorManagerType.REFRESH_OPTION.getCode());
		ccmManageProducer.sendCcmManageDTO(dto);
		return "Done";
	}

	@GetMapping(value = CcmManageUrl.REFRESH_CRYPTO_COIN_OPTION)
	@ResponseBody
	public String refreshCryptoCoinOption() {
		CryptoCoinMonitorManagerDTO dto = new CryptoCoinMonitorManagerDTO();
		dto.setCode(CryptoCoinMonitorManagerType.REFRESH_CRYPTO_COIN_OPTION.getCode());
		ccmManageProducer.sendCcmManageDTO(dto);
		return "Done";
	}

	@GetMapping(value = CcmManageUrl.REFRESH_CRYPTO_COIN_PRICE_RANGE_OPTION)
	@ResponseBody
	public String refreshCryptoCoinPriceRangeOption() {
		CryptoCoinMonitorManagerDTO dto = new CryptoCoinMonitorManagerDTO();
		dto.setCode(CryptoCoinMonitorManagerType.REFRESH_CRYPTO_COIN_PRICE_RANGE_OPTION.getCode());
		ccmManageProducer.sendCcmManageDTO(dto);
		return "Done";
	}

	@GetMapping(value = CcmManageUrl.RESTART_CRYPTO_COIN_WEB_SOCKET_CLIENT)
	@ResponseBody
	public String restartCryptoCoinWebSocketClient() {
		CryptoCoinMonitorManagerDTO dto = new CryptoCoinMonitorManagerDTO();
		dto.setCode(CryptoCoinMonitorManagerType.RESTART_CRYPTO_COIN_WEB_SOCKET_CLIENT.getCode());
		ccmManageProducer.sendCcmManageDTO(dto);
		return "Done";
	}

	@GetMapping(value = CcmManageUrl.CLEAN_ALL_OLD_REDIS_KEY)
	@ResponseBody
	public String clearAllOldRedisKey() {
		CryptoCoinMonitorManagerDTO dto = new CryptoCoinMonitorManagerDTO();
		dto.setCode(CryptoCoinMonitorManagerType.CLEAN_ALL_OLD_REDIS_KEY.getCode());
		ccmManageProducer.sendCcmManageDTO(dto);
		return "Done";
	}

	@GetMapping(value = CcmManageUrl.REFRESH_BINANCE_TRADING_OPTION)
	@ResponseBody
	public String refreshBinanceTradingOption() {
		CryptoCoinMonitorManagerDTO dto = new CryptoCoinMonitorManagerDTO();
		dto.setCode(CryptoCoinMonitorManagerType.REFRESH_BINANCE_TRADING_OPTION.getCode());
		ccmManageProducer.sendCcmManageDTO(dto);
		return "Done";
	}
}
