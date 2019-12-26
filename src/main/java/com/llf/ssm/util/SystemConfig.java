package com.llf.ssm.util;

public class SystemConfig {
	
	private String exceptionPaths;
	
	private String exceptionApiPaths;
	
	private Integer activeTime;
	
	private String pdfReportPath;
	
	private String sendUrl;
	
	private String webUrl;
	
	private String mailHost;
	
	private String fromEmail;
	
	private String fromEmailKey;
	
	private String solrzks;

	public String getExceptionPaths() {
		return exceptionPaths;
	}

	public void setExceptionPaths(String exceptionPaths) {
		this.exceptionPaths = exceptionPaths;
	}

	public Integer getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Integer activeTime) {
		this.activeTime = activeTime;
	}

	public String getExceptionApiPaths() {
		return exceptionApiPaths;
	}

	public void setExceptionApiPaths(String exceptionApiPaths) {
		this.exceptionApiPaths = exceptionApiPaths;
	}

	public String getPdfReportPath() {
		return pdfReportPath;
	}

	public void setPdfReportPath(String pdfReportPath) {
		this.pdfReportPath = pdfReportPath;
	}

	public String getSendUrl() {
		return sendUrl;
	}

	public void setSendUrl(String sendUrl) {
		this.sendUrl = sendUrl;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getMailHost() {
		return mailHost;
	}

	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getFromEmailKey() {
		return fromEmailKey;
	}

	public void setFromEmailKey(String fromEmailKey) {
		this.fromEmailKey = fromEmailKey;
	}

	public String getSolrzks() {
		return solrzks;
	}

	public void setSolrzks(String solrzks) {
		this.solrzks = solrzks;
	}
}
