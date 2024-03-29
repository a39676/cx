package demo.finance.metal.mq.reciver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;

import demo.common.service.CommonMessageQueueReceiverService;
import demo.finance.metal.service.PreciousMetalService;
import finance.precious_metal.pojo.constant.PreciousMetalMQConstant;
import finance.precious_metal.pojo.dto.PreciousMetailPriceDTO;

@Component
@RabbitListener(queues = PreciousMetalMQConstant.transmissionMetalPriceData)
public class MetalPriceTransmissionAckReceiver extends CommonMessageQueueReceiverService {

	@Autowired
	private PreciousMetalService preciousMetalService;

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		try {
			PreciousMetailPriceDTO dto = new Gson().fromJson(messageStr, PreciousMetailPriceDTO.class);
			preciousMetalService.reciveMetalPrice(dto);
		} catch (Exception e) {
			log.error("mq error, " + PreciousMetalMQConstant.transmissionMetalPriceData + ", e:" + e.getLocalizedMessage());
			log.error(messageStr);
		}
	}
}
