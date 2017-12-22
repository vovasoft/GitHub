package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.List;


public class ChannelDetailChannel extends ChannelBaseInfo{

	private List<ChannelDetailDetail> details = new ArrayList<ChannelDetailDetail>();
	
	
	
	/**
	* <p>Title: </p>
	* <p>Description: </p>
	*/
	public ChannelDetailChannel() {
	}

	
	public ChannelDetailChannel(String channel, String parent, String child,
			String seq, List<ChannelDetailDetail> details) {
		this.setChannel(channel); 
		this.setParent(parent);
		this.setChild(child);
		this.setSeq(seq);
		this.setDetails(details);
	}

	public List<ChannelDetailDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ChannelDetailDetail> details) {
		this.details = details;
	}

}
