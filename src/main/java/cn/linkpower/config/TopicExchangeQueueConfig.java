package cn.linkpower.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicExchangeQueueConfig {
	
	/**
	 * 创建topic交换机
	 * @return
	 */
	@Bean(name="getTopicExchange")
	public TopicExchange getTopicExchange(){
		return new TopicExchange("topicExchange", true, false);
	}
	
	@Bean(name="getTopicQueue1")
	public Queue getTopicQueue1(){
		return new Queue("topicQueue1", true, false, false);
	}
	
	@Bean(name="getTopicQueue2")
	public Queue getTopicQueue2(){
		return new Queue("topicQueue2", true, false, false);
	}
	
	@Bean
	public Binding bindTopicExchangeAndQueue1(
			@Qualifier(value="getTopicExchange") TopicExchange getTopicExchange,
			@Qualifier(value="getTopicQueue1") Queue getTopicQueue1){
		//匹配 xiangjiao前的所有单词，但香蕉后智能匹配一个单词
		return BindingBuilder.bind(getTopicQueue1).to(getTopicExchange).with("#.xiangjiao.*");
	}
	
	@Bean
	public Binding bindTopicExchangeAndQueue2(
			@Qualifier(value="getTopicExchange") TopicExchange getTopicExchange,
			@Qualifier(value="getTopicQueue2") Queue getTopicQueue2){
		//匹配 xiangjiao前的所有单词 和后的所有单词
		return BindingBuilder.bind(getTopicQueue2).to(getTopicExchange).with("#.xiangjiao.#");
	}
}
