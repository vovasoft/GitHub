package com.xcloud.schedule.util;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;

public class RecordLogUtil {

	public static void writeErrorLog(String channel){
		File file = new File("errors.log");
			try {
				if(!file.exists()){
				file.createNewFile();
				file.setWritable(true, false);
				}
				FileWriter fw = new FileWriter(file,true);
				int seconds = DateUtil.getNowTime();
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
				long timestamp = (long)seconds*1000;
				String time = format.format(timestamp);
				fw.write(time + " | "+ seconds + " | " + channel + "\n");
				fw.flush();
				fw.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
	}
	
}
