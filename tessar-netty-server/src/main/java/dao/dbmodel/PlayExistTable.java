package dao.dbmodel;

import java.util.Date;
import java.util.ArrayList;

public class PlayExistTable {
    Date playTableDate;
    int newIn;
    String arrayDays;
    String arrayWeeks;
    String arrayMonths;
    String others;


    public int getNewIn() {
        return newIn;
    }

    public void setNewIn(int newIn) {
        this.newIn = newIn;
    }

    public String getArrayDays() {
        return arrayDays;
    }

    public void setArrayDays(String arrayDays) {
        this.arrayDays = arrayDays;
    }

    public String getArrayWeeks() {
        return arrayWeeks;
    }

    public void setArrayWeeks(String arrayWeeks) {
        this.arrayWeeks = arrayWeeks;
    }

    public String getArrayMonths() {
        return arrayMonths;
    }

    public void setArrayMonths(String arrayMonths) {
        this.arrayMonths = arrayMonths;
    }

    public PlayExistTable(Date playTableDate, int newIn, String arrayDays, String arrayWeeks, String arrayMonths) {

        this.playTableDate = playTableDate;
        this.newIn = newIn;
        this.arrayDays = arrayDays;
        this.arrayWeeks = arrayWeeks;
        this.arrayMonths = arrayMonths;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public Date getPlayTableDate() {
        return playTableDate;
    }

    public void setPlayTableDate(Date playTableDate) {
        this.playTableDate = playTableDate;
    }
}
