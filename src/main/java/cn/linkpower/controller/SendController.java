package cn.linkpower.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkpower.service.IMessageServcie;

@Controller
public class SendController {
	
	private static Logger log = LoggerFactory.getLogger(SendController.class);
	
	@Value("${exchange.directExchange.name}")
	private String directExchangeName;
	
	@Value("${exchange.directExchange.routingKey}")
	private String directExchangeRoutingKey;
	
	@Autowired
	private IMessageServcie messageServiceImpl;
	
	
	@RequestMapping("/sendDirect")
	@ResponseBody
	public String sendDirectMsg(HttpServletRequest request){
		String msg = request.getParameter("msg");
		messageServiceImpl.sendMessage(directExchangeName, directExchangeRoutingKey, msg);
		return "sendDirectMsg";
	}
	
	@RequestMapping("/sendFanoutMsg")
	@ResponseBody
	public String sendFanoutMsg(HttpServletRequest request){
		String msg = request.getParameter("msg");
		messageServiceImpl.sendMessage("fanoutExchange", "", msg);
		return "sendFanoutMsg";
	}
	
	@RequestMapping("/sendTopicMsg1")
	@ResponseBody
	public String sendTopicMsg1(HttpServletRequest request){
		String msg = request.getParameter("msg");
		messageServiceImpl.sendMessage("topicExchange", "bunana.xiangjiao.xj", msg);
		return "sendTopicMsg";
	}
	
	@RequestMapping("/sendTopicMsg2")
	@ResponseBody
	public String sendTopicMsg2(HttpServletRequest request){
		String msg = request.getParameter("msg");
		messageServiceImpl.sendMessage("topicExchange", "bunana.xiangjiao.xj.bn", msg);
		return "sendTopicMsg";
	}
}
