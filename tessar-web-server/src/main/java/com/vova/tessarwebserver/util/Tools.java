package com.vova.tessarwebserver.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: Vova
 * @create: date 14:03 2017/12/26
 */
public class Tools {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static long dateToSecByFormat(Date date) {
        if (date != null) {
            return date.getTime() / 1000;
        }
        return -1;
    }

    public static Date secToDateByFormat(long sec) throws ParseException {
        String date = sdf.format(new Date(sec * 1000));

        return sdf.parse(date);
    }

    public static long dateToSec(Date date) {
        return date.getTime() / 1000;
    }

    public static Date secToDate(long sec) {
        return new Date(sec * 1000);
    }

    /**
     * 得到本周周一
     *
     * @return yyyy-MM-dd
     */
    public static Date getMondayOfDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);


        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);
        return c.getTime();
    }

    /**
     * 得到本周周日
     *
     * @return yyyy-MM-dd
     */
    public static Date getSundayOfDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7);
        return c.getTime();
    }

    /**
     * 得到本月第一天
     */
    public static Date getFirstOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return c.getTime();
    }

    /**
     * 得到本月最后一天
     */
    public static Date getLastOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    //数字数组转字符串
    public static String numArrayToStr(int[] intarr) {
        String res = "";
        for (Object i : intarr) {
            res = res + i + ",";
        }
        return res.substring(0, res.length() - 1);
    }


    //字符转转数字数组
    public static int[] strToNumArray(String str, String split) {
        String[] subStrs = str.split(split);
        int length = subStrs.length;
        int[] res = new int[length];
        for (int i = 0; i < length; i++) {
            res[i] = new Integer(subStrs[i]);
        }
        return res;
    }

    /**
     * 计算两个日期之间的间隔，单位是日，周，月
     */

    public static int countTwoDateSpace(Date dStart, Date dEnd, Class clazz) {
        String clazzName = clazz.getSimpleName();
        int res = 0;
        Calendar cRegister = Calendar.getInstance();
        Calendar cLogin = Calendar.getInstance();
        if (clazzName.equals("StayDay")) {

            cRegister.setTime(dStart);
            cLogin.setTime(dEnd);

            while (cRegister.before(cLogin)) {
                res++;
                cRegister.add(Calendar.DAY_OF_YEAR, 1);
            }
        } else if (clazzName.equals("StayWeek")) {
            cRegister = Calendar.getInstance();
            cRegister.setTime(dStart);

            cLogin = Calendar.getInstance();
            cLogin.setTime(dEnd);

            while (cRegister.before(cLogin)) {
                res++;
                cRegister.add(Calendar.WEEK_OF_YEAR, 1);
            }
        } else if (clazzName.equals("StayMon")) {
            cRegister = Calendar.getInstance();
            cRegister.setTime(dStart);
            cLogin = Calendar.getInstance();
            cLogin.setTime(dEnd);

            while (cRegister.before(cLogin)) {
                res++;
                cRegister.add(Calendar.MONTH, 1);
            }
        }
        return res;
    }

}
