package com.vova.webserver.db;

import java.io.Serializable;

/**
 * @author: Vova
 * @create: date 18:24 2017/12/22
 */



public class User2 implements Serializable {
    private  int id;
    private String name;

    public User2(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public User2() {
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
}
