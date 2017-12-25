package dao.dbsql;

import dao.dbmongo.MongoTest;


import domain.*;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class debugTest {

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
        UseMySql useMySql = new UseMySql();
        useMySql.insert(user);
    }


    @Test
    public void fun1() throws IOException, ClassNotFoundException {
        User user = null;


        UseMySql useMySql = new UseMySql();
        user = (User) useMySql.utilSQL(User.class,EnumSQL.SELECT,1);

        System.out.println(user.getId()+",,,,,,,"+user.getName()+user.getAge());

        user.setAge(user.getAge()+1);
        try {
            useMySql.utilSQL(user, EnumSQL.UPDATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fun2() throws IOException, ParseException {
//        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
//
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
//        String dstr="2017-11-25";

        Date date = new Date(System.currentTimeMillis());
        User2 user2 = new User2(date,1,"name",11);
        UseMySql useMySql = new UseMySql();
        useMySql.insert(user2);
    }
    @Test
    public void fun3() throws ParseException, IOException {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        String dstr="2017-11-25";

        Date date2 = sdf.parse(dstr);
        User2 user2 = new User2(date2,1,"name",11);
        UseMySql useMySql = new UseMySql();
        useMySql.insert(user2);
    }

    @Test  //find one by date
    public void fun4() throws IOException, ClassNotFoundException {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        UseMySql useMySql = new UseMySql();
        User2 user2 = (User2) useMySql.utilSQL(User2.class, EnumSQL.SELECT, "2017-11-25");

        System.out.println(sdf.format(user2.getDate()));
    }

    @Test   //find List
    public void fun5() throws IOException, ClassNotFoundException {
        QueryDate queryDate = new QueryDate("2017-5-12","2017-12-26");

        UseMySql useMySql = new UseMySql();

        List<User2> ulist =useMySql.utilSQL(User2.class,EnumSQL.SELECTLIST,queryDate);
        for (User2 user2 : ulist) {
            System.out.println(user2.getDate());
        }
    }

    @Test
    public void insertMongo() {
        MongoTest customer1 = new MongoTest("vov1a","wang");
        MongoTest customer2 = new MongoTest("vov2a","wang");
        MongoTest customer3 = new MongoTest("vov3a","wang");

        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
        MongoTemplate mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
        mongoTemplate.insert(customer1);

    }

//    @Test
//    public void insertMongoBigTable() throws ParseException {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
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
}
