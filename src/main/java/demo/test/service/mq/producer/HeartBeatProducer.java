//package demo.test.service.mq.producer;
//
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import autoTest.testEvent.common.pojo.constant.AutomationTestMQConstant;
//import demo.common.service.CommonService;
//
//@Component
//public class HeartBeatProducer extends CommonService {
//
//	@Autowired
//	private RabbitTemplate rabbitTemplate;
//
//	public void send() {
//		rabbitTemplate.convertAndSend(AutomationTestMQConstant.HEART_BEAT, "_");
//	}
//
//}
