package com.xcloudeye.stats.util;

public class StaticValueUtil {

	//三个小时的秒数
	public static final Integer THREE_HOURS_SECONDS=10800;
	/** ONE_DAY_SECONDS */
	public static final Integer ONE_DAY_SECONDS = 86400;

	//一天分成288个五分钟；
	public static final Integer FIVE_MINUTES_COUNT = 289;

	/** FIFTEEN_MINUTES_SECONDS*/
	public static final Integer FIFTEEN_MINUTES_SECONDS = 900;
	
	/*------------------------------------------------------------------*/
	
	/** ADVACE_DAILY "daily" collections的编号*/
	public static final Integer ADVANCE_DAILY = 11;
	
	/** ADVANCE_FIFTEEN "fifteen" collection 编号*/
	public static final Integer ADVANCE_FIFTEEN = 22;
	
	/** ADVANCE_RETENTION "retention" 编号*/
	public static final Integer ADVANCE_RETENTION = 33;

	/*---------------------app id---------------------------------------------*/
	public static final int BLOODSTRIK_ID = 1001;
	public static final int PLATFORM_ID = 7001;
	public static final int WON_ID = 2001;

	/*---------------------down path---------------------------------------------*/
	public static final String DOWNLOAD_PATH = "D:\\";
//	public static final String DOWNLOAD_PATH = "/tmp/stats_csv/";

	/*---------------------callback flage value---------------------------------------------*/
	public static final int SUCCESS = 200;
	public static final int VALUE_NOT_EXIST = 201;
	public static final int INSERT_FAILED = 203;

	/** BS_PAY bloodstrik的支付记录保存在redis中的  链表名*/
	public static final String BS_PAY = "bspay";
	/** WON_PAY won"神戒 "的支付记录保存在redis中的  链表名*/
	public static final String WON_PAY = "wonpay";
	/** BS_INCOME bloodstrik的每个渠道的有效付费总收入保存在redis中的  链表名*/
	public static final String BS_INCOME = "bsincome";
	/** WON_INCOME won"神戒 "的每个渠道的有效付费总收入保存在redis中的  链表名*/
	public static final String WON_INCOME = "bsincome";
}
