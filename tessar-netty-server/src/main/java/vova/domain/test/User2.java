package vova.domain.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author vova
 * @version Create in 上午1:27 2017/12/21
 */



public class User2 {

    private Date date;
    private int id;
    private String name;
    private int age;

    public User2() {
    }

    public User2(Date date, int uid, String uname, int uage) {

        this.date = date;
        this.id = uid;
        this.name = uname;
        this.age = uage;
    }

    private List<User2> user2s = new ArrayList<User2>();


    public List<User2> getOrders() {
        return user2s;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
