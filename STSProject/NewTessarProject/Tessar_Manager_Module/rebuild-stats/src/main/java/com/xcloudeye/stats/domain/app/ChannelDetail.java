package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.List;

public class ChannelDetail extends AppOutBase{
	private List<ChannelDetailChannel> channels = new ArrayList<ChannelDetailChannel>();
	
	public ChannelDetail() {
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param set
	* @param api
	* @param channels
	*/
	public ChannelDetail(String set, String api, String app,
			List<ChannelDetailChannel> channels) {
		this.setApi(api);
		this.setSet(set);
		this.setApp(app);
		this.channels = channels;
	}

	public List<ChannelDetailChannel> getChannels() {
		return channels;
	}

	public void setChannels(List<ChannelDetailChannel> channels) {
		this.channels = channels;
	}

}
