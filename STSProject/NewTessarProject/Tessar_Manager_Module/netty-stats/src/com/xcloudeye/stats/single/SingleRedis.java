package com.xcloudeye.stats.single;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SingleRedis {
    private static final SingleRedis singleRedis = new SingleRedis();


    public SingleRedis() {
    }

    public static SingleRedis getSingleRedis() {
        return singleRedis;
    }

    public ClassPathXmlApplicationContext getRedisDaoContext() {
        return new ClassPathXmlApplicationContext("META-INF/redis-conf.xml");
    }


}
