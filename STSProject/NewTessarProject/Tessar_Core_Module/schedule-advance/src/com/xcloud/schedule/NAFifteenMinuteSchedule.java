package com.xcloud.schedule;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.xcloud.schedule.logic.FifteenMinuteLogic;
import com.xcloud.schedule.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;

/**
 * Hello world!
 *
 */
public class NAFifteenMinuteSchedule
{
	
  HashMap<String, MongoDatabase> mAppDbMap = new HashMap<String, MongoDatabase>();
  private static Jedis jedis = null;
  private static JedisPool jedisPool = null;
  private static MongoClient client = null;
  private static MongoDatabase mongoDb = null;
  private static HashMap<String, String> mAppMap = new HashMap<String, String>();
  private static HashMap<String, String> mTimezoneOffsetMap = new HashMap<String, String>();
  private static MongoClient client_user;
  private static MongoDatabase mongoDb_user;
  private static String NARUTO_NAME_ID = "5";
    private static final Logger logger = LoggerFactory.getLogger(NAFifteenMinuteSchedule.class);

  public static void main( String[] args )
  {
//      ApplicationContext context = new ClassPathXmlApplicationContext("/hoursch-conf.xml");
	    NAFifteenMinuteSchedule test = new NAFifteenMinuteSchedule();
		test.getNAQuartzByFifMinutes();
  }
  
   /**
	* <p>Title: initApp</p>
	* <p>Description: 初始化本调度需要的数据</p>
	*/
	public void initApp(){
	  InitAppUtil initAppUtil = new InitAppUtil(NARUTO_NAME_ID);
	  mAppDbMap = initAppUtil.getmAppDbMap();
	  jedisPool = initAppUtil.getJedisPool();
	  jedis = initAppUtil.getJedis();
	  client = SingleNarutoMongoConf.getInstance().getClient();
	  mongoDb = SingleNarutoMongoConf.getInstance().getMongoDb();
	  mAppMap = initAppUtil.getmAppMap();
	  mTimezoneOffsetMap = initAppUtil.getmTimezoneOffsetMap();
	  client_user = SingleMongoUser.getInstance().getClient_user();
	  mongoDb_user = SingleMongoUser.getInstance().getMongoDb_user();
  }
	
  public void getNAQuartzByFifMinutes (){
  	try{
        //火影时间与生死狙击一致
  	    DateUtil.setBSCurrentTimeMinuteSecond();
        int start=DateUtil.getBscurrentMinuteTime();
        System.out.println("naruto fifteen quart start..."+DateUtil.getBscurrentMinuteTime());
      //初始化Dao操作参数
      initApp();
      
      //需要每15分钟计算一次的接口 接口数据
      FifteenMinuteLogic fifteenLogic = new FifteenMinuteLogic(jedis, client, mongoDb, client_user,mongoDb_user, jedisPool, mAppMap.get(NARUTO_NAME_ID));
      fifteenLogic.insertData();
      
      System.out.println("naruto fifteen quart end..."+DateUtil.getBscurrentMinuteTime());
        Integer end=DateUtil.getBscurrentMinuteTime();
        logger.info("byDaily:  start--"+start+" end--"+end);
  	}catch(Exception e){
  		e.printStackTrace();
  	}finally{
  		jedis.close();
  		jedisPool.close();
  		LogicUtil.setNewUsersMap(null);
  	}
  }
  
}
