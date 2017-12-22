package com.xcloud.schedule;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import com.xcloud.schedule.logic.FiveAllLogic;
import com.xcloud.schedule.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;

/**
 * 五分钟不去重
 * Created by admin on 2016/1/6.
 */


public class BSRepeatFiveMinuteSchedule {
    HashMap<String, MongoDatabase> mAppDbMap = new HashMap<String, MongoDatabase>();

    private static MongoClient client = null;
    private static MongoDatabase mongoDb = null;
    private static HashMap<String, String> mAppMap = new HashMap<String, String>();
    private static HashMap<String, String> mTimezoneOffsetMap = new HashMap<String, String>();
    private static MongoClient ac_client_user;
    private static MongoDatabase activedb;
    private static String BS_NAME_ID = "2";
    private static final Logger logger = LoggerFactory.getLogger(BSRepeatFiveMinuteSchedule.class);


    @SuppressWarnings("resource")
    public static void main( String[] args )
    {
        //ApplicationContext context = new ClassPathXmlApplicationContext("/hoursch-conf.xml");
         BSRepeatFiveMinuteSchedule mongoQuery = new BSRepeatFiveMinuteSchedule();
//         mongoQuery.getActiveFive();
    }
    public static Integer Query(Integer fiveMinuteAgo, Integer endTime) {

        //创建连接
//        Mongo m = new Mongo("69.60.106.98", 27017);
//        // 得到数据库
//        DB activedb = m.getDB("bsadvance");
//       System.out.println("sda"+users.find(new BasicDBObject("addtime", fiveMinuteAgo)));


         BasicDBObject query = new BasicDBObject();
        query.put("date",(new BasicDBObject("$gte", fiveMinuteAgo).append("$lt", endTime)));
        Integer counts  = (int)activedb.getCollection("loginfo").count(query);

        System.out.println(counts);

        return counts;

    }




    public void initApp() {
        InitAppUtil initAppUtil = new InitAppUtil(BS_NAME_ID);
        client = SingleRepetitiveMongoConf.getInstance().getClient();
        mongoDb = SingleRepetitiveMongoConf.getInstance().getMongoDb();
        mAppDbMap = initAppUtil.getmAppDbMap();
        mAppMap = initAppUtil.getmAppMap();
        mTimezoneOffsetMap = initAppUtil.getmTimezoneOffsetMap();
        ac_client_user=SingleRepetitiveMongoUser.getInstance().getClient_user();
        activedb = SingleRepetitiveMongoUser.getInstance().getActivedb();

    }

    public void getActiveFive() {
        try {

            DateUtil.setBSCurrentTimeMinuteSecond();
            int start=DateUtil.getBscurrentMinuteTime();
            System.out.println("bs native five quart start..." + DateUtil.getBscurrentMinuteTime());
            //初始化Dao操作参数
            initApp();

            //需要每5分钟计算一次的接口 接口数据
            FiveAllLogic fiveAllLogic = new FiveAllLogic(client,mongoDb,ac_client_user, activedb,mAppMap.get(BS_NAME_ID));
            fiveAllLogic.insertFiveAllData();

            System.out.println("bs native five quart end..." + DateUtil.getBscurrentMinuteTime());

            int end=DateUtil.getBscurrentMinuteTime();
            logger.info("byDaily:  start--" + start + " end--" + end);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            LogicUtil.setNewUsersMap(null);
        }
    }

    }
