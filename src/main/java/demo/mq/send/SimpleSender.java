package demo.mq.send;

import java.util.Date;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mq.pojo.constant.MQQueueConstant;

@Component
public class SimpleSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send(String message) {
		String context = "hello: " + message + new Date();
		System.out.println("sender:" + context);
		this.rabbitTemplate.convertAndSend(MQQueueConstant.hello, context);
	}

}
