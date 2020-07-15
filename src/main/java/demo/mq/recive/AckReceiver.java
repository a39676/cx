package demo.mq.recive;

import java.io.IOException;
import java.util.Date;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import mq.pojo.constant.MQQueueConstant;

@Component
@RabbitListener(queues = MQQueueConstant.helloAck)
public class AckReceiver {

//	@RabbitHandler
//	public void ackReceiver() throws Exception {
//		boolean autoAck = false;
//		ConnectionFactory factory = new ConnectionFactory();
//		factory.setVirtualHost("/");
//		factory.setHost("${spring.rabbitmq.host}");
//		factory.setPort(AMQP.PROTOCOL.PORT);
//		factory.setUsername("${spring.rabbitmq.username}");
//		factory.setPassword("${spring.rabbitmq.password}");
//
//		Connection connection = factory.newConnection();
//		final Channel channel = connection.createChannel();
//		String EXCHANGE_NAME = "exchange.direct";
//		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
//		channel.queueBind(MQQueueConstant.helloAck, EXCHANGE_NAME, "key");
//
//        GetResponse response = channel.basicGet(MQQueueConstant.helloAck, false);
//        byte[] body = response.getBody();
//        System.out.println(new String(body).toString());
//
//		Consumer consumer = new DefaultConsumer(channel) {
//			@Override
//			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
//					byte[] body) throws IOException {
//				String message = new String(body, StandardCharsets.UTF_8);
//				System.out.println(message);
//
//				if (message.contains(":3")) {
//					// requeue：重新入队列，false：直接丢弃，相当于告诉队列可以直接删除掉
//					channel.basicReject(envelope.getDeliveryTag(), true);
//				} else {
//					channel.basicAck(envelope.getDeliveryTag(), true);
//				}
//			}
//		};
//
//		channel.basicConsume(MQQueueConstant.helloAck, autoAck, consumer);
//
//	}

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		System.out.println("HelloReceiver收到  : " + messageStr + "收到时间" + new Date());
		
		try {
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			System.out.println("receiver success");
		} catch (IOException e) {
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
			e.printStackTrace();
			System.out.println("receiver fail");
		}

	}
}
