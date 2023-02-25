package demo.tool.telegram.mq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.common.service.CommonService;
import net.sf.json.JSONObject;
import telegram.pojo.constant.TelegramMessageMQConstant;
import telegram.pojo.dto.TelegramBotNoticeMessageDTO;

@Component
public class TelegramMessageAckProducer extends CommonService {
	
	/**
	 * Just an exmaple should NOT use in CX project
	 */

	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * Just an exmaple should NOT use in CX project
	 */
	public void send(TelegramBotNoticeMessageDTO dto) {
		if (dto == null) {
			return;
		}
		
		JSONObject json = dtoToJSON(dto);
		rabbitTemplate.convertAndSend(TelegramMessageMQConstant.TELEGRAM_MESSAGE_MSG_QUEUE, json.toString());
	}
	
	private JSONObject dtoToJSON(TelegramBotNoticeMessageDTO te) {
		JSONObject json = new JSONObject();

		if (te.getId() != null) {
			json.put("id", te.getId());
		}
		if (te.getMsg() != null) {
			json.put("msg", te.getMsg());
		}
		if (te.getBotName() != null) {
			json.put("botName", te.getBotName());
		}

		return json;
	}

}
