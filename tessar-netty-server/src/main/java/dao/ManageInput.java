package dao;

import dao.dbmongo.UseMyMongo;
import dao.dbsql.UseMySql;
import domain.EnumSQL;
import domain.Player;
import domain.newadd.NewAddDay;
import domain.newadd.NewAddMon;
import domain.newadd.NewAddWeek;
import org.springframework.beans.factory.annotation.Autowired;
import util.Tools;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * @author: Vova
 * @create: date 13:49 2017/12/26
 */
public class ManageInput {

    @Autowired
    UseMyMongo umm;
    @Autowired
    UseMySql mys;

    public int HandPlayerDate(Player player) throws IOException {
        String uid = player.getUid();  //获取当前用户id
        Date uLoginDate = Tools.secToDate(player.getLastdate());//获取登录日期
        Date uRegDate = Tools.secToDate(player.getRegdate()); //获取注册信息

        //判断登录时间是否为当日首次
        boolean isExistDay = umm.findDayInMongo(player);

        //判断登录时间是否为当周首次
        boolean isExistWeek = umm.findWeekInMongo(player);

        //判断登录时间是否为当月首次
        boolean isExistMon = umm.findMonInMongo(player);
        int activeDay=isExistDay?0:1 ,activeMon=isExistMon?0:1, activeWeek=isExistWeek?0:1 ;  //记录活跃用户增加

        //登录次数，直接+1；
        int loginCount=1;

        //记录新增的数值
        int newAddDay=0,newAddWeek=0,newAddMon=0;

        if (uLoginDate.equals(uRegDate) && isExistDay){
            System.out.println("注册和登录时间一致");
            newAddDay=1;
        }
        if (uLoginDate.equals(uRegDate) && isExistWeek){
            System.out.println("注册和登录时间一致");
            newAddWeek=1;
        }
        if (uLoginDate.equals(uRegDate) && isExistMon){
            System.out.println("注册和登录时间一致");
            newAddMon=1;
        }

        //判断三个表是否存在当日当周当月词条，如果没有则insert
        NewAddDay tmp1 = (NewAddDay) mys.utilSQL(NewAddDay.class, EnumSQL.SELECT,uLoginDate);
        if (tmp1==null){
         //   NewAddDay newAddDay = NewAddDay(uLoginDate,);
            mys.utilSQL(NewAddDay.class, EnumSQL.INSERT);
        }
        NewAddWeek tmp2 = (NewAddWeek) mys.utilSQL(NewAddWeek.class, EnumSQL.SELECT,uLoginDate);
        NewAddMon tmp3 = (NewAddMon) mys.utilSQL(NewAddMon.class, EnumSQL.SELECT,uLoginDate);



        //原始数据存入mongodb
        try {
            umm.insertMongo(player);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }


}
