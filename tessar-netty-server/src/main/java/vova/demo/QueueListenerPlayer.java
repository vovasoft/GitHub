package vova.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import vova.dao.manager.ManageGameInput;

import java.io.IOException;
import java.text.ParseException;

/**
 * @author: Vova
 * @create: date 20:48 2018/1/8
 */
public class QueueListenerPlayer<Player> implements RedisQueueListener<Player> {

    public void onMessage(Player value) throws InterruptedException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-mongodb.xml");
        ManageGameInput manageGameInput= (ManageGameInput) ac.getBean("manageGameInput");
        try {
            manageGameInput.HandPlayerData((vova.domain.Player) value);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}