package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.List;

public class ChannelTrack extends AppOutBase{
	
	private List<ChannelTrackChannel> channels = new ArrayList<ChannelTrackChannel>();

	public ChannelTrack() {
	}

	public ChannelTrack(String set, String api, String app, 
			List<ChannelTrackChannel> channels) {
		this.setSet(set);
		this.setApi(api);
		this.setApp(app);
		this.setChannels(channels);
	}

	public List<ChannelTrackChannel> getChannels() {
		return channels;
	}

	public void setChannels(List<ChannelTrackChannel> channels) {
		this.channels = channels;
	}
	
}
