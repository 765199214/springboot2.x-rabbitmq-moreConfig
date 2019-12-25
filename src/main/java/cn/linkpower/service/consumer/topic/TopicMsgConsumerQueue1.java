package cn.linkpower.service.consumer.topic;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
@RabbitListener(queues="topicQueue1")
public class TopicMsgConsumerQueue1 {
	
	@RabbitHandler
	public void process(String msg,Channel channel, Message message) throws IOException {
		//拿到消息延迟消费
		try {
			Thread.sleep(1000*2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			//正确执行  手动ack
			//假设收到消息失败呢？  假定收到消息是 message 表示失败
			if("message".equalsIgnoreCase(msg)){
				/*channel.basicNack(message.getMessageProperties().getDeliveryTag(),
						false, false);*/
				channel.basicReject(message.getMessageProperties().getDeliveryTag(),
						true);
				System.err.println("get topic msg1 failed msg = "+msg);
			}else{
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
				System.out.println("get topic msg1 success msg = "+msg);
			}
			
		} catch (Exception e) {
			//消费者处理出了问题，需要告诉队列信息消费失败
			channel.basicNack(message.getMessageProperties().getDeliveryTag(),
					false, false);
			System.err.println("get topic msg1 failed msg = "+msg);
		}
		
		
	}
}
