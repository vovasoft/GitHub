package com.xcloudeye.stats.domain.db;

public class LogInfo extends LoginPlatform {

    private String email;
    private int emailactive;
    private String nickname;
    private String sex;
    private String firstname;
    private String lastname;
    private String phonenumber;
    private Integer birthday;
    private String facebook_id;
    private String google_id;
    private String linkedin_id;
    private String twitter_id;
    private String channel;
    private String locale;
    private String location;
    private String ad;
    private String status;
    private String language;

    public LogInfo(String uid, String username, String gid, String sid, Integer addtime,
                   String fbid, String appname, String ip, Integer date, String client, String os,
                   String action, long rcvtime, String email, int emailactive, String nickname,
                   String sex, String firstname, String lastname, String phonenumber, Integer birthday,
                   String facebook_id, String google_id, String linkedin_id, String twitter_id, String channel,
                   String locale, String location, String ad, String status, String language) {
        super(uid, username, gid, sid, addtime, fbid, appname, ip, date, client, os, action, rcvtime);
        this.email = email;
        this.emailactive = emailactive;
        this.nickname = nickname;
        this.sex = sex;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.birthday = birthday;
        this.facebook_id = facebook_id;
        this.google_id = google_id;
        this.linkedin_id = linkedin_id;
        this.twitter_id = twitter_id;
        this.channel = channel;
        this.locale = locale;
        this.location = location;
        this.ad = ad;
        this.status = status;
        this.language = language;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public int getEmailactive() {
        return emailactive;
    }


    public void setEmailactive(int emailactive) {
        this.emailactive = emailactive;
    }


    public String getNickname() {
        return nickname;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getSex() {
        return sex;
    }


    public void setSex(String sex) {
        this.sex = sex;
    }


    public String getFirstname() {
        return firstname;
    }


    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }


    public String getLastname() {
        return lastname;
    }


    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public String getPhonenumber() {
        return phonenumber;
    }


    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }


    public Integer getBirthday() {
        return birthday;
    }


    public void setBirthday(Integer birthday) {
        this.birthday = birthday;
    }

    public String getFacebook_id() {
        return facebook_id;
    }


    public void setFacebook_id(String facebook_id) {
        this.facebook_id = facebook_id;
    }


    public String getGoogle_id() {
        return google_id;
    }


    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
    }


    public String getLinkedin_id() {
        return linkedin_id;
    }


    public void setLinkedin_id(String linkedin_id) {
        this.linkedin_id = linkedin_id;
    }


    public String getTwitter_id() {
        return twitter_id;
    }


    public void setTwitter_id(String twitter_id) {
        this.twitter_id = twitter_id;
    }


    public String getChannel() {
        return channel;
    }


    public void setChannel(String channel) {
        this.channel = channel;
    }


    public String getAd() {
        return ad;
    }


    public void setAd(String ad) {
        this.ad = ad;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public String getLanguage() {
        return language;
    }


    public void setLanguage(String language) {
        this.language = language;
    }


    public LogInfo() {
        // TODO Auto-generated constructor stub
    }

}
