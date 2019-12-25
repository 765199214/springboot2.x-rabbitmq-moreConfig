package cn.linkpower.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 广播模式的交换机和队列配置
 * @author 765199214
 *
 */
@Configuration
public class FanoutExchangeRabbitMqConfig {
	private static Logger log = LoggerFactory.getLogger(FanoutExchangeRabbitMqConfig.class);
	
	/**
	 * 申明配置交换机
	 * @return
	 */
	@Bean(name="getFanoutExchange")
	public FanoutExchange getFanoutExchange(){
		return new FanoutExchange("fanoutExchange", true, false);
	}
	
	/**
	 * 申明队列1
	 * @return
	 */
	@Bean(name="getFanoutQueue1")
	public Queue getFanoutQueue1(){
		//name, durable, exclusive, autoDelete, arguments
		//name, durable, exclusive, autoDelete
		return new Queue("fanoutQueue1", true, false, false);
	}
	
	/**
	 * 申明队列2
	 * @return
	 */
	@Bean(name="getFanoutQueue2")
	public Queue getFanoutQueue2(){
		//name, durable, exclusive, autoDelete, arguments
		//name, durable, exclusive, autoDelete
		return new Queue("fanoutQueue2", true, false, false);
	}
	
	/**
	 * 绑定队列1
	 * @return
	 */
	@Bean
	public Binding bindFanoutExchangeAndQueue1(@Qualifier(value="getFanoutExchange") FanoutExchange getFanoutExchange,
			@Qualifier(value="getFanoutQueue1") Queue getFanoutQueue1){
		return BindingBuilder.bind(getFanoutQueue1).to(getFanoutExchange);
	}
	/**
	 * 绑定队列2
	 * @return
	 */
	@Bean
	public Binding bindFanoutExchangeAndQueue2(@Qualifier(value="getFanoutExchange") FanoutExchange getFanoutExchange,
			@Qualifier(value="getFanoutQueue2") Queue getFanoutQueue2){
		return BindingBuilder.bind(getFanoutQueue2).to(getFanoutExchange);
	}
}
