package com.xcloudeye.stats.domain.manage;

public class ChangeLog {

	private String action;
	private String operator;
	private Integer time;
	private String content;
	
	public ChangeLog() {
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param action
	* @param operator
	* @param time
	* @param content
	*/
	public ChangeLog(String action, String operator, Integer time,
			String content) {
		this.action = action;
		this.operator = operator;
		this.time = time;
		this.content = content;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
