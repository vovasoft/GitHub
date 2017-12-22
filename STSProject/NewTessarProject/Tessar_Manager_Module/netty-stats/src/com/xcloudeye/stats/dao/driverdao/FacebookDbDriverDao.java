package com.xcloudeye.stats.dao.driverdao;

import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;


/**
 * This class file is used to test java-mongodb-driver
 */
public class FacebookDbDriverDao {
    private static final String FACEBOOK_COLLECTION = "facebook.userinfo";


    private MongoClient mongoClient;
    private DB db;

    public FacebookDbDriverDao() {
        SingleFacebookMongoConf singleFacebookMongoConf = SingleFacebookMongoConf.getInstance();
        mongoClient = singleFacebookMongoConf.getMongoClient();
        db = singleFacebookMongoConf.getDb();
    }


    public DBObject getOneFacebookUserInfo(DBObject filter) {
        return db.getCollection(FACEBOOK_COLLECTION).findOne(filter);
    }

}
