package com.xcloudeye.stats.domain.app;

public class ChannelBaseInfo {
	
	private String channel;
	private String parent;
	private String child;
	private String seq;
	
	public ChannelBaseInfo() {
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

}
