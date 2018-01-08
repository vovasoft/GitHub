package com.vova.tessarwebserver.domain.payment;

/**
 * @author: Vova
 * @create: date 15:57 2018/1/2
 */
public class PayMentJson {
    private String dateID;
    private long newAddNum;                  //新增用户数
    private long newAddPayPlayerNum;         //新增用户付费数
    private float newAddPayAllMoney;          //新增付费总额
    private float newAddPlayerARPPU;         //新增用户ARPPU
    private float newAddPayPlayerARPPU;      //新增用户的付费用户ARPPU

    private long newPayPlayerNum;            //首冲人数
    private float newPayAllMoney;             //首冲总额

    private float averageNewPayMoney;        //平均首冲
    private long activeNum;                  //活跃用户数
    private long payPlayerNum;               //付费用户数
    private float todayAllPayMoney;           //日总付费

    private float activeARPPU;               //活跃用户ARPPU
    private float payPlayerARPPU;            //付费用户ARPPU

    private float allMoney;                   //累计付费

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

    public long getNewAddPayPlayerNum() {
        return newAddPayPlayerNum;
    }

    public void setNewAddPayPlayerNum(long newAddPayPlayerNum) {
        this.newAddPayPlayerNum = newAddPayPlayerNum;
    }

    public float getNewAddPayAllMoney() {
        return newAddPayAllMoney;
    }

    public void setNewAddPayAllMoney(float newAddPayAllMoney) {
        this.newAddPayAllMoney = newAddPayAllMoney;
    }

    public float getNewAddPlayerARPPU() {
        return newAddPlayerARPPU;
    }

    public void setNewAddPlayerARPPU(float newAddPlayerARPPU) {
        this.newAddPlayerARPPU = newAddPlayerARPPU;
    }

    public float getNewAddPayPlayerARPPU() {
        return newAddPayPlayerARPPU;
    }

    public void setNewAddPayPlayerARPPU(float newAddPayPlayerARPPU) {
        this.newAddPayPlayerARPPU = newAddPayPlayerARPPU;
    }

    public long getNewPayPlayerNum() {
        return newPayPlayerNum;
    }

    public void setNewPayPlayerNum(long newPayPlayerNum) {
        this.newPayPlayerNum = newPayPlayerNum;
    }

    public float getNewPayAllMoney() {
        return newPayAllMoney;
    }

    public void setNewPayAllMoney(float newPayAllMoney) {
        this.newPayAllMoney = newPayAllMoney;
    }

    public float getAverageNewPayMoney() {
        return averageNewPayMoney;
    }

    public void setAverageNewPayMoney(float averageNewPayMoney) {
        this.averageNewPayMoney = averageNewPayMoney;
    }

    public long getActiveNum() {
        return activeNum;
    }

    public void setActiveNum(long activeNum) {
        this.activeNum = activeNum;
    }

    public long getPayPlayerNum() {
        return payPlayerNum;
    }

    public void setPayPlayerNum(long payPlayerNum) {
        this.payPlayerNum = payPlayerNum;
    }

    public float getTodayAllPayMoney() {
        return todayAllPayMoney;
    }

    public void setTodayAllPayMoney(float todayAllPayMoney) {
        this.todayAllPayMoney = todayAllPayMoney;
    }

    public float getActiveARPPU() {
        return activeARPPU;
    }

    public void setActiveARPPU(float activeARPPU) {
        this.activeARPPU = activeARPPU;
    }

    public float getPayPlayerARPPU() {
        return payPlayerARPPU;
    }

    public void setPayPlayerARPPU(float payPlayerARPPU) {
        this.payPlayerARPPU = payPlayerARPPU;
    }

    public float getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(float allMoney) {
        this.allMoney = allMoney;
    }

    public PayMentJson() {

    }

    public PayMentJson(String dateID, long newAddNum, long newAddPayPlayerNum, float newAddPayAllMoney, float newAddPlayerARPPU, float newAddPayPlayerARPPU, long newPayPlayerNum, float newPayAllMoney, float averageNewPayMoney, long activeNum, long payPlayerNum, float todayAllPayMoney, float activeARPPU, float payPlayerARPPU, float allMoney) {

        this.dateID = dateID;
        this.newAddNum = newAddNum;
        this.newAddPayPlayerNum = newAddPayPlayerNum;
        this.newAddPayAllMoney = newAddPayAllMoney;
        this.newAddPlayerARPPU = newAddPlayerARPPU;
        this.newAddPayPlayerARPPU = newAddPayPlayerARPPU;
        this.newPayPlayerNum = newPayPlayerNum;
        this.newPayAllMoney = newPayAllMoney;
        this.averageNewPayMoney = averageNewPayMoney;
        this.activeNum = activeNum;
        this.payPlayerNum = payPlayerNum;
        this.todayAllPayMoney = todayAllPayMoney;
        this.activeARPPU = activeARPPU;
        this.payPlayerARPPU = payPlayerARPPU;
        this.allMoney = allMoney;
    }
}
