//package demo.test.service.mq.receiver;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class FanoutExchangeReceiver {
//
//	@RabbitListener(queues = { "fanout1" })
//	public void receiveMessageFromFanout1(String message) {
//		System.out.println("Received fanout 1 message: " + message);
//	}
//
//	@RabbitListener(queues = { "fanout2" })
//	public void receiveMessageFromFanout2(String message) {
//		System.out.println("Received fanout 2 message: " + message);
//	}
//}
