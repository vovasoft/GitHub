package domain.newadd;

import java.util.Date;

/**
 * @author: Vova
 * @create: date 11:20 2017/12/26
 */
public class NewAddDay {
    private Date dayDate;
    private String cID;
    private String gID;
    private String sID;
    private long newAddNum;
    private long activeNum;
    private long loginCount;
    private long averageLogin;
    private long allPlayerNum;

    public NewAddDay() {
    }

    public Date getDayDate() {
        return dayDate;
    }

    public void setDayDate(Date dayDate) {
        this.dayDate = dayDate;
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

    public long getAverageLogin() {
        return averageLogin;
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

    public NewAddDay(Date dayDate, String cID, String gID, String sID, long newAddNum, long activeNum, long loginCount, long averageLogin, long allPlayerNum) {
        this.dayDate = dayDate;
        this.cID = cID;
        this.gID = gID;
        this.sID = sID;
        this.newAddNum = newAddNum;
        this.activeNum = activeNum;
        this.loginCount = loginCount;
        this.averageLogin = averageLogin;
        this.allPlayerNum = allPlayerNum;
    }
}
