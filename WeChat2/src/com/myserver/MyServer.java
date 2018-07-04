package com.myserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.model.Message;
import com.model.MessageDecoder;
import com.model.MessageEncoder;


@ServerEndpoint(value="/chat",encoders=MessageEncoder.class,decoders=MessageDecoder.class)
public class MyServer {
	
	//记录连接的客户端的方式     方法一  将 建立好连接的session保存到set集合中
//	static Set<Session> peers=Collections.synchronizedSet(new HashSet<Session>());
	//ashSet是非同步的。如果多个线程同时访问一个哈希 set，而其中至少一个线程修改了该 set，那么它必须 保持外部同步。这通常是通过对自然封装该 set 的对象执行同步操作来完成的。如果不存在这样的对象，则应该使用 Collections.synchronizedSet 方法来“包装” set。最好在创建时完成这一操作，以防止对该 set 进行意外的不同步访问：
	
	//方法二  session定义为成员变量  将当前的类对象保存到集合中去
	private static CopyOnWriteArraySet<MyServer> websocketset=new CopyOnWriteArraySet<MyServer>();
	private static List<String> names=new ArrayList<String>();
	private Session session;
	private String username;
	Message message=new Message();
	/*
	 * 它是线程安全的无序的集合，可以将它理解成线程安全的HashSet。
	 * 参考博客
	 * https://www.cnblogs.com/skywang12345/p/3498497.html
	 * */
	
	@OnOpen
	public void onOpen(Session session) {
		//用户连接成功后  提示其它用户该用户已上线
		this.session=session;
		this.username="用户"+session.getId();
		this.names.add(this.username);

		
		System.out.println(this.username+"已上线");
		websocketset.add(this);
		
		message.setAlert(this.username+"已上线");
		message.setNames(this.names);
		message.setSender(this.username);
		message.setDate(new Date());
		message.setMessage("");
		if(message!=null) {
			broadcast(message);
		}
		
	}
	@OnMessage
	public void onMessage(Session session, String str) {
		//收到消息后将消息转发给其他人
		//System.out.println(str);
		message.setSender("用户"+session.getId());
		message.setMessage(str);
		message.setAlert("");
		if(message!=null) {
			broadcast(message);
		}
	}
	@OnClose
	public void onClose(Session session) {
		//关闭连接时 提示其它用户该用户已下线
		websocketset.remove(this);
		message.getNames().remove("用户"+session.getId());
		message.setMessage("");
		message.setAlert("用户"+session.getId()+"已下线");
		broadcast(message);
	}
	
	public void broadcast(Message message) {
		//实现群发功能的函数
		MessageEncoder md=new MessageEncoder();
		try {
			String text=md.encode(message);
			for(Iterator<MyServer> iterator=websocketset.iterator();iterator.hasNext();) {
				MyServer myserver=iterator.next();
				myserver.session.getBasicRemote().sendText(text);
			}
		} catch (EncodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
