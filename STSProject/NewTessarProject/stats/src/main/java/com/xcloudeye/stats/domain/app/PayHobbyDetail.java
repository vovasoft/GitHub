package com.xcloudeye.stats.domain.app;

import com.xcloudeye.stats.domain.db.PayHobbyType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joker on 2015/9/7.
 */
public class PayHobbyDetail {
    private String channel;
    private String parent;
    private String child;
    private String seq;
    private Integer all_payer;
    private Integer all_order;
    private double income;
    private List<PayHobbyType> types = new ArrayList<PayHobbyType>();

    public PayHobbyDetail() {
    }

    public PayHobbyDetail(String channel, String parent, String child, String seq, Integer all_payer
            , Integer all_order, double income, List<PayHobbyType> types) {
        this.channel = channel;
        this.parent = parent;
        this.child = child;
        this.seq = seq;
        this.all_payer = all_payer;
        this.all_order = all_order;
        this.income = income;
        this.types = types;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public Integer getAll_payer() {
        return all_payer;
    }

    public void setAll_payer(Integer all_payer) {
        this.all_payer = all_payer;
    }

    public Integer getAll_order() {
        return all_order;
    }

    public void setAll_order(Integer all_order) {
        this.all_order = all_order;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public List<PayHobbyType> getTypes() {
        return types;
    }

    public void setTypes(List<PayHobbyType> types) {
        this.types = types;
    }
}
