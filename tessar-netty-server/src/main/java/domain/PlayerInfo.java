package domain;

import java.beans.beancontext.BeanContext;

//玩家信息
public class PlayerInfo {
    private String palyerID;
    private long registerTime;
    private long LoginTime;
    private long stayTime;
    private long logoutTime;
    private boolean isFirstToday;
    private boolean isFirstWeek;
    private boolean isFirstMonth;

    public PlayerInfo(String palyerID, long registerTime, long loginTime, long stayTime, long logoutTime, boolean isFirstToday, boolean isFirstWeek, boolean isFirstMonth) {
        this.palyerID = palyerID;
        this.registerTime = registerTime;
        LoginTime = loginTime;
        this.stayTime = stayTime;
        this.logoutTime = logoutTime;
        this.isFirstToday = isFirstToday;
        this.isFirstWeek = isFirstWeek;
        this.isFirstMonth = isFirstMonth;
    }

    public String getPalyerID() {
        return palyerID;
    }

    public void setPalyerID(String palyerID) {
        this.palyerID = palyerID;
    }

    public long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }

    public long getLoginTime() {
        return LoginTime;
    }

    public void setLoginTime(long loginTime) {
        LoginTime = loginTime;
    }

    public long getStayTime() {
        return stayTime;
    }

    public void setStayTime(long stayTime) {
        this.stayTime = stayTime;
    }

    public long getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(long logoutTime) {
        this.logoutTime = logoutTime;
    }

    public boolean isFirstToday() {
        return isFirstToday;
    }

    public void setFirstToday(boolean firstToday) {
        isFirstToday = firstToday;
    }

    public boolean isFirstWeek() {
        return isFirstWeek;
    }

    public void setFirstWeek(boolean firstWeek) {
        isFirstWeek = firstWeek;
    }

    public boolean isFirstMonth() {
        return isFirstMonth;
    }

    public void setFirstMonth(boolean firstMonth) {
        isFirstMonth = firstMonth;
    }
}
