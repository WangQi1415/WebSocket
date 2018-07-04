package com.model;

import java.awt.List;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<Message> {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Message decode(String textMessage) throws DecodeException {
		// TODO Auto-generated method stub
		Message message=new Message();
		JsonObject jsonObject=Json.createReader(new StringReader(textMessage)).readObject();
		ArrayList<String> list=new ArrayList<String>();
		JsonArray jsonarray= jsonObject.getJsonArray("names");
		if(jsonarray!=null) {
			for(int i=0;i<jsonarray.size();i++) {
				list.add(jsonarray.getString(i));
			}
		}
		message.setNames(list);
		message.setSender(jsonObject.getString("sender"));
		message.setMessage(jsonObject.getString("message"));
		message.setDate(new Date());
		return message;
	}

	@Override
	public boolean willDecode(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
