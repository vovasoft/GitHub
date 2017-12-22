package com.xcloudeye.stats.domain.app;

import com.xcloudeye.stats.domain.db.PayHobbyGenericDb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joker on 2015/9/7.
 */
public class PayHobby {
    private String set;
    private Integer start;
    private Integer end;
    private PayHobbyGeneric generic;
    private List<PayHobbyDetail> detail = new ArrayList<PayHobbyDetail>();

    public PayHobby() {
    }

    public PayHobby(String set, Integer start, Integer end, PayHobbyGeneric generic, List<PayHobbyDetail> detail) {
        this.set = set;
        this.start = start;
        this.end = end;
        this.generic = generic;
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

    public PayHobbyGeneric getGeneric() {
        return generic;
    }

    public void setGeneric(PayHobbyGeneric generic) {
        this.generic = generic;
    }

    public List<PayHobbyDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<PayHobbyDetail> detail) {
        this.detail = detail;
    }
}
