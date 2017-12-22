package com.xcloud.schedule.logic;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.xcloud.schedule.appdomain.PayHobbyGeneric;
import com.xcloud.schedule.daily.DailyCheck;
import com.xcloud.schedule.mongodao.BsparseDao;
import com.xcloud.schedule.util.CharUtil;
import com.xcloud.schedule.util.DateUtil;


/**
 *
 *
 */
public class PayHobbyGenericLogic {
	
	private final static Integer ONE_DAY_SECONDS = 86400;
	private String API = "pay_hobby";
	private String PAY_TYPE = "paytype:*";
	private String ALL = "all";
	private String MONEY = "money";
	private String ORDER = "order";
	private String COLON = ":";
	private Jedis jedis;
	private MongoClient client;
	private MongoDatabase mongoDb;
	private JedisPool pool;
	private String appName;
	
	public PayHobbyGenericLogic(Jedis jedis,MongoClient client,MongoDatabase mongoDb,
		  JedisPool pool, String appName){
		this.jedis = jedis;
		this.client = client;
		this.mongoDb = mongoDb;
		this.pool = pool;
		this.appName = appName;
	}
	public void insertData() {
		try{
			Integer currentTime = 0;
			/*if (appName == "bs" || "bs".equals(appName)) {*/
				currentTime = DateUtil.getBscurrentTime();
				System.out.println("appName:"+appName+", getQuartzByDaily()  start...."+currentTime);
			/*}else if (appName == "naruto" || "naruto".equals(appName)) {
				currentTime = DateUtil.getBscurrentTime();
				System.out.println("appName:"+appName+", getNarutoQuartzByDaily()  start...."+currentTime);
			}*/
			Integer all_payer = 0;
			Integer all_order = 0;
			double all_income = 0d;
			Set<String> payTypes = DailyCheck.getChannelByKeys(jedis, appName, PAY_TYPE);
			BsparseDao bsDao = new BsparseDao(client, mongoDb);
			bsDao.drophGenericCollection();
			for(String payType:payTypes){
				PayHobbyGeneric phGeneric = getPayHobbyGeneric(payType, currentTime);
				if(phGeneric != null){
					all_payer += phGeneric.getPayer();
					all_order += phGeneric.getOrder();
					all_income += phGeneric.getIncome();
					bsDao.insertphGeneric(phGeneric);
				}
			}
			PayHobbyGeneric phGeneric = new PayHobbyGeneric(API, currentTime, ALL, all_payer, all_order, all_income);
			bsDao.insertphGeneric(phGeneric);
	}catch(Exception e){
		e.printStackTrace();
	}
	}
	
	public PayHobbyGeneric getPayHobbyGeneric(String payType, Integer currentTime){
		PayHobbyGeneric phGeneric = null;
		try{
		double income = 0d;
		String type = CharUtil.splitChannel(payType, COLON)[1];
		Set<String> orders = DailyCheck.getSetByZrange(jedis, payType);
		for(String order:orders){
			income += DailyCheck.getMoneyByOrderId(jedis, appName + MONEY, order);
		}
		Set<String> uidSet = new HashSet<String>();
		for(String orderid:orders){
			String uid = DailyCheck.getUidByOrderId(jedis, appName + ORDER, orderid);
			uidSet.add(uid);
		}
		phGeneric = new PayHobbyGeneric(API, currentTime, type, uidSet.size(), orders.size(), income);
	  }catch(Exception e){
		  e.printStackTrace();
	  }
		return phGeneric;
	}

}
