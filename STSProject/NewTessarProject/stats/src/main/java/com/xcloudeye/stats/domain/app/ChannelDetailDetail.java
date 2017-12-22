package com.xcloudeye.stats.domain.app;

public class ChannelDetailDetail {

	private Integer time;
	private Integer new_user;
	
	public ChannelDetailDetail() {
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param time
	* @param new_user
	*/
	public ChannelDetailDetail(Integer time, Integer new_user) {
		this.time = time;
		this.new_user = new_user;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public Integer getNew_user() {
		return new_user;
	}

	public void setNew_user(Integer new_user) {
		this.new_user = new_user;
	}

}
