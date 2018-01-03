package vova.domain.payment;

import java.util.Date;

/**
 * @author: Vova
 * @create: date 11:41 2018/1/3
 */
//用于接收支付平台发送的信息
public class PayReceive {
    private String oid;                 //order id
    private String gid;                 //game id
    private String sid;                 //server id
    private String uid;                 //user id
    private String payType;             //
    private String currency;            //币种
    private float amount;               //支付金额
    private long payTime;               //支付时间

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public long getPayTime() {
        return payTime;
    }

    public void setPayTime(long payTime) {
        this.payTime = payTime;
    }

    public PayReceive(String oid, String gid, String sid, String uid, String payType, String currency, float amount, long payTime) {

        this.oid = oid;
        this.gid = gid;
        this.sid = sid;
        this.uid = uid;
        this.payType = payType;
        this.currency = currency;
        this.amount = amount;
        this.payTime = payTime;
    }

    public PayReceive() {

    }
}
