package demo.automationTest.mq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import autoTest.testEvent.pojo.constant.TestEventMQConstant;
import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import demo.common.service.CommonService;
import net.sf.json.JSONObject;

@Component
public class TestEventBingDemoInsertAckProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send(AutomationTestInsertEventDTO dto) {
		if (dto == null) {
			return;
		}
		JSONObject json = JSONObject.fromObject(dto);
		json.put("appointment", localDateTimeHandler.dateToStr(dto.getAppointment()));
		rabbitTemplate.convertAndSend(TestEventMQConstant.TEST_EVENT_INSERT_QUEUE, json.toString());
	}

}