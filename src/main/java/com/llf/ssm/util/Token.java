package com.llf.ssm.util;

import java.io.Serializable;
import java.util.List;

public class Token implements Serializable{
	
	private int id;
	
	private String key;	
	
	private String token;
	
	private String nick;
	
	private String userName;
	
	private String password;
    
    private Boolean isCompel;

    private long clientTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getIsCompel() {
		return isCompel;
	}

	public void setIsCompel(Boolean isCompel) {
		this.isCompel = isCompel;
	}

	public long getClientTime() {
		return clientTime;
	}

	public void setClientTime(long clientTime) {
		this.clientTime = clientTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}	
	
}
