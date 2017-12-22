package com.xcloudeye.stats.domain.query;

/**
 * Created by joker on 2015/9/16.
 */
public class PayOrderDetail {
    private String orid;
    private String uid;
    private String gid;
    private String server;
    private String good;
    private String payType;
    private String currency;
    private String amount;
    private String channel;
    private String newChannel;
    private String input_time;
    private String checkout_time;
    private String status;

    public PayOrderDetail() {
    }

    public PayOrderDetail(String channel, String orid, String uid, String gid, String server, String good
            , String payType, String currency, String amount, String newChannel, String input_time, String checkout_time, String status) {
        this.channel = channel;
        this.orid = orid;
        this.uid = uid;
        this.gid = gid;
        this.server = server;
        this.good = good;
        this.payType = payType;
        this.currency = currency;
        this.amount = amount;
        this.newChannel = newChannel;
        this.input_time = input_time;
        this.checkout_time = checkout_time;
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getInput_time() {
        return input_time;
    }

    public void setInput_time(String input_time) {
        this.input_time = input_time;
    }

    public String getCheckout_time() {
        return checkout_time;
    }

    public void setCheckout_time(String checkout_time) {
        this.checkout_time = checkout_time;
    }

    public String getNewChannel() {
        return newChannel;
    }

    public void setNewChannel(String newChannel) {
        this.newChannel = newChannel;
    }
}
