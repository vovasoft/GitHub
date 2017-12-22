package com.xcloudeye.stats.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {
	private static final int FIRST_DAY = 1430438400;
	
	public static String intToDate(int time) throws ParseException{
		long time2 = ((long)time)*1000;
		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd"); 
		String d = format.format(time2);  
//		Date date=format.parse(d);
//		System.out.println(time + "time to date: "+d +"\n");
		return d;
	}
	
	
	public static boolean isValidDate(int time){
		if (time >= FIRST_DAY) {
			return true;
		}
		return false;
	}
	
	
	public static int dateToInt(String time) throws ParseException{
		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");   
	    Date date = format.parse(time);  
	    long utcTime = date.UTC(date.getYear(), date.getMonth(), date.getDay(), date.getHours(), date.getMinutes(), date.getSeconds());
	    
	    System.out.print("utcTime:"+utcTime+"\n");
	    int cDate = (int) (date.getTime()/1000 - date.getTimezoneOffset()*60);
	    System.out.print("Format To times:"+ cDate +"\n");
	    return cDate;
	} 
	
	public static int getCurrentDate(){
	    Date date = new Date(); 
	    System.out.print("Format To times:"+date+"\n");
	    System.out.print("Format To times:"+date.getTime()/1000 +"\n");
	    return (int)(date.getTime()/1000);
	}
	
	public static int getCurrentDateDay() throws ParseException {
		Date date = new Date();
        DateFormat df1 = DateFormat.getDateInstance();//日期格式，精确到日
        System.out.println(df1.format(date));
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
	    Date date2 = format.parse(df1.format(date)+" 00:00:00");
	    System.out.print("getCurrentDateDay :"+date2+"\n");
	    System.out.print("getCurrentDateDay :"+ date2.getTime() +"\n");
	    return (int)(date2.getTime());
	}

	public static String getCurrentDateByFormat(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date());
	}
}
