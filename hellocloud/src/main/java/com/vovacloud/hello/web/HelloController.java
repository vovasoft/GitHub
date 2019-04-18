package com.vovacloud.hello.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.PipedReader;

/**
 * @author vova
 * @version Create in 下午4:06 2018/2/28
 */


@RestController
public class HelloController {
    
    @Autowired
    private CounterService counterService;
    
    @RequestMapping("/hello")
    public String hei(){
        counterService.increment("vova.hello.count"); //自定义信息，获取登录hello点击count
        return "hello";
    }
}
