package demo.interaction.ccm.mq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.common.service.CommonService;
import finance.cryptoCoin.pojo.constant.CryptoCoinMQConstant;
import interaction.pojo.dto.CryptoCoinMonitorManagerDTO;
import net.sf.json.JSONObject;

@Component
public class CcmManageProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendCcmManageDTO(CryptoCoinMonitorManagerDTO dto) {
		JSONObject json = JSONObject.fromObject(dto);
		rabbitTemplate.convertAndSend(CryptoCoinMQConstant.MANAGE, json.toString());
	}

}