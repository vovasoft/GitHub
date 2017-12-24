package com.vova;

/**
 * @author vova
 * @version Create in 下午9:39 2017/12/24
 */


public class User {

    private Integer id;
    private String name;
    private String password;
    private String phone;

    //省略 get 和 set ...


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
}