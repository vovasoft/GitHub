package com.xcloud.schedule.timescheduler;

import com.xcloud.schedule.BSDailySchedule;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by admin on 2015/12/25.
 */
public class BSDaily implements Job {
//    public static int i=1;
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {

//        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+ "★★★★★★★★★★★");
        System.out.println("BSDaily");
        BSDailySchedule BSDaily=new BSDailySchedule();
        BSDaily.getBSQuartzByDaily();

//        System.out.println("一天一次输出开始" + i + "次");
//        i++;
    }
}
