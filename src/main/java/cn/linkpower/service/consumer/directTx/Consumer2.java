package cn.linkpower.service.consumer.directTx;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
@RabbitListener(queues="directQueueTx")
public class Consumer2 {
	@RabbitHandler
	public void process(String msg,Channel channel, Message message) throws IOException {
		//拿到消息延迟消费
		try {
			Thread.sleep(1000*3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
		try {
			if(!isNull(msg)){
				String numstr = msg.substring(3);
				Integer num = Integer.parseInt(numstr);
				if(num >= 3){
					//channel.basicNack(message.getMessageProperties().getDeliveryTag(),
							//false, true);
					//和上面的代码一样的功能
					channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
					System.out.println("get msg2 basicNack msg = "+msg);
				}else{
					channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
					System.out.println("get msg2 basicAck msg = "+msg);
				}
			}
		} catch (Exception e) {
			//消费者处理出了问题，需要告诉队列信息消费失败
			channel.basicNack(message.getMessageProperties().getDeliveryTag(),
					false, true);
			System.err.println("get msg2 failed msg = "+msg);
		}
	}
	
	public static boolean isNull(Object obj){
		return obj == null || obj == ""||obj == "null";
	}
}
