package com.xcloud.schedule;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
import com.xcloud.schedule.util.SingleBsMongoConf;
import com.xcloud.schedule.util.SingleMongoUser;

/**
 * Hourly Scheduler
 * 
 * version 0.1
 * Only App realtime data need check hourly.
 *
 */
public class BSDailySchedule
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
	private static String BS_NAME_ID = "2";
	private static final Logger logger = LoggerFactory.getLogger(BSDailySchedule.class);
	@SuppressWarnings("resource")
   public static void main( String[] args )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("/hoursch-conf.xml");
		//new BSDailySchedule().getBSQuartzByDaily();
    }
	 /**
		* <p>Title: initApp</p>
		* <p>Description: 初始化本调度需要的数据</p>
		*/
		public void initApp(){
		  InitAppUtil initAppUtil = new InitAppUtil(BS_NAME_ID);
		  mAppDbMap = initAppUtil.getmAppDbMap();
		  jedisPool = initAppUtil.getJedisPool();
		  jedis = initAppUtil.getJedis();
		  client = SingleBsMongoConf.getInstance().getClient();
		  mongoDb = SingleBsMongoConf.getInstance().getMongoDb();
		  mAppMap = initAppUtil.getmAppMap();
		  mTimezoneOffsetMap = initAppUtil.getmTimezoneOffsetMap();
		  client_user = SingleMongoUser.getInstance().getClient_user();
		  mongoDb_user = SingleMongoUser.getInstance().getMongoDb_user();
	  }
	  public void getBSQuartzByDaily (){
	  	try{
	  	  DateUtil.setBSCurrentTimeSecond();
			int start=DateUtil.getCurrentHourTimeSecond();
	      System.out.println("bs daily quart start..."+DateUtil.getBscurrentTime());
	      //初始化Dao操作参数
	      initApp();
	      //计算一天的接口 接口数据
			System.out.println("dailyLogic method is invoking....");
	      DailyLogic dailyLogic = new DailyLogic(jedis, client, mongoDb,client_user,mongoDb_user, jedisPool, mAppMap.get(BS_NAME_ID));
	      dailyLogic.insertData();

			//计算retention一天的数据接口 接口数据
			System.out.println("retentionLogic method is invoking....");
			RetentionLogic retentionLogic = new RetentionLogic(jedis, client, mongoDb,client_user,mongoDb_user, jedisPool, mAppMap.get(BS_NAME_ID));
			retentionLogic.insertData();

	      //app_trend_generic 接口数据
	      System.out.println("atgLogic method is invoking....");
		  AppTrendGenericLogic atgLogic = new AppTrendGenericLogic(jedis, client, mongoDb, jedisPool, mAppMap.get(BS_NAME_ID));
	      atgLogic.insertData();

			//pay_hobby_generic 接口
		  System.out.println("phgLogic method is invoking.... ");
		  PayHobbyGenericLogic phgLogic = new PayHobbyGenericLogic(jedis, client, mongoDb, jedisPool, mAppMap.get(BS_NAME_ID));
		  phgLogic.insertData();

	      System.out.println("bs daily quart end..."+DateUtil.getCurrentHourTimeSecond());
			int end=DateUtil.getCurrentHourTimeSecond();

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
