package com.xcloudeye.stats.dao.driverdao;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.UnknownHostException;

/**
 * Created by joker on 2015/9/7.
 */
public class SingleWonAdvanceMongoConf {
    private static final SingleWonAdvanceMongoConf singleBsMongoConf =
            (new ClassPathXmlApplicationContext("META-INF/mongo-driver-conf.xml")).getBean("singleWonAdvanceMongoConf", SingleWonAdvanceMongoConf.class);

    private String host;
    private Integer port;
    private String dbName = null;
    private String userName;
    private String password;

    /**
     * connectionsPerHost
     * The maximum number of connections allowed per host for this Mongo instance.
     */
    private Integer poolsize;
    /**
     * threadsAllowedToBlockForConnectionMultiplier
     *  this multiplier, multiplied with the connectionsPerHost setting,
     *  gives the maximum number of threads that may be waiting
     *  for a connection to become available from the pool.
     */
    private Integer blocksize;

    private MongoClient mongoClient;
    private DB db;


    public SingleWonAdvanceMongoConf() {
    }

    public SingleWonAdvanceMongoConf(String host, Integer port, String dbName,
                                     String userName, String password, Integer poolsize, Integer blocksize) {
        this.host = host;
        this.port = port;
        this.dbName = dbName;
        this.userName = userName;
        this.password = password;
        this.poolsize = poolsize;
        this.blocksize = blocksize;
        initDao();
    }

    /**
     * Initial mongodb connection parameters
     */
    private void initDao(){
        String dburi = "mongodb://"+userName+":"+password+"@"+host+":"+port+"/?authSource="+dbName;
        System.out.println("dburi---:"+dburi);
        MongoClientOptions.Builder op = new MongoClientOptions.Builder();
        op.connectionsPerHost(poolsize);
        op.threadsAllowedToBlockForConnectionMultiplier(blocksize);
        try {
            mongoClient =  new MongoClient(new MongoClientURI(dburi, op));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        db = mongoClient.getDB(dbName);
    }

    public synchronized static SingleWonAdvanceMongoConf getInstance(){
        return singleBsMongoConf;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public void setMongoClient(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public DB getDb() {
        return db;
    }

    public void setDb(DB db) {
        this.db = db;
    }
}
