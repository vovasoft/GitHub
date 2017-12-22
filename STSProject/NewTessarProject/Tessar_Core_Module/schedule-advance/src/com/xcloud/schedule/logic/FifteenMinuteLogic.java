package com.xcloud.schedule.logic;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.xcloud.schedule.appdomain.FifteenMinuteOut;
import com.xcloud.schedule.daily.DailyCheck;
import com.xcloud.schedule.hourly.HourlyCheck;
import com.xcloud.schedule.mongodao.BsparseDao;
import com.xcloud.schedule.util.CharUtil;
import com.xcloud.schedule.util.DateUtil;
import com.xcloud.schedule.util.GetDocmentUtil;

/**
 * 分渠道计算fifteen_minutes collection 中的数据，包括：新增用户、活跃用户、付费用户、
 * 新增付费用户 、所有订单、付费订单，收入，
 * 每15分钟计算一次。
 *
 */
public class FifteenMinuteLogic {

	private final static Integer ONE_DAY_SECONDS = 86400;
	private final static Integer ONE_MINUTE_SECONDS = 60;
	private final static Integer ONE_HOUR_SECONDS = 3600;
	private static Set<String> uidSet = null;
	private final static String REG_KEY = "reg:*";
	private final static String LOGIN_KEY = "login:*";
	
	private  Jedis jedis;
	private  MongoClient client;
	private MongoDatabase mongoDb;
	private JedisPool pool;
	private String appName;
	private MongoDatabase mongoDb_user;
	private MongoClient client_user;
	

	public FifteenMinuteLogic(Jedis jedis, MongoClient client,
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

	/**
	* <p>Title: insertData</p>
	* <p>Description: 将计算后的数据插入数据库中</p>
	*/
	public void insertData() {
		Integer currentTimeSecond = 0;
		//三款游戏时区相同
		/* if (appName == "bs" || "bs".equals(appName)) {*/
			 currentTimeSecond = DateUtil.getBscurrentMinuteTime();
		/*}else if (appName == "naruto" || "naruto".equals(appName)) {
			 //火影时间与生死狙击一致
			currentTimeSecond = DateUtil.getBscurrentMinuteTime();
		   // currentTimeSecond = 1473233353;
		}*/
		Integer endTime = (int)(currentTimeSecond/ONE_MINUTE_SECONDS)*ONE_MINUTE_SECONDS;
		Integer fifteenMinuteAgo = endTime-ONE_MINUTE_SECONDS*15;
		
		Set<String> regChannels = HourlyCheck.getChannelByKeys(jedis, appName,REG_KEY);
		BsparseDao bsDao = new BsparseDao(client, mongoDb);
		Integer new_user = 0;
		Integer active_user = 0;
		Integer new_payer = 0;
		Integer all_order = 0;
		Integer payed_order = 0;
		double sum = 0d;
		uidSet = new HashSet<String>();
		for(String key:regChannels){
			try{
				FifteenMinuteOut out = getFifteenMinuteOut(key,fifteenMinuteAgo, endTime);
				new_user += out.getNew_user();
				active_user += out.getActive_user();
				new_payer += out.getNew_payer();
				all_order += out.getAll_order();
				payed_order += out.getPayed_order();
				sum += out.getIncome();
				bsDao.insert(out);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		try{
			FifteenMinuteOut out = GetDocmentUtil.getFifteenMinuteOut(endTime,"all", "", "", "", new_user, active_user,
					uidSet.size(), new_payer, all_order, payed_order, sum);
			bsDao.insert(out);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println("FifteenMinuteOut insert successfully...");
		
	}
	
	private FifteenMinuteOut getFifteenMinuteOut(String key, Integer fifteenMinuteAgo, Integer endTime) {
		String channel = CharUtil.cutString(key,":");
		Integer new_user = (int)DailyCheck.getCountByChannel(jedis, key, fifteenMinuteAgo, endTime);
		Integer active_user = (int)DailyCheck.getCountByChannel(jedis, appName+"login:"+channel, fifteenMinuteAgo, endTime);
		Integer all_order = (int)DailyCheck.getCountByChannel(jedis, appName+"allorder:"+channel, fifteenMinuteAgo, endTime);
		Set<String> setByZrange = DailyCheck.getSetByChannelTime(jedis,appName+"paych:"+channel,fifteenMinuteAgo,endTime);
		Integer payed_order = setByZrange.size();
		Set<String> payer = new HashSet<String>();
		double sum = 0d;
		double count = 0d;
		for(String orderid:setByZrange){
			String uid = DailyCheck.getUidByOrderId(jedis, appName+"order", orderid);
			payer.add(uid);
			uidSet.add(uid);
			count = DailyCheck.getMoneyByOrderId(jedis, appName+"money", orderid);
			sum += count;
		}
		int new_payer = (int)DailyCheck.getCountByChannel(jedis, appName+"newpayer:"+channel, fifteenMinuteAgo, endTime);
		String[] arrs = GetDocmentUtil.getChannelArr(jedis, channel, appName);
		FifteenMinuteOut out = GetDocmentUtil.getFifteenMinuteOut(endTime, channel, arrs[0], arrs[1], arrs[2], new_user,
				active_user, payer.size(), new_payer, all_order, payed_order, sum);
		return out;
	}
}
