package vova.domain;

/**
 * @author: Vova
 * @create: date 11:03 2017/12/25
 */


//PHP传输过来的json文件
//"uid":10023722086,"regdate":1514185002,
// "lastdate":1514185002,"channel_from":"ngBrazil","gid":"bloodstrke","sub":"lp-fb","sid":"0"

public class Player {
    private String uid;
    private long regdate; //registerTime
//    private String ucID;  //user channel create id
//    private String gameID;
    private long lastdate;//
    private String channel_from; //game channel create id
    private String gid;
    private String sub; //sid channel create id;
    private String sid;
//    private boolean fristDay;
//    private boolean fristweek;
//    private boolean fristMonth;
//    private long duration;
//    private String other1;
//    private String other2;
//    private String other3;
//    private String other4;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getRegdate() {
        return regdate;
    }

    public void setRegdate(long regdate) {
        this.regdate = regdate;
    }

    public long getLastdate() {
        return lastdate;
    }

    public void setLastdate(long lastdate) {
        this.lastdate = lastdate;
    }

    public String getChannel_from() {
        return channel_from;
    }

    public void setChannel_from(String channel_from) {
        this.channel_from = channel_from;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Player() {

    }

    public Player(String uid, long regdate, long lastdate, String channel_from, String gid, String sub, String sid) {
        this.uid = uid;
        this.regdate = regdate;
        this.lastdate = lastdate;
        this.channel_from = channel_from;
        this.gid = gid;
        this.sub = sub;
        this.sid = sid;
    }
}
