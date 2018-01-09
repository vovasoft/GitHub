package vova.demo;

import vova.demo.RedisQueueListener;

/**
 * @author: Vova
 * @create: date 20:48 2018/1/8
 */
public class QueueListener<String> implements RedisQueueListener<String> {

    public void onMessage(String value) throws InterruptedException {
        System.out.println(value);
    }
}