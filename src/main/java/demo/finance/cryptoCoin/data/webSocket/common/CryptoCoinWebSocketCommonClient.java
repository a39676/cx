package demo.finance.cryptoCoin.data.webSocket.common;

import java.util.List;

import demo.common.service.CommonService;

public abstract class CryptoCoinWebSocketCommonClient extends CommonService {

	/**
	 * 迁移代码 此处应该获取订阅币种
	 * 
	 * @return
	 */
	protected List<String> getSubscriptionList() {
		return null;
	}

}
