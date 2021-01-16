package demo.finance.cryptoCoin.mq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.common.service.CommonService;
import net.sf.json.JSONObject;
import telegram.pojo.constant.TelegramMessageMQConstant;
import telegram.pojo.dto.TelegramMessageDTO;

@Component
public class TelegramMessageAckProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send(TelegramMessageDTO dto) {
		if (dto == null) {
			return;
		}
		JSONObject json = testEventPOToJSON(dto);
		rabbitTemplate.convertAndSend(TelegramMessageMQConstant.TELEGRAM_CRYPTO_COIN_MSG_QUEUE, json.toString());
	}

	private JSONObject testEventPOToJSON(TelegramMessageDTO te) {
		JSONObject json = new JSONObject();

		if (te.getId() != null) {
			json.put("id", te.getId());
		}
		if (te.getMsg() != null) {
			json.put("msg", te.getMsg());
		}
		

		return json;
	}

}
