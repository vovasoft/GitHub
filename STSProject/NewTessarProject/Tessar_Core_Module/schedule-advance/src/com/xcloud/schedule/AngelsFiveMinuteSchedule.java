package com.xcloud.schedule;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.xcloud.schedule.logic.FiveMinuteLogic;
import com.xcloud.schedule.logic.FiveNewUserLogic;
import com.xcloud.schedule.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;

/**
 * Created by 瑞刚 on 2015/12/18.
 */
public class AngelsFiveMinuteSchedule {


    HashMap<String, MongoDatabase> mAppDbMap = new HashMap<String, MongoDatabase>();
    private static Jedis jedis = null;
    private static JedisPool jedisPool = null;
    private static MongoClient client = null;
    private static MongoDatabase mongoDb = null;
    private static HashMap<String, String> mAppMap = new HashMap<String, String>();
    private static HashMap<String, String> mTimezoneOffsetMap = new HashMap<String, String>();
    private static MongoClient client_user;
    private static MongoDatabase mongoDb_user;
    private static MongoClient ac_client_user;
    private static MongoDatabase activedb;
    private static String AG_NAME_ID = "7";
    private static final Logger logger = LoggerFactory.getLogger(AngelsFiveMinuteSchedule.class);
  @SuppressWarnings("resource")
  public static void main( String[] args )
  {
     //ApplicationContext context = new ClassPathXmlApplicationContext("/hoursch-conf.xml");
      AngelsFiveMinuteSchedule test = new AngelsFiveMinuteSchedule();
		test.getAGQuartzByFiveMinutes();
  }

    /**
     * <p>Title: initApp</p>
     * <p>Description: 初始化本调度需要的数据</p>
     */
    public void initApp() {
        InitAppUtil initAppUtil = new InitAppUtil(AG_NAME_ID);
        mAppDbMap = initAppUtil.getmAppDbMap();
        jedisPool = initAppUtil.getJedisPool();
        jedis = initAppUtil.getJedis();
        client = SingleAngelsMongoConf.getInstance().getClient();
        mongoDb = SingleAngelsMongoConf.getInstance().getMongoDb();
        mAppMap = initAppUtil.getmAppMap();
        mTimezoneOffsetMap = initAppUtil.getmTimezoneOffsetMap();
        client_user = SingleMongoUser.getInstance().getClient_user();
        mongoDb_user = SingleMongoUser.getInstance().getMongoDb_user();
    }

    public void getAGQuartzByFiveMinutes() {
        try {
            DateUtil.setBSCurrentTimeMinuteSecond();
            Integer start=DateUtil.getBscurrentMinuteTime();
            System.out.println("AG five quart start..." + DateUtil.getBscurrentMinuteTime());
            //初始化Dao操作参数
            initApp();

            //需要每5分钟计算一次的接口 接口数据(五分钟去重数据)
            FiveMinuteLogic fiveLogic = new FiveMinuteLogic(jedis, client, mongoDb, client_user, mongoDb_user, jedisPool, mAppMap.get(AG_NAME_ID));
            fiveLogic.insertData();

            //需要每5分钟计算一次的接口 接口数据(五分钟新注册用户)
            FiveNewUserLogic fiveNewUser = new FiveNewUserLogic(jedis, client, mongoDb, client_user, mongoDb_user, jedisPool, mAppMap.get(AG_NAME_ID));
            fiveNewUser.insertNewUserData();

            System.out.println("AG five quart end..." + DateUtil.getBscurrentMinuteTime());
            Integer end=DateUtil.getBscurrentMinuteTime();
            logger.info("byDaily:  start--" + start + " end--" + end);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
            jedisPool.close();
            LogicUtil.setNewUsersMap(null);
        }

    }
}