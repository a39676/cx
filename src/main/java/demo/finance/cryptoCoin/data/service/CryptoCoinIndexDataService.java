package demo.finance.cryptoCoin.data.service;

import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;

public interface CryptoCoinIndexDataService {

	void receiveData(CryptoCoinPriceCommonDataBO bo);

}
