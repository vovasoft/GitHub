package util;

import domain.PlayerInfo;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author:    WangYang
 * @Data:      15:37 2017/12/21
 *
 */
public class DbUtil {
    //根据两个时间点，计算第二个时间点是第一个时间点的第几天（计算留存）。

    public static void main(String[] args) throws ParseException {

//        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));

        DateFormat df= DateFormat.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        String dstr="2017-10-25";
        java.util.Date date=sdf.parse(dstr);
        java.util.Date date2=sdf.parse("2017-10-31");

        //test
//        Calendar calendar = Calendar.getInstance();
//        System.out.println(calendar.get(Calendar.DAY_OF_YEAR));
//        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
//        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));

        int days=getDayIndex(new PlayerInfo("1",date.getTime(),date2.getTime(),123,123,true
                ,true,true));

        int weeks=getWeekIndex(new PlayerInfo("1",date.getTime(),date2.getTime(),123,123,true
                ,true,true));

        int month=getMonthIndex(new PlayerInfo("1",date.getTime(),date2.getTime(),123,123,true
                ,true,true));

        System.out.println("day:"+days);
        System.out.println("week:"+weeks);
        System.out.println("month:"+month);


    }

    //根据注册时间返回用户的留存
    public static int getDayIndex(PlayerInfo user) {

        Calendar cRegister = Calendar.getInstance();
        cRegister.setTimeInMillis(user.getRegisterTime());

        Calendar cLogin = Calendar.getInstance();
        cLogin.setTimeInMillis(user.getLoginTime());

        int days=0;
        while(cRegister.before(cLogin)){
            days++;
            if (cRegister.getTime().equals(cLogin.getTime()))days--;
            cRegister.add(Calendar.DAY_OF_YEAR, 1);
        }
        return days;
    }

    //根据两个时间点，计算第二个时间点是第一个时间点的第几周（计算留存）。
    public static int getWeekIndex(PlayerInfo user) {
        Calendar cRegister = Calendar.getInstance();
        cRegister.setTimeInMillis(user.getRegisterTime());
        Calendar cLogin = Calendar.getInstance();
        cLogin.setTimeInMillis(user.getLoginTime());

        int weeks=0;
        while(cRegister.before(cLogin)){
            weeks++;
            if (cRegister.get(Calendar.WEEK_OF_YEAR)==cLogin.get(Calendar.WEEK_OF_YEAR))weeks--;
            cRegister.add(Calendar.WEEK_OF_YEAR, 1);
        }
        return weeks>0?weeks:0;
    }
    //根据两个时间点，计算第二个时间点是第一个时间点的第几月（计算留存）。

    public static int getMonthIndex(PlayerInfo user) {
        Calendar cRegister = Calendar.getInstance();
        cRegister.setTimeInMillis(user.getRegisterTime());
        Calendar cLogin = Calendar.getInstance();
        cLogin.setTimeInMillis(user.getLoginTime());

        int months=0;
        while(cRegister.before(cLogin)){
            months++;
            if (cRegister.get(Calendar.MONTH)==cLogin.get(Calendar.MONTH))months--;
            cRegister.add(Calendar.MONTH, 1);
        }
        return months>0?months:0;
    }
}
