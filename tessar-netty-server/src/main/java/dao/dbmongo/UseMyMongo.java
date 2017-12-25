package dao.dbmongo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author: Vova
 * @create: date 20:43 2017/12/25
 */

@Component
public class UseMyMongo {

    public UseMyMongo() {
    }

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
}
