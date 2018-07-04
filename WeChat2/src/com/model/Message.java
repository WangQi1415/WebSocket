package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class Message {
	private List<String> names=new ArrayList<String>();
	private String alert;//用户上线提醒
	private String sender;  //发送方
	private String message; //消息
	private Date date;//发送时间
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAlert() {
		return alert;
	}
	public void setAlert(String alert) {
		this.alert = alert;
	}
	public List<String> getNames() {
		return names;
	}
	public void setNames(List<String> names) {
		for(int i=0;i<names.size();i++) {
			this.names.add(names.get(i));
		}
	}
	
}
