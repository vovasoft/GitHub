package com.vova.tessarwebserver.controler;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.vova.tessarwebserver.dbmapper.NewAddDayMapper;
import com.vova.tessarwebserver.domain.Player;
import com.vova.tessarwebserver.domain.newadd.NewAddDay;
import org.hibernate.boot.jaxb.SourceType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: Vova
 * @create: date 14:10 2017/12/29
 */


@RestController
@SpringBootApplication
public class CTest {

    private NewAddDayMapper newAddDayMapper;
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    @GetMapping("/get")
    @ResponseBody Object fun(@RequestParam String app,@RequestParam String cid,@RequestParam String gid,
               @RequestParam String sid,@RequestParam String sDate,@RequestParam String eDate) throws ParseException {


        List<NewAddDay> nadList= newAddDayMapper.findByState(cid,gid,sid,sdf.parse(sDate),sdf.parse(eDate));
        for (NewAddDay newAddDay : nadList) {
            System.out.println(newAddDay.getDateID());
        }

        return "lalalala";
    }

    @GetMapping("/test")
    @ResponseBody Object fun2(){
        Player p = new Player();

        return p;
    }

}