package com.xcloudeye.stats.single;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SingleMongo {
	private static final SingleMongo singleMongo = new SingleMongo();
	
	
	public SingleMongo() {
	}

	public static SingleMongo getSinglemongo() {
		return singleMongo;
	}

	public  ClassPathXmlApplicationContext getDaoContext(){
		return new ClassPathXmlApplicationContext("META-INF/mongo-config.xml");
	}
	
}
