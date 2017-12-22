package com.xcloud.schedule.logic;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
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
 * 五分钟新注册用户
 * Created by admin on 2016/1/25.
 */
public class FiveNewUserLogic {
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
    public FiveNewUserLogic(Jedis jedis, MongoClient client,
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
    public void insertNewUserData() {
        Integer currentTimeSecond = 0;
        /*if (appName == "bs" || "bs".equals(appName)) {*/
            currentTimeSecond = DateUtil.getBscurrentMinuteTime();
       /* }else if (appName == "naruto" || "naruto".equals(appName)) {
            currentTimeSecond = DateUtil.getBscurrentMinuteTime();
        }*/
        Integer endTime = (int)(currentTimeSecond/ONE_MINUTE_SECONDS)*ONE_MINUTE_SECONDS;
        Integer fiveMinuteAgo = endTime-ONE_MINUTE_SECONDS*5;

        Set<String> regChannels = HourlyCheck.getChannelByKeys(jedis, appName, REG_KEY);
        BsparseDao bsDao = new BsparseDao(client, mongoDb);
        Integer new_user = 0;
        uidSet = new HashSet<String>();
        for(String key:regChannels){
            try{
                FiveMinuteOut out = getFiveNewUserOut(key, fiveMinuteAgo, endTime);
                new_user += out.getNew_user();
                bsDao.insertFiveNewUser(out);
            } catch(Exception e){
                e.printStackTrace();
            }
        }

        try{
            FiveMinuteOut out = GetDocmentUtil.FiveNewUserOut(endTime, "all", "", "", "", new_user);
            bsDao.insertFiveNewUser(out);
        }catch(Exception e){
            e.printStackTrace();
        }

        System.out.println("five_new_user insert successfully...");

    }

    private FiveMinuteOut getFiveNewUserOut(String key, Integer fiveMinuteAgo, Integer endTime) {
        String channel = CharUtil.cutString(key, ":");
        Integer new_user = (int) DailyCheck.getCountByChannel(jedis, appName + "reg:" + channel, fiveMinuteAgo, endTime);
        String[] arrs = GetDocmentUtil.getChannelArr(jedis, channel, appName);
        FiveMinuteOut out = GetDocmentUtil.FiveNewUserOut(endTime, channel, arrs[0], arrs[1],arrs[2],new_user);
        return out;
    }
}
