//package demo.mq.send;
//
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import mq.pojo.constant.MQQueueConstant;
//
//@Component
//public class AckProducer {
//
//	@Autowired
//	private RabbitTemplate rabbitTemplate;
//
////	public void ackSender() throws IOException, TimeoutException, InterruptedException {
////		ConnectionFactory factory = new ConnectionFactory();
////		factory.setVirtualHost("/");
////		factory.setHost("${spring.rabbitmq.host}");
////		factory.setPort(AMQP.PROTOCOL.PORT);
////		factory.setUsername("${spring.rabbitmq.username}");
////		factory.setPassword("${spring.rabbitmq.password}");
////
////		Connection connection = factory.newConnection();
////		Channel channel = connection.createChannel();
////
////		String EXCHANGE_NAME = "exchange.direct";
////		String ROUTING_KEY = "";
////		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
////		channel.queueDeclare(MQQueueConstant.helloAck, true, false, false, null);
////		channel.queueBind(MQQueueConstant.helloAck, EXCHANGE_NAME, ROUTING_KEY);
////
////		String message = "Hello Ack:";
////		for (int i = 0; i < 5; i++) {
////			channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, (message + i).getBytes(StandardCharsets.UTF_8));
////		}
////
////		channel.close();
////		connection.close();
////	}
//
//	public void send(String context) {
//		System.out.println("HelloSender发送内容 : " + context);
//		rabbitTemplate.convertAndSend(MQQueueConstant.helloAck, context);
//	}
//
//}
