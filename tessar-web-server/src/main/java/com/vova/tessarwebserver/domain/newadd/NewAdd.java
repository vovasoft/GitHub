package com.vova.tessarwebserver.domain.newadd;

import java.util.Date;

/**
 * @author: Vova
 * @create: date 17:01 2017/12/26
 */
public class NewAdd {
    private int id;
    private Date dateID;
    private String cID;
    private String gID;
    private String sID;
    private long newAddNum;
    private long activeNum;
    private long loginCount;
    private float averageLogin;
    private long allPlayerNum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateID() {
        return dateID;
    }

    public void setDateID(Date dateID) {
        this.dateID = dateID;
    }

    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }

    public String getgID() {
        return gID;
    }

    public void setgID(String gID) {
        this.gID = gID;
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
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


    public NewAdd(int id, Date dateID, String cID, String gID, String sID, long newAddNum, long activeNum, long loginCount, float averageLogin, long allPlayerNum) {
        this.id = id;
        this.dateID = dateID;
        this.cID = cID;
        this.gID = gID;
        this.sID = sID;
        this.newAddNum = newAddNum;
        this.activeNum = activeNum;
        this.loginCount = loginCount;
        this.averageLogin = averageLogin;
        this.allPlayerNum = allPlayerNum;
    }

    public float getAverageLogin() {

        return averageLogin;
    }

    public void setAverageLogin(float averageLogin) {
        this.averageLogin = averageLogin;
    }

    public void setAverageLogin(long averageLogin) {
        this.averageLogin = averageLogin;
    }

    public long getAllPlayerNum() {
        return allPlayerNum;
    }

    public void setAllPlayerNum(long allPlayerNum) {
        this.allPlayerNum = allPlayerNum;
    }

    public NewAdd() {

    }

}
