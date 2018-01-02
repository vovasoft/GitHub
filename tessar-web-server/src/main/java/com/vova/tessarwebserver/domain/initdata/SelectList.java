package com.vova.tessarwebserver.domain.initdata;

/**
 * @author: Vova
 * @create: date 17:25 2018/1/2
 */
public class SelectList {
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

    public SelectList() {

    }

    public SelectList(int id, String name) {

        this.id = id;
        this.name = name;
    }

    int id;
    String name;
}
