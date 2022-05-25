package demo.base.system.mq.receiver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;

import auxiliaryCommon.pojo.constant.ServiceMQConstant;
import auxiliaryCommon.pojo.dto.ServiceMsgDTO;
import demo.common.service.CommonService;
import demo.tool.telegram.service.TelegramService;
import telegram.pojo.constant.TelegramBotType;
import telegram.pojo.constant.TelegramStaticChatID;

@Component
@RabbitListener(queues = ServiceMQConstant.BBT_SEND_MESSAGE_QUEUE)
public class BbtMsgAckReceiver extends CommonService {

	@Autowired
	private TelegramService telegramService;

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		try {
			ServiceMsgDTO dto = new Gson().fromJson(messageStr, ServiceMsgDTO.class);
			telegramService.sendMessage(TelegramBotType.BOT_2, dto.getMsg(), TelegramStaticChatID.MY_ID);
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (Exception e) {
			log.error("mq error, " + ServiceMQConstant.BBT_SEND_MESSAGE_QUEUE + ", e:" + e.getLocalizedMessage());
			log.error(messageStr);
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
		}
	}
}
