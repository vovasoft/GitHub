package com.xcloud.schedule.timescheduler;

import com.xcloud.schedule.BSFiveMinuteSchedule;
import com.xcloud.schedule.BSRepeatFiveMinuteSchedule;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by admin on 2016/1/10.
 */
public class BSActiveFive implements Job {
    //    public static int i=1;
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.out.println("BSFiveActive...++五分钟不去重方法开始执行++......");
        BSRepeatFiveMinuteSchedule BSActive = new BSRepeatFiveMinuteSchedule();
        BSActive.getActiveFive();
    }
}