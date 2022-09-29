package demo.tool.telegram.mq.reciver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import demo.common.service.CommonService;
import demo.tool.telegram.service.TelegramService;
import net.sf.json.JSONObject;
import telegram.pojo.constant.TelegramBotType;
import telegram.pojo.constant.TelegramMessageMQConstant;
import telegram.pojo.dto.TelegramBotNoticeMessageDTO;

@Component
@RabbitListener(queues = TelegramMessageMQConstant.TELEGRAM_CRYPTO_COIN_MSG_QUEUE)
public class TelegramCryptoCoinMessageAckReceiver extends CommonService {

	@Autowired
	private TelegramService msgService;

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {

		try {
			TelegramBotNoticeMessageDTO dto = msgToDTO(messageStr);

			if (dto != null) {
				msgService.sendMessageByChatRecordId(TelegramBotType.getType(dto.getBotName()), dto.getMsg(), dto.getId());
			}

			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (Exception e) {
			log.error("mq error, " + TelegramMessageMQConstant.TELEGRAM_CRYPTO_COIN_MSG_QUEUE + ", e:" + e.getLocalizedMessage());
			log.error(messageStr);
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
		}

	}

	private TelegramBotNoticeMessageDTO msgToDTO(String messageStr) {
		try {
			JSONObject json = JSONObject.fromObject(messageStr);

			TelegramBotNoticeMessageDTO dto = new TelegramBotNoticeMessageDTO();

			dto.setMsg(json.getString("msg"));
			dto.setId(json.getLong("id"));
			dto.setBotName(json.getString("botName"));
			return dto;
		} catch (Exception e) {
			return null;
		}
	}

}
