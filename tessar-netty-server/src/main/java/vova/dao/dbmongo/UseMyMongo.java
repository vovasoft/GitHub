package vova.dao.dbmongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.junit.Test;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import vova.domain.Player;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import vova.domain.payment.PayMentForKeep;
import vova.domain.payment.PayMentWeek;
import vova.util.Tools;

import javax.print.Doc;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;

/**
 * @author: Vova
 * @create: date 20:43 2017/12/25
 */


public class UseMyMongo {

    public UseMyMongo() {
    }

    //插入数据
    public void insertMongo(Object object) throws ParseException {
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
//        MongoTest customer1 = new MongoTest("vov1a","wang",sdf.parse("2018-05-17"));
//        MongoTest customer2 = new MongoTest("vov2a","wang",sdf.parse("2018-05-18"));
//        MongoTest customer3 = new MongoTest("vov3a","wang",sdf.parse("2018-05-21"));

        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
        MongoTemplate mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
        mongoTemplate.insert(object);
//        mongoTemplate.insert(customer2);
//        mongoTemplate.insert(customer3);
    }

//    private String uid;
//    private long regdate;
//    private long lastdate;
//    private String channel_from;
//    private String gid;
//    private String sub;
//    private String sid;

    //Find count of all players by ID distinct;
    public int findPlayerCountInMongo(String collectionName, String id) {
        int res = 0;
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
        MongoTemplate mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
        DBObject query = new BasicDBObject();
        res = mongoTemplate.getCollection(collectionName).distinct(id, query).size();

        //system.out.println(res);

        return res;
    }

    //Exist in one day
    //这里的计算比较复杂，每个用户登录的时间以秒计算，算法中只能精确到天，数据库中的date没有小时，所以要手动控制一下时间区间
    public boolean findDayInMongo(Player player) throws ParseException {
        String uid = player.getUid();
        String gid = player.getGid();
        String sid = player.getSid();
        String cid = player.getCid();
        Date todayDate = Tools.secToDateByFormat(player.getLastdate());
        long todayTime = todayDate.getTime() / 1000;
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
        MongoTemplate mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
        Query query = new Query();
        query.addCriteria(Criteria.where("uid").is(uid).and("cid").is(cid).and("gid").is(gid).and("sid").is(sid).and("lastdate").gte(todayTime).lt(todayTime + (24 * 3600)));
        Player resPlayer = mongoTemplate.findOne(query, Player.class);
        if (resPlayer == null) {
            //system.out.println("resPlayer is not exist!!!");
            return false;
        } else {
            //system.out.println("resPlayer is exist!!!");
            return true;
        }
    }

    //Exist in one week
    public boolean findWeekInMongo(Player player) throws ParseException {

        String uid = player.getUid();
        Date loginDate = Tools.secToDateByFormat(player.getLastdate());
        String gid = player.getGid();
        String sid = player.getSid();
        String cid = player.getCid();
        Calendar cRegister = Calendar.getInstance();
        cRegister.setTime(loginDate);
        cRegister.get(Calendar.WEEK_OF_YEAR);

        Date mondayOfDate = Tools.getMondayOfDate(loginDate);
        Date sundayOfDate = Tools.getSundayOfDate(loginDate);
        //system.out.println("mondayOfDate........." + mondayOfDate);
        //system.out.println("sundayOfDate........." + sundayOfDate);

        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
        MongoTemplate mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
        Query query = new Query();
        query.addCriteria(Criteria.where("uid").is(uid).and("gid").is(gid).and("cid").is(cid).and("sid")
                .is(sid).and("lastdate").gte(Tools.dateToSec(mondayOfDate)).lt(Tools.dateToSec(sundayOfDate) + (24 * 3600)));

        Player resPlayer = mongoTemplate.findOne(query, Player.class);
        if (resPlayer == null) {
            //system.out.println("resPlayer is not exist!!!");
            return false;
        } else {
            return true;
        }
    }

    //Exist in one Month
    public boolean findMonInMongo(Player player) throws ParseException {

        String uid = player.getUid();
        Date loginDate = Tools.secToDateByFormat(player.getLastdate());
        String cid = player.getCid();
        String gid = player.getGid();
        String sid = player.getSid();

        Date firstMonthOfDate = Tools.getFirstOfMonth(loginDate);
        Date endMonthOfDate = Tools.getLastOfMonth(loginDate);
        //system.out.println("firtMonth........." + Tools.dateToSec(firstMonthOfDate));
        //system.out.println("endMonthOfDate........." + Tools.dateToSec(endMonthOfDate) + (24 * 3600));

        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
        MongoTemplate mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
        Query query = new Query();
        query.addCriteria(Criteria.where("uid").is(uid).and("cid").is(cid).and("gid").is(gid).and("sid")
                .is(sid).and("lastdate").gte(Tools.dateToSec(firstMonthOfDate)).lt(Tools.dateToSec(endMonthOfDate) + (24 * 3600)));
        Player resPlayer = mongoTemplate.findOne(query, Player.class);
        if (resPlayer == null) {
            //system.out.println("Player is not exist!!!");
            return false;
        } else {
            return true;
        }
    }

    public boolean findNeverPayInMongo(PayMentForKeep payMentForKeep){
        String uid = payMentForKeep.getUid();
        String gid = payMentForKeep.getGid();
        String sid = payMentForKeep.getSid();
        String cid = payMentForKeep.getCid();

        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
        MongoTemplate mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
        Query query = new Query();
        query.addCriteria(Criteria.where("uid").is(uid).and("cid").is(cid).and("gid").is(gid).and("sid").is(sid));
        PayMentForKeep res = mongoTemplate.findOne(query, PayMentForKeep.class);
        if (res == null) {
            //system.out.println("None Exist from Day");
            return true;
        } else {
            //system.out.println("False First pay");
            return false;
        }
    }

    //ExistPay in one day week mon
    //这里的计算更复杂，每个用户登录的时间以秒计算，算法中只能精确到天，数据库中的date没有小时，所以要手动控制一下时间区间
    //记录当天是否新增付费
    public boolean findDayPayInMongo(PayMentForKeep payMentForKeep) throws ParseException {
        String uid = payMentForKeep.getUid();
        String gid = payMentForKeep.getGid();
        String sid = payMentForKeep.getSid();
        String cid = payMentForKeep.getCid();
        Date todayDate = Tools.secToDateByFormat(payMentForKeep.getPayTime());
        long todayTime = todayDate.getTime() / 1000;

        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
        MongoTemplate mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
        Query query = new Query();
        query.addCriteria(Criteria.where("uid").is(uid).and("cid").is(cid).and("gid").is(gid).and("sid").is(sid).and("payTime").gte(todayTime).lt(todayTime + (24 * 3600)));
        PayMentForKeep res = mongoTemplate.findOne(query, PayMentForKeep.class);
        if (res == null) {
            //system.out.println("None Exist from Day");
            return true;
        } else {
            //system.out.println("False First pay");
            return false;
        }
    }

    public boolean findWeekPayInMongo(PayMentForKeep payMentForKeep) throws ParseException {
        String uid = payMentForKeep.getUid();
        Date loginDate = Tools.secToDateByFormat(payMentForKeep.getPayTime());
        String gid = payMentForKeep.getGid();
        String sid = payMentForKeep.getSid();
        String cid = payMentForKeep.getCid();
        Calendar cRegister = Calendar.getInstance();
        cRegister.setTime(loginDate);
        cRegister.get(Calendar.WEEK_OF_YEAR);

        Date mondayOfDate = Tools.getMondayOfDate(loginDate);
        Date sundayOfDate = Tools.getSundayOfDate(loginDate);

        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
        MongoTemplate mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
        Query query = new Query();
        query.addCriteria(Criteria.where("uid").is(uid).and("gid").is(gid).and("cid").is(cid).and("sid")
                .is(sid).and("payTime").gte(Tools.dateToSec(mondayOfDate)).lt(Tools.dateToSec(sundayOfDate) + (24 * 3600)));

        PayMentForKeep res = mongoTemplate.findOne(query, PayMentForKeep.class);
        if (res == null) {
            //system.out.println("None Exist from week");
            return true;
        } else {
            //system.out.println("False First pay");
            return false;
        }
    }

    public boolean findMonPayInMongo(PayMentForKeep payMentForKeep) throws ParseException {
        String uid = payMentForKeep.getUid();
        Date loginDate = Tools.secToDateByFormat(payMentForKeep.getPayTime());
        String cid = payMentForKeep.getCid();
        String gid = payMentForKeep.getGid();
        String sid = payMentForKeep.getSid();

        Date firstMonthOfDate = Tools.getFirstOfMonth(loginDate);
        Date endMonthOfDate = Tools.getLastOfMonth(loginDate);
        //system.out.println("firtMonth........." + Tools.dateToSec(firstMonthOfDate));
        //system.out.println("endMonthOfDate........." + Tools.dateToSec(endMonthOfDate) + (24 * 3600));

        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
        MongoTemplate mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
        Query query = new Query();
        query.addCriteria(Criteria.where("uid").is(uid).and("cid").is(cid).and("gid").is(gid).and("sid")
                .is(sid).and("payTime").gte(Tools.dateToSec(firstMonthOfDate)).lt(Tools.dateToSec(endMonthOfDate) + (24 * 3600)));
        PayMentForKeep res = mongoTemplate.findOne(query, PayMentForKeep.class);
        if (res == null) {
            //system.out.println("None Exist from Month");
            return true;
        } else {
            //system.out.println("False First pay");
            return false;
        }
    }

    public PayMentForKeep findOnePayer(PayMentForKeep payMentForKeep) {
        String uid = payMentForKeep.getUid();
        String gid = payMentForKeep.getGid();
        String sid = payMentForKeep.getSid();

        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
        MongoTemplate mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
        Query query = new Query();
        query.addCriteria(Criteria.where("uid").is(uid).and("gid").is(gid).and("sid").is(sid));

        PayMentForKeep pmfk = mongoTemplate.findOne(query, PayMentForKeep.class);

        return pmfk;

    }

    //应该还有问题，有bug
    public float findAllPayMoney(PayMentForKeep payMentForKeep){
        String cid = payMentForKeep.getCid();
        String gid = payMentForKeep.getGid();
        String sid = payMentForKeep.getSid();
        long date = payMentForKeep.getPayTime();


        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
        MongoTemplate mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
        float total = 0;
        Query query = new Query();
        query.addCriteria(Criteria.where("cid").is(cid).and("gid").is(gid).and("sid").is(sid).and("payTime").lte(date));

        List<PayMentForKeep> resList = mongoTemplate.find(query, PayMentForKeep.class);
        for (PayMentForKeep mentForKeep : resList) {
            total+=mentForKeep.getAmount();
        }


        return total;
    }

    public Player findOnePlayer(Player player) {
        String gid = player.getGid();
        String sid = player.getSid();
        String uid = player.getUid();
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
        MongoTemplate mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");

        Query query = new Query();
        query.addCriteria(Criteria.where("uid").is(uid).and("gid").is(gid).and("sid").is(sid));
        Player resPlayer = mongoTemplate.findOne(query, Player.class);
        return resPlayer;
    }


    //获取数据,通过ChannelID,GameID,ServerID,以及时间段start Date\ end date
    public void getPlayersListMongo(String cID, String gID, String sID, Date sDate, Date eDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
        MongoTemplate mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
        Query query = new Query();
        query.addCriteria(Criteria.where("date").gt(sDate).lt(eDate));
        query.addCriteria(Criteria.where("channel_from").is(cID));
        MongoTest mt = mongoTemplate.findOne(query, MongoTest.class);
        List<MongoTest> mtList = mongoTemplate.find(query, MongoTest.class);
        //system.out.println("date::::" + sdf.format(mt.date));

        for (MongoTest test : mtList) {
            //system.out.println("for::::" + sdf.format(test.date));
        }
    }
}
