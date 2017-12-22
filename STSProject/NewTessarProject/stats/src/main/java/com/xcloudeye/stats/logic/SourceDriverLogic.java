package com.xcloudeye.stats.logic;

import com.google.gson.Gson;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.xcloudeye.stats.dao.AdvanceDao;
import com.xcloudeye.stats.dao.ApolloRedisDao;
import com.xcloudeye.stats.dao.SlaveRedisDao;
import com.xcloudeye.stats.dao.driverdao.SourceDbDriverDao;
import com.xcloudeye.stats.domain.app.ChannelTrack;
import com.xcloudeye.stats.domain.app.ChannelTrackChannel;
import com.xcloudeye.stats.domain.db.Payment;
import com.xcloudeye.stats.domain.query.*;
import com.xcloudeye.stats.domain.db.DailyData;
import com.xcloudeye.stats.single.SingleMongo;
import com.xcloudeye.stats.util.CharUtil;
import com.xcloudeye.stats.util.StaticValueUtil;
import org.bson.BasicBSONObject;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.*;

/**
 * Created by joker on 2015/8/25.
 */
public class SourceDriverLogic {

    private static final String RET_TITLE = "ret:";
    private SourceDbDriverDao sourceDbDriverDao;
    private AdvanceDao advanceDao;
    private String appname = null;
    private SlaveRedisDao slaveRedisDao ;
    private ApolloRedisDao apolloRedisDao;

    public AdvanceDao getAdvanceDao() {
        return advanceDao;
    }

    public void setAdvanceDao(AdvanceDao advanceDao) {
        this.advanceDao = advanceDao;
    }

    public SourceDriverLogic(int appid){
        SingleMongo singleMongo = SingleMongo.getSinglemongo();
        ClassPathXmlApplicationContext ctx = singleMongo.getDaoContext();

        ClassPathXmlApplicationContext ctx2 = new ClassPathXmlApplicationContext("classpath:redis-conf.xml");
        this.slaveRedisDao = ctx2.getBean("redisSlaveDao", SlaveRedisDao.class);
        this.apolloRedisDao= ctx2.getBean("redisApolloDao", ApolloRedisDao.class);
        sourceDbDriverDao = new SourceDbDriverDao(appid);
        switch (appid) {
            case StaticValueUtil.BLOODSTRIK_ID:
                this.appname = "bs";
                this.advanceDao = ctx.getBean("bsAdvanceDao", AdvanceDao.class);
                break;
            case StaticValueUtil.WON_ID:
                this.appname = "won";
                this.advanceDao = ctx.getBean("wonAdvanceDao", AdvanceDao.class);
                break;
            default:
                break;
        }
    }

    public List<String> getChs(){
        BasicDBObject filter = new BasicDBObject("action","reg");
        String key = "channel";
        return sourceDbDriverDao.getDistinctList(key, filter);
    }
    
    /**
     * @param start start time to query
     * @param end end time to query
     * @return return a list of channel
     */
    public List<String> getChsByTime(Integer start, Integer end){
        BasicDBObject filter = new BasicDBObject("date"
                , new BasicDBObject("$gte",start).append("$lte", end)).append("action", "reg");
        String key = "channel";
        return sourceDbDriverDao.getDistinctList(key, filter);
    }

    /**
     * @param ch Channel need to be queried
     * @param start
     * @param end
     * @return Returns the total number of channels to meet the conditions
     */
   /* public long getTotalChAtTime(String ch, Integer start, Integer end){
       *//* BasicDBObject filter = new BasicDBObject("date" , new BasicDBObject("$gte",start).append("$lte", end)).append("action", "reg").append("channel", ch);
        return sourceDbDriverDao.getSum(filter);*//*
        BasicDBObject filter = new BasicDBObject("date", new BasicDBObject("$gte",start).append("$lte", end)).append("action", "reg").append("channel", ch);
        return sourceDbDriverDao.getregSum(filter);
    }*/

    /**
     * @param ch Channel needs to be queried
     * @param start
     * @param end
     * @return Returns a list of channels to meet the conditions
     */
    public List<String> getChsAtTimes(String ch, Integer start, Integer end){
        BasicDBObject filter = new BasicDBObject("date"
                , new BasicDBObject("$gte",start).append("$lte", end)).append("action", "reg").append("channel", ch);
        return sourceDbDriverDao.getDistinctList("uid", filter);
    }

    /**
     * @param channel Channel needs to be queried
     * @param regStart The start time of query regitstered users
     * @param regEnd The end time of query regitstered users
     * @param start The end time of query logined users
     * @param end The end time of query logined users
     * @return Returns user retention
     */
    public Integer getChAnyTimeRetLogin(String channel, int regStart, int regEnd, int start, int end){
        List<String> regUid= new ArrayList<String>();
        regUid = getChsAtTimes(channel, regStart, regEnd);

        System.out.println("regUid----:" + regUid.size());
        BasicDBObject filter = new BasicDBObject("date"
                , new BasicDBObject("$gte",start).append("$lte", end)).append("action", "login").append("channel", channel);

        List<String> uid= new ArrayList<String>();
        uid = sourceDbDriverDao.getDistinctList("uid", filter);
        System.out.println("login uid----:" + uid.size());
        uid.retainAll(regUid);
        System.out.println("sum----:" + uid.size());
        return uid.size();
    }

    /**
     * @param ch
     * @param start
     * @param end
     * @return Returns the total number of channels to meet the conditions of pay
     */
    public long getPayerAtTime(String ch, int start, int end){
        BasicDBList status = new BasicDBList();
        status.add(new BasicDBObject("status", "1"));
        status.add(new BasicDBObject("status", "2"));
        BasicDBObject filter = new BasicDBObject("date" , new BasicDBObject("$gte",start).append("$lte", end)).append("channel", ch) .append("$or", status);
        return sourceDbDriverDao.getDistinctPayCount("userid", filter);
    }

    public double getIncomeAtTime(String ch, int start, int end){
        List<BasicDBObject> orValue = new ArrayList<BasicDBObject>();
        orValue.add(new BasicDBObject("status", "1"));
        orValue.add(new BasicDBObject("status", "2"));
        BasicDBObject filter = new BasicDBObject("date"
                , new BasicDBObject("$gte",start).append("$lte", end)).append("channel", ch)
                .append("$or", orValue);
        DBCursor data = sourceDbDriverDao.getPaymentQuery(filter);
        double income = 0;
        while (data.hasNext()){
        	BasicBSONObject doc = (BasicBSONObject) data.next();
            income += Double.valueOf( doc.getString("amount"));
        }
        return income;
    }

    public ChannelTrack getChTrack(int start, int end){
        //List<String> chName = new ArrayList<String>();
        //chName = getChsByTime(start, end);

        Set<String> chName=new HashSet<String>();
        chName= slaveRedisDao.getChannelByKeys();

        System.out.println("chName.size()---:" + chName.size());
        List<ChannelTrackChannel> channels = new ArrayList<ChannelTrackChannel>();
        Iterator<String> it = chName.iterator();
        long payer1 = getPayerAtTime("_0002000000000001_mincFk", start, end);
        System.out.print(payer1);
        while (it.hasNext()) {
            String ch = it.next();
            if (ch.length()>6) {
                ch = ch.substring(6, ch.length());
               DailyData dailyOne = advanceDao.getDailyOneChannel(new Query(Criteria.where("channel").is(ch)));
                if (dailyOne != null && !dailyOne.equals(null)) {
                    System.out.println("channel---:" + ch);
                    //long new_user = getTotalChAtTime(ch, start, end);
                    long new_user=slaveRedisDao.getNewUser(ch, start, end);
                    System.out.println(ch + "-----" + new_user);
                    if(new_user!=0) {
                        long payer = getPayerAtTime(ch, start, end);
                        System.out.println("payer---:" + payer);
                        double income = getIncomeAtTime(ch, start, end);
                        System.out.println("income---:" + income);
                        float arppu = 0;
                        if (payer == 0) {
                            arppu = 0;
                        } else {
                            arppu = (float) income / payer;
                        }
                        channels.add(new ChannelTrackChannel(ch, dailyOne.getParent(), dailyOne.getChild(), dailyOne.getSeq(), income, (int) new_user, (int) payer, arppu));
                    }
                }
            }
        }
        return new ChannelTrack("app", "ch_track", null, channels);
    }

    /**
     * @param uidOrName The uid of user or name of user.
     * @return The information of the user from userinfo.
     */
    public DBObject getUserFromUserinfo(String uidOrName){
        List<BasicDBObject> filters = new ArrayList<BasicDBObject>();
        if(CharUtil.judgeStringIsNumber(uidOrName)){
            filters.add(new BasicDBObject("uid", Long.valueOf(uidOrName)));
            filters.add(new BasicDBObject("username", uidOrName));
        }else{
            filters.add(new BasicDBObject("username", uidOrName));
        }

        BasicDBObject filter = new BasicDBObject("$or", filters);
        return sourceDbDriverDao.getOneUserInfo(filter);
    }

    /**
     * @param uidOrName  The uid of user or name of user.
     * @return The information of the user from Loginfo.
     */
    public DBObject getUserFromLoginfo(String uidOrName){
        List<BasicDBObject> filters = new ArrayList<BasicDBObject>();
        filters.add(new BasicDBObject("uid", uidOrName));
        filters.add(new BasicDBObject("username", uidOrName));

        BasicDBObject filter = new BasicDBObject("$or", filters).append("action", "reg");
        return sourceDbDriverDao.getOneUserLogInfo(filter);
    }

    /**
     * @param uidOrName UserId or username.
     * @return The data of the interface used to query information of user.
     */
    public UserQuery getUserQuery(String uidOrName){
        if (uidOrName == null || "".equals(uidOrName)){
            return  null;
        }
        UserQuery userQuery = new UserQuery();
        DBObject userLogin = getUserFromLoginfo(uidOrName);
        if (userLogin == null){
            DBObject user = getUserFromUserinfo(uidOrName);
            if (user != null){
                String newCh = null;
                if (user.get("channel_from") == null || "".equals(user.get("channel_from"))) {
                    newCh = getNewCh((String)user.get("channel"));
                } else {
                    newCh = getNewCh((String)user.get("channel_from"));
                }
                userQuery = new UserQuery(user.get("uid").toString(), "2"
                        , null, (String)user.get("username"), (String)user.get("channel_from"),
                        (int)user.get("regdate"), newCh , (String)user.get("regip")
                        , null, null);
            }
        }else{
            String newCh = getNewCh((String)userLogin.get("channel"));
            userQuery = new UserQuery((String)userLogin.get("uid"), (String)userLogin.get("gid")
                    , (String)userLogin.get("sid"), (String)userLogin.get("username"), (String)userLogin.get("channel")
                    , (int)userLogin.get("date"), newCh, (String)userLogin.get("ip")
                    , (String)userLogin.get("location"), (String)userLogin.get("local"));

        }
        return userQuery;
    }

    /**
     * @param uid The id of user.
     * @param orid The id of order.
     * @return Return a list of match the condition's data.
     */
    private List<UserOrderDetail> getOrderInfo(String uid, String orid){
        List<UserOrderDetail> detail = new ArrayList<UserOrderDetail>();
        BasicDBObject filter = null;
        if (uid == null || "".equals(uid)){
            if (orid != null && !"".equals(orid)){
                filter = new BasicDBObject("orderid", orid);
            }else{
                return null;
            }
        }else{
            if (orid != null && !"".equals(orid)){
                filter = new BasicDBObject("userid", uid).append("orderid", orid);
            }else{
                filter = new BasicDBObject("userid", uid);
            }
        }
        DBCursor data = sourceDbDriverDao.getPaymentQuery(filter);
        System.out.println("data.size()--:"+data.size());
        if (data.size() > 0){
            data.forEach(d -> {
                String newCh = getNewCh((String)d.get("channel"));
                detail.add(new UserOrderDetail(d.get("orderid").toString(), d.get("userid").toString(), d.get("gid").toString()
                        , d.get("server").toString(), (String)d.get("role"), (String)d.get("payment_type"), (String)d.get("currency")
                ,(String)d.get("amount"), (String)d.get("game_money"), d.get("date").toString(), d.get("status").toString(), (String)d.get("ip")
                        , (String)d.get("channel"), newCh));
            });
        }
        return detail;
    }

    public UserOrder getUserOrder(String uid, String orid){
        return new UserOrder("user_order", getOrderInfo(uid, orid));
    }


    /**
     * @param orderid The id of order.
     * @return All information of order that match the conditions.
     */
    public OrderTrack getOrderTrack(String orderid){
        OrderTrack orderTrack = new OrderTrack("order_track", null);
        List<Payment> detail = new ArrayList<Payment>();
        if (orderid != null && !"".equals(null)){
            BasicDBObject filter = new BasicDBObject("orderid", orderid);
            DBCursor data = sourceDbDriverDao.getPaymentQuery(filter);
            if (data.size() > 0){
                data.forEach(d -> {
                    Gson gson = new Gson();
                    Payment payment = gson.fromJson(d.toString(), Payment.class);
                    detail.add(payment);
                });
            }
            orderTrack.setDetail(detail);
        }
        return orderTrack;
    }

    /**
     * @param start The start time.
     * @param end The end time.
     * @return Return the data within the period of time.
     */
    public PayOrder getPayOrder(Integer start, Integer end){
        PayOrder payOrder = new PayOrder("pay_order", start, end, null);
        List<PayOrderDetail> detail = new ArrayList<PayOrderDetail>();
        BasicDBObject filter = new BasicDBObject("date", new BasicDBObject("$gte", start).append("$lte", end));
        DBCursor data = sourceDbDriverDao.getPaymentQuery(filter);
        if (data.size() > 0){
            data.forEach(d -> {
                String newCh = getNewCh((String)d.get("channel"));
                detail.add(new PayOrderDetail((String) d.get("channel"), d.get("orderid").toString(), (String) d.get("userid")
                        , (String) d.get("gid"), (String) d.get("server"), (String) d.get("good"), (String) d.get("payment_type")
                        , (String) d.get("currency"), (String) d.get("amount"), newCh, d.get("input_time").toString(), d.get("checkout_time").toString()
                        , (String) d.get("status")));
            });
        }
        payOrder.setDetail(detail);
        return payOrder;
    }

    /**
     * @param ch The name of old channel.
     * @return The new name of the old channel.
     */
    private String getNewCh(String ch){
        String newCh = slaveRedisDao.getChNewName(appname, ch);
        if (newCh == null || "".equals(newCh)){
            if (CharUtil.judgeStringMatchFormat(ch)){
                newCh = ch;
            }else { newCh = "NA_NA_NA"; }
        }
        return newCh;
    }
}
