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
import telegram.pojo.constant.TelegramMessageMQConstant;
import telegram.pojo.dto.TelegramMessageDTO;

@Component
@RabbitListener(queues = TelegramMessageMQConstant.TELEGRAM_MSG_QUEUE)
public class TelegramMsgAckReceiver extends CommonService {

	@Autowired
	private TelegramService msgService;

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {

		try {
			TelegramMessageDTO dto = msgToDTO(messageStr);

			if (dto != null) {
				msgService.sendMessage(dto.getMsg(), dto.getId());
			}

			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (IOException e) {

			channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
		}

	}

	private TelegramMessageDTO msgToDTO(String messageStr) {
		try {
			JSONObject json = JSONObject.fromObject(messageStr);

			TelegramMessageDTO dto = new TelegramMessageDTO();

			dto.setMsg(json.getString("msg"));
			dto.setId(json.getLong("id"));
			return dto;
		} catch (Exception e) {
			return null;
		}
	}

}
