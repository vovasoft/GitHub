package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.List;

public class UserNewChannel extends ChannelBaseInfo{
	
	private List<UserNewDetail> detail = new ArrayList<UserNewDetail>();

	public UserNewChannel() {
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param channel
	* @param parent
	* @param child
	* @param seq
	* @param detail
	*/
	public UserNewChannel(String channel, String parent, String child,
			String seq, List<UserNewDetail> detail) {
		this.setChannel(channel); 
		this.setParent(parent);
		this.setChild(child);
		this.setSeq(seq);
		this.detail = detail;
	}


	public List<UserNewDetail> getDetail() {
		return detail;
	}

	public void setDetail(List<UserNewDetail> detail) {
		this.detail = detail;
	}

	
}
