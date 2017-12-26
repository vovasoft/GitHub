package dao.dbmongo;

import domain.Player;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import util.Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author: Vova
 * @create: date 20:43 2017/12/25
 */

@Component
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

    public boolean findDayInMongo(Player player) {
        String uid = player.getUid();
        String gid = player.getGid();
        String sid = player.getSid();
        String cid = player.getChannel_from();
        Date loginDate = Tools.secToDate(player.getLastdate());
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
        MongoTemplate mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
        Query query = new Query();
        query.addCriteria(Criteria.where("uid").is(uid).and("cid").is(cid).and("gid").is(gid).and(sid).is(sid).and("dayDate").is(loginDate));
        Player resPlayer = mongoTemplate.findOne(query, Player.class);
        if (resPlayer == null) {
            System.out.println("resPlayer is not exist!!!");
            return false;
        } else {
            System.out.println("resPlayer is exist!!!");
            return true;
        }
    }

    public boolean findWeekInMongo(Player player) {

        String uid = player.getUid();
        Date loginDate = Tools.secToDate(player.getLastdate());
        String gid = player.getGid();
        String sid = player.getSid();
        String cid = player.getChannel_from();
        Calendar cRegister = Calendar.getInstance();
        cRegister.setTime(loginDate);
        cRegister.get(Calendar.WEEK_OF_YEAR);

        Date mondayOfDate = Tools.getMondayOfDate(loginDate);
        Date sundayOfDate = Tools.getSundayOfDate(loginDate);

        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
        MongoTemplate mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
        Query query = new Query();
        query.addCriteria(Criteria.where("uid").is(uid).and("gid").and("cid").is(cid).is(gid).and(sid).is(sid).and("MonDate").gte(mondayOfDate).lte(sundayOfDate));
        Player resPlayer = mongoTemplate.findOne(query, Player.class);
        if (resPlayer == null) {
            System.out.println("resPlayer is not exist!!!");
            return false;
        } else {
            return true;
        }
    }

    public boolean findMonInMongo(Player player) {

        String uid = player.getUid();
        Date loginDate = Tools.secToDate(player.getLastdate());
        String cid = player.getChannel_from();
        String gid = player.getGid();
        String sid = player.getSid();


        Calendar cRegister = Calendar.getInstance();
        cRegister.setTime(loginDate);
        cRegister.get(Calendar.WEEK_OF_YEAR);

        Date firstMonthOfDate = Tools.getMondayOfDate(loginDate);
        Date endMonthOfDate = Tools.getSundayOfDate(loginDate);

        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
        MongoTemplate mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
        Query query = new Query();
        query.addCriteria(Criteria.where("uid").is(uid).and("cid").is(cid).and("gid").is(gid).and(sid).is(sid).and("MonDate").gte(firstMonthOfDate).lte(endMonthOfDate));
        Player resPlayer = mongoTemplate.findOne(query, Player.class);
        if (resPlayer == null) {
            System.out.println("Player is not exist!!!");
            return false;
        } else {
            return true;
        }
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
        System.out.println("date::::" + sdf.format(mt.date));

        for (MongoTest test : mtList) {
            System.out.println("for::::" + sdf.format(test.date));
        }
    }
}