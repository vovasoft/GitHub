package com.xcloudeye.stats.util;

import java.util.Date;

public class DateUtil {

	 /**
	* <p>Title: getCurrentTimeSecond</p>
	* <p>Description: 获取当前系统的UTC时间</p>
	* @return 精确到秒的UTC时间戳
	*/
	public static Integer getCurrentTimeSecond(){
		 Date date = new Date();
		 long currentMills = date.getTime(); 
		 return (int) (currentMills/1000);
	 }
	
	/**
	* <p>Title: getCurrentTimeMinute</p>
	* <p>Description: 返回精度为分的当前时间戳</p>
	* @return
	*/
	public static Integer getCurrentTimeMinute(){
		 Date date = new Date();
		 long currentMills = date.getTime(); 
		 return (int) (currentMills/1000)/60*60;
	 }
}
