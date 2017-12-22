package com.xcloudeye.stats.domain.query;

/**
 * Created by joker on 2015/9/15.
 */
public class UserQuery {
    private String uid;
    private String gid;
    private String server;
    private String userName;
    private Integer addTime;
    private String channel;
    private String newChannel;
    private String ip;
    private String location;
    private String local;

    public UserQuery() {
    }

    public UserQuery(String uid, String gid, String server, String userName, String channel, Integer addTime
            , String newChannel, String ip, String location, String local) {
        this.uid = uid;
        this.gid = gid;
        this.server = server;
        this.userName = userName;
        this.channel = channel;
        this.addTime = addTime;
        this.newChannel = newChannel;
        this.ip = ip;
        this.location = location;
        this.local = local;
    }

    public String getNewChannel() {
        return newChannel;
    }

    public void setNewChannel(String newChannel) {
        this.newChannel = newChannel;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}
