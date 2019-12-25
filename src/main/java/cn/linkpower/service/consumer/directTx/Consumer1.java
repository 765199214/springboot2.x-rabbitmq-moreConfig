package cn.linkpower.service.consumer.directTx;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
@RabbitListener(queues="directQueueTx")
public class Consumer1 {
	@RabbitHandler
	public void process(String msg,Channel channel, Message message) throws IOException {
		//拿到消息延迟消费
		try {
			Thread.sleep(1000*1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		try {
			/**
			 * 确认一条消息：<br>
			 * channel.basicAck(deliveryTag, false); <br>
			 * deliveryTag:该消息的index <br>
			 * multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息 <br>
			 */
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			System.out.println("get msg1 success msg = "+msg);
			
		} catch (Exception e) {
			//消费者处理出了问题，需要告诉队列信息消费失败
			/**
			 * 拒绝确认消息:<br>
			 * channel.basicNack(long deliveryTag, boolean multiple, boolean requeue) ; <br>
			 * deliveryTag:该消息的index<br>
			 * multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。<br>
			 * requeue：被拒绝的是否重新入队列 <br>
			 */
			channel.basicNack(message.getMessageProperties().getDeliveryTag(),
					false, true);
			System.err.println("get msg1 failed msg = "+msg);
			
			/**
			 * 拒绝一条消息：<br>
			 * channel.basicReject(long deliveryTag, boolean requeue);<br>
			 * deliveryTag:该消息的index<br>
			 * requeue：被拒绝的是否重新入队列 
			 */
			//channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
		}
	}
}
