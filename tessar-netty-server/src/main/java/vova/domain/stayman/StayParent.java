package vova.domain.stayman;

import java.util.Date;

/**
 * @author: Vova
 * @create: date 17:01 2017/12/26
 */
public class StayParent {
    private int id;
    private Date dateID;
    private String cID;
    private String gID;
    private String sID;
    String stayList;

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

    public String getStayList() {
        return stayList;
    }

    public void setStayList(String stayList) {
        this.stayList = stayList;
    }

    public StayParent() {

    }

    public StayParent(int id, Date dateID, String cID, String gID, String sID, String stayList) {

        this.id = id;
        this.dateID = dateID;
        this.cID = cID;
        this.gID = gID;
        this.sID = sID;
        this.stayList = stayList;
    }
}
