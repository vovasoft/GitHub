package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.List;

public class ChannelList extends AppOutBase {

    private List<ChannelListChannel> channels = new ArrayList<ChannelListChannel>();

    public ChannelList() {
    }


    /**
     * @param channels
     */
    public ChannelList(String set, String api, String app,
                       List<ChannelListChannel> channels) {
        this.setSet(set);
        this.setApi(api);
        this.setApp(app);
        this.setChannels(channels);
    }


    public List<ChannelListChannel> getChannels() {
        return channels;
    }


    public void setChannels(List<ChannelListChannel> channels) {
        this.channels = channels;
    }
}
