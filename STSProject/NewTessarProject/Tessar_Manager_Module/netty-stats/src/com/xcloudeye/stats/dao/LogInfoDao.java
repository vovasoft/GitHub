package com.xcloudeye.stats.dao;

import com.mongodb.DBObject;
import com.xcloudeye.stats.domain.db.LogInfo;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class LogInfoDao implements ILogInfoDao {

    private static final String LOGINFO_COLLECTION = "loginfo";

    private MongoOperations mongoPlatformOps;

    private MongoOperations mongoBloodstrikeOps;


    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param mongoPlatformOps
     * @param mongoBloodstrikeOps
     */
    public LogInfoDao(MongoOperations mongoPlatformOps,
                      MongoOperations mongoBloodstrikeOps) {
        this.mongoPlatformOps = mongoPlatformOps;
        this.mongoBloodstrikeOps = mongoBloodstrikeOps;
    }

    public MongoOperations getMongoPlatformOps() {
        return mongoPlatformOps;
    }

    public void setMongoPlatformOps(MongoOperations mongoPlatformOps) {
        this.mongoPlatformOps = mongoPlatformOps;
    }

    public MongoOperations getMongoBloodstrikeOps() {
        return mongoBloodstrikeOps;
    }

    public void setMongoBloodstrikeOps(MongoOperations mongoBloodstrikeOps) {
        this.mongoBloodstrikeOps = mongoBloodstrikeOps;
    }

    public int readMoreDistinctByQuery(String key, DBObject query) {
        return this.mongoBloodstrikeOps.getCollection(LOGINFO_COLLECTION).distinct(key, query).size();
    }

    @SuppressWarnings("unchecked")
    public <E> List<E> readDistinctBloodstrike(String key, DBObject query) {
        return this.mongoBloodstrikeOps.getCollection(LOGINFO_COLLECTION).distinct(key, query);
    }

    @SuppressWarnings("unchecked")
    public <E> List<E> readDistinctPlatform(String key, DBObject query) {
        return this.mongoPlatformOps.getCollection(LOGINFO_COLLECTION).distinct(key, query);
    }


    public long sumPlatform(Query query) {
        return this.mongoPlatformOps.count(query, LogInfo.class, LOGINFO_COLLECTION);
    }

    public long sumBloodstrike(Query query) {
        return this.mongoBloodstrikeOps.count(query, LogInfo.class, LOGINFO_COLLECTION);
    }
}
