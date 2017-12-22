package com.xcloudeye.stats.domain.manage;

public class ChannelManageCallback {
	private long chid;
	private String channel;
	private Integer flag;
	
	public ChannelManageCallback() {
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param chid
	* @param channel
	* @param flag
	*/
	public ChannelManageCallback(long chid, String channel, Integer flag) {
		this.chid = chid;
		this.channel = channel;
		this.flag = flag;
	}

	public long getChid() {
		return chid;
	}

	public void setChid(long chid) {
		this.chid = chid;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

}
