package com.xcloudeye.stats.domain.manage;

public class UserManageCallBack {
    private String set;
    private String api;
    private long uid;
    private String username;
    private String group;
    private Integer status;
    private Integer flag;

    public UserManageCallBack() {
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param set
     * @param api
     * @param uid
     * @param username
     * @param group
     * @param status
     * @param flag
     */
    public UserManageCallBack(String set, String api, long uid, String username,
                              String group, Integer status, Integer flag) {
        this.set = set;
        this.api = api;
        this.uid = uid;
        this.username = username;
        this.group = group;
        this.status = status;
        this.flag = flag;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }


}
