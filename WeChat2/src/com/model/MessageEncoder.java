package com.model;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message> {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String encode(final Message msg) throws EncodeException {
		// TODO Auto-generated method stub
		
		
		JsonObjectBuilder objectbuilder=Json.createObjectBuilder()
				.add("alert", msg.getAlert())
				.add("sender",msg.getSender())
				.add("message",msg.getMessage())
				.add("date","");
		
		//使用JsonObjectBuilder时是无法将list的数据添加进去的   此时就需要使用 JsonArrayBuilder
		JsonArrayBuilder arrayBuilder=Json.createArrayBuilder();
		for(String name:msg.getNames()) {
			arrayBuilder.add(name);
		}
		objectbuilder.add("names",arrayBuilder);
		String str=objectbuilder.build().toString();
		System.out.println(str);
		return str;
		
	}

}
