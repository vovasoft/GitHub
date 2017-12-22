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
 * 不去重五分钟调用
 * Created by admin on 2016/1/7.
 *
 */
public class SingleRepetitiveNaMongoUser {
    private static final SingleRepetitiveNaMongoUser singleRepetitiveMongoUser = new SingleRepetitiveNaMongoUser();

    private MongoClient ac_client_user;
    private MongoDatabase activedb;




    private SingleRepetitiveNaMongoUser() {
        Element element = this.loadXmlElement(GetConfigUtil.configFile);
        initMongoClient(element);
    }




    public synchronized static SingleRepetitiveNaMongoUser getInstance(){
        return singleRepetitiveMongoUser;
    }
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
     * @param el
     */
    private void initMongoClient(Element el) {
        if(el == null) return;
        try {
            Element foo = el.element("mongodb");
            Element mongo = null;
            String name = null;
            String dburi = null;
            Iterator i = foo.elementIterator("nativedatabase");
            mongo = (Element) i.next();
            name = mongo.elementText("name");
            dburi = mongo.elementText("dburi");

            MongoClientOptions.Builder op = new MongoClientOptions.Builder();
            op.connectionsPerHost(10);
            op.threadsAllowedToBlockForConnectionMultiplier(10);
            this.ac_client_user = new MongoClient(new MongoClientURI(dburi, op));
//            this.activedb = this.ac_client_user.getDatabase(name);
            this.activedb = this.ac_client_user.getDatabase(name);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MongoDatabase getActivedb() {
        return activedb;
    }

    public void setActivedb(MongoDatabase activedb) {
        this.activedb = activedb;
    }

    public MongoClient getClient_user() {
        return ac_client_user;
    }

    public void setClient_user(MongoClient client_user) {
        this.ac_client_user = client_user;
    }
}
