package com.xcloudeye.stats.util;

import java.util.Date;

/**
* <p>Title: UidUtil</p>
* <p>Description: 生成唯一的uid</p>
* <p>Company: LTGames</p> 
* @author xjoker
* @date 2015年7月15日
*/
public class UidUtil {

	private static Date date = new Date();
	private static StringBuilder buf = new StringBuilder();
	private static int seq = 0;
	private static final int ROTATION = 9999;
	private static final int CH_ROTATION = 99;
	
	public static synchronized long next(){
		if (seq > ROTATION) 
			seq = 0;
		buf.delete(0, buf.length());
		date.setTime(System.currentTimeMillis());
		String str = String.format("%1$tY%1$tm%1$td%1$tk%1$tM%2$04d", date, seq++);
		return Long.parseLong(str);
	}
	
	public static synchronized long chIDnext(){
		if (seq > ROTATION) 
			seq = 0;
		buf.delete(0, buf.length());
		date.setTime(System.currentTimeMillis());
		String str = String.format("%1$tY%1$tm%1$td%1$tk%1$tM%2$03d", date, seq++);
//		String str = System.currentTimeMillis()+""+seq++;
		return Long.parseLong(str);
	}
}
