package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.List;

public class UserActive extends AppOutBase {

    private List<UserActiveChannel> channels = new ArrayList<UserActiveChannel>();

    public UserActive() {
    }

    public UserActive(String set, String api, String app,
                      List<UserActiveChannel> channels) {
        this.setSet(set);
        this.setApi(api);
        this.setApp(app);
        this.setChannels(channels);
    }

    public List<UserActiveChannel> getChannels() {
        return channels;
    }

    public void setChannels(List<UserActiveChannel> channels) {
        this.channels = channels;
    }
}
