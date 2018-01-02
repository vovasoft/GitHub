package com.vova.tessarwebserver.domain.stayman;

import java.util.Date;

/**
 * @author: Vova
 * @create: date 15:36 2018/1/2
 */
public class StayJson {
    private String dateID;
    private long newAddNum;
    private int[] stayList;

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

    public int[] getStayList() {
        return stayList;
    }

    public void setStayList(int[] stayList) {
        this.stayList = stayList;
    }

    public StayJson() {

    }

    public StayJson(String dateID, long newAddNum, int[] stayList) {

        this.dateID = dateID;
        this.newAddNum = newAddNum;
        this.stayList = stayList;
    }
}
