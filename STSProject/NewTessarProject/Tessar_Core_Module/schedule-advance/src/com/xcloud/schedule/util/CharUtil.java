package com.xcloud.schedule.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 根据给定的字符串，返回数组工具类
 *
 */
public class CharUtil {

	/**
	 * 格式：xx:xx:xx 返回分割后的最后一个字符串。
	 * @param str
	 * @param key
	 * @return
	 */
	public static String cutString(String str, String key){
     	String[]  strs = str.split(key);
     	int count = 0;
     	for(String element  :  strs){
     		count ++;
     		if (count == 2) {
     			return element;
			}
     	}
     	return str;
	}
	/**
	 * 根据传来的channel和分割字符regex，生成数组
	 * @param channel
	 * @param regex
	 * @return String[]
	 */
	public static String[] splitChannel(String channel,String regex){
			String[] split = channel.split(regex);
			return split;
	}
	
	/**
	 * 判断channel是否符合给定的正则表达式。
	 * @param channel
	 * @return
	 */
	public static boolean checkPattern(String channel){
		Pattern pattern = Pattern.compile("[0-9a-zA-Z]+_[0-9a-zA-Z]+_[0-9a-zA-Z]+");
		Matcher matcher = pattern.matcher(channel);
		boolean b= matcher.matches();
		return b;
	}
}
