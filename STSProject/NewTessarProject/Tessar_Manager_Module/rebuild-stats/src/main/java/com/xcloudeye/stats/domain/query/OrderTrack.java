package com.xcloudeye.stats.domain.query;

import com.xcloudeye.stats.domain.db.Payment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joker on 2015/9/15.
 */
public class OrderTrack {
    private String api;
    private List<Payment> detail = new ArrayList<Payment>();

    public OrderTrack() {
    }

    public OrderTrack(String api, List<Payment> detail) {
        this.api = api;
        this.detail = detail;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public List<Payment> getDetail() {
        return detail;
    }

    public void setDetail(List<Payment> detail) {
        this.detail = detail;
    }
}
