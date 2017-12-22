package com.xcloud.schedule;

import java.util.HashMap;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.xcloud.schedule.logic.FifteenMinuteLogic;
import com.xcloud.schedule.util.DateUtil;
import com.xcloud.schedule.util.InitAppUtil;
import com.xcloud.schedule.util.SingleMongoUser;
import com.xcloud.schedule.util.LogicUtil;
import com.xcloud.schedule.util.SingleNarutoMongoConf;

/**
 * Hello world!
 *
 */
public class WonFifteenMinuteSchedule 
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
  private static String WON_NAME_ID = "5";

  /*@SuppressWarnings("resource")
  public static void main( String[] args )
  {
//      ApplicationContext context = new ClassPathXmlApplicationContext("/hoursch-conf.xml");
	  WonFifteenMinuteSchedule test = new WonFifteenMinuteSchedule();
		test.getWonQuartzByFifMinutes();
  }*/
  
  /**
* <p>Title: initApp</p>
* <p>Description: 初始化本调度需要的数据</p>
*/
  public void initApp(){
	  InitAppUtil initAppUtil = new InitAppUtil(WON_NAME_ID);
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
	
  public void getWonQuartzByFifMinutes (){
  	try{
  	  DateUtil.setWonCurrentTimeMinuteSecond();
      System.out.println("won fifteen quart start..."+DateUtil.getWoncurrentMinuteTime());
      //初始化Dao操作参数
      initApp();
      
      //需要每15分钟计算一次的接口 接口数据
      FifteenMinuteLogic fifteenLogic = new FifteenMinuteLogic(jedis, client, mongoDb, client_user, mongoDb_user, jedisPool, mAppMap.get(WON_NAME_ID));
      fifteenLogic.insertData();
      
      System.out.println("won fifteen quart end..."+DateUtil.getWoncurrentMinuteTime());
  	}catch(Exception e){
  		e.printStackTrace();
  	}finally{
  		jedis.close();
  		jedisPool.close();
  		LogicUtil.setNewUsersMap(null);
  	}
  }
  
}
