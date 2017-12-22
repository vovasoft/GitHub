package com.xcloud.schedule.timescheduler;

import com.xcloud.schedule.AngelsDailySchedule;
import com.xcloud.schedule.NADailySchedule;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by admin on 2015/12/25.
 */
public class AGDaily implements Job {
//    public static int i=1;
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {

//      System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+ "★★★★★★★★★★★");

        System.out.println("AGDaily");
        AngelsDailySchedule AGDaily=new AngelsDailySchedule();
        AGDaily.getAGQuartzByDaily();

    }
}
