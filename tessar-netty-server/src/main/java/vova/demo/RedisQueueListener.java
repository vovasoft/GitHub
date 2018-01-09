package vova.demo;

/**
 * @author: Vova
 * @create: date 20:48 2018/1/8
 */
public interface RedisQueueListener<T> {

    public void onMessage(T value) throws InterruptedException;
}
