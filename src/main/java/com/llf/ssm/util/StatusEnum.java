package com.llf.ssm.util;

public enum StatusEnum {
	
	SUCCESS("成功", 0),
	ERROR("失败", 1),
	NOTDATA("没有数据", 2),
	EXCEPTION("异常", 3),
	WITHOUTPERMISSION("无权限，请用授权账号重新登录。", 4),
	HAVELOGGIN("用户已登录，是否强制登录？", 5),
	ADMINUSERERROR("用户名密码错误", 6),
	OVERDUEL("登录已过期，请重新登录", 7);

	private String msg;
	private int key;
	
	StatusEnum(String msg, int key) {
		this.msg = msg;
		this.key = key;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public int getKey() {
		return key;
	}
}
