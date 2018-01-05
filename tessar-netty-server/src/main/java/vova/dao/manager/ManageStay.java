package vova.dao.manager;

import vova.dao.dbsql.EnumSQL;
import vova.dao.dbsql.UseMySql;
import vova.domain.stayman.StayParent;
import vova.util.Tools;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Vova
 * @create: date 11:53 2018/1/5
 */
public class ManageStay {
    public ManageStay() {
    }

    private static Lock lock = new ReentrantLock();// 锁对象

    public static int manageStayData(Date firstDate, Date lastDate, String cid, String gid, String sid, int newAddSomeNum, boolean dayExist, boolean weekExist, boolean MonExist,
                                     UseMySql mys, Class clazz) throws IOException {

        try {
            lock.lockInterruptibly();
            try {
                StayParent findSeed = new StayParent();
                String clazzName = clazz.getSimpleName();
                int flag = 0;
                String stayStr = "";
                Date thisDate = null;

                if (clazzName.equals("StayDay") || clazzName.equals("StayPayDay")) {
                    if (dayExist) return -2;                     //当本日登录过一次，将不再记录，避免重复计算
                    stayStr = Tools.numArrayToStr(new int[30]);    //初始化数据字段
                    thisDate = firstDate;
                    flag = 1;
                } else if (clazzName.equals("StayMon") || clazzName.equals("StayPayMon")) {
                    if (MonExist) return -2;
                    stayStr = Tools.numArrayToStr(new int[8]);
                    thisDate = Tools.getFirstOfMonth(firstDate);
                    flag = 2;
                } else if (clazzName.equals("StayWeek") || clazzName.equals("StayPayWeek")) {
                    if (weekExist) return -2;
                    stayStr = Tools.numArrayToStr(new int[4]);
                    thisDate = Tools.getMondayOfDate(firstDate);
                    flag = 3;
                }

                findSeed.setcID(cid);
                findSeed.setgID(gid);
                findSeed.setsID(sid);
                findSeed.setDateID(thisDate);
                StayParent tmp = (StayParent) mys.utilSQL(clazz, EnumSQL.SELECT, findSeed);  //表的第一位为注册时间，一个注册记录当天注册用户的留存情况

                if (tmp == null) {
                    StayParent sp = new StayParent(0, thisDate,
                            cid,
                            gid,
                            sid,
                            0,
                            stayStr);
                    mys.utilSQL(clazz, EnumSQL.INSERT, sp);
                    tmp = (StayParent) mys.utilSQL(clazz, EnumSQL.SELECT, findSeed);
                }
                if (flag == 1) {
                    tmp.setNewAddNum(tmp.getNewAddNum() + newAddSomeNum);
                } else if (flag == 2) {
                    tmp.setNewAddNum(tmp.getNewAddNum() + newAddSomeNum);
                } else if (flag == 3) {
                    tmp.setNewAddNum(tmp.getNewAddNum() + newAddSomeNum);
                }
                mys.utilSQL(clazz, EnumSQL.UPDATE, tmp);
                //得到tmp再对表进行更新
                String updateStr = tmp.getStayList();
                int[] updateInt = Tools.strToNumArray(updateStr, ",");
                int index = Tools.countTwoDateSpace(firstDate, lastDate, clazz);
                if (index == 0 && index - 1 < updateInt.length) {
                    return -1;
                }
                updateInt[index - 1]++;
                tmp.setStayList(Tools.numArrayToStr(updateInt));
                mys.utilSQL(clazz, EnumSQL.UPDATE, tmp);

                return 1;
            } finally {
                lock.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return -1;
    }

}
