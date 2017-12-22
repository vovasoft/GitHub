package com.xcloud.schedule.timescheduler;

import com.xcloud.schedule.BleachFiveMinuteSchedule;
import com.xcloud.schedule.HerocraftFiveMinuteSchedule;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Description: 任务执行类
 *
 * @ClassName: BSFive
 * @Copyright: Copyright (c) 2014
 *
 * @author Comsys-LZP
 * @date 2014-6-26 下午03:37:11
 * @version V2.0
 */
public class BleachFive implements Job {
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.out.println("BleachFive...++五分钟方法开始执行++......");

        BleachFiveMinuteSchedule HCFive = new BleachFiveMinuteSchedule();
        System.out.println("BleachFive......去重");
        HCFive.getBlQuartzByFiveMinutes();

    }
}
