package com.xcloud.schedule.timescheduler;

import com.xcloud.schedule.BSFifteenMinuteSchedule;
import com.xcloud.schedule.HerocraftFifteenMinuteSchedule;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by admin on 2015/12/25.
 */
public class HerocraftFifteen implements Job{
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("HCFifteen>>>>>++十五分钟方法开始执行++>>>>>");
        HerocraftFifteenMinuteSchedule HCFifteen = new HerocraftFifteenMinuteSchedule();
        HCFifteen.getHCQuartzByFifMinutes();
    }
}
