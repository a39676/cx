package demo.finance.currencyExchangeRate.notice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.finance.cryptoCoin.mq.producer.TelegramCryptoCoinMessageAckProducer;
import demo.finance.currencyExchangeRate.notice.service.CurrencyExchangeRateNoticeService;

@Service
public class CurrencyExchangeRateNoticeServiceImpl implements CurrencyExchangeRateNoticeService {

	@Autowired
	private TelegramCryptoCoinMessageAckProducer telegramMessageAckProducer;
	
}
