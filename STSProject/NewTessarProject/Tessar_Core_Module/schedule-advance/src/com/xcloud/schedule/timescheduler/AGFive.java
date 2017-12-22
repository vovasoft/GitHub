package com.xcloud.schedule.timescheduler;

import com.xcloud.schedule.AngelsFiveMinuteSchedule;
import com.xcloud.schedule.NAFiveMinuteSchedule;
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
public class AGFive implements Job {
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.out.println("AGFive...++五分钟方法开始执行++......");

        AngelsFiveMinuteSchedule AGFive = new AngelsFiveMinuteSchedule();
        System.out.println("AGfive......去重");
        AGFive.getAGQuartzByFiveMinutes();

    }
}
