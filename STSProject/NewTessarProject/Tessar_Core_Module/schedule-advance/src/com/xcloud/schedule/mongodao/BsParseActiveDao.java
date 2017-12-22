package com.xcloud.schedule.mongodao;

import com.google.gson.Gson;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import com.xcloud.schedule.appdomain.*;
import com.xcloud.schedule.logic.FiveAllLogic;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 五分钟不去重,连接mongodb
 * Created by admin on 2016/1/7.
 */
public class BsParseActiveDao {
    private static final Logger logger = LoggerFactory.getLogger(BsParseActiveDao.class);
    private MongoClient client;

    public MongoClient getClient() {
        return client;
    }

    public void setClient(MongoClient client) {
        this.client = client;
    }


    private MongoDatabase activedb_bsparse;


    private MongoCollection<Document> five_repeat;

    public BsParseActiveDao(MongoClient ac_client_user, MongoDatabase activedb_bsparse) {
       this.client = ac_client_user;
       this.activedb_bsparse = activedb_bsparse;
       initDB();
    }


    /**
     * 初始化mongo
     */
    public void  initDB() {
        five_repeat = activedb_bsparse.getCollection("five_repeat");

    }

    /**
     * <p>Title: insert</p>
     * <p>Description: 向mongo中插入接口的document数据</p>
     *
     * @param fiveAllOut 插入到库中的document对象
     */
    public void insert(FiveAllLogic fiveAllOut) {
        try {
            Gson gson = new Gson();
            DBObject dbObject = (DBObject) JSON.parse(gson.toJson(fiveAllOut));
            Document doc = Document.parse(dbObject.toString());

            five_repeat.insertOne(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * 不去重五分钟活跃
    * */
    public void insertloginfo(repetitiveFiveOut allactiveout) {
        try {
            Gson gson = new Gson();
            DBObject dbObject = (DBObject) JSON.parse(gson.toJson(allactiveout));
            Document doc = Document.parse(dbObject.toString());
            System.out.println(doc);
            logger.info("five_repeat: "+doc);
            five_repeat.insertOne(doc);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
