package com.xcloud.schedule.logic;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.xcloud.schedule.appdomain.AppTrendGeneric;
import com.xcloud.schedule.mongodao.BsparseDao;
import com.xcloud.schedule.util.DateUtil;
import com.xcloud.schedule.util.LogicUtil;


/**
 * app_trend_generic collection中数据的计算类，
 * 包含了7天活跃用户、30天活跃用户、总付费人数、总收入等数据信息，
 * 数据一天计算一次。
 *
 */
public class AppTrendGenericLogic {
	private String API = "app_trend";
	private final static Integer ONE_DAY_SECONDS = 86400;
	private  Jedis jedis;
	private  MongoClient client;
	private MongoDatabase mongoDb;
	private JedisPool pool;
	private String appName;
	
	public AppTrendGenericLogic(Jedis jedis,MongoClient client,MongoDatabase mongoDb,JedisPool pool, String appName){
		this.jedis = jedis;
		this.client = client;
		this.mongoDb = mongoDb;
		this.pool = pool;
		this.appName = appName;
	}
	public void insertData(){
		try{
		Jedis ojedis = new Jedis("127.0.0.1",6379);
		ojedis.auth("Xcloud2015$!");
		String chUid = null;
		Integer currentTimeSecond = 0;
		if (appName == "bs" || "bs".equals(appName)) {
			currentTimeSecond = DateUtil.getBscurrentTime();
			chUid = new String("channel");
		}else if (appName == "naruto" || "naruto".equals(appName)) {
			currentTimeSecond = DateUtil.getBscurrentTime();
			chUid = new String("narutochannel");
		}else if(appName=="herocraft" || "herocraft".equals(appName)){
			currentTimeSecond = DateUtil.getBscurrentTime();
			chUid = new String("herocraftchannel");
		}else if(appName=="angels" ||"angels".equals(appName)){
			currentTimeSecond = DateUtil.getBscurrentTime();
			chUid = new String("angelschannel");
		}
		else {
			//默认计算bloodstrike的游戏数据
			chUid = new String("channel");
		}
		long hlen = ojedis.hlen(chUid);
		ojedis.close();
		int sstartTime = currentTimeSecond-7*ONE_DAY_SECONDS;
		//7天活跃用户
		int sactiveUsers = LogicUtil.getActiveUsers(jedis,sstartTime,currentTimeSecond, appName);
		int tstartTime = currentTimeSecond-30*ONE_DAY_SECONDS;
		//30天活跃用户
		int tactiveUsers = LogicUtil.getActiveUsers(jedis,tstartTime,currentTimeSecond, appName);
		//总付费人数
		int payers = LogicUtil.getPayers(jedis, appName);
		//总收入
		double income = LogicUtil.getIncome(jedis, appName);
		//arpu
		float arpu = 0.0f;
		float arppu = 0.0f;
		try{
			if(hlen != 0){
				arpu = (float) (income/hlen);
			}
			if(payers != 0){
				arppu = (float) (income/payers);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		AppTrendGeneric atGeneric = new AppTrendGeneric(API, (int)hlen, sactiveUsers, tactiveUsers, payers, income, arpu, arppu);
		BsparseDao bsDao = new BsparseDao(client, mongoDb);
		bsDao.upsertAtg(atGeneric);
		System.out.println(appName + " app_trend_generic insert successfully...");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
