package vova.dao.test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import vova.SpringConfig;
import vova.dao.ManageGameInput;
import vova.dao.dbmongo.MongoTest;


import vova.dao.dbmongo.UseMyMongo;
import vova.dao.dbsql.EnumSQL;
import vova.dao.dbsql.UseMySql;
import vova.domain.*;

import vova.domain.newadd.NewAddDay;
import vova.domain.test.User;
import vova.domain.test.User2;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vova.util.Tools;


import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= SpringConfig.class)
public class debugTest {


    @Autowired
    ManageGameInput mgi;


    @Test
    public void fun() throws IOException {
//        User user = new User(13,"vova",13);
//        UseMySql.insert(user);
//UseMySql

//        UseMySql useMySql;
//
//        Date date = new Date(System.currentTimeMillis());
//        Play_ExistTable pet = new Play_ExistTable(date,123,"1,2,3","1k3kk4"
//        ,"1,3,4,5,6,,,1,1,,23");
//
//        useMySql.insert(pet);

        User user = new User(1,"vova",123);
        vova.dao.dbsql.UseMySql useMySql = new vova.dao.dbsql.UseMySql();
        useMySql.insert(user);
    }


//    @Test
//    public void fun0(){
//        System.out.println(mgi);
//    }

    @Test
    public void fun1() throws IOException, ClassNotFoundException {
        User user = null;


        vova.dao.dbsql.UseMySql useMySql = new vova.dao.dbsql.UseMySql();
        user = (User) useMySql.utilSQL(User.class, EnumSQL.SELECT,1);

        System.out.println(user.getId()+",,,,,,,"+user.getName()+user.getAge());

        user.setAge(user.getAge()+1);
        try {
            useMySql.utilSQL(user, EnumSQL.UPDATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void funFindSelect() throws IOException, ParseException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
        UseMyMongo umm = (UseMyMongo) ac.getBean("useMyMongo");
        UseMySql mys = (UseMySql) ac.getBean("useMySql");

        SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
        Date thisday = new Date();
        Date uLoginDate = Tools.secToDate(1514184954);
        NewAddDay findSeedDay = new NewAddDay();
        findSeedDay.setcID("ngBrazil");
        findSeedDay.setgID("bloodstrke");
        findSeedDay.setsID("0");
        findSeedDay.setDateID( uLoginDate);

        NewAddDay tmp1 = (NewAddDay) mys.utilSQL(NewAddDay.class, EnumSQL.SELECT, findSeedDay);
        System.out.println(tmp1);
    }

    @Test
    public void fun2() throws IOException, ParseException {
//        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
//
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
//        String dstr="2017-11-25";

        Date date = new Date(System.currentTimeMillis());
        User2 user2 = new User2(date,1,"name",11);
        vova.dao.dbsql.UseMySql useMySql = new vova.dao.dbsql.UseMySql();
        useMySql.insert(user2);
    }
    @Test
    public void fun3() throws ParseException, IOException {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        String dstr="2017-11-25";

        Date date2 = sdf.parse(dstr);
        User2 user2 = new User2(date2,1,"name",11);
        vova.dao.dbsql.UseMySql useMySql = new vova.dao.dbsql.UseMySql();
        useMySql.insert(user2);
    }

    @Test  //find one by date
    public void fun4() throws IOException, ClassNotFoundException {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        vova.dao.dbsql.UseMySql useMySql = new vova.dao.dbsql.UseMySql();
        User2 user2 = (User2) useMySql.utilSQL(User2.class, EnumSQL.SELECT, "2017-11-25");

        System.out.println(sdf.format(user2.getDate()));
    }

    @Test   //find List
    public void fun5() throws IOException, ClassNotFoundException {
        QueryDate queryDate = new QueryDate("2017-5-12","2017-12-26");

        vova.dao.dbsql.UseMySql useMySql = new UseMySql();

        List<User2> ulist =useMySql.utilSQL(User2.class,EnumSQL.SELECTLIST,queryDate);
        for (User2 user2 : ulist) {
            System.out.println(user2.getDate());
        }
    }
    @Test   //find List
    public void funExtend() throws IOException, ClassNotFoundException, ParseException {
        String resoure = "batis-conf.xml";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        InputStream inputStream = Resources.getResourceAsStream(resoure);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession ss = sf.openSession();
        NewAddDay nn = new NewAddDay(0,sdf.parse("2017-11-02"),"cid2","gid1","sid1",123,2,1,3,2);
        ss.insert("NewAddDay.insert",nn);
        ss.commit();
        ss.close();
    }
    //findByKey
    @Test   //find List
    public void findByKey() throws IOException, ClassNotFoundException, ParseException {
        String resoure = "batis-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resoure);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession ss = sf.openSession();
        NewAddDay nn = new NewAddDay();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      //  nn.setId(0);
        nn.setcID("cid2");
        nn.setgID("gid1");
        nn.setsID("sid1");
        nn.setDateID(sdf.parse("2017-11-2"));
        NewAddDay res = ss.selectOne("NewAddDay.findByObject",nn);
        ss.commit();
        ss.close();

        System.out.println("getcID::::");
    }

    @Test   //find List
    public void updatedaytable() throws IOException, ClassNotFoundException, ParseException {
        String resoure = "batis-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resoure);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession ss = sf.openSession();
        NewAddDay nn = new NewAddDay();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //  nn.setId(0);
        nn.setcID("cid2");
        nn.setgID("gid1");
        nn.setsID("sid1");
        nn.setDateID(sdf.parse("2017-11-2"));
        nn.setActiveNum(1);
        nn.setAllPlayerNum(1);
        nn.setNewAddNum(1);

        NewAddDay res = ss.selectOne("NewAddDay.findByObject",nn);
        nn.setId(res.getId());
        System.out.println("id:"+res.getId());
        int ress =ss.update("NewAddDay.update",nn);
        ss.commit();
        ss.close();

        System.out.println("getcID::::"+ress);
    }
    @Test
    public void findPlayerCountInMongo(){
        long res=0;
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
        MongoTemplate mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
        DBObject query = new BasicDBObject();
        int count=mongoTemplate.getCollection("player").distinct("uid", query).size();

        System.out.println(count);

    }
    @Test
    public void insertMongo() throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        MongoTest customer1 = new MongoTest("vov1a","wang",sdf.parse("2018-05-17"));
        MongoTest customer2 = new MongoTest("vov2a","wang",sdf.parse("2018-05-18"));
        MongoTest customer3 = new MongoTest("vov3a","wang",sdf.parse("2018-05-21"));

        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
        MongoTemplate mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
        mongoTemplate.insert(customer1);
        mongoTemplate.insert(customer2);
        mongoTemplate.insert(customer3);
    }

    @Test
    public void findMongo() throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
        MongoTemplate mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
        Query query = new Query();
        query.addCriteria(Criteria.where("date").gt(sdf.parse("2018-05-13")).lt(sdf.parse("2018-05-17")));
        MongoTest mt = mongoTemplate.findOne(query, MongoTest.class);
        List<MongoTest> mtList = mongoTemplate.find(query, MongoTest.class);
        System.out.println("date::::"+sdf.format(mt.date));

        for (MongoTest test : mtList) {
            System.out.println("for::::"+sdf.format(test.date));
        }

    }

//    @Test
//    public void insertMongoBigTable() throws ParseException {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//ObjectId("5a  40  c2  67  70  70  93  40  48  20  52  65")
//        Date time = new Date();
//        System.out.println(time.getTime());
//
//        System.out.println(simpleDateFormat.format(time));
//        String timeID = simpleDateFormat.format(time);
//
//        //初始化大表数据，ID为创建日期的time
//        long timeIDLong = simpleDateFormat.parse(timeID).getTime();
//        int dayNum = 60;
//        long[] dayArray = new long[dayNum];
//
//
//        BigTable bt1 = new BigTable(timeIDLong, dayArray, dayNum);
//
//        timeIDLong = simpleDateFormat.parse("2017-01-01").getTime();
//
//        BigTable bt2 = new BigTable(timeIDLong, dayArray, dayNum);
//        timeIDLong = simpleDateFormat.parse("2017-01-02").getTime();
//        BigTable bt3 = new BigTable(timeIDLong, dayArray, dayNum);
//        timeIDLong = simpleDateFormat.parse("2017-01-03").getTime();
//        BigTable bt4 = new BigTable(timeIDLong, dayArray, dayNum);
//        timeIDLong = simpleDateFormat.parse("2017-01-04").getTime();
//        BigTable bt5 = new BigTable(timeIDLong, dayArray, dayNum);
//
//
//        //   System.out.println("daylist"+dayArray);
//
//        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
//        MongoTemplate mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
//
//        mongoTemplate.insert(bt1);
//        mongoTemplate.insert(bt2);
//        mongoTemplate.insert(bt3);
//        mongoTemplate.insert(bt4);
//
//    }

    @Test
    public void weekDate(){
        Date date = new Date(System.currentTimeMillis());

        System.out.println(Tools.getSundayOfDate(date));
        System.out.println(Tools.getMondayOfDate(date));
    }

    @Test
    public void monDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2026-1-1");

        System.out.println(Tools.getFirstOfMonth(date));
        System.out.println(Tools.getLastOfMonth(date));
    }
}
