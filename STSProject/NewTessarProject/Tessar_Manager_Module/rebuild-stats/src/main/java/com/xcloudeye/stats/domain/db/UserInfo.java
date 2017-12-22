package com.xcloudeye.stats.domain.db;

/**
 * Created by joker on 2015/9/9.
 */
public class UserInfo {
    private String uid;
    private String username;
    private String password;
    private String random;
    private String email;
    private Integer  emailactive;
    private String regip;
    private Integer  regdate;
    private String lastip;
    private Integer lastdate;
    private String appname;
    private String type;
    private String avatar;
    private String nickname;
    private String sex;
    private String firstname;
    private String lastname;
    private String phonenumber;
    private String birthday;
    private Integer is_rss;
    private String ucuserid;
    private String facebook_id;
    private String google_id;
    private String linkedin_id;
    private String twitter_id;
    private String channel_from;
    private String ad_from;
    private Integer status;

    public UserInfo() {
    }

    public UserInfo(String uid, String username, String password, String email, String random, Integer emailactive
            , String nickname, String regip, Integer regdate, Integer lastdate, String lastip, String appname
            , String type, String avatar, String sex, String firstname, String lastname, String phonenumber
            , String birthday, Integer is_rss, String facebook_id, String ucuserid, String google_id, String linkedin_id
            , String twitter_id, String channel_from, String ad_from, Integer status) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.email = email;
        this.random = random;
        this.emailactive = emailactive;
        this.nickname = nickname;
        this.regip = regip;
        this.regdate = regdate;
        this.lastdate = lastdate;
        this.lastip = lastip;
        this.appname = appname;
        this.type = type;
        this.avatar = avatar;
        this.sex = sex;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.birthday = birthday;
        this.is_rss = is_rss;
        this.facebook_id = facebook_id;
        this.ucuserid = ucuserid;
        this.google_id = google_id;
        this.linkedin_id = linkedin_id;
        this.twitter_id = twitter_id;
        this.channel_from = channel_from;
        this.ad_from = ad_from;
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEmailactive() {
        return emailactive;
    }

    public void setEmailactive(Integer emailactive) {
        this.emailactive = emailactive;
    }

    public String getRegip() {
        return regip;
    }

    public void setRegip(String regip) {
        this.regip = regip;
    }

    public Integer getRegdate() {
        return regdate;
    }

    public void setRegdate(Integer regdate) {
        this.regdate = regdate;
    }

    public String getLastip() {
        return lastip;
    }

    public void setLastip(String lastip) {
        this.lastip = lastip;
    }

    public Integer getLastdate() {
        return lastdate;
    }

    public void setLastdate(Integer lastdate) {
        this.lastdate = lastdate;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getIs_rss() {
        return is_rss;
    }

    public void setIs_rss(Integer is_rss) {
        this.is_rss = is_rss;
    }

    public String getUcuserid() {
        return ucuserid;
    }

    public void setUcuserid(String ucuserid) {
        this.ucuserid = ucuserid;
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

    public String getChannel_from() {
        return channel_from;
    }

    public void setChannel_from(String channel_from) {
        this.channel_from = channel_from;
    }

    public String getAd_from() {
        return ad_from;
    }

    public void setAd_from(String ad_from) {
        this.ad_from = ad_from;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
