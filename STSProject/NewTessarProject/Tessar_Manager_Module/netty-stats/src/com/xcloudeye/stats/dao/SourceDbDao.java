package com.xcloudeye.stats.dao;

import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.xcloudeye.stats.domain.db.LogInfo;
import com.xcloudeye.stats.domain.db.Payment;
import com.xcloudeye.stats.domain.db.UserInfo;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class SourceDbDao {

    private MongoOperations mongoBsparse;
    private Mongo mongo;

    private static final String LOGINFO_COLLECTION = "loginfo";
    private static final String PAYMENT_COLLECTION = "payment";
    private static final String USERINFO_COLLECTION = "userinfo";

    public SourceDbDao() {
    }

    public MongoOperations getMongoBsparse() {
        return mongoBsparse;
    }

    public void setMongoBsparse(MongoOperations mongoBsparse) {
        this.mongoBsparse = mongoBsparse;
    }

    public Mongo getMongo() {
        return mongo;
    }

    public void setMongo(Mongo mongo) {
        this.mongo = mongo;
    }

    public SourceDbDao(MongoOperations mongoBsparse, Mongo mongo) {
        this.mongoBsparse = mongoBsparse;
        this.mongo = mongo;
    }

    /**
     * <p>Title: closeMongoClient</p>
     * <p>Description: close mongo connection</p>
     */
    public void closeMongoClient() {
        this.mongo.close();
    }

    /**
     * <p>Title: readDistinctByKey</p>
     * <p>Description: 根据条件和指定的字段去重</p>
     *
     * @param key   指定去重的字段
     * @param query 查询条件
     * @return 去重后的数组
     */
    @SuppressWarnings("unchecked")
    public <E> List<E> readDistinctByKey(String key, DBObject query) {
        return this.mongoBsparse.getCollection(LOGINFO_COLLECTION).distinct(key, query);
    }

    /**
     * <p>Title: readDistinctByKeyInUserInfo</p>
     * <p>Description: 根据条件和指定的字段去重</p>
     *
     * @param key   指定去重的字段
     * @param query 查询条件
     * @return 去重后的数组
     */
    @SuppressWarnings("unchecked")
    public <E> List<E> readDistinctByKeyInUserInfo(String key, DBObject query) {
        return this.mongoBsparse.getCollection(USERINFO_COLLECTION).distinct(key, query);
    }


    /**
     * <p>Title: sum</p>
     * <p>Description: 根据查询条件求和</p>
     *
     * @param query 查询条件
     * @return
     */
    public long sum(Query query) {
        return this.mongoBsparse.count(query, LogInfo.class, LOGINFO_COLLECTION);
    }

    /**
     * <p>Title: sum</p>
     * <p>Description: 根据查询条件求和</p>
     *
     * @param query 查询条件
     * @return
     */
    public long sumUserInfo(Query query) {
        return this.mongoBsparse.count(query, UserInfo.class, USERINFO_COLLECTION);
    }


    /**
     * <p>Title: readPaymentDistinctByKey</p>
     * <p>Description: 根据人输入都得时间和去重字段去重指定的collection</p>
     *
     * @param key
     * @param query
     * @return
     */
    @SuppressWarnings("unchecked")
    public <E> List<E> readPaymentDistinctByKey(String key, DBObject query) {
        return this.mongoBsparse.getCollection(PAYMENT_COLLECTION).distinct(key, query);
    }


    /**
     * <p>Title: readPayment</p>
     * <p>Description: </p>
     *
     * @param query
     * @return
     */
    public List<Payment> readPayment(Query query) {
        return this.mongoBsparse.find(query, Payment.class, PAYMENT_COLLECTION);
    }

    /**
     * <p>Title: sum</p>
     * <p>Description: 根据查询条件求和</p>
     *
     * @param query 查询条件
     * @return
     */
    public <E> List<E> sumPay(String key,DBObject query) {
        return this.mongoBsparse.getCollection(PAYMENT_COLLECTION).distinct(key, query);
    }


}
