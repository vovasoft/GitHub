package com.xcloud.schedule.timescheduler;

import com.xcloud.schedule.AngelsFifteenMinuteSchedule;
import com.xcloud.schedule.HerocraftFifteenMinuteSchedule;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by admin on 2015/12/25.
 */
public class AngelsFifteen implements Job{
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("AGFifteen>>>>>++十五分钟方法开始执行++>>>>>");
        AngelsFifteenMinuteSchedule AGFifteen = new AngelsFifteenMinuteSchedule();
        AGFifteen.getAGQuartzByFifMinutes();
    }
}
