package com.xcloudeye.stats.single;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SingleApps {
    private static final SingleApps singleApps = new SingleApps();


    public SingleApps() {
    }

    public static SingleApps getSingleApps() {
        return singleApps;
    }

    public ClassPathXmlApplicationContext getContext() {
        return new ClassPathXmlApplicationContext("META-INF/appid-conf.xml");
    }


}
