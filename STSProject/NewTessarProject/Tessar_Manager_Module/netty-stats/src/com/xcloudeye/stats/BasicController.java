package com.xcloudeye.stats;


import java.util.Map;

public class BasicController {
    protected String app;
    protected String date;
    protected String start;
    protected String end;
    protected String channel;
    protected String username;
    protected String password;
    BasicController(Map<String, Object> paramMap){
        this.app=(String)paramMap.get("app");
        this.date=(String)paramMap.get("date");
        this.start=(String)paramMap.get("start");
        this.end=(String)paramMap.get("end");
        this.channel=(String)paramMap.get("channel");
        this.username=(String)paramMap.get("username");
        this.password=(String)paramMap.get("password");
    }
}
