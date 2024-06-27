//package demo.test.service.mq.producer;
//
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import demo.common.service.CommonService;
//
//@Component
//public class FanoutExchangeProducer extends CommonService {
//
//	@Autowired
//	private RabbitTemplate rabbitFanoutExchangeTemplate;
//
//	public void send(String msg) {
//		rabbitFanoutExchangeTemplate.convertAndSend("amq.fanout", "", msg);
//	}
//
//}