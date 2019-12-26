package com.llf.ssm.util;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(value = {"handler"})
public class ResultJSON implements Serializable {
	
	private int status;
	private String msg;
	private Object data;
	
	public ResultJSON() {
		this.setStatus(StatusEnum.SUCCESS.getKey());
		this.setMsg(StatusEnum.SUCCESS.getMsg());
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
