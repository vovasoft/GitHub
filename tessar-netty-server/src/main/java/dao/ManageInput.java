package dao;

import dao.dbmongo.UseMyMongo;
import dao.dbsql.UseMySql;
import domain.Player;
import org.springframework.beans.factory.annotation.Autowired;
import util.Tools;

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

    public int HandPlayerDate(Player player){
        String uid = player.getUid();  //获取当前用户id
        Date uLoginDate = Tools.secToDate(player.getLastdate());//获取登录日期
        Date uRegDate = Tools.secToDate(player.getRegdate()); //获取注册信息

        //判断登录时间是否为当日首次
        boolean isExistDay = umm.findDayInMongo(player);
        //判断登录时间是否为当周首次
        boolean isExistWeek = umm.findWeekInMongo(player);
        //判断登录时间是否为当月首次
        boolean isExistMon = umm.findMonInMongo(player);

        //判断三个表是否存在当日当周当月词条，如果没有则insert，如果有update




        //原始数据存入mongodb
        try {
            umm.insertMongo(player);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }


}
