package com.xcloud.schedule.hourly;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

import javax.management.Query;

/*
 * HourlyCheck
 * 
 * version 0.1
 * 每15分钟处理Redis服务器中的数据。主要用于应用的实时显示。
 * 
 * 注意：redis中缺少‘所有订单’的信息。
 * 
 */

public class HourlyCheck {
    private static String BS_MONEY = "bsmoney";
    private static String WON_MONEY = "wonmoney";
    private static final int BS_ID = 2;
    private static final int WON_ID = 5;
    private static String app = new String("bs");
    
    public static void checkAndSaveData(Jedis jedis, long start, long end) {
    	

    }
    
    public static Set<String> getChannelByKeys(Jedis jedis, String appname, String keys) {
        app = appname;
        Set<String> chList = jedis.keys(appname + keys);
        return chList;
    }


    public static Set<String> getChannelByKTime(Jedis jedis, String appname, String key,long start,long end) {
        app = appname;
        Set<String> zrangeByScore = jedis.zrangeByScore(app + key, start, end);
        return zrangeByScore;
    }
    public static int getCountByZrange(Jedis jedis,String appname,String key){
    	app = appname;
    	return jedis.zrange(appname+key,0,-1).size();
    }
    public static Set<String> getSetByZrange(Jedis jedis,String key){
    	return jedis.zrange(key, 0, -1);
    }
    public static long getCountByChannel(Jedis jedis, String key, long start, long end) {
        return jedis.zcount(key, start, end);
    }
    
    public static double getPayMoneyByChannel(Jedis jedis,String key, int appid) {
        //获取所有订单，将订单的金额累加
        Set<String> orderList = jedis.zrange(key, 0, -1);
        double money = 0.0;
        switch (appid) {
		case BS_ID:
			for(String id : orderList) {
                if(jedis.hget("bsmoney", id)!=null) {
                    money = money + Double.valueOf(jedis.hget("bsmoney", id));
                }else{
                    System.out.println("id==null"+id);
                }
	        }
			break;
		case WON_ID:
			for(String id : orderList) {
	            money = money + Double.valueOf(jedis.hget("wonmoney", id));
	        }
			break;
		default:
			break;
		}
        
        return money;
    }

	public static Set<String> getSetByChannelTime(Jedis jedis, String key,
			int startTime, Integer currentTimeSecond) {
	        Set<String> zrangeByScore = jedis.zrangeByScore(key, startTime, currentTimeSecond);
	        return zrangeByScore;
	}

}
