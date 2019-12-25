package cn.linkpower.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkpower.service.IMessageServcie;

@Controller
public class SendMessageTx {
	
	@Autowired
	private IMessageServcie messageServiceImpl;
	
	@RequestMapping("/sendMoreMsgTx")
	@ResponseBody
	public String sendMoreMsgTx(){
		//发送10条消息
		for (int i = 0; i < 10; i++) {
			String msg = "msg"+i;
			System.out.println("发送消息  msg："+msg);
			messageServiceImpl.sendMessage("directExchangeTx", "directQueueTxRoutingKey", msg);
			//每两秒发送一次
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return "send ok";
	}
}
