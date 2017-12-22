package com.xcloud.schedule.timescheduler;

import com.xcloud.schedule.BleachFifteenMinuteSchedule;
import com.xcloud.schedule.HerocraftFifteenMinuteSchedule;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by admin on 2015/12/25.
 */
public class BleachFifteen implements Job{
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("BleachFifteen>>>>>++十五分钟方法开始执行++>>>>>");
        BleachFifteenMinuteSchedule BlFifteen = new BleachFifteenMinuteSchedule();
        BlFifteen.getBlQuartzByFifMinutes();
    }
}
