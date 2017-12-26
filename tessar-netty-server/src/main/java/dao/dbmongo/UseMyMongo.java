package dao.dbmongo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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


    //获取数据,通过ChannelID,GameID,ServerID,以及时间段start Date\ end date
    public void getPlayersListMongo(String cID, String gID, String sID, Date sDate,Date eDate) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
        MongoTemplate mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
        Query query = new Query();
        query.addCriteria(Criteria.where("date").gt(sDate).lt(eDate));
        query.addCriteria(Criteria.where("channel_from").is(cID));
//query.addCriteria(Criteria.where().)
        MongoTest mt = mongoTemplate.findOne(query, MongoTest.class);
        List<MongoTest> mtList = mongoTemplate.find(query, MongoTest.class);
        System.out.println("date::::"+sdf.format(mt.date));

        for (MongoTest test : mtList) {
            System.out.println("for::::"+sdf.format(test.date));
        }
    }
}
