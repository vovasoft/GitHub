package vova.dao;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import vova.dao.dbmongo.UseMyMongo;
import vova.dao.dbsql.UseMySql;
import vova.dao.dbsql.EnumSQL;
import vova.domain.Player;
import vova.domain.newadd.NewAdd;
import vova.domain.newadd.NewAddDay;
import vova.domain.newadd.NewAddMon;
import vova.domain.newadd.NewAddWeek;
import org.springframework.stereotype.Component;
import vova.util.Tools;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * @author: Vova
 * @create: date 13:49 2017/12/26
 */

@Component
public class ManageGameInput {

    public ManageGameInput() {
    }

    public int HandPlayerDate(Player player) throws IOException, ParseException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
        UseMyMongo umm = (UseMyMongo) ac.getBean("useMyMongo");
        UseMySql mys = (UseMySql) ac.getBean("useMySql");
        System.out.println("Thread=========================" + this);
        String uid = player.getUid();  //获取当前用户id
        Date uLoginDate = Tools.secToDateByFormat(player.getLastdate());//获取登录日期
        Date uRegDate = Tools.secToDateByFormat(player.getRegdate()); //获取注册信息
        String cid = player.getCid();
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
        System.out.println("uLoginDate:"+uLoginDate+", uRegDate"+uRegDate+", isExistDay"+isExistDay);
        System.out.println("uLoginDate:"+uLoginDate+", uRegDate"+uRegDate+", isExistWeek"+isExistWeek);
        System.out.println("uLoginDate:"+uLoginDate+", uRegDate"+uRegDate+", isExistMon"+isExistMon);
        if (uLoginDate.equals(uRegDate) && !isExistDay) {
            //System.out.println("注册和登录时间一致");
            newAddDayNum = 1;
        }
        if (uLoginDate.equals(uRegDate) && !isExistWeek) {
            //System.out.println("注册和登录时间一致");
            newAddWeekNum = 1;
        }
        if (uLoginDate.equals(uRegDate) && !isExistMon) {
            //System.out.println("注册和登录时间一致");
            newAddMonNum = 1;
        }
        System.out.println("Thread=========================" + this);
        //判断三个表是否存在当日当周当月词条，如果没有则insert
        NewAddDay tmp1 = (NewAddDay) findOrCreate(uLoginDate, cid, gid, sid, mys, NewAddDay.class);
        NewAddWeek tmp2 = (NewAddWeek) findOrCreate(uLoginDate, cid, gid, sid, mys, NewAddWeek.class);
        NewAddMon tmp3 = (NewAddMon) findOrCreate(uLoginDate, cid, gid, sid, mys, NewAddMon.class);

        //计算玩家总数，计入到数据中

        long allPlayerCount = umm.findPlayerCountInMongo("player", "uid");
        //处理完表之后，往表中增加数据。
        //更新日增表
        Integer dayCount = (Integer) mys.utilSQL(Integer.class, EnumSQL.GETCOUNT, tmp1);
        NewAddDay updateDay = new NewAddDay(tmp1.getId(), uLoginDate, cid, gid, sid, newAddDayNum,
                activeDay, loginCount, (float) (((tmp1.getLoginCount()+loginCount) * 1.0) / dayCount), allPlayerCount + newAddDayNum);
        mys.utilSQL(NewAddDay.class,updateDay, EnumSQL.UPDATE);

        //更新周增表
        Integer weekCount = (Integer) mys.utilSQL(Integer.class, EnumSQL.GETCOUNT, tmp2);
        NewAddWeek updateWeek = new NewAddWeek(tmp2.getId(), uLoginDate, cid, gid, sid, newAddWeekNum,
                activeWeek, loginCount, (float) (((tmp2.getLoginCount()+loginCount) * 1.0) / weekCount), allPlayerCount + newAddWeekNum);
        mys.utilSQL(NewAddWeek.class,updateWeek, EnumSQL.UPDATE);

        //更新月增表
        Integer monthCount = (Integer) mys.utilSQL(Integer.class, EnumSQL.GETCOUNT, tmp3);
        NewAddMon updateMon = new NewAddMon(tmp3.getId(), uLoginDate, cid, gid, sid, newAddWeekNum,
                activeMon, loginCount, (float) (((tmp3.getLoginCount()+loginCount)* 1.0) / monthCount), allPlayerCount + newAddMonNum);
        mys.utilSQL(NewAddMon.class,updateMon, EnumSQL.UPDATE);

        //原始数据存入mongodb
        try {
            umm.insertMongo(player);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    //查找增表某行是否存在，如果不存在则插入一个
    private <T> NewAdd findOrCreate(Date uLoginDate, String cid, String gid, String sid, UseMySql mys, Class<T> clazz) throws IOException {
        String clazzName = clazz.getSimpleName();
        Date thisDate = null;
        if (clazzName.equals("NewAddDay")) {
            thisDate = uLoginDate;
        } else if (clazzName.equals("NewAddMon")){
            thisDate = Tools.getFirstOfMonth(uLoginDate);
        } else if (clazzName.equals("NewAddWeek")) {
            thisDate = Tools.getMondayOfDate(uLoginDate);
        }
        NewAdd findSeed = new NewAdd();
        findSeed.setcID(cid);
        findSeed.setgID(gid);
        findSeed.setsID(sid);
        findSeed.setDateID(thisDate);
        NewAdd tmp1 = (NewAdd) mys.utilSQL(clazz, EnumSQL.SELECT, findSeed);
        if (tmp1 == null) {    //新增表中行不存在则需要增加行
            NewAdd newLine = new NewAdd(0, thisDate,
                    cid,
                    gid,
                    sid,
                    0, 0, 0, 0, 0);
            mys.utilSQL(clazz,newLine, EnumSQL.INSERT);
            tmp1 = (NewAdd) mys.utilSQL(clazz, EnumSQL.SELECT, findSeed);
        }
        return tmp1;
    }
}
