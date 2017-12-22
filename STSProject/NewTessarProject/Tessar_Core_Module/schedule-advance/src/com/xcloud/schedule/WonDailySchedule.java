package com.xcloud.schedule;

import java.util.HashMap;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.xcloud.schedule.logic.AppTrendGenericLogic;
import com.xcloud.schedule.logic.DailyLogic;
import com.xcloud.schedule.logic.PayHobbyGenericLogic;
import com.xcloud.schedule.logic.RetentionLogic;
import com.xcloud.schedule.util.DateUtil;
import com.xcloud.schedule.util.InitAppUtil;
import com.xcloud.schedule.util.LogicUtil;
import com.xcloud.schedule.util.SingleMongoUser;
import com.xcloud.schedule.util.SingleNarutoMongoConf;

/**
 * Hourly Scheduler
 * 
 * version 0.1
 * Only App realtime data need check hourly.
 *
 */
public class WonDailySchedule 
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
//        ApplicationContext context = new ClassPathXmlApplicationContext("/hoursch-conf.xml");
        
        new WonDailySchedule().getWonQuartzByDaily();

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
		
	  public void getWonQuartzByDaily (){
	  	try{
	  	  DateUtil.setWonCurrentTimeSecond();
	      System.out.println("won daily quart start..."+DateUtil.getWoncurrentTime());
	      //初始化Dao操作参数
	      initApp();
	      
	      //计算won一天的数据接口 接口数据
	      DailyLogic dailyLogic = new DailyLogic(jedis, client, mongoDb,client_user,mongoDb_user, jedisPool, mAppMap.get(WON_NAME_ID));
	      dailyLogic.insertData();
	      
	    //app_trend_generic 接口数据
	      AppTrendGenericLogic atgLogic = new AppTrendGenericLogic(jedis, client, mongoDb, jedisPool, mAppMap.get(WON_NAME_ID));
	      atgLogic.insertData();
	      
	      //pay_hobby_generic 接口
	      PayHobbyGenericLogic phgLogic = new PayHobbyGenericLogic(jedis, client, mongoDb, jedisPool, mAppMap.get(WON_NAME_ID));
	      phgLogic.insertData();
	      
	      //计算retention数据接口 接口数据
	      RetentionLogic retentionLogic = new RetentionLogic(jedis, client, mongoDb,client_user,mongoDb_user, jedisPool, mAppMap.get(WON_NAME_ID));
	      retentionLogic.insertData();
	      
	      System.out.println("won daily quart end..."+DateUtil.getCurrentHourTimeSecond());
	  	}catch(Exception e){
	  		e.printStackTrace();
	  	}finally{
	  		jedis.close();
	  		jedisPool.close();
	  		LogicUtil.setNewUsersMap(null);
	  	}
	  }


}
