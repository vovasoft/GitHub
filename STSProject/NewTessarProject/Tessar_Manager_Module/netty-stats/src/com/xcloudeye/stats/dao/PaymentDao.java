package com.xcloudeye.stats.dao;

import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.xcloudeye.stats.domain.db.Payment;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class PaymentDao implements IPaymentDao {

    private static final String PAYMENT_COLLECTION = "payment";


    private MongoOperations mongoOps;
    private Mongo mongo;

    /**
     * @param mongoOps
     */
    public PaymentDao(MongoOperations mongoOps, Mongo mongo) {
        this.mongoOps = mongoOps;
        this.mongo = mongo;
    }

    public MongoOperations getMongoOps() {
        return mongoOps;
    }

    public void setMongoOps(MongoOperations mongoOps) {
        this.mongoOps = mongoOps;
    }

    public Mongo getMongo() {
        return mongo;
    }

    public void setMongo(Mongo mongo) {
        this.mongo = mongo;
    }


    @Override
    public Payment readByQuery(Query query) {
        return this.mongoOps.findOne(query, Payment.class, PAYMENT_COLLECTION);
    }


    @Override
    public List<Payment> readMoreByQuery(Query query) {
        return this.mongoOps.find(query, Payment.class, PAYMENT_COLLECTION);
    }


    /**
     * 根据query查询数据，再根据key将值为key的字段去重
     */
    @Override
    public <E> List<E> readMoreDistinctByQuery(String key, DBObject query) {
        return this.mongoOps.getCollection(PAYMENT_COLLECTION).distinct(key, query);
    }

}