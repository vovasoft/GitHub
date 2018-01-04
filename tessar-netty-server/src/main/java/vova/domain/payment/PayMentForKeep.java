package vova.domain.payment;

/**
 * @author: Vova
 * @create: date 11:51 2018/1/3
 */
//That's class for save db for payment http
public class PayMentForKeep {
    private String uid;                 //用户id
    private String cid;                 //channelID 渠道id
    private String gid;                 //game id
    private String sid;                 //server id
    private String payType;             //支付类型
    private String currency;            //币种
    private float amount;               //支付金额
    private long firstPayTime;          //首充时间
    private long payTime;               //本次支付时间

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
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

    public long getFirstPayTime() {
        return firstPayTime;
    }

    public void setFirstPayTime(long firstPayTime) {
        this.firstPayTime = firstPayTime;
    }

    public long getPayTime() {
        return payTime;
    }

    public void setPayTime(long payTime) {
        this.payTime = payTime;
    }

    public PayMentForKeep() {

    }

    public void show() {
        System.out.println(
                        ", uid:" + uid +
                        ", cid:" + cid +
                        ", gid:" + gid +
                        ", sid:" + sid +
                        ", payType:" + payType +
                        ", currency:" + currency +
                        ", amount:" + amount +
                        ", firstPayTime:" + firstPayTime +
                        ", payTime:" + payTime);
    }

    public PayMentForKeep( String uid, String cid, String gid, String sid, String payType, String currency, float amount, long firstPayTime, long payTime) {
        this.uid = uid;
        this.cid = cid;
        this.gid = gid;
        this.sid = sid;
        this.payType = payType;
        this.currency = currency;
        this.amount = amount;
        this.firstPayTime = firstPayTime;
        this.payTime = payTime;
    }
}
