package com.xcloudeye.stats.domain.user;

public class UserCallBack {
	private String username;
	private String email;
	private String skype;
	private Integer date;
	private int flag;
	private String group;

	public UserCallBack() {
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param username
	* @param email
	* @param skype
	* @param date
	* @param flag
	* @param group
	*/
	public UserCallBack(String username, String email, String skype,
			Integer date, int flag, String group) {
		this.username = username;
		this.email = email;
		this.skype = skype;
		this.date = date;
		this.flag = flag;
		this.group = group;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	public Integer getDate() {
		return date;
	}

	public void setDate(Integer date) {
		this.date = date;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

}
