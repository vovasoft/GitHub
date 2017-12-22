package com.xcloud.schedule.util;

import java.util.Date;

public class DateUtil {

	private static Integer bscurrentTime = 0;
	public static Integer getBscurrentTime() {
		return bscurrentTime;
	}
	public static void setBscurrentTime(Integer bscurrentTime) {
		DateUtil.bscurrentTime = bscurrentTime;
	}
	public static Integer getWoncurrentTime() {
		return woncurrentTime;
	}
	public static void setWoncurrentTime(Integer woncurrentTime) {
		DateUtil.woncurrentTime = woncurrentTime;
	}

	private static Integer woncurrentTime = 0;
	private static Integer bscurrentMinuteTime = 0;
	private static Integer woncurrentMinuteTime = 0;
	
	 /**
	* <p>Title: getCurrentTimeSecond</p>
	* <p>Description: 获取当前系统的UTC时间</p>
	* @return 精确到秒的UTC时间戳
	*/
	public static void setBSCurrentTimeSecond(){
		 Date date = new Date();
		 //int offset = TimeZone.getDefault().getOffset(0L);
		 long seconds = date.getTime()/1000;
		//所有计算，只关心整点的UTC时间。确保所有时间正确无误。
		 int exactTime =(int) (seconds/3600)*3600;
		 System.out.print("time--:"+exactTime+"\n");
		 setBscurrentTime(exactTime);
	}
	public static void setWonCurrentTimeSecond(){
		 Date date = new Date();
		 //int offset = TimeZone.getDefault().getOffset(0L);
		 long seconds = date.getTime()/1000;
		//所有计算，只关心整点的UTC时间。确保所有时间正确无误。
		 int exactTime =(int) (seconds/3600)*3600;
		 System.out.print("time--:"+exactTime+"\n");
		 setWoncurrentTime(exactTime);
	}
	
	public static Integer getBscurrentMinuteTime() {
		return bscurrentMinuteTime;
	}
	public static void setBscurrentMinuteTime(Integer bscurrentMinuteTime) {
		DateUtil.bscurrentMinuteTime = bscurrentMinuteTime;
	}
	
	public static Integer getWoncurrentMinuteTime() {
		return woncurrentMinuteTime;
	}
	public static void setWoncurrentMinuteTime(Integer woncurrentMinuteTime) {
		DateUtil.woncurrentMinuteTime = woncurrentMinuteTime;
	}
	public static Integer getCurrentHourTimeSecond(){
		 Date date = new Date();
		 //int offset = TimeZone.getDefault().getOffset(0L);
		 long seconds = date.getTime()/1000;
		 int exactTime = (int) ((seconds/3600)*3600);
		 System.out.print("currentSecond--:"+exactTime+"\n");
		 return (int) (exactTime);
	 }
	
	public static Integer getNowTime(){
		Date date = new Date();
		int seconds = (int)(date.getTime()/1000);
		return seconds;
	}
	
	/**
	* <p>Title: getBSCurrentTimeMinute</p>
	* <p>Description: 获取当前时间,精确到整分</p>
	*/
	public static void setBSCurrentTimeMinuteSecond(){
		 Date date = new Date();
		 //int offset = TimeZone.getDefault().getOffset(0L);
		 long seconds = date.getTime()/1000;
		//所有计算，只关心整点的UTC时间。确保所有时间正确无误。
		 int exactTime =(int) (seconds/60)*60;
		 System.out.print("time minute--:"+exactTime+"\n");
		 setBscurrentMinuteTime(exactTime);
	 }

	/**
	* <p>Title: getWonCurrentTimeMinute</p>
	* <p>Description: 获取当前时间,精确到整分</p>
	*/
	public static void setWonCurrentTimeMinuteSecond(){
		 Date date = new Date();
		 //int offset = TimeZone.getDefault().getOffset(0L);
		 long seconds = date.getTime()/1000;
		//所有计算，只关心整点的UTC时间。确保所有时间正确无误。
		 int exactTime =(int) (seconds/60)*60;
		 System.out.print("time minute--:"+exactTime+"\n");
		 setWoncurrentMinuteTime(exactTime);
	 }
}

	
