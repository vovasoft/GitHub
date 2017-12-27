package vova.domain;

import java.sql.Date;


public class Pay_ExistTable {
    Date dateTime;
    int newIn;
    String days;//"xxx,xxx,xxx,xxxx,xxx"
    String weeks;
    String months;
    String others;

    public Pay_ExistTable(Date dateTime, int newIn, String days, String weeks, String months, String others) {
        this.dateTime = dateTime;
        this.newIn = newIn;
        this.days = days;
        this.weeks = weeks;
        this.months = months;
        this.others = others;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public int getNewIn() {
        return newIn;
    }

    public void setNewIn(int newIn) {
        this.newIn = newIn;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getWeeks() {
        return weeks;
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }
}
