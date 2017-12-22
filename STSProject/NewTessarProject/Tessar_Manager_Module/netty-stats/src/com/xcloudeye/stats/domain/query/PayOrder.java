package com.xcloudeye.stats.domain.query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joker on 2015/9/15.
 */
public class PayOrder {
    private String api;
    private Integer start;
    private Integer end;
    private List<PayOrderDetail> detail = new ArrayList<PayOrderDetail>();

    public PayOrder() {
    }

    public PayOrder(String api, Integer start, Integer end, List<PayOrderDetail> detail) {
        this.api = api;
        this.start = start;
        this.end = end;
        this.detail = detail;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public List<PayOrderDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<PayOrderDetail> detail) {
        this.detail = detail;
    }
}
