package demo.interaction.wechat.mq.producer;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import demo.common.service.CommonService;
import wechatSdk.pojo.constant.WechatSdkMqConstant;
import wechatSdk.pojo.dto.WechatSendTemplateMessageBonusRechargeDTO;

@Component
public class SendBonusRechargeTemplateMessageProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send(WechatSendTemplateMessageBonusRechargeDTO dto) {
		if (dto == null) {
			return;
		}
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, localDateTimeAdapter).create();
		String str = gson.toJson(dto);
		rabbitTemplate.convertAndSend(WechatSdkMqConstant.SEND_BONUS_RECHARGE_TEMPLATE_MESSAGE, str);
	}

}
