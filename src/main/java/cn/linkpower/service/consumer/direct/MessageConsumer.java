package cn.linkpower.service.consumer.direct;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import cn.linkpower.config.DirectRabbitMqConfig;

@Component
@RabbitListener(queues=DirectRabbitMqConfig.directQueueName)
public class MessageConsumer {
	
	@RabbitHandler
	public void process(String msg,Channel channel, Message message) throws IOException {
		//拿到消息延迟消费
		try {
			Thread.sleep(1000*8);
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
				System.err.println("get msg failed msg = "+msg);
			}else{
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
				System.out.println("get msg success msg = "+msg);
			}
			
		} catch (Exception e) {
			//消费者处理出了问题，需要告诉队列信息消费失败
			channel.basicNack(message.getMessageProperties().getDeliveryTag(),
					false, false);
			System.err.println("get msg failed msg = "+msg);
		}
		
		
	}
}
