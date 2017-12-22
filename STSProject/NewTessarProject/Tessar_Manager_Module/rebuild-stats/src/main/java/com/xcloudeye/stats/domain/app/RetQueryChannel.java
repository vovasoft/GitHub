package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RetQueryChannel {
	
	private String channel;
	private Integer new_user;
	private List<HashMap<String, Integer>> ret = new ArrayList<HashMap<String,Integer>>();
	/**
	* <p>Title: </p>
	* <p>Description: </p>
	*/
	public RetQueryChannel() {
	}
	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param channel
	* @param new_user
	* @param ret
	*/
	public RetQueryChannel(String channel, Integer new_user,
			List<HashMap<String, Integer>> ret) {
		this.channel = channel;
		this.new_user = new_user;
		this.ret = ret;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public Integer getNew_user() {
		return new_user;
	}
	public void setNew_user(Integer new_user) {
		this.new_user = new_user;
	}
	public List<HashMap<String, Integer>> getRet() {
		return ret;
	}
	public void setRet(List<HashMap<String, Integer>> ret) {
		this.ret = ret;
	}

}
