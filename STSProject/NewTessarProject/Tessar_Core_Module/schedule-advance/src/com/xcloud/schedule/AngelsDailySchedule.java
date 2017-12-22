package com.xcloud.schedule;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.xcloud.schedule.logic.AppTrendGenericLogic;
import com.xcloud.schedule.logic.DailyLogic;
import com.xcloud.schedule.logic.PayHobbyGenericLogic;
import com.xcloud.schedule.logic.RetentionLogic;
import com.xcloud.schedule.util.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;

/**
 * Hourly Scheduler
 * 
 * version 0.1
 * Only App realtime data need check hourly.
 *
 */
public class AngelsDailySchedule
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
	private static String AG_NAME_ID = "7";


    public static void main( String[] args )
    {
		new AngelsDailySchedule().getAGQuartzByDaily();
    }
	 /**
		* <p>Title: initApp</p>
		* <p>Description: 初始化本调度需要的数据</p>
		*/
	public void initApp(){
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
		
	  public void getAGQuartzByDaily (){
	  	try{
		  //游戏和BS时区相同
	  	  DateUtil.setBSCurrentTimeSecond();
	      System.out.println("Angels daily quart start..."+DateUtil.getBscurrentTime());
	      //初始化Dao操作参数
	      initApp();
	      
			//计算naruto一天的数据接口 接口数据
			DailyLogic dailyLogic = new DailyLogic(jedis, client, mongoDb,client_user,mongoDb_user, jedisPool, mAppMap.get(AG_NAME_ID));
			dailyLogic.insertData();

			//app_trend_generic 接口数据
			System.out.println("atgLogic method is invoking....");
			AppTrendGenericLogic atgLogic = new AppTrendGenericLogic(jedis, client, mongoDb, jedisPool, mAppMap.get(AG_NAME_ID));
			atgLogic.insertData();

			//pay_hobby_generic 接口
			System.out.println("phgLogic method is invoking.... ");
			PayHobbyGenericLogic phgLogic = new PayHobbyGenericLogic(jedis, client, mongoDb, jedisPool, mAppMap.get(AG_NAME_ID));
			phgLogic.insertData();

			//计算retention一天的数据接口 接口数据
			System.out.println("retentionLogic method is invoking....");
			RetentionLogic retentionLogic = new RetentionLogic(jedis, client, mongoDb,client_user,mongoDb_user, jedisPool, mAppMap.get(AG_NAME_ID));
			retentionLogic.insertData();

	      
	      System.out.println("naruto daily quart end..."+DateUtil.getCurrentHourTimeSecond());
	  	}catch(Exception e){
	  		e.printStackTrace();
	  	}finally{
	  		jedis.close();
	  		jedisPool.close();
	  		LogicUtil.setNewUsersMap(null);
	  	}
	  }


}
