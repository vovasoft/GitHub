package com.xcloudeye.stats;

import java.text.ParseException;
import java.util.*;

import com.xcloudeye.stats.domain.app.*;
import com.xcloudeye.stats.logic.*;
import com.xcloudeye.stats.util.StaticValueUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.model.JSONValue;
import com.xcloudeye.stats.util.DateFormatUtil;
import com.xcloudeye.stats.util.DateUtil;

import javax.servlet.http.HttpSession;

/**
 * App Controller,contain all interface of all application.
 * The default root url is "http://ip/stats/app"
 */

public class AppController {

    AppRebuildLogic appRebuildLogic = new AppRebuildLogic();
    AppSourceDbLogic appSourceDbLogic = new AppSourceDbLogic();
    RedisLogic redisLogic = new RedisLogic();
    PaymentLogic paymentLogic = new PaymentLogic();
    private static final String DATE_ERROR = "Date is invalid!";
    private static Set<String> Set = new HashSet<String>();


    private class ErrorMessage {
        private String error;

        public ErrorMessage(String error) {
            this.error = error;
        }

    }

    /**
     * The url is "app/app_rt"
     * According to the inputRt.getApp() and inputRt.getChannel()
     * choose output which app's app_rt data.
     *
     * @throws MapperException
     */
    public String AppRt(Map<String, Object> map) throws MapperException {

        int appid = Integer.valueOf(map.get("app").toString());
        int cDate = Integer.valueOf(map.get("date").toString());
        StringBuilder result = new StringBuilder();
        appRebuildLogic.initLogic(appid);
        AppRt geData = appRebuildLogic.getAppRt(cDate);
        geData.setApp(map.get("app").toString());
        //appRebuildLogic.getAdvanceDao().closeMongoClient();
        JSONValue jsonValue = JSONMapper.toJSON(geData);
        String jsonStr = jsonValue.render(false);
        System.out.println("jsonStr----:" + jsonStr);
        result.append("apiStatus").append('(').append(jsonStr).append(')');
        return result.toString();
    }

    /**
     * The url is "app/app_trend"
     * According to the inputTrend.getApp() and inputTrend.getChannel()
     * choose output which app's app_trend data.
     *
     * @throws MapperException
     */
    //@RequestMapping(value = "/app_trend",method = RequestMethod.GET)
    public String AppTrend(Map<String, Object> map) throws MapperException {
        System.out.println("app--:");

        int startTime = Integer.valueOf(map.get("start").toString());
        int endTime = Integer.valueOf(map.get("end").toString());
        int appid = Integer.valueOf(map.get("app").toString());

        StringBuilder result = new StringBuilder();
        JSONValue jsonValue;
        if (DateFormatUtil.isValidDate(startTime) &&
                DateFormatUtil.isValidDate(endTime)) {
            appRebuildLogic.initLogic(appid);
            AppTrend geData = appRebuildLogic.getAppTrend(startTime, endTime);
            geData.setApp(map.get("app").toString());
//			appRebuildLogic.getAdvanceDao().closeMongoClient();
            jsonValue = JSONMapper.toJSON(geData);
        } else {
            jsonValue = JSONMapper.toJSON(new ErrorMessage(DATE_ERROR));
        }
        String jsonStr = jsonValue.render(true);
        result.append("apiStatus").append('(').append(jsonStr).append(')');
        return result.toString();
    }


    /**
     * The url is "app/user_new"
     * According to the inputTrend.getApp() and inputTrend.getChannel()
     * choose output which app's user_new data.
     * the style of request is same with app_trend
     *
     * @throws MapperException
     */
    //@RequestMapping(value = "/user_new",method = RequestMethod.GET)
    public String AppUserNew(Map<String, Object> map) throws MapperException {

        int startTime = Integer.valueOf(map.get("start").toString());
        int endTime = Integer.valueOf(map.get("end").toString());
        int appid = Integer.valueOf(map.get("app").toString());

        StringBuilder result = new StringBuilder();
        JSONValue jsonValue;
        if (DateFormatUtil.isValidDate(startTime) &&
                DateFormatUtil.isValidDate(endTime)) {
            appRebuildLogic.initLogic(appid);
            UserNew geData = appRebuildLogic.getUserNew(startTime, endTime);
            geData.setApp(map.get("app").toString());
//			appRebuildLogic.getAdvanceDao().closeMongoClient();
            jsonValue = JSONMapper.toJSON(geData);
        } else {
            jsonValue = JSONMapper.toJSON(new ErrorMessage(DATE_ERROR));
        }
        String jsonStr = jsonValue.render(true);
        result.append("apiStatus").append('(').append(jsonStr).append(')');
        return result.toString();
    }


    /**
     * The url is "app/user_new"
     * According to the inputTrend.getApp() and inputTrend.getChannel()
     * choose output which app's user_new data.
     * the style of request is same with app_trend
     *
     * @throws MapperException
     */
    //@RequestMapping(value = "/user_pay",method = RequestMethod.GET)
    public String AppUserPay(Map<String, Object> map) throws MapperException {

        int startTime = Integer.valueOf(map.get("start").toString());
        int endTime = Integer.valueOf(map.get("end").toString());
        int appid = Integer.valueOf(map.get("app").toString());

        StringBuilder result = new StringBuilder();
        JSONValue jsonValue;
        if (DateFormatUtil.isValidDate(startTime) &&
                DateFormatUtil.isValidDate(endTime)) {
            appRebuildLogic.initLogic(appid);
            UserPay geData = appRebuildLogic.getUserPay(startTime, endTime);
            geData.setApp(map.get("app").toString());
//			appRebuildLogic.getAdvanceDao().closeMongoClient();
            jsonValue = JSONMapper.toJSON(geData);
        } else {
            jsonValue = JSONMapper.toJSON(new ErrorMessage(DATE_ERROR));
        }
        String jsonStr = jsonValue.render(true);
        result.append("apiStatus").append('(').append(jsonStr).append(')');
        return result.toString();
    }


    /**
     * The url is "app/user_active"
     * According to the inputTrend.getApp() and inputTrend.getChannel()
     * choose output which app's user_active data.
     * the style of input request is same with app_trend,
     * so use class "AppTrendInput"
     *
     * @throws MapperException
     * @throws ParseException
     */
    //@RequestMapping(value = "/user_active",method = RequestMethod.GET)
    public String AppUserActive(Map<String, Object> map) throws MapperException, ParseException {
        int startTime = Integer.valueOf(map.get("start").toString());
        int endTime = Integer.valueOf(map.get("end").toString());
        int appid = Integer.valueOf(map.get("app").toString());

        StringBuilder result = new StringBuilder();
        JSONValue jsonValue;
        if (DateFormatUtil.isValidDate(startTime) &&
                DateFormatUtil.isValidDate(endTime)) {
            appRebuildLogic.initLogic(appid);
            UserActive geData = appRebuildLogic.getUserActive(startTime, endTime);
            geData.setApp(map.get("app").toString());
//			appRebuildLogic.getAdvanceDao().closeMongoClient();
            jsonValue = JSONMapper.toJSON(geData);
        } else {
            jsonValue = JSONMapper.toJSON(new ErrorMessage(DATE_ERROR));
        }
        String jsonStr = jsonValue.render(true);
        result.append("apiStatus").append('(').append(jsonStr).append(')');
        return result.toString();
    }


    /**
     * The url is "app/user_retention"
     * According to the inputTrend.getApp() and inputTrend.getChannel()
     * choose output which app's user_active data.
     * the style of input request is same with user_retention,
     * so use class "AppTrendInput"
     * ---after rebuild get data from advace
     *
     * @throws MapperException
     */
    //@RequestMapping(value = "/user_retention",method = RequestMethod.GET)
    public String AppUserRetention(Map<String, Object> map) throws MapperException {
        int startTime = Integer.valueOf(map.get("start").toString());
        int endTime = Integer.valueOf(map.get("end").toString());
        int appid = Integer.valueOf(map.get("app").toString());

        StringBuilder result = new StringBuilder();
        JSONValue jsonValue;
        if (DateFormatUtil.isValidDate(startTime) &&
                DateFormatUtil.isValidDate(endTime)) {
            appRebuildLogic.initLogic(appid);
            UserRetention geData = appRebuildLogic.getUserRetention(startTime, endTime);
//			appRebuildLogic.getAdvanceDao().closeMongoClient();
            jsonValue = JSONMapper.toJSON(geData);
        } else {
            jsonValue = JSONMapper.toJSON(new ErrorMessage(DATE_ERROR));
        }
        String jsonStr = jsonValue.render(true);
        result.append("apiStatus").append('(').append(jsonStr).append(')');
        return result.toString();
    }


    /**
     * <p>Title: retQuery</p>
     * <p>Description: 根据前端输入时间计算时间范围内的留存信息</p>
     * The id of app.
     *
     * @return
     * @throws MapperException
     */
    //@RequestMapping(value = "/ret_query", method = RequestMethod.GET)
    public String retQuery(Map<String, Object> map) throws MapperException {
        int startDate = Integer.valueOf(map.get("start").toString());
        int endDate = Integer.valueOf(map.get("end").toString());
        int currentDate = DateUtil.getCurrentTimeSecond() / 86400 * 86400;

        int appid = Integer.valueOf(map.get("app").toString());

        StringBuilder result = new StringBuilder();
        JSONValue jsonValue;
        if (DateFormatUtil.isValidDate(startDate) &&
                DateFormatUtil.isValidDate(endDate)) {
            appSourceDbLogic.initLogic(appid);
            RetQuery geData = appSourceDbLogic.getRetQuery(startDate, endDate, currentDate);

//			appSourceDbLogic.getSourceDbDao().closeMongoClient();
            jsonValue = JSONMapper.toJSON(geData);
        } else {
            jsonValue = JSONMapper.toJSON(new ErrorMessage(DATE_ERROR));
        }
        String jsonStr = jsonValue.render(true);
        result.append("apiStatus").append('(').append(jsonStr).append(')');
        return result.toString();
    }


    /**
     * The url is "app/ch_detail"
     * According to the inputTrend.getApp() and inputTrend.getChannel()
     * choose output which app's ch_detail data.
     * the style of input request is same with ch_detail,
     * so use class "AppTrendInput"
     *
     * @throws MapperException
     */
    //@RequestMapping(value = "/ch_detail",method = RequestMethod.GET)
    public String AppChDetail(Map<String, Object> map) throws MapperException {
        int iDate = Integer.valueOf(map.get("date").toString());
        int appid = Integer.valueOf(map.get("app").toString());
        StringBuilder result = new StringBuilder();
        appRebuildLogic.initLogic(appid);
        ChannelDetail geData = appRebuildLogic.getChDetail(iDate);
        geData.setApp(map.get("app").toString());
//		appRebuildLogic.getAdvanceDao().closeMongoClient();
        JSONValue jsonValue = JSONMapper.toJSON(geData);
        String jsonStr = jsonValue.render(false);
        System.out.println("jsonStr----:" + jsonStr);
        result.append("apiStatus").append('(').append(jsonStr).append(')');

        return result.toString();
    }


    /**
     * The url is "app/ch_list"
     * According to the inputTrend.getApp() and inputTrend.getChannel()
     * choose output which app's ch_list data.
     * the style of input request is same with ch_list,
     * so use class "AppTrendInput"
     *
     * @throws MapperException
     */
    //@RequestMapping(value = "/ch_list",method = RequestMethod.GET)
    public String AppChList(Map<String, Object> map) throws MapperException {
        int startTime = Integer.valueOf(map.get("start").toString());
        int endTime = Integer.valueOf(map.get("end").toString());
        int appid = Integer.valueOf(map.get("app").toString());

        StringBuilder result = new StringBuilder();
        JSONValue jsonValue;
        if (DateFormatUtil.isValidDate(startTime) &&
                DateFormatUtil.isValidDate(endTime)) {
            appRebuildLogic.initLogic(appid);
            ChannelList geData = appRebuildLogic.getChList(startTime, endTime);
            geData.setApp(map.get("app").toString());
//			appRebuildLogic.getAdvanceDao().closeMongoClient();
            jsonValue = JSONMapper.toJSON(geData);
        } else {
            jsonValue = JSONMapper.toJSON(new ErrorMessage(DATE_ERROR));
        }
        String jsonStr = jsonValue.render(true);
        result.append("apiStatus").append('(').append(jsonStr).append(')');
        return result.toString();
    }


    /**
     * The url is "app/ch_track"
     * According to the inputTrend.getApp() and inputTrend.getChannel()
     * choose output which app's ch_track data.
     * the style of input request is same with ch_track,
     * so use class "AppTrendInput"
     *
     * @throws MapperException
     */
    //@RequestMapping(value = "/ch_track",method = RequestMethod.GET)
    public String AppChTrack(Map<String, Object> map) throws MapperException {
        int appid = Integer.valueOf(map.get("app").toString());
        int startTime = Integer.valueOf(map.get("start").toString());
        int endTime = Integer.valueOf(map.get("end").toString());
        StringBuilder result = new StringBuilder();
        JSONValue jsonValue;
//		appSourceDbLogic.initLogic(appid);
//		ChannelTrack geData = appSourceDbLogic.getChTrack(startTime, endTime);
//		geData.setApp(app);
        /*appSourceDbLogic.getSourceDbDao().closeMongoClient();
		appSourceDbLogic.getAdvanceDao().closeMongoClient();*/

        SourceDriverLogic sourceDriverLogic = new SourceDriverLogic(appid);
        ChannelTrack geData = sourceDriverLogic.getChTrack(startTime, endTime);
        jsonValue = JSONMapper.toJSON(geData);
        String jsonStr = jsonValue.render(true);
        result.append("apiStatus").append('(').append(jsonStr).append(')');
        return result.toString();
    }


    /**
     * The url is "app/pay_sort"
     * According to the inputTrend.getApp() and inputTrend.getChannel()
     * choose output which app's pay_sort data.
     * the style of input request is same with pay_sort,
     * so use class "AppTrendInput"
     *
     * @throws MapperException
     */
    //@RequestMapping(value = "/pay_sort",method = RequestMethod.GET)
    public String AppPaySort(Map<String, Object> map) throws MapperException {
        int appid = Integer.valueOf(map.get("app").toString());
        redisLogic = new RedisLogic();
        StringBuilder result = new StringBuilder();
        JSONValue jsonValue;
        redisLogic.initRedisLogic(appid);
        PaySortOut geData = redisLogic.getPaySort();
        geData.setApp(map.get("app").toString());
        redisLogic.getApolloRedisDao().destoryJedisPool();
        jsonValue = JSONMapper.toJSON(geData);
        String jsonStr = jsonValue.render(true);
        result.append("apiStatus").append('(').append(jsonStr).append(')');
        return result.toString();
    }


    /**
     * The url is "app/pay_trend"
     * According to the inputTrend.getApp() and inputTrend.getChannel()
     * choose output which app's pay_trend data.
     * the style of input request is same with pay_trend,
     * so use class "AppTrendInput"
     *
     * @throws MapperException
     */
    //@RequestMapping(value = "/pay_trend",method = RequestMethod.GET)
    public String AppPayTrend(Map<String, Object> map) throws MapperException {
        int startTime = Integer.valueOf(map.get("start").toString());
        int endTime = Integer.valueOf(map.get("end").toString());
        int appid = Integer.valueOf(map.get("app").toString());

        StringBuilder result = new StringBuilder();
        JSONValue jsonValue;
        if (DateFormatUtil.isValidDate(startTime) &&
                DateFormatUtil.isValidDate(endTime)) {
            appRebuildLogic.initLogic(appid);
            PayTrend geData = appRebuildLogic.getPayTrend(startTime, endTime);
            geData.setApp(map.get("app").toString());
//			appRebuildLogic.getAdvanceDao().closeMongoClient();
            jsonValue = JSONMapper.toJSON(geData);
        } else {
            jsonValue = JSONMapper.toJSON(new ErrorMessage(DATE_ERROR));
        }
        String jsonStr = jsonValue.render(true);
        result.append("apiStatus").append('(').append(jsonStr).append(')');
        return result.toString();
    }

    /**

     */
    //@RequestMapping(value = "/pay_hobby",method = RequestMethod.GET)
    public String AppPayHobby(Map<String, Object> map) throws MapperException {
        int startTime = Integer.valueOf(map.get("start").toString());
        int endTime = Integer.valueOf(map.get("end").toString());
        int appid = Integer.valueOf(map.get("app").toString());

        StringBuilder result = new StringBuilder();
        JSONValue jsonValue;
        if (DateFormatUtil.isValidDate(startTime) &&
                DateFormatUtil.isValidDate(endTime)) {
            redisLogic.initRedisLogic(appid);
            PayHobby geData = redisLogic.getPayHobby(startTime, endTime);
            redisLogic.getSlaveRedisDao().destoryJedisPool();
            jsonValue = JSONMapper.toJSON(geData);
        } else {
            jsonValue = JSONMapper.toJSON(new ErrorMessage(DATE_ERROR));
        }
        String jsonStr = jsonValue.render(true);
        result.append("apiStatus").append('(').append(jsonStr).append(')');
        return result.toString();
    }


    /**
     * The url is "app/pay_sort_ch"
     * According to the inputTrend.getApp() and inputTrend.getChannel()
     * choose output which app's pay_sort data.
     * the style of input request is same with pay_sort,
     * so use class "AppTrendInput"
     *
     * @throws MapperException
     */
//	@RequestMapping(value = "/pay_sort_ch",method = RequestMethod.GET)
//	public @ResponseBody
    public String AppPayChSort(Map<String, Object> paramMap) throws MapperException {
        int appid = Integer.valueOf(paramMap.get("app").toString());
        int startTime = Integer.valueOf(paramMap.get("start").toString());
        int endTime = Integer.valueOf(paramMap.get("end").toString());
        redisLogic = new RedisLogic();
        StringBuilder result = new StringBuilder();
        JSONValue jsonValue;
        redisLogic.initRedisForChPay(appid);
        paymentLogic = new PaymentLogic();
        paymentLogic.initPaymentLogic();
        //admin 测试时间11.6-11.7/1446778800,1446865200
        List<String> channels = paymentLogic.getChannels(startTime, endTime);
        PaySortChOut geData = redisLogic.getPaySortCh(paramMap.get("sessionid").toString(), channels, startTime, endTime);
        geData.setApp(paramMap.get("app").toString());
        geData.setApi("pay_sort_ch");
        redisLogic.getSlaveRedisDao().destoryJedisPool();
        jsonValue = JSONMapper.toJSON(geData);
        String jsonStr = jsonValue.render(true);
        result.append("apiStatus").append('(').append(jsonStr).append(')');
        return result.toString();
    }

	/**
	 * The url is "app/order_ch"
	 *
	 * */
	@RequestMapping(value = "/order_ch",
			method = RequestMethod.GET)
	public @ResponseBody
	String orderFormCh(@RequestParam String app,HttpSession session) throws MapperException{
		return "";
	}
	/**
	 * The url is "app/detail_ch_order"
	 *
	 * */
	/*@RequestMapping(value = "/detail_ch_order",method = RequestMethod.GET)
	public @ResponseBody*/
    public String AppDetailChOrder(Map<String, Object> map) throws MapperException {
        int startTime = Integer.valueOf(map.get("start").toString());
        int endTime = Integer.valueOf(map.get("end").toString());
        StringBuilder result = new StringBuilder();
        paymentLogic = new PaymentLogic();
        paymentLogic.initPaymentLogic();
        //测试11.6-11.7/1446778800，1446865200
        ChOrder data = paymentLogic.getPaymentByCh(startTime, endTime, map.get("channel").toString());
        JSONValue jsonValue = JSONMapper.toJSON(data);
        String jsonStr = jsonValue.render(false);
        result.append("apiStatus").append('(').append(jsonStr).append(')');
        return result.toString();
    }

    /**
     * 微端留存 可以查出1 2 4 7 14日留存
     * app app号码生死狙击1001
     * start 开始时间
     * end 结束时间
     *
     * @return
     * @throws MapperException
     */
    //@RequestMapping(value = "/wd_retention",method = RequestMethod.GET)
    public String AppWdRetention(Map<String, Object> parmarMap) throws MapperException {
        int startTime = Integer.valueOf(parmarMap.get("start").toString());
        int endTime = Integer.valueOf(parmarMap.get("end").toString());
        int appid = Integer.valueOf(parmarMap.get("app").toString());
        StringBuilder result = new StringBuilder();
        JSONValue jsonValue;
        appRebuildLogic.initLogic(appid);
        if (DateFormatUtil.isValidDate(startTime) && DateFormatUtil.isValidDate(endTime)) {
            Map<String, Object> map = new HashMap<String, Object>();
            Map<String, String> map1 = new HashMap<String, String>();
            for (int day = 1; day < 14; day++) {
                if (day == 1 || day == 2 || day == 4 || day == 7 || day == 14) {
                    map1.put(day + "day", appRebuildLogic.RententionDailySchedule(startTime, endTime, day));
                }
            }
            map.put("reg", appRebuildLogic.getSetSize());
            map.put("end", parmarMap.get("end").toString());
            map.put("start", parmarMap.get("start").toString());
            map.put("wd", map1);
            map.put("api", "wd_retention");
            jsonValue = JSONMapper.toJSON(map);
        } else {
            jsonValue = JSONMapper.toJSON(new ErrorMessage(DATE_ERROR));
        }
        String jsonStr = jsonValue.render(true);
        result.append("apiStatus").append('(').append(jsonStr).append(')');
        return result.toString();
    }

    /**
     * The url is "app/fiveminutes_act"
     * According to the inputRt.getApp() and inputRt.getChannel()
     * choose output which app's app_rt data.
     *
     * @throws MapperException
     */
    //@RequestMapping(value = "/fiveminutes_act",	method = RequestMethod.GET)
    public String ActInFiveMinutes(Map<String, Object> map) throws MapperException {
        int appid = Integer.valueOf(map.get("app").toString());
        int cDate = Integer.valueOf(map.get("date").toString());
        StringBuilder result = new StringBuilder();
        appRebuildLogic.initLogic(appid);
        ActInFiveMinutes geData = appRebuildLogic.getFiveMinutesAct(cDate);
        geData.setApp(map.get("app").toString());
//		appRebuildLogic.getAdvanceDao().closeMongoClient();
        JSONValue jsonValue = JSONMapper.toJSON(geData);
        String jsonStr = jsonValue.render(false);
        System.out.println("jsonStr----:" + jsonStr);
        result.append("apiStatus").append('(').append(jsonStr).append(')');

        return result.toString();
    }

    /**
     * The url is "app/repeatfiveminutes_act"
     * According to the inputRt.getApp() and inputRt.getChannel()
     * choose output which app's app_rt data.
     *
     * @throws MapperException
     */
    //@RequestMapping(value = "/repeatfiveminutes_act",	method = RequestMethod.GET)
    public String RepeatFiveMinutes(Map<String, Object> map) throws MapperException {
        int appid = Integer.valueOf(map.get("app").toString());
        int cDate = Integer.valueOf(map.get("date").toString());
        StringBuilder result = new StringBuilder();
        appRebuildLogic.initLogic(appid);
        RepeatFiveMinutes geData = appRebuildLogic.getRepeatMinutesAct(cDate);
        geData.setApp(map.get("app").toString());
//		appRebuildLogic.getAdvanceDao().closeMongoClient();
        JSONValue jsonValue = JSONMapper.toJSON(geData);
        String jsonStr = jsonValue.render(false);
        System.out.println("jsonStr----:" + jsonStr);
        result.append("apiStatus").append('(').append(jsonStr).append(')');

        return result.toString();
    }


    /**
     * The url is "app/five_new_user"
     * According to the inputRt.getApp() and inputRt.getChannel()
     * choose output which app's app_rt data.
     *
     * @throws MapperException
     */
    //@RequestMapping(value = "/five_new_user",	method = RequestMethod.GET)
    public String FiveNewUser(Map<String, Object> map) throws MapperException {
        int appid = Integer.valueOf(map.get("app").toString());
        int cDate = Integer.valueOf(map.get("date").toString());
        StringBuilder result = new StringBuilder();
        appRebuildLogic.initLogic(appid);
        FiveNewUser geData = appRebuildLogic.getFiveNewUser(cDate);
        geData.setApp(map.get("app").toString());
        JSONValue jsonValue = JSONMapper.toJSON(geData);
        String jsonStr = jsonValue.render(false);
        System.out.println("jsonStr----:" + jsonStr);
        result.append("apiStatus").append('(').append(jsonStr).append(')');

        return result.toString();
    }

    /**
     * <p>Title: retQuery</p>
     * <p>Description: 根据前端输入时间计算时间范围内的留存信息</p>
     * The id of app.
     *
     * @return
     * @throws MapperException
     */
    //@RequestMapping(value = "/roi_query", method = RequestMethod.GET)
    public String RoiQuery(Map<String, Object> map) throws MapperException {
        int appid = Integer.valueOf(map.get("app").toString());
        int cDate = Integer.valueOf(map.get("date").toString());
        Integer startDate = cDate;
        Integer endDate = cDate + StaticValueUtil.ONE_DAY_SECONDS;
        int currentDate = DateUtil.getCurrentTimeSecond() / 86400 * 86400+ StaticValueUtil.THREE_HOURS_SECONDS;
        StringBuilder result = new StringBuilder();
        JSONValue jsonValue;
        if (DateFormatUtil.isValidDate(startDate) &&
                DateFormatUtil.isValidDate(endDate)) {
            appSourceDbLogic.initLogic(appid);
            RoiQuery geData = appSourceDbLogic.getRoiQuery(startDate, endDate, currentDate);

//			appSourceDbLogic.getSourceDbDao().closeMongoClient();
            jsonValue = JSONMapper.toJSON(geData);
        } else {
            jsonValue = JSONMapper.toJSON(new ErrorMessage(DATE_ERROR));
        }
        String jsonStr = jsonValue.render(true);
        result.append("apiStatus").append('(').append(jsonStr).append(')');
        return result.toString();
    }




}
