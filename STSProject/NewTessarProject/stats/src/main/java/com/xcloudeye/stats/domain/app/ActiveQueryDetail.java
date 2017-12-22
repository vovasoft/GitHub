package com.xcloudeye.stats.domain.app;

/**
 * Created by joker on 2015/9/9.
 */
public class ActiveQueryDetail {
    private String fbid;
    private String uid;
    private String email;
    private String location;

    public ActiveQueryDetail() {
    }

    public ActiveQueryDetail(String fbid, String uid, String email, String location) {
        this.fbid = fbid;
        this.uid = uid;
        this.email = email;
        this.location = location;
    }

    public String getFbid() {
        return fbid;
    }

    public void setFbid(String fbid) {
        this.fbid = fbid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
