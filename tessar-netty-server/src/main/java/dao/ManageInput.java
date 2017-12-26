package dao;

import dao.dbmongo.UseMyMongo;
import dao.dbsql.UseMySql;
import dao.dbsql.EnumSQL;
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
        String cid = player.getChannel_from();
        String gid = player.getGid();
        String sid = player.getSid();

        //判断登录时间是否为当日首次
        boolean isExistDay = umm.findDayInMongo(player);

        //判断登录时间是否为当周首次
        boolean isExistWeek = umm.findWeekInMongo(player);

        //判断登录时间是否为当月首次
        boolean isExistMon = umm.findMonInMongo(player);

        //记录活跃用户增加
        int activeDay = isExistDay ? 0 : 1, activeMon = isExistMon ? 0 : 1, activeWeek = isExistWeek ? 0 : 1;

        //登录次数，直接+1；
        int loginCount = 1;

        //记录新增的数值
        int newAddDayNum = 0, newAddWeekNum = 0, newAddMonNum = 0;

        if (uLoginDate.equals(uRegDate) && isExistDay) {
            System.out.println("注册和登录时间一致");
            newAddDayNum = 1;
        }
        if (uLoginDate.equals(uRegDate) && isExistWeek) {
            System.out.println("注册和登录时间一致");
            newAddWeekNum = 1;
        }
        if (uLoginDate.equals(uRegDate) && isExistMon) {
            System.out.println("注册和登录时间一致");
            newAddMonNum = 1;
        }

        //判断三个表是否存在当日当周当月词条，如果没有则insert

        Date thisday = uLoginDate;
        NewAddDay findSeedDay = new NewAddDay();
        findSeedDay.setcID(cid);
        findSeedDay.setgID(gid);
        findSeedDay.setsID(sid);
        findSeedDay.setDateID(thisday);

        NewAddDay tmp1 = (NewAddDay) mys.utilSQL(NewAddDay.class, EnumSQL.SELECT, thisday);
        if (tmp1 == null) {    //新增表中行不存在则需要增加行
            NewAddDay newLine = new NewAddDay(0, uLoginDate,
                    cid,
                    gid,
                    sid,
                    0, 0, 0, 0, 0);
            mys.utilSQL(newLine, EnumSQL.INSERT);
            tmp1 = (NewAddDay) mys.utilSQL(NewAddDay.class, EnumSQL.SELECT, thisday);
        }

        //查询周表中是否存在该词条，并且周表中的日期是每周的第一个周一
        Date thisWeek = Tools.getMondayOfDate(uLoginDate);
        NewAddWeek findSeedWeek = new NewAddWeek();
        findSeedWeek.setcID(cid);
        findSeedWeek.setgID(gid);
        findSeedWeek.setsID(sid);
        findSeedWeek.setDateID(thisWeek);
        NewAddWeek tmp2 = (NewAddWeek) mys.utilSQL(NewAddWeek.class, EnumSQL.SELECT, findSeedWeek);
        if (tmp2 == null) {//新增表中行不存在则需要增加行
            NewAddWeek newLine = new NewAddWeek(0, thisWeek,
                    cid,
                    gid,
                    sid,
                    0, 0, 0, 0, 0);
            mys.utilSQL(NewAddDay.class, EnumSQL.INSERT);
            tmp2 = (NewAddWeek) mys.utilSQL(NewAddWeek.class, EnumSQL.SELECT, findSeedWeek);
        }

        //查询月表中是否存在该词条，并且月表中的日期是每月的第一天
        Date thisMonth = Tools.getFirstOfMonth(uLoginDate);
        NewAddMon findSeedM = new NewAddMon();
        findSeedM.setcID(cid);
        findSeedM.setgID(gid);
        findSeedM.setsID(sid);
        findSeedM.setDateID(thisWeek);
        NewAddMon tmp3 = (NewAddMon) mys.utilSQL(NewAddMon.class, EnumSQL.SELECT, findSeedM);

        if (tmp3 == null) {//新增表中行不存在则需要增加行
            NewAddMon newLine = new NewAddMon(0, thisMonth,
                    cid,
                    gid,
                    sid,
                    0, 0, 0, 0, 0);
            mys.utilSQL(NewAddMon.class, EnumSQL.INSERT);
            tmp3 = (NewAddMon) mys.utilSQL(NewAddMon.class, EnumSQL.SELECT, findSeedM);
        }
        //处理完表之后，往表中增加数据。

        //更新日增表
        Integer dayCount = (Integer) mys.utilSQL(Integer.class, EnumSQL.GETCOUNT, tmp1);
        NewAddDay updateDay = new NewAddDay(tmp1.getId(), uLoginDate, cid, gid, sid, newAddDayNum, activeDay, loginCount, loginCount / dayCount, 0);
        mys.utilSQL(NewAddDay.class, EnumSQL.UPDATE, updateDay);
        //更新周增表
        Integer weekCount = (Integer) mys.utilSQL(Integer.class, EnumSQL.GETCOUNT, tmp2);
        NewAddWeek updateWeek = new NewAddWeek(tmp2.getId(), uLoginDate, cid, gid, sid, newAddDayNum, activeDay, loginCount, loginCount / weekCount, 0);
        mys.utilSQL(NewAddWeek.class, EnumSQL.UPDATE, updateWeek);
        //更新月增表
        Integer monthCount = (Integer) mys.utilSQL(Integer.class, EnumSQL.GETCOUNT, tmp3);
        NewAddMon updateMon = new NewAddMon(tmp3.getId(), uLoginDate, cid, gid, sid, newAddDayNum, activeDay, loginCount, loginCount / monthCount, 0);
        mys.utilSQL(NewAddMon.class, EnumSQL.UPDATE, updateMon);


        //原始数据存入mongodb
        try {
            umm.insertMongo(player);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }


}
