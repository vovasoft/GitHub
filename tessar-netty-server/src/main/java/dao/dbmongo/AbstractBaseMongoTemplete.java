package dao.dbmongo;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 *
 * @author:    Vova
 * @Data:      11:32 2017/12/25
 *
 */
public class AbstractBaseMongoTemplete implements ApplicationContextAware {
    public AbstractBaseMongoTemplete(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    protected MongoTemplate mongoTemplate;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        MongoTemplate mongoTemplate = applicationContext.getBean("mongoTemplate", MongoTemplate.class);
        setMongoTemplate(mongoTemplate);
    }
}
