package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.List;

public class PayTrendChannel extends ChannelBaseInfo{

	private List<PayTrendDetail> details = new ArrayList<PayTrendDetail>();
	
	public PayTrendChannel() {
	}

	public PayTrendChannel(String channel, String parent, String child,
			String seq, List<PayTrendDetail> details) {
		this.setChannel(channel); 
		this.setParent(parent);
		this.setChild(child);
		this.setSeq(seq);
		this.setDetails(details);
	}

	public List<PayTrendDetail> getDetails() {
		return details;
	}

	public void setDetails(List<PayTrendDetail> details) {
		this.details = details;
	}
	
}
