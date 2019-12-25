package cn.linkpower.service;

public interface IMessageServcie {
	public void sendMessage(String exchange,String routingKey,Object msg);
}
