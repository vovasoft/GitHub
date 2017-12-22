package com.xcloudeye.stats.domain.query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joker on 2015/9/15.
 */
public class UserOrder {
    private String api;
    private List<UserOrderDetail> detail = new ArrayList<UserOrderDetail>();

    public UserOrder() {
    }

    public UserOrder(String api, List<UserOrderDetail> detail) {
        this.api = api;
        this.detail = detail;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public List<UserOrderDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<UserOrderDetail> detail) {
        this.detail = detail;
    }
}
