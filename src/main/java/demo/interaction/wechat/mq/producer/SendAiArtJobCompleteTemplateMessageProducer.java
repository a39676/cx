package demo.interaction.wechat.mq.producer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.common.service.CommonService;
import wechatSdk.pojo.constant.WechatSdkMqConstant;

@Component
public class SendAiArtJobCompleteTemplateMessageProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send(String openId) {
		if (StringUtils.isBlank(openId)) {
			return;
		}
		rabbitTemplate.convertAndSend(WechatSdkMqConstant.SEND_AI_ART_JOB_COMPLETE_TEMPLATE_MESSAGE, openId);
	}

}
