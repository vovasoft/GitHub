package com.xcloudeye.stats.dao;

import com.mongodb.DBObject;
import com.xcloudeye.stats.domain.db.Payment;
import com.xcloudeye.stats.domain.db.PaymentGroup;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class PaymentMasterDao implements IPaymentDao {

    private static final String PAYMENT_COLLECTION = "payment";

    private MongoOperations mongoOps;


    /**
     * @param mongoOps
     */
    public PaymentMasterDao(MongoOperations mongoOps) {
        this.mongoOps = mongoOps;
    }

    public MongoOperations getMongoOps() {
        return mongoOps;
    }

    public void setMongoOps(MongoOperations mongoOps) {
        this.mongoOps = mongoOps;
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


    public int readGroupByQuery(int start, int end, String gid) {
        Aggregation agg = newAggregation(
                match(Criteria.where("date").gt(start).lte(end).and("game").is(gid)),
                group("userid").count().as("count")
        );
        AggregationResults<PaymentGroup> groupResults
                = mongoOps.aggregate(agg, Payment.class, PaymentGroup.class);
        List<PaymentGroup> result = groupResults.getMappedResults();
        int total = 0;
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getCount() == 1) {
                total++;
            }
        }
        return total;
    }


}
