package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.List;

public class UserPay extends AppOutBase {

    private List<UserPayChannel> channels = new ArrayList<UserPayChannel>();

    public UserPay() {
    }

    public UserPay(String set, String api, String app, List<UserPayChannel> channels) {
        this.setSet(set);
        this.setApi(api);
        this.setApp(app);
        this.setChannels(channels);
    }

    public List<UserPayChannel> getChannels() {
        return channels;
    }

    public void setChannels(List<UserPayChannel> channels) {
        this.channels = channels;
    }


}
