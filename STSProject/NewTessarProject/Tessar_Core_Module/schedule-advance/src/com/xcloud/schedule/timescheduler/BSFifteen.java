package com.xcloud.schedule.timescheduler;

import com.xcloud.schedule.BSFifteenMinuteSchedule;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by admin on 2015/12/25.
 */
public class BSFifteen implements Job{
//    public static int i=1;
//     static BSFifteenMinuteSchedule bs=new BSFifteenMinuteSchedule();
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("BSFifteen>>>>>++十五分钟方法开始执行++>>>>>");
        BSFifteenMinuteSchedule BSFifteen = new BSFifteenMinuteSchedule();
        BSFifteen.getBSQuartzByFifMinutes();
//        int i=0;
//        while(true){
//            i++;
//            System.out.println("15分钟的输出数字："+i);
//        }
//        System.out.println("15分钟一次输出开始"+i+"次");
//        i++;
    }
}
