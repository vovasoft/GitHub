package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joker on 2015/9/9.
 */
public class ActiveQuery {
    private String set;
    private Integer start;
    private Integer end;
    private List<ActiveQueryDetail> detail = new ArrayList<ActiveQueryDetail>();

    public ActiveQuery() {
    }

    public ActiveQuery(String set, Integer start, Integer end, List<ActiveQueryDetail> detail) {
        this.set = set;
        this.start = start;
        this.end = end;
        this.detail = detail;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
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

    public List<ActiveQueryDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<ActiveQueryDetail> detail) {
        this.detail = detail;
    }
}
