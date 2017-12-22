package com.xcloudeye.stats.domain.query;

/**
 * Created by joker on 2015/9/15.
 */
public class UserOrderDetail {
    private String orid;
    private String uid;
    private String gid;
    private String server;
    private String role;
    private String payType;
    private String currency;
    private String amount;
    private String gMony;
    private String addTime;
    private String status;
    private String ip;
    private String channel;
    private String newChannel;

    public UserOrderDetail() {
    }

    public UserOrderDetail(String orid, String uid, String gid, String server, String role, String payType
            , String currency, String amount, String gMony, String addTime, String status, String ip, String channel, String newChannel) {
        this.orid = orid;
        this.uid = uid;
        this.gid = gid;
        this.server = server;
        this.role = role;
        this.payType = payType;
        this.currency = currency;
        this.amount = amount;
        this.gMony = gMony;
        this.addTime = addTime;
        this.status = status;
        this.ip = ip;
        this.channel = channel;
        this.newChannel = newChannel;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getgMony() {
        return gMony;
    }

    public void setgMony(String gMony) {
        this.gMony = gMony;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getNewChannel() {
        return newChannel;
    }

    public void setNewChannel(String newChannel) {
        this.newChannel = newChannel;
    }
}
