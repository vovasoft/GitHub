package com.vova.domain;

import org.springframework.data.annotation.Id;

/**
 * @author vova
 * @version Create in 上午1:27 2017/12/21
 */

public class User {

    private Integer id;
    private String name;
    private String password;
    private String phone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
//省略 get 和 set ...
}