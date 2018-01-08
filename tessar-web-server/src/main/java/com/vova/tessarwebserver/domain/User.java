package com.vova.tessarwebserver.domain;

/**
 * @author: Vova
 * @create: date 15:30 2018/1/8
 */
public class User {
    private int id;
    private String name;
    private String passwd;

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

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public User() {

    }

    public User(int id, String name, String passwd) {

        this.id = id;
        this.name = name;
        this.passwd = passwd;
    }
}
