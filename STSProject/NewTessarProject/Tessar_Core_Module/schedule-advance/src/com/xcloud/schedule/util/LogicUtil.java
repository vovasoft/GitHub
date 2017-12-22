package com.xcloud.schedule.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.Jedis;

import com.xcloud.schedule.daily.DailyCheck;
import com.xcloud.schedule.hourly.HourlyCheck;

public class LogicUtil {
	
	private final static String REG_KEY = "reg:*";
	private final static String LOGIN_KEY = "login:*";
	private final static String NEW_PAYER_KEY = "newpayer:*";
	private final static String PAYCH_KEY = "paych:*";
	private final static String ORDER = "order";
	private final static String COLON = ":";
	private final static String ALL_ORDER_KEY = "allorder:*";
	private static HashMap<String ,Integer> newUsersMap = new HashMap<String, Integer>();
	private static HashMap<String ,Integer> dauMap = new HashMap<String, Integer>();
	
	private static final int ONE_DAY_SECONDS = 86400;
	
	public static HashMap<String, Integer> getNewUsersMap() {
		return newUsersMap;
	}
	
	public static HashMap<String, Integer> getDauMap() {
		return dauMap;
	}

	public static void setDauMap(HashMap<String, Integer> dauMap) {
		LogicUtil.dauMap = dauMap;
	}

	public static void setNewUsersMap(HashMap<String, Integer> newUsersMap) {
		LogicUtil.newUsersMap = newUsersMap;
	}
	public static Integer bsTimezoneOffset;
	public static Integer getBsTimezoneOffset() {
		return bsTimezoneOffset;
	}

	public static void setBsTimezoneOffset(Integer bsTimezoneOffset) {
		LogicUtil.bsTimezoneOffset = bsTimezoneOffset;
	}

	public static Integer getWonTimezoneOffset() {
		return wonTimezoneOffset;
	}

	public static void setWonTimezoneOffset(Integer wonTimezoneOffset) {
		LogicUtil.wonTimezoneOffset = wonTimezoneOffset;
	}
	public static Integer wonTimezoneOffset;

	public static Integer narutoTimezoneOffset;

	public static Integer getNarutoTimezoneOffset() {
		return narutoTimezoneOffset;
	}

	public static void setNarutoTimezoneOffset(Integer narutoTimezoneOffset) {
		LogicUtil.narutoTimezoneOffset = narutoTimezoneOffset;
	}

	/**
	 * 获得新注册用户人数
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int getNewUsers(Jedis jedis,int startTime,int endTime, String appName, String key){
		int count = 0;
		Set<String> regChannels = HourlyCheck.getChannelByKeys(jedis, appName,REG_KEY);
		if(regChannels == null || regChannels.size() == 0){
			return count;
		}
		count = (int) HourlyCheck.getCountByChannel(jedis, key, startTime,endTime);
		return count;
	}
	
	/**
	 * 获得活跃用户人数
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int getActiveUsers(Jedis jedis,int startTime,int endTime, String appName, String key){
		int count = 0;
		Set<String> loginChannels = HourlyCheck.getChannelByKeys(jedis, appName, LOGIN_KEY);
		if(loginChannels == null || loginChannels.size() == 0){
			return count;
		}
		//获得活跃用户人数
		count = (int) HourlyCheck.getCountByChannel(jedis, key, startTime, endTime);
		return count;
	}
	
	/**
	 * 获得所有的付费用户
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int getPayers(Jedis jedis, String appName){
		
		int sum = 0;
		Set<String> channelByKeys = HourlyCheck.getChannelByKeys(jedis, appName, PAYCH_KEY);
		if(channelByKeys == null || channelByKeys.size() == 0){
			return sum;
		}
		Set<String> uidSet = new HashSet<String>();
		for(String key:channelByKeys){
			Set<String> orders = DailyCheck.getSetByZrange(jedis, key);
			for(String orderid:orders){
				String uid = DailyCheck.getUidByOrderId(jedis, appName + ORDER, orderid);
				uidSet.add(uid);
			}
		}
		return uidSet.size();
	}
	
	/**
	 * 获得所有的付费金额
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static double getIncome(Jedis jedis, String appName){
		double money = 0.0;
		double sum = 0.0;
		
		Set<String> payChChannels = HourlyCheck.getChannelByKeys(jedis, appName, PAYCH_KEY);
		if(payChChannels == null || payChChannels.size() == 0){
			return sum;
		}
		int appid = 0;
		if (appName == "bs" || appName.equals("bs")) {
			appid = 2;
		}else if (appName == "won" || appName.equals("won")) {
			appid = 5;
		}
		for(String key:payChChannels){
			//System.out.println("key:"+key+",startTime:"+startTime+",endTime:"+endTime);
			money = HourlyCheck.getPayMoneyByChannel(jedis, key, appid);
			//System.out.println("money:"+count);
			sum += money;
		}
		return sum;
	}
	
	/**
	 * 获得所有订单
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int getAllOrders(Jedis jedis,int startTime,int endTime, String appName){
		int count = 0;
		int sum = 0;
		Set<String> allOrdersChannels = HourlyCheck.getChannelByKeys(jedis, appName, ALL_ORDER_KEY);
		if(allOrdersChannels == null || allOrdersChannels.size() == 0){
			return sum;
		}
		for(String key:allOrdersChannels){
			//System.out.println("key:"+key+",startTime:"+startTime+",endTime:"+endTime);
			count = (int) HourlyCheck.getCountByChannel(jedis, key, startTime, endTime);
			//System.out.println("count:"+count);
			sum += count;
		}
		return sum;
	}
	
	/**
	 * 获得所有有效订单
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int getSuccessOrders(Jedis jedis,int startTime,int endTime, String appName){
		int count = 0;
		int sum = 0;
		Set<String> payChChannels = HourlyCheck.getChannelByKeys(jedis, appName, PAYCH_KEY);
		if(payChChannels == null || payChChannels.size() == 0){
			return sum;
		}
		for(String key:payChChannels){
			//System.out.println("key:"+key+",startTime:"+startTime+",endTime:"+endTime);
			count = (int) HourlyCheck.getCountByChannel(jedis, key, startTime, endTime);
			//System.out.println("count:"+count);
			sum += count;
		}
		return sum;
	}
	
	/**
	 * 获得所有的新增付费用户
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int getNewPayers(Jedis jedis,int startTime,int endTime, String appName){
		int count = 0;
		int sum = 0;
		Set<String> payChChannels = HourlyCheck.getChannelByKeys(jedis, appName, NEW_PAYER_KEY);
		if(payChChannels == null || payChChannels.size() == 0){
			return sum;
		}
		for(String key:payChChannels){
			//System.out.println("key:"+key+",startTime:"+startTime+",endTime:"+endTime);
			count = (int) HourlyCheck.getCountByChannel(jedis, key, startTime, endTime);
			//System.out.println("count:"+count);
			sum += count;
		}
		return sum;
	}
	
	
	/**
	* <p>Title: getRetention</p>
	* <p>Description: 根据传入的当前整点时间，和天数，计算指定天数前的留存</p>
	* @param jedis redis客户端对象
	* @param currentTime 当前系统的整点石楠
	* @param days 指定多少天的留存数据
	* @param channels 渠道号
	* @return 返回对留存数据值
	*/
	public static Integer getRetention(Jedis jedis, Integer currentTime, int days, String channels, String appName){
		String loginKey = appName+"login:"+CharUtil.cutString(channels, COLON);
		String regKey = appName+"reg:"+CharUtil.cutString(channels, COLON);
		//System.out.println("channels---:"+CharUtil.cutString(channels, ":"));
		Integer loginStart = currentTime - ONE_DAY_SECONDS;
		Integer regStart = currentTime - (days)*ONE_DAY_SECONDS;
		Integer regEnd = currentTime - (days-1)*ONE_DAY_SECONDS;
		Set<String> retention = new HashSet<String>();
		Set<String> reg = DailyCheck.getSetByChannel(jedis, regKey, regStart, regEnd);
		//System.out.println("regcount---:"+reg.size());
		if (reg == null || reg.size() == 0) {
			return 0;
		}else {
			Set<String> login = DailyCheck.getSetByChannel(jedis, loginKey, loginStart, currentTime);
			//System.out.println("login count---:"+login.size());
			if (login == null || login.size() == 0) {
				return 0;
			}else {
				retention.clear();
				retention.addAll(login);
				retention.retainAll(reg);
			}
			//System.out.println("count---:"+retention.size());
			//System.out.println("retention: "+(float)retention.size() / reg.size() +"\n");
			return retention.size();
		}
	}

	public static int getActiveUsers(Jedis jedis, int sstartTime,
			Integer currentTimeSecond, String appName) {
		int count = 0;
		int sum = 0;
		Set<String> loginChannels = HourlyCheck.getChannelByKeys(jedis, appName, LOGIN_KEY);
		if(loginChannels == null || loginChannels.size() == 0){
			return sum;
		}
		//获得活跃用户人数
		for (String key : loginChannels) {
			//System.out.println("endTime:"+endTime);
			//System.out.println("key:"+key+",startTime:"+startTime+",endTime:"+endTime);
			count = (int) HourlyCheck.getCountByChannel(jedis, key, sstartTime, currentTimeSecond);
			//System.out.println("count:"+count);
			sum += count;
		}
		return sum;
	}
		
}
