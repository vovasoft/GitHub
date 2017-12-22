package com.xcloud.schedule.daily;

import java.util.List;
import java.util.Set;
import redis.clients.jedis.Jedis;




/**
 *
 */
public class DailyCheck {
    
    public static void checkAndSaveData(Jedis jedis, long start, long end) {

    }
    
    public static Set<String> getRegChannels(Jedis jedis, String appname) {
        Set<String> chList = jedis.keys(appname+"reg:*");
        return chList;
    }
    
    public static Set<String> getLoginChannels(Jedis jedis, String appname) {
        Set<String> chList = jedis.keys(appname+"login:*");
        return chList;
    }
    
    public static Set<String> getSetByChannel(Jedis jedis, String key, long start, long end) {
        return jedis.zrangeByScore(key, start, end);
    }
    
    public static long getCountByChannel(Jedis jedis, String key){
    	return jedis.zcard(key);
    }
    
    public static double getIncomeByChannel(Jedis jedis, String key, String channel){
    	if (jedis.zscore(key, channel) == null) {
			return 0;
		}
    	return jedis.zscore(key, channel);
    }
    
    public static void getNewUserByChannel(Jedis jedis, String channel, long start, long end) {
    }
    
    public static void getDauByChannel(Jedis jedis, String channel, long start, long end) {
        
    }
    
    public static void getWauByChannel(Jedis jedis, String channel, long start, long end) {
        
    }
    
    public static void getMauByChannel(Jedis jedis, String channel, long start, long end) {
        
    }
    
    public static void getPayerByChannel(Jedis jedis, String channel, long start, long end) {
        
    }
    
    public static void getNewPayerByChannel(Jedis jedis, String channel, long start, long end) {
        
    }
    public static void getAllOrderByChannel(Jedis jedis, String channel, long start, long end) {
        
    }
    
    public static void getSuccessOrderByChannel(Jedis jedis, String channel, long start, long end) {
        
    }

	public static double getMoneyByOrderId(Jedis jedis, String key,String orderid) {
        double money = 0.0;
        if(jedis.hget(key,orderid)==null){
            System.out.println("orderid:"+orderid);
            return 0d;
        }else {
            System.out.println("orderid****:"+orderid+"jedis:"+jedis.hget(key,orderid));
             money = Double.valueOf(jedis.hget(key,orderid));
            return money;
        }
	}
	public static String getUidByOrderId(Jedis jedis,String key,String orderid){
    	return jedis.hget(key, orderid);
    }
	
	public static String getChannelName(Jedis jedis,String key,String oldChName){
    	return jedis.hget(key,oldChName);
    }

    public static Set<String> getChannelByKeys(Jedis jedis, String appname, String key) {
        Set<String> chList = jedis.keys(appname+key);
        return chList;
    }
	
	public static long getCountByChannel(Jedis jedis, String key, long start, long end) {
        return jedis.zcount(key, start, end);
    }

	public static int getCountByZrange(Jedis jedis,String key){
    	return jedis.zrange(key,0,-1).size();
    }
	
	public static Set<String> getSetByZrange(Jedis jedis,String key){
    	Set<String> zrange = jedis.zrange(key, 0, -1);
    	return zrange;
    }
	
	public static Set<String> getSetByChannelTime(Jedis jedis, String key,
			Integer startTime, Integer currentTimeSecond) {
	        Set<String> zrangeByScore = jedis.zrangeByScore(key, startTime, currentTimeSecond);
	        return zrangeByScore;
	}



}
