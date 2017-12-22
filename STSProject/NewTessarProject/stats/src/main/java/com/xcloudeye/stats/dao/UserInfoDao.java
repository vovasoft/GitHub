package com.xcloudeye.stats.dao;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.Mongo;
import com.xcloudeye.stats.domain.db.UserInfo;

public class UserInfoDao {
    private static final String USERINFO_COLLECTION = "userinfo";

    private MongoOperations mongoOps;
    private Mongo mongo;

    public UserInfoDao(MongoOperations mongoOps, Mongo mongo) {
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

    public List<UserInfo> readMoreByQuery(Query query) {
        return this.mongoOps.find(query, UserInfo.class, USERINFO_COLLECTION);
    }
}
