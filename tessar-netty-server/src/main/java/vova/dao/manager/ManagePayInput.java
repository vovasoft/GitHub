package vova.dao.manager;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import vova.dao.dbmongo.UseMyMongo;
import vova.dao.dbsql.EnumSQL;
import vova.dao.dbsql.UseMySql;
import vova.domain.Player;
import vova.domain.newadd.NewAdd;
import vova.domain.newadd.NewAddDay;
import vova.domain.newadd.NewAddMon;
import vova.domain.newadd.NewAddWeek;
import vova.domain.payment.*;
import vova.domain.staypay.StayPayDay;
import vova.domain.staypay.StayPayMon;
import vova.domain.staypay.StayPayWeek;
import vova.util.Tools;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Vova
 * @create: date 14:32 2018/1/3
 */

//Big思路，能从游戏平台数据库找到的数据从那边找，然后算出这一次支付的细节信息，然后再找原始表，如果没有，新建一个。
@Component
public class ManagePayInput {
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ManagePayInput.class);

    public ManagePayInput() {
    }

    public int HandPayData(PayReceive payReceive) throws ParseException, IOException {
        log.info("Begin Job for Pay Data: thread--"+Thread.currentThread());
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
        UseMyMongo umm = (UseMyMongo) ac.getBean("useMyMongo");
        UseMySql mys = (UseMySql) ac.getBean("useMySql");
        ac=null;
        float amount = payReceive.getAmount();
        String uid = payReceive.getUid();
        String gid = payReceive.getGid();
        String sid = payReceive.getSid();
        String oid = payReceive.getOid(); //暂时用不到
        String currency = payReceive.getCurrency();
        String payType = payReceive.getPayType();
        String cid = "";
        long payTime = payReceive.getPayTime();

        Date payDate = Tools.secToDateByFormat(payTime);
        Date firstPayDate = null;

        Player tmpPlayer = new Player();
        tmpPlayer.setUid(uid);
        tmpPlayer.setSid(sid);
        tmpPlayer.setGid(gid);

        Player player = umm.findOnePlayer(tmpPlayer);
        tmpPlayer=null;
        if (player == null) {
            System.out.println("error player not exist");
            return -1;
        }
//        Date uLoginDate = Tools.secToDateByFormat(player.getLastdate());//获取登录日期
        Date uRegDate = Tools.secToDateByFormat(player.getRegdate()); //获取注册信息

        //处理web请求数据表单的字段信息

        PayMentForKeep pmfk = new PayMentForKeep();    //这个对象用于最终的保存记录

        pmfk.show();
        pmfk.setUid(uid);
        pmfk.setGid(gid);
        pmfk.setSid(sid);
        pmfk.setAmount(amount);
        pmfk.setPayTime(payTime);
        pmfk.setCurrency(currency);
        pmfk.setPayType(payType);

        //通过数据库查找之前的id字段，如果有则去处cid以及首充时间。
        PayMentForKeep tmp = umm.findOnePayer(pmfk);
        if (tmp == null) {
            cid = player.getCid();
            pmfk.setFirstPayTime(payTime);
            pmfk.setCid(cid);  //根据游戏平台找的数据同步到支付平台的channelID
        } else {
            cid = tmp.getCid();
            pmfk.setFirstPayTime(tmp.getFirstPayTime());
            pmfk.setCid(cid);
        }
        firstPayDate = Tools.secToDateByFormat(pmfk.getFirstPayTime());

        //计算web展示表的字段数据，分为日，周，月             1.计算新增和活跃，需要查找游戏平台数据
        //新增和活跃
        int[] newAddDay = getNewAddNumAndActiveNum(payDate, cid, gid, sid, mys, NewAddDay.class);
        int[] newAddWeek = getNewAddNumAndActiveNum(payDate, cid, gid, sid, mys, NewAddWeek.class);
        int[] newAddMon = getNewAddNumAndActiveNum(payDate, cid, gid, sid, mys, NewAddMon.class);

        //从现有库取出原始数据。
        int newAddDayNum = newAddDay[0], activeDayNum = newAddDay[1];
        int newAddWeekNum = newAddWeek[0], activeWeekNum = newAddWeek[1];
        int newAddMonNum = newAddMon[0], activeMonNum = newAddMon[1];

        //计算新增用户付费数，如果该用户为新增，则为新增付费,新增全天的充值都算新增用户付费。
        //判断标准:1,该用户为新增，用player判断，2.该用户在新增这天充值。

        float newAddDayMoney = 0, newAddWeekMoney = 0, newAddMonMoney = 0;
        int newAddDayPayNum = 0, newAddWeekPayNum = 0, newAddMonPayNum = 0;

        //判断日首次冲，周首次冲，月首次冲
        boolean firstPayDay = umm.findDayPayInMongo(pmfk);
        boolean firstPayWeek = umm.findWeekPayInMongo(pmfk);
        boolean firstPayMon = umm.findMonPayInMongo(pmfk);

        //判断从来没有充过值，是第一次充值用户；
        boolean neverPay = umm.findNeverPayInMongo(pmfk);

        //日新增，判断支付日期和注册日期一致
        if (payDate.equals(uRegDate)) {
            newAddDayMoney += amount;          //新增付费总额
            newAddDayPayNum=firstPayDay&&neverPay?1:0;               //新增付费人数，人数需要判断是否重复
        }
        //周新增，判断支付日期在注册日期的那个周
        if (Tools.checkDateInWeekDate(payDate, uRegDate)) {
            newAddWeekMoney += amount;//新增付费总额
            newAddWeekPayNum=firstPayWeek&&neverPay?1:0;//新增付费人数
        }
        //月新增，判断支付日期在注册日期的那个月
        if (Tools.checkDateInMonDate(payDate, uRegDate)) {
            newAddMonMoney += amount;//新增付费总额
            newAddMonPayNum=firstPayMon&&neverPay?1:0;//新增付费人数
        }


        //记录首冲用户数，之前没有充过值
        int firstPayDayNum = firstPayDay&&neverPay ? 1 : 0, firstPayWeekNum = firstPayWeek&&neverPay ? 1 : 0, firstPayMonNum = firstPayMon&&neverPay ? 1 : 0;
        //记录首冲金额
        float firstPayDayMoney = firstPayDay&&neverPay ? amount : 0;
        float firstPayWeekMoney = firstPayWeek&&neverPay ? amount : 0;
        float firstPayMonMoney = firstPayMon&&neverPay ? amount : 0;


        //增加付费用户和付费金额
        int dayPayNum = firstPayDay ? 1 : 0, weekPayNum = firstPayDay ? 1 : 0, MonPayNum = firstPayDay ? 1 : 0;
//        float dayPayMoney = amount, weekPayMoney = amount, monPayMoney = amount;

        //查询或新建  PayMent
        PayMentDay tmp1 = (PayMentDay) findOrCreate(payDate, cid, gid, sid, mys, PayMentDay.class);
        PayMentWeek tmp2 = (PayMentWeek) findOrCreate(payDate, cid, gid, sid, mys, PayMentWeek.class);
        PayMentMon tmp3 = (PayMentMon) findOrCreate(payDate, cid, gid, sid, mys, PayMentMon.class);

        if (tmp1 == null || tmp2 == null || tmp3 == null) {
            log.error("find or create error ,and someone is null");
            return -1;
        }


        //获取累计付费
        float allPayMoney = umm.findAllPayMoney(pmfk);
        int res1 = updatePayTable(tmp1, mys, PayMentDay.class, newAddDayNum, newAddDayPayNum, newAddDayMoney, firstPayDayNum, firstPayDayMoney, activeDayNum, dayPayNum, amount, allPayMoney);
        int res2 = updatePayTable(tmp2, mys, PayMentWeek.class, newAddWeekNum, newAddWeekPayNum, newAddWeekMoney, firstPayWeekNum, firstPayWeekMoney, activeWeekNum, weekPayNum, amount, allPayMoney);
        int res3 = updatePayTable(tmp3, mys, PayMentMon.class, newAddMonNum, newAddMonPayNum, newAddMonMoney, firstPayMonNum, firstPayMonMoney, activeMonNum, MonPayNum, amount, allPayMoney);

        log.info("获取累计付费/n res1:" + res1 + ", res2:" + res2 + ", res3:" + res3);


        //处理留存逻辑
        res1 = ManageStay.manageStayData(firstPayDate,payDate,cid,gid,sid,firstPayDayNum,!firstPayDay,!firstPayWeek,!firstPayMon,mys, StayPayDay.class);
        res2 = ManageStay.manageStayData(firstPayDate,payDate,cid,gid,sid,firstPayWeekNum,!firstPayDay,!firstPayWeek,!firstPayMon,mys, StayPayWeek.class);
        res3 = ManageStay.manageStayData(firstPayDate,payDate,cid,gid,sid,firstPayMonNum,!firstPayDay,!firstPayWeek,!firstPayMon,mys, StayPayMon.class);

        log.info("处理留存逻辑/n res1:" + res1 + ", res2:" + res2 + ", res3:" + res3);

        log.info("End Job for Pay Data: thread--"+Thread.currentThread());

        try {
            umm.insertMongo(pmfk);        //原始数据存入MongoDB
            pmfk = null;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }

        return 1;
    }

    private int[] getNewAddNumAndActiveNum(Date uLoginDate, String cid, String gid, String sid, UseMySql mys, Class clazz) throws IOException {
        String clazzName = clazz.getSimpleName();
        int res[] = new int[2];
        Date thisDate = null;
        if (clazzName.equals("NewAddDay")) {
            thisDate = uLoginDate;
        } else if (clazzName.equals("NewAddMon")) {
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
        if (tmp1 == null) {
            return res;
        } else {
            res[0] = (int) tmp1.getNewAddNum();
            res[1] = (int) tmp1.getActiveNum();
            return res;
        }
    }

    private static Lock lock = new ReentrantLock();// 锁对象

    private PayAllShow findOrCreate(Date payDate, String cid, String gid, String sid, UseMySql mys, Class clazz) throws IOException {
        lock.lock();
        try {
            String clazzName = clazz.getSimpleName();
            Date thisDate = null;
            if (clazzName.equals("PayMentDay")) {
                thisDate = payDate;
            } else if (clazzName.equals("PayMentMon")) {
                thisDate = Tools.getFirstOfMonth(payDate);
            } else if (clazzName.equals("PayMentWeek")) {
                thisDate = Tools.getMondayOfDate(payDate);
            }
            PayAllShow findSeed = new PayAllShow();
            findSeed.setcId(cid);
            findSeed.setgId(gid);
            findSeed.setsId(sid);
            findSeed.setDateID(thisDate);
            PayAllShow tmp1 = (PayAllShow) mys.utilSQL(clazz, EnumSQL.SELECT, findSeed);
            if (tmp1 == null) {    //新增表中行不存在则需要增加行
                PayAllShow newLine = new PayAllShow(0, thisDate,
                        cid,
                        gid,
                        sid,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
                mys.utilSQL(clazz, EnumSQL.INSERT, newLine);
                newLine=null;
                tmp1 = (PayAllShow) mys.utilSQL(clazz, EnumSQL.SELECT, findSeed);
            }
            return tmp1;
        }finally {
            lock.unlock();
        }


    }

    private int updatePayTable(PayAllShow payAllShow, UseMySql mys, Class clazz,
                               int newAdd, int newAddMoneyNum, float newAddMoney,
                               int firstPayNum, float firstPayMoney, int activeNums,
                               int payNum, float payMoney, float allPayMoney) throws IOException {

        //原始信息
        long newAddNum = newAdd;
        long newAddPayPlayerNum = payAllShow.getNewAddPayPlayerNum() + newAddMoneyNum;
        float newAddPayAllMoney = payAllShow.getNewAddPayAllMoney() + newAddMoney;
        long newPayPlayerNum = payAllShow.getNewPayPlayerNum() + firstPayNum;
        float newPayAllMoney = payAllShow.getNewPayAllMoney() + firstPayMoney;
        long activeNum = activeNums;
        long payPlayerNum = payAllShow.getPayPlayerNum() + payNum;
        float todayAllPayMoney = payAllShow.getTodayAllPayMoney() + payMoney;
        float allMoney = allPayMoney;

        //以下数据通过计算得出
        float newAddPlayerARPPU = newAddNum != 0 ? newAddPayAllMoney / newAddNum : 0;
        float newAddPayPlayerARPPU = newAddPayPlayerNum != 0 ? newAddPayAllMoney / newAddPayPlayerNum : 0;
        float payPlayerARPPU = payPlayerNum != 0 ? todayAllPayMoney / payPlayerNum : 0;
        float activeARPPU = activeNum != 0 ? todayAllPayMoney / activeNum : 0;
        float averageNewPayMoney = newPayPlayerNum!=0?newPayAllMoney/newPayPlayerNum:0;
        //以下进行表格构建
        payAllShow.setNewAddNum(newAddNum);
        payAllShow.setNewAddPayAllMoney(newAddPayAllMoney);
        payAllShow.setNewAddPayPlayerNum(newAddPayPlayerNum);
        payAllShow.setActiveARPPU(activeARPPU);
        payAllShow.setActiveNum(activeNum);
        payAllShow.setAllMoney(allMoney);
        payAllShow.setAverageNewPayMoney(averageNewPayMoney);
        payAllShow.setNewAddPayPlayerARPPU(newAddPayPlayerARPPU);
        payAllShow.setNewAddPlayerARPPU(newAddPlayerARPPU);
        payAllShow.setNewPayAllMoney(newPayAllMoney);
        payAllShow.setNewPayPlayerNum(newPayPlayerNum);
        payAllShow.setPayPlayerARPPU(payPlayerARPPU);
        payAllShow.setPayPlayerNum(payPlayerNum);
        payAllShow.setTodayAllPayMoney(todayAllPayMoney);

        mys.utilSQL(clazz, EnumSQL.UPDATE, payAllShow);
        return -1;
    }



}
