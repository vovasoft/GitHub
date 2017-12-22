package com.xcloud.schedule.logic;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.xcloud.schedule.appdomain.DailyOut;
import com.xcloud.schedule.daily.DailyCheck;
import com.xcloud.schedule.mongodao.BsparseDao;
import com.xcloud.schedule.util.CharUtil;
import com.xcloud.schedule.util.DateUtil;
import com.xcloud.schedule.util.GetDocmentUtil;


/**
 *
 */
public class DailyLogic {
	
	private final static Integer ONE_DAY_SECONDS = 86400;
	private final static Integer ONE_MINUTE_SECONDS = 60;
	private final static Integer ONE_HOUR_SECONDS = 3600;
	
	private final static String REG_KEY = "reg:*";
	private final static String LOGIN = "login:";
	private final static String ALL_ORDER = "allorder:";
	private final static String PAYCH = "paych:";
	private final static String ORDER = "order";
	private final static String MONEY = "money";
	private final static String NEW_PAYER = "newpayer:";
	private final static String COLON = ":";
	private final static String ALL = "all";
	private static Set<String> uidSet = null;
	private Jedis jedis;
	private MongoClient client;
	private MongoDatabase mongoDb;
	private JedisPool pool;
	private String appName;
	private MongoDatabase mongoDb_user;
	private MongoClient client_user;

	private static final Logger logger = LoggerFactory.getLogger(DailyLogic.class);
	public DailyLogic(Jedis jedis,MongoClient client,MongoDatabase mongoDb,
			MongoClient client_user, MongoDatabase mongoDb_user, JedisPool pool, String appName){
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
	* <p>Description: </p>
	*/
	public void insertData() {
		try{
		Integer currentTime = 0;
		//三款游戏的时区一致
		/*if (appName == "bs" || "bs".equals(appName)) {*/
			currentTime = DateUtil.getBscurrentTime();
			System.out.println("appName:"+appName+", QuartzByDaily()  start...."+currentTime);
		/*}else if (appName == "naruto" || "naruto".equals(appName)) {
			currentTime = DateUtil.getBscurrentTime();
			System.out.println("appName:"+appName+", getNAQuartzByDaily()  start...."+currentTime);
		}*/
		int startTime = currentTime-ONE_DAY_SECONDS;
		Set<String> channelByKeys = DailyCheck.getChannelByKeys(jedis, appName, REG_KEY);
		BsparseDao bsDao = new BsparseDao(client, mongoDb);
		Integer new_user = 0;
		Integer active_user = 0;
		Integer new_payer = 0;
		Integer all_order = 0;
		Integer payed_order = 0;
		double sum = 0d;
		uidSet = new HashSet<String>();
		for(String key:channelByKeys){
			try{
				DailyOut out = getDailyOut(key,startTime,currentTime);
				new_user += out.getNew_user();
				active_user += out.getActive_user();
				new_payer += out.getNew_payer();
				all_order += out.getAll_order();
				payed_order += out.getPayed_order();
				sum += out.getIncome();
				bsDao.insertDailyOut(out);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
		try{
			DailyOut out = GetDocmentUtil.getDailyOut(startTime, ALL, "", "", "", new_user, active_user,
					uidSet.size(), new_payer, all_order, payed_order, sum);
			bsDao.insertDailyOut(out);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println("DailyOut insert successfully...");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	* <p>Title: getDailyOut</p>
	* <p>Descripti</p>
	* @param
	* @return
	*/
	public DailyOut getDailyOut(String key,Integer startTime,Integer currentTime){
		String channel = CharUtil.cutString(key, COLON);
		Integer new_user = (int)DailyCheck.getCountByChannel(jedis, key, startTime, currentTime);
		Integer active_user = (int)DailyCheck.getCountByChannel(jedis, appName + LOGIN + channel, startTime, currentTime);
		Integer all_order = (int)DailyCheck.getCountByChannel(jedis, appName + ALL_ORDER + channel, startTime, currentTime);
		Set<String> orders = DailyCheck.getSetByChannelTime(jedis,appName + PAYCH + channel,startTime,currentTime);
		Integer payed_order = orders.size();
		//System.out.println("range----------"+setByZrange.size());
		Set<String> payer = new HashSet<String>();
		double sum = 0d;
		double count = 0d;
		for(String orderid:orders){
			String uid = DailyCheck.getUidByOrderId(jedis, appName + ORDER, orderid);
			payer.add(uid);
			uidSet.add(uid);
			count = DailyCheck.getMoneyByOrderId(jedis, appName + MONEY, orderid);
			sum += count;
		}
		int new_payer = (int)DailyCheck.getCountByChannel(jedis, appName + NEW_PAYER + channel, startTime, currentTime);
		String[] arrs = GetDocmentUtil.getChannelArr(jedis, channel, appName);
		DailyOut out = GetDocmentUtil.getDailyOut(startTime, channel, arrs[0], arrs[1], arrs[2], new_user, 
				active_user, payer.size(), new_payer, all_order, payed_order, sum);
		return out;
	}
}
