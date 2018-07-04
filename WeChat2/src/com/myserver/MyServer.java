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
	
	//��¼���ӵĿͻ��˵ķ�ʽ     ����һ  �� ���������ӵ�session���浽set������
//	static Set<Session> peers=Collections.synchronizedSet(new HashSet<Session>());
	//ashSet�Ƿ�ͬ���ġ��������߳�ͬʱ����һ����ϣ set������������һ���߳��޸��˸� set����ô������ �����ⲿͬ������ͨ����ͨ������Ȼ��װ�� set �Ķ���ִ��ͬ����������ɵġ���������������Ķ�����Ӧ��ʹ�� Collections.synchronizedSet ����������װ�� set������ڴ���ʱ�����һ�������Է�ֹ�Ը� set ��������Ĳ�ͬ�����ʣ�
	
	//������  session����Ϊ��Ա����  ����ǰ������󱣴浽������ȥ
	private static CopyOnWriteArraySet<MyServer> websocketset=new CopyOnWriteArraySet<MyServer>();
	private static List<String> names=new ArrayList<String>();
	private Session session;
	private String username;
	Message message=new Message();
	/*
	 * �����̰߳�ȫ������ļ��ϣ����Խ��������̰߳�ȫ��HashSet��
	 * �ο�����
	 * https://www.cnblogs.com/skywang12345/p/3498497.html
	 * */
	
	@OnOpen
	public void onOpen(Session session) {
		//�û����ӳɹ���  ��ʾ�����û����û�������
		this.session=session;
		this.username="�û�"+session.getId();
		this.names.add(this.username);

		
		System.out.println(this.username+"������");
		websocketset.add(this);
		
		message.setAlert(this.username+"������");
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
		//�յ���Ϣ����Ϣת����������
		//System.out.println(str);
		message.setSender("�û�"+session.getId());
		message.setMessage(str);
		message.setAlert("");
		if(message!=null) {
			broadcast(message);
		}
	}
	@OnClose
	public void onClose(Session session) {
		//�ر�����ʱ ��ʾ�����û����û�������
		websocketset.remove(this);
		message.getNames().remove("�û�"+session.getId());
		message.setMessage("");
		message.setAlert("�û�"+session.getId()+"������");
		broadcast(message);
	}
	
	public void broadcast(Message message) {
		//ʵ��Ⱥ�����ܵĺ���
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
