package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.List;

public class PayTrend extends AppOutBase{
	
	private List<PayTrendChannel> channels = new ArrayList<PayTrendChannel>();
	
	public PayTrend() {
	}

	public PayTrend(String set, String api, String app,
			List<PayTrendChannel> channels) {
		this.setApi(api);
		this.setSet(set);
		this.setApp(app);
		this.setChannels(channels);
	}

	public List<PayTrendChannel> getChannels() {
		return channels;
	}

	public void setChannels(List<PayTrendChannel> channels) {
		this.channels = channels;
	}
	
}
