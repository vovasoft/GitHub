package com.xcloud.schedule.logic;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.xcloud.schedule.appdomain.FifteenMinuteOut;
import com.xcloud.schedule.appdomain.FiveMinuteOut;
import com.xcloud.schedule.daily.DailyCheck;
import com.xcloud.schedule.hourly.HourlyCheck;
import com.xcloud.schedule.mongodao.BsparseDao;
import com.xcloud.schedule.util.CharUtil;
import com.xcloud.schedule.util.DateUtil;
import com.xcloud.schedule.util.GetDocmentUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by 瑞刚 on 2015/12/18.
 */
public class FiveMinuteLogic {
    private final static Integer ONE_DAY_SECONDS = 86400;
    private final static Integer ONE_MINUTE_SECONDS = 60;
    private final static Integer ONE_HOUR_SECONDS = 3600;
    private static Set<String> uidSet = null;
    private final static String REG_KEY = "reg:*";
    private final static String LOGIN_KEY = "login:*";

    private Jedis jedis;
    private MongoClient client;
    private MongoDatabase mongoDb;
    private JedisPool pool;
    private String appName;
    private MongoDatabase mongoDb_user;
    private MongoClient client_user;


    public FiveMinuteLogic(Jedis jedis, MongoClient client,
                              MongoDatabase mongoDb, MongoClient client_user,
                              MongoDatabase mongoDb_user, JedisPool pool, String appName) {
        this.jedis = jedis;
        this.client = client;
        this.mongoDb = mongoDb;
        this.pool = pool;
        this.appName = appName;
        this.mongoDb_user = mongoDb_user;
        this.client_user = client_user;
    }

    /**
     * <p>Title: insertData</p>
     * <p>Description: 将计算后的数据插入数据库中</p>
     */
    public void insertData() {
        Integer currentTimeSecond = 0;
        //三款游戏时区相同
       /* if (appName == "bs" || "bs".equals(appName)) {*/
            currentTimeSecond = DateUtil.getBscurrentMinuteTime();
       /* }else if (appName == "naruto" || "naruto".equals(appName)) {
            currentTimeSecond = DateUtil.getBscurrentMinuteTime();
        }*/
        Integer endTime = (int)(currentTimeSecond/ONE_MINUTE_SECONDS)*ONE_MINUTE_SECONDS;
        Integer fiveMinuteAgo = endTime-ONE_MINUTE_SECONDS*5;

        Set<String> regChannels = HourlyCheck.getChannelByKeys(jedis, appName, REG_KEY);
        BsparseDao bsDao = new BsparseDao(client, mongoDb);
        Integer active_user = 0;
        uidSet = new HashSet<String>();
        for(String key:regChannels){
            try{
                FiveMinuteOut out = getFiveMinuteOut(key, fiveMinuteAgo, endTime);
                active_user += out.getActive_user();
                bsDao.insertFiveMinute(out);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        try{
            FiveMinuteOut out = GetDocmentUtil.FiveMinuteOut(endTime, "all", "", "", "",active_user);
            bsDao.insertFiveMinute(out);
        }catch(Exception e){
            e.printStackTrace();
        }

       // System.out.println("FifteenMinuteOut insert successfully...");

    }

    private FiveMinuteOut getFiveMinuteOut(String key, Integer fifteenMinuteAgo, Integer endTime) {
        String channel = CharUtil.cutString(key, ":");
        Integer active_user = (int)DailyCheck.getCountByChannel(jedis, appName+"login:"+channel, fifteenMinuteAgo, endTime);
        String[] arrs = GetDocmentUtil.getChannelArr(jedis, channel, appName);
        FiveMinuteOut out = GetDocmentUtil.FiveMinuteOut(endTime,channel,arrs[0],arrs[1],arrs[2],active_user);
        return out;
    }
}
