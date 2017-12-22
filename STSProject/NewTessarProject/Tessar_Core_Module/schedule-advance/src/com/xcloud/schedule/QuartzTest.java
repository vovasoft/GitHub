package com.xcloud.schedule;


import com.xcloud.schedule.timescheduler.*;

/**
 * @Description: 测试类
 *
 * @ClassName: QuartzTest
 * @Copyright: Copyright (c) 2014
 *
 * @author Comsys-LZP
 * @date 2014-6-26 下午03:35:05
 * @version V2.0
 */
public class QuartzTest {
    public static void main(String[] args) {
        try {

            String BSFivejob_name = "五分钟统计";
            QuartzManager.addJob(BSFivejob_name, BSFive.class, "0 0/5 * * * ?");

            String BSFiveActive_name="五分钟不去重统计";
            ActiveFiveQuartzManager.addactiveJob(BSFiveActive_name, BSActiveFive.class, "0 0/5 * * * ?");

            String BSFifteenjob_name="十五分钟统计";
            FifteenQuartzManager.addJobFifteen(BSFifteenjob_name, BSFifteen.class, "0 0/15 * * * ?");

            String BSDaylyjob_name="一天统计";
            DailyQuartzManager.addJobDaily(BSDaylyjob_name, BSDaily.class, "0 0 11 * * ?"); //服务器是北京时间，所以这里调整成11点




            String NADaylyjob_name="火影一天统计";
            DailyQuartzManager.addJobDaily(NADaylyjob_name,NADaily.class,"0 0 11 * * ?");

            String NAFifteenjob_name="火影十五分钟统计";
            FifteenQuartzManager.addJobFifteen(NAFifteenjob_name,NAFifteen.class,"0 0/15 * * * ?");

            String NAFivejob_name = "火影五分钟统计";
            QuartzManager.addJob(NAFivejob_name, NAFive.class, "0 0/5 * * * ?");



            String HCFivejob_name = "英雄五分钟统计";
            QuartzManager.addJob(HCFivejob_name,HerocraftFive.class,"0 0/5 * * * ?");

            String HCFifteenjob_name = "英雄十五分钟统计";
            FifteenQuartzManager.addJobFifteen(HCFifteenjob_name,HerocraftFifteen.class,"0 0/15 * * * ?");

            String HCDaylyjob_name = "英雄一天统计";
            DailyQuartzManager.addJobDaily(HCDaylyjob_name,HerocraftDaily.class,"0 0 11 * * ?");



            String AGFivejob_name = "女神联盟(2)五分钟统计";
            QuartzManager.addJob(AGFivejob_name,AGFive.class,"0 0/5 * * * ?");

            String AGFifteenjob_name = "女神联盟(2)十五分钟统计";
            FifteenQuartzManager.addJobFifteen(AGFifteenjob_name,AngelsFifteen.class,"0 0/15 * * * ?");

            String AGDaylyjob_name = "女神联盟(2)一天统计";
            DailyQuartzManager.addJobDaily(AGDaylyjob_name,AGDaily.class,"0 0 11 * * ?");


            String Bleachjob_name = "死神五分钟统计";
            QuartzManager.addJob(Bleachjob_name,BleachFive.class,"0 0/5 * * * ?");

            String BleachFifteenjob_name = "死神十五分钟统计";
            FifteenQuartzManager.addJobFifteen(BleachFifteenjob_name,BleachFifteen.class,"0 0/15 * * * ?");

            String BleachDaylyjob_name = "死神)一天统计";
            DailyQuartzManager.addJobDaily(BleachDaylyjob_name,BleachDaily.class,"0 0 11 * * ?");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

