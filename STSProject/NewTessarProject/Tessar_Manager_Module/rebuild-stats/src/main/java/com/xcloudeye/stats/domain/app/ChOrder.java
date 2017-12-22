package com.xcloudeye.stats.domain.app;

import com.xcloudeye.stats.domain.query.PayOrderDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/12/6.
 */
public class ChOrder {
    private String api;
    private Integer start;
    private Integer end;
    private List<ChOrderDetail> detail = new ArrayList<ChOrderDetail>();

    public ChOrder() {
    }

    public ChOrder(String api, Integer start, Integer end, List<ChOrderDetail> detail) {
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

    public List<ChOrderDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<ChOrderDetail> detail) {
        this.detail = detail;
    }
}
