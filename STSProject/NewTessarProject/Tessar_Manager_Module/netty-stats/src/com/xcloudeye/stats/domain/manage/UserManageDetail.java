package com.xcloudeye.stats.domain.manage;

public class UserManageDetail {

    private long uid;
    private String username;
    private String password;
    private String email;
    private String skype;
    private Integer addtime;
    private Integer changetime;
    private String group;
    private long ch_total;
    private long total;
    private long payed;
    private Integer status;

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     */
    public UserManageDetail() {
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param uid
     * @param username
     * @param password
     * @param email
     * @param skype
     * @param addtime
     * @param changetime
     * @param group
     * @param ch_total
     * @param total
     * @param payed
     * @param status
     */
    public UserManageDetail(long uid, String username, String password,
                            String email, String skype, Integer addtime, Integer changetime,
                            String group, long ch_total, long total, long payed, Integer status) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.email = email;
        this.skype = skype;
        this.addtime = addtime;
        this.changetime = changetime;
        this.group = group;
        this.ch_total = ch_total;
        this.total = total;
        this.payed = payed;
        this.status = status;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
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

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public Integer getAddtime() {
        return addtime;
    }

    public void setAddtime(Integer addtime) {
        this.addtime = addtime;
    }

    public Integer getChangetime() {
        return changetime;
    }

    public void setChangetime(Integer changetime) {
        this.changetime = changetime;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public long getCh_total() {
        return ch_total;
    }

    public void setCh_total(long ch_total) {
        this.ch_total = ch_total;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPayed() {
        return payed;
    }

    public void setPayed(long payed) {
        this.payed = payed;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
