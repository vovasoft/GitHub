package com.xcloudeye.stats.dao.driverdao;

import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.xcloudeye.stats.util.StaticValueUtil;


/**
 * This class file is used to test java-mongodb-driver
 */
public class AdvanceDbDriverDao {
    private static final String PAY_HOBBY_COLLECTION = "pay_hobby";
    private static final String PAY_HOBBY_GENERIC_COLLECTION = "pay_hobby_generic";

    private MongoClient mongoClient;
    private DB db;

    public AdvanceDbDriverDao(int appid) {
        switch (appid) {
            case StaticValueUtil.BLOODSTRIK_ID:
                SingleBsAdvanceMongoConf singleBsAdvanceMongoConf = SingleBsAdvanceMongoConf.getInstance();
                db = singleBsAdvanceMongoConf.getDb();
                mongoClient = singleBsAdvanceMongoConf.getMongoClient();
                break;
            case StaticValueUtil.NARUTO_ID:
                SingleWonAdvanceMongoConf singleWonAdvanceMongoConf = SingleWonAdvanceMongoConf.getInstance();
                db = singleWonAdvanceMongoConf.getDb();
                mongoClient = singleWonAdvanceMongoConf.getMongoClient();
                break;
            default:
                break;
        }
    }

    /**
     * @return return pay_hobby_genneric
     */
    public DBCursor getPayHobbyGeneric() {
        return db.getCollection(PAY_HOBBY_GENERIC_COLLECTION).find();
    }


	/*@SuppressWarnings("unchecked")
    public List<String> getDistinctList(String key, DBObject filter){
		return db.getCollection(LOGINFO_COLLECTION).distinct(key, filter);
	}*/

}
