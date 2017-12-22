package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.List;

public class UserNew extends AppOutBase{
	
	private List<UserNewChannel> channels = new ArrayList<UserNewChannel>();

	public UserNew() {
	}

	public UserNew(String set, String api, String app,
			List<UserNewChannel> channels){
		this.setSet(set);
		this.setApi(api);
		this.setApp(app);
		this.setChannels(channels);
	}

	public List<UserNewChannel> getChannels() {
		return channels;
	}

	public void setChannels(List<UserNewChannel> channels) {
		this.channels = channels;
	}
	
}
