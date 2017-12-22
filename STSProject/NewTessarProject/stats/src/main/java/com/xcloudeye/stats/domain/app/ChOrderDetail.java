package com.xcloudeye.stats.domain.app;

/**
 * Created by admin on 2015/12/6.
 */
public class ChOrderDetail {
    private String orid;
    private String uid;
    private String server;
    private String good;
    private String payType;
    private String currency;
    private String amount;
    private String channel;
    private int input_time;
    private int checkout_time;
    private String status;

    public ChOrderDetail() {
    }

    public ChOrderDetail(String orid, String uid, String server, String good, String payType, String currency,
                         String amount, String channel, int input_time, int checkout_time, String status) {
        this.orid = orid;
        this.uid = uid;
        this.server = server;
        this.good = good;
        this.payType = payType;
        this.currency = currency;
        this.amount = amount;
        this.channel = channel;
        this.input_time = input_time;
        this.checkout_time = checkout_time;
        this.status = status;
    }

    public String getOrid() {
        return orid;
    }

    public void setOrid(String orid) {
        this.orid = orid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getInput_time() {
        return input_time;
    }

    public void setInput_time(int input_time) {
        this.input_time = input_time;
    }

    public int getCheckout_time() {
        return checkout_time;
    }

    public void setCheckout_time(int checkout_time) {
        this.checkout_time = checkout_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
