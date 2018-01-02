package com.vova.tessarwebserver.domain.newadd;

import java.util.Date;

/**
 * @author: Vova
 * @create: date 15:57 2018/1/2
 */
public class NewJson {
    private String dateID;
    private long newAddNum;
    private long activeNum;
    private long loginCount;
    private float averageLogin;
    private long allPlayerNum;

    public String getDateID() {
        return dateID;
    }

    public void setDateID(String dateID) {
        this.dateID = dateID;
    }

    public long getNewAddNum() {
        return newAddNum;
    }

    public void setNewAddNum(long newAddNum) {
        this.newAddNum = newAddNum;
    }

    public long getActiveNum() {
        return activeNum;
    }

    public void setActiveNum(long activeNum) {
        this.activeNum = activeNum;
    }

    public long getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(long loginCount) {
        this.loginCount = loginCount;
    }

    public float getAverageLogin() {
        return averageLogin;
    }

    public void setAverageLogin(float averageLogin) {
        this.averageLogin = averageLogin;
    }

    public long getAllPlayerNum() {
        return allPlayerNum;
    }

    public void setAllPlayerNum(long allPlayerNum) {
        this.allPlayerNum = allPlayerNum;
    }

    public NewJson() {

    }

    public NewJson(String dateID, long newAddNum, long activeNum, long loginCount, float averageLogin, long allPlayerNum) {

        this.dateID = dateID;
        this.newAddNum = newAddNum;
        this.activeNum = activeNum;
        this.loginCount = loginCount;
        this.averageLogin = averageLogin;
        this.allPlayerNum = allPlayerNum;
    }
}
