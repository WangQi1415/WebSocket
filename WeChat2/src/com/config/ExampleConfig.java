package com.config;

import java.util.Set;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;

public class ExampleConfig implements ServerApplicationConfig {

	@Override
	public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scan) {
		// TODO Auto-generated method stub
		System.out.println("echo");
		
		//�ж��ٸ�����size���ж��
		System.out.println("size:"+scan.size());
		//return null;
		
		
		//�˴�һ��Ҫע�� ���ص���scan  ���ױ�����
		return scan;
	}

	@Override
	
	public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
