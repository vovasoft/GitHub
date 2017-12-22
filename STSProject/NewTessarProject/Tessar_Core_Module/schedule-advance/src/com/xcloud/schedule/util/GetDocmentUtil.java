package com.xcloud.schedule.util;

import com.xcloud.schedule.appdomain.repetitiveFiveOut;
import com.xcloud.schedule.appdomain.FiveMinuteOut;
import org.bson.Document;

import redis.clients.jedis.Jedis;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.xcloud.schedule.appdomain.DailyOut;
import com.xcloud.schedule.appdomain.FifteenMinuteOut;
import com.xcloud.schedule.daily.DailyCheck;
import com.xcloud.schedule.mongodao.XcloudeyeUserDao;

public class GetDocmentUtil {
	private static final String CH_NAME = "chname";
	public static Document getDocument(MongoClient client_user, MongoDatabase mongoDb_user, String channel){
		
		XcloudeyeUserDao xuDao = null;
		Document document = null;
		try{
			xuDao = new XcloudeyeUserDao(client_user, mongoDb_user);
			document = xuDao.findChannel(channel);
		}catch(Exception e){
			e.printStackTrace();
		}
		return document;
	}
	
	public static String[] getChannelArr(Jedis jedis, String channel, String appName){
		
		String[] arrs = new String[3];
		String channelName = DailyCheck.getChannelName(jedis, appName + CH_NAME, channel);
		System.out.println("channel:" + channel + ", channelName:" + channelName);
		if("".equals(channelName) || channelName == null){
			boolean b = CharUtil.checkPattern(channel);
			if(b){
				arrs = CharUtil.splitChannel(channel,"_");
			}else{
				arrs[0] = "NA";
				arrs[1] = "NA";
				arrs[2] = "NA";
			}
		}else{
			boolean b = CharUtil.checkPattern(channelName);
			if(b){
				arrs = CharUtil.splitChannel(channelName,"_");
			}else{
				arrs[0] = "NA";
				arrs[1] = "NA";
				arrs[2] = "NA";
			}
		}
		return arrs;
	}

	public static DailyOut getDailyOut(Integer startTime,String channel, String parent, String child, String seq, Integer new_user, Integer active_user,
			Integer payer, Integer new_payer, Integer all_order,
			Integer payed_order, double sum) {
		DailyOut out = new DailyOut();
		out.setDate(startTime);
		out.setChannel(channel);
		out.setParent(parent);
		out.setChild(child);
		out.setSeq(seq);
		out.setNew_user(new_user);
		out.setActive_user(active_user);
		out.setPayer(payer);
		out.setNew_payer(new_payer);
		out.setAll_order(all_order);
		out.setPayed_order(payed_order);
		out.setIncome(sum);
		return out;
	}

	public static FifteenMinuteOut getFifteenMinuteOut(Integer endTime, String channel,
			String parent, String child, String seq, Integer new_user,
			Integer active_user, Integer payer, Integer new_payer,
			Integer all_order, Integer payed_order, double sum) {
		FifteenMinuteOut out = new FifteenMinuteOut();
		out.setTime(endTime);
		out.setChannel(channel);
		out.setParent(parent);
		out.setChild(child);
		out.setSeq(seq);
		out.setNew_user(new_user);
		out.setActive_user(active_user);
		out.setPayer(payer);
		out.setNew_payer(new_payer);
		out.setAll_order(all_order);
		out.setPayed_order(payed_order);
		out.setIncome(sum);
		return out;
	}
	public static FiveMinuteOut FiveMinuteOut(Integer endTime, String channel,String parent, String child, String seq,Integer active_user){
		FiveMinuteOut out=new FiveMinuteOut();
		out.setTime(endTime);
		out.setChannel(channel);
		out.setParent(parent);
		out.setChild(child);
		out.setSeq(seq);
		out.setActive_user(active_user);
		return out;
	}

	
	public static FiveMinuteOut FiveNewUserOut(Integer endTime, String channel,String parent, String child, String seq,Integer new_user){
		FiveMinuteOut out=new FiveMinuteOut();
		out.setTime(endTime);
		out.setChannel(channel);
		out.setParent(parent);
		out.setChild(child);
		out.setSeq(seq);
		out.setNew_user(new_user);
		return out;
	}

	public static String[] getAllChannelArr(Jedis jedis, String channel, String appName){

		String[] arrs = new String[3];
		String channelName = DailyCheck.getChannelName(jedis, appName + CH_NAME, channel);
		System.out.println("channel:" + channel + ", channelName:" + channelName);
		if("".equals(channelName) || channelName == null){
			boolean b = CharUtil.checkPattern(channel);
			if(b){
				arrs = CharUtil.splitChannel(channel,"_");
			}else{
				arrs[0] = "NA";
				arrs[1] = "NA";
				arrs[2] = "NA";
			}
		}else{
			boolean b = CharUtil.checkPattern(channelName);
			if(b){
				arrs = CharUtil.splitChannel(channelName,"_");
			}else{
				arrs[0] = "NA";
				arrs[1] = "NA";
				arrs[2] = "NA";
			}
		}
		return arrs;
	}

	
	public static repetitiveFiveOut ActiveFiveOut(Integer endTime, Integer active_user){
		repetitiveFiveOut out=new repetitiveFiveOut();
		out.setTime(endTime);
		out.setActive_user(active_user);
		return out;
	}
}
