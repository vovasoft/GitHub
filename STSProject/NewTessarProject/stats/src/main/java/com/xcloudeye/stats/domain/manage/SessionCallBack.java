package com.xcloudeye.stats.domain.manage;

import com.xcloudeye.stats.domain.user.User;

public class SessionCallBack {
	
	private String set;
	private String api;
	private Integer flag;
	User user = new User();
	
	public SessionCallBack() {
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param set
	* @param api
	* @param flag
	* @param user
	*/
	public SessionCallBack(String set, String api, Integer flag, User user) {
		this.set = set;
		this.api = api;
		this.flag = flag;
		this.user = user;
	}

	public String getSet() {
		return set;
	}

	public void setSet(String set) {
		this.set = set;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


}
