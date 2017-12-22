package com.xcloud.schedule.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.xcloud.schedule.appdomain.RetentionOut;
import com.xcloud.schedule.daily.DailyCheck;
import com.xcloud.schedule.mongodao.BsparseDao;
import com.xcloud.schedule.mongodao.XcloudeyeUserDao;
import com.xcloud.schedule.util.CharUtil;
import com.xcloud.schedule.util.DateUtil;
import com.xcloud.schedule.util.GetDocmentUtil;
import com.xcloud.schedule.util.LogicUtil;


/**
 *retention 记录每个渠道的留存情况。
 *获取当前时间，计算当天的新增用户，根据date和channel查找以前的数据，
 *然后更新留存，
 *每天更新一次。
 *
 */
public class RetentionLogic {
	
	private final static Integer ONE_DAY_SECONDS = 86400;
	private final static Integer ONE_MINUTE_SECONDS = 60;
	private final static Integer ONE_HOUR_SECONDS = 3600;
	
	private final static String REG_KEY = "reg:*";
	private final static String LOGIN_KEY = "login:*";
	private final static String HASH_KEY = "ret:";
	
	
	private  Jedis jedis;
	private  MongoClient client;
	private MongoDatabase mongoDb;
	private JedisPool pool;
	private String appName;
	private MongoClient client_user;
	private MongoDatabase mongoDb_user;
	
	public RetentionLogic(Jedis jedis,MongoClient client,MongoDatabase mongoDb,MongoClient client_user,MongoDatabase mongoDb_user,JedisPool pool, String appName){
		this.jedis = jedis;
		this.client = client;
		this.mongoDb = mongoDb;
		this.client_user = client_user;
		this.mongoDb_user = mongoDb_user;
		this.pool = pool;
		this.appName = appName;
	
	}

	/**
	* <p>Title: insertData</p>
	* <p>Description: </p>
	*/
	public void insertData() {
		
		Integer currentTime = 0;
		/*if (appName == "bs" || "bs".equals(appName)) {*/
			currentTime = DateUtil.getBscurrentTime();
			System.out.println("appName:"+appName+", getQuartzByDaily()  start...."+currentTime);
		/*}else if (appName == "naruto" || "naruto".equals(appName)) {
			currentTime = DateUtil.getBscurrentTime();
			System.out.println("appName:"+appName+", getNarutoQuartzByDaily()  start...."+currentTime);
		}*/
		int startTime = currentTime-ONE_DAY_SECONDS;
		Set<String> channelByKeys = DailyCheck.getChannelByKeys(jedis, appName, REG_KEY);
		for(String key:channelByKeys){
			try{
				getRententionOut(key, startTime, currentTime);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	* <p>Title: getRententionOut</p>
	* <p>Description: 计算库中保存渠道条数，大于1则存在需要更新上一天的留存数据</p>
	* @param key
	* @param startTime
	* @param currentTime
	*/
	public void getRententionOut(String key,Integer startTime,Integer currentTime){
		String channel = CharUtil.cutString(key,":");
		System.out.println("mongoDb---:"+mongoDb);
		BsparseDao bsDao = new BsparseDao(client, mongoDb);
		XcloudeyeUserDao xuDao = new XcloudeyeUserDao(client_user, mongoDb_user);
		long retNum = bsDao.chNumRetention(channel);
		if (retNum <= 0) {
			firstInsertRetention(bsDao, xuDao, channel, key, startTime, currentTime);
		}else {
			updateRetention(bsDao, xuDao, channel, key, startTime, currentTime, retNum);
		}
		
	}
	
	/**
	* <p>Title: updateRetention</p>
	* <p>Description: 当</p>
	* @param bsDao
	* @param channel
	* @param key
	* @param startTime
	* @param currentTime
	* @param num
	*/
	public void updateRetention(BsparseDao bsDao, XcloudeyeUserDao xuDao, String channel
					, String key, Integer startTime, Integer currentTime, long num){
		//先将次日留存新加如库中
		firstInsertRetention(bsDao, xuDao, channel, key, startTime, currentTime);
		
		//更新出昨日次日留存数据的其他数据
		int number = (int) num ;
		if (number > 30) {
			number = 30;
		}
		for (int i = 0; i < number; i++) {
			int udateDocTime = currentTime - (i+2)*ONE_DAY_SECONDS;
			HashMap<String, Integer> retD = new HashMap<String, Integer>();
			List<HashMap<String, Integer>> ret = new ArrayList<HashMap<String,Integer>>();
			
			System.out.println("bsDao---:"+bsDao);
			RetentionOut reOut = bsDao.queryRetention(udateDocTime, channel);
			if(reOut != null){
				ret = reOut.getRet();
				if(ret != null){
					String hashKey = HASH_KEY+(startTime);
					retD.put(hashKey, LogicUtil.getRetention(jedis, currentTime, i+2, channel, appName));
						ret.add(retD);
					try {
						bsDao.updateRetention(udateDocTime, channel, ret);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
	/**
	* <p>Title: firstInsertRetention</p>
	* <p>Description: 当库里没有当前计算渠道的数据时，需要做首次插入计算，并计算前日的留存人数</p>
	* @param bsDao
	* @param channel
	* @param key
	* @param startTime
	* @param currentTime
	*/
	public void firstInsertRetention(BsparseDao bsDao, XcloudeyeUserDao xuDao, String channel
			, String key, Integer startTime, Integer currentTime){
		int new_user = 0;
			List<HashMap<String, Integer>> ret = new ArrayList<HashMap<String,Integer>>();
			RetentionOut out = new RetentionOut();
			out.setDate(startTime);
//			System.out.println("date::::"+startTime);
			out.setChannel(channel);
			String[] arrs = GetDocmentUtil.getChannelArr(jedis, channel, appName);
			out.setParent(arrs[0]);
			out.setChild(arrs[1]);
			out.setSeq(arrs[2]);
			new_user = (int)DailyCheck.getCountByChannel(jedis, key, startTime, currentTime);
			out.setNew_user(new_user);
			out.setRet(ret);
		try {
			bsDao.insertRetentionOut(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
