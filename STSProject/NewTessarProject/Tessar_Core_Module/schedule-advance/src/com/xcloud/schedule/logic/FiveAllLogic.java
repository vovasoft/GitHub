package com.xcloud.schedule.logic;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.xcloud.schedule.BSFiveMinuteSchedule;
import com.xcloud.schedule.appdomain.FiveMinuteOut;
import com.xcloud.schedule.appdomain.repetitiveFiveOut;
import com.xcloud.schedule.BSRepeatFiveMinuteSchedule;
import com.xcloud.schedule.hourly.HourlyCheck;
import com.xcloud.schedule.mongodao.BsParseActiveDao;
import com.xcloud.schedule.util.DateUtil;
import com.xcloud.schedule.util.GetDocmentUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * 不去重五分钟
 * Created by admin on 2016/1/8.
 */
public class FiveAllLogic {

    private final static Integer ONE_DAY_SECONDS = 86400;
    private final static Integer ONE_MINUTE_SECONDS = 60;
    private final static Integer ONE_HOUR_SECONDS = 3600;
    private static Set<String> uidSet = null;
    private final static String REG_KEY = "reg:*";
    private final static String LOGIN_KEY = "login:*";

    private MongoClient client;
    private MongoDatabase mongoDb;
    private MongoClient ac_client_user;
    private MongoDatabase activedb;
    private String appName;

    public FiveAllLogic(MongoClient client, MongoDatabase mongoDb, MongoClient ac_client_user, MongoDatabase activedb, String appName) {
        this.client = client;
        this.mongoDb = mongoDb;
        this.ac_client_user = ac_client_user;
        this.activedb = activedb;
        this.appName = appName;
    }

    public void insertFiveAllData() {
        Integer currentTimeSecond = 0;
        if (appName == "bs" || "bs".equals(appName)) {
            currentTimeSecond = DateUtil.getBscurrentMinuteTime();
        }else if (appName == "won" || "won".equals(appName)) {
            currentTimeSecond = DateUtil.getWoncurrentMinuteTime();
        }
        Integer endTime = (int)(currentTimeSecond/ONE_MINUTE_SECONDS)*ONE_MINUTE_SECONDS;
        Integer fiveMinuteAgo = endTime-ONE_MINUTE_SECONDS*5;

        BsParseActiveDao bsDao = new BsParseActiveDao(client,mongoDb);


        Integer active_user = 0;
        uidSet = new HashSet<String>();
                repetitiveFiveOut out =getFiveAllOut("", fiveMinuteAgo, endTime);
//                active_user = out.getActive_user();
                bsDao.insertloginfo(out);
        System.out.println("repetitiveFiveOut insert successfully...");
    }

    private repetitiveFiveOut getFiveAllOut(String key,Integer fiveMinuteAgo, Integer endTime) {
        Integer all_user = BSRepeatFiveMinuteSchedule.Query(fiveMinuteAgo, endTime);
        repetitiveFiveOut out = GetDocmentUtil.ActiveFiveOut(endTime, all_user);
        return out;
    }
}
