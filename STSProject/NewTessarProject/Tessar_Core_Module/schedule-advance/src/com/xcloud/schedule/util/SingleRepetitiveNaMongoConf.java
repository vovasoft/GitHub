package com.xcloud.schedule.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;

/**
 * 五分钟不去重
 * Created by admin on 2016/1/10.
 */
public class SingleRepetitiveNaMongoConf {

    private static final SingleRepetitiveNaMongoConf singleRepetitiveMongoConf = new SingleRepetitiveNaMongoConf();

    private MongoClient active_client = null;
    private MongoDatabase active_mongoDb = null;

    private SingleRepetitiveNaMongoConf() {
        Element element = this.loadXmlElement(GetConfigUtil.configFile);
        initMongoClient(element);
    }


    public synchronized static SingleRepetitiveNaMongoConf getInstance() {
        return singleRepetitiveMongoConf;
    }


    public MongoClient getClient() {
        return active_client;
    }


    public void setClient(MongoClient client) {
        this.active_client = client;
    }


    public MongoDatabase getMongoDb() {
        return active_mongoDb;
    }


    public void setMongoDb(MongoDatabase mongoDb) {
        this.active_mongoDb = mongoDb;
    }



    /**
     * <p>Title: loadXmlElement</p>
     * <p>Description: 载入配置文件</p>
     *
     * @param path
     * @return
     */
    private Element loadXmlElement(String path) {
        SAXReader reader = new SAXReader();
        Element el = null;
        try {
            org.dom4j.Document d = reader.read(new File(path));
            el = d.getRootElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return el;
    }


    /**
     * <p>Title: initMongoClient</p>
     * <p>Description: 初始化Mongo</p>
     *
     * @param el
     */
    private void initMongoClient(Element el) {
        if (el == null) return;
        try {
            Element foo = el.element("mongodb");
            Element mongo = null;
            String gid = null;
            String name = null;
            String retention = null;
            String dburi = null;
            Iterator i = foo.elementIterator("bsdatabase");
            mongo = (Element) i.next();
            gid = mongo.elementText("gid");
            name = mongo.elementText("name");
            retention = mongo.elementText("bloodstrike");
            dburi = mongo.elementText("dburi");
            MongoClientOptions.Builder op = new MongoClientOptions.Builder();
            op.connectionsPerHost(10);
            op.threadsAllowedToBlockForConnectionMultiplier(10);
            this.active_client = new MongoClient(new MongoClientURI(dburi, op));
            this.active_mongoDb = this.active_client.getDatabase(name);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
