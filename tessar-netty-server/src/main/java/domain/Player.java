package domain;

import java.util.Date;

/**
 * @author: Vova
 * @create: date 11:03 2017/12/25
 */
public class Player {
    private String uid;
    private Date rTime; //registerTime
    private String ucID;  //user channel create id
    private String gameID;
    private Date grTime;//game registerTime
    private String gcID; //game channel create id
    private String sid;
    private Date scID; //sid channel create id;
    private Date loginTime;
    private boolean fristDay;
    private boolean fristweek;
    private boolean fristMonth;
    private long duration;
    private String other1;
    private String other2;
    private String other3;
    private String other4;

    public Player(String uid, Date rTime, String ucID, String gameID,
                  Date grTime, String gcID, String sid, Date scID,
                  Date loginTime, boolean fristDay, boolean fristweek,
                  boolean fristMonth, long duration, String other1,
                  String other2, String other3, String other4) {
        this.uid = uid;
        this.rTime = rTime;
        this.ucID = ucID;
        this.gameID = gameID;
        this.grTime = grTime;
        this.gcID = gcID;
        this.sid = sid;
        this.scID = scID;
        this.loginTime = loginTime;
        this.fristDay = fristDay;
        this.fristweek = fristweek;
        this.fristMonth = fristMonth;
        this.duration = duration;
        this.other1 = other1;
        this.other2 = other2;
        this.other3 = other3;
        this.other4 = other4;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getrTime() {
        return rTime;
    }

    public void setrTime(Date rTime) {
        this.rTime = rTime;
    }

    public String getUcID() {
        return ucID;
    }

    public void setUcID(String ucID) {
        this.ucID = ucID;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public Date getGrTime() {
        return grTime;
    }

    public void setGrTime(Date grTime) {
        this.grTime = grTime;
    }

    public String getGcID() {
        return gcID;
    }

    public void setGcID(String gcID) {
        this.gcID = gcID;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Date getScID() {
        return scID;
    }

    public void setScID(Date scID) {
        this.scID = scID;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public boolean isFristDay() {
        return fristDay;
    }

    public void setFristDay(boolean fristDay) {
        this.fristDay = fristDay;
    }

    public boolean isFristweek() {
        return fristweek;
    }

    public void setFristweek(boolean fristweek) {
        this.fristweek = fristweek;
    }

    public boolean isFristMonth() {
        return fristMonth;
    }

    public void setFristMonth(boolean fristMonth) {
        this.fristMonth = fristMonth;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getOther1() {
        return other1;
    }

    public void setOther1(String other1) {
        this.other1 = other1;
    }

    public String getOther2() {
        return other2;
    }

    public void setOther2(String other2) {
        this.other2 = other2;
    }

    public String getOther3() {
        return other3;
    }

    public void setOther3(String other3) {
        this.other3 = other3;
    }

    public String getOther4() {
        return other4;
    }

    public void setOther4(String other4) {
        this.other4 = other4;
    }
}
