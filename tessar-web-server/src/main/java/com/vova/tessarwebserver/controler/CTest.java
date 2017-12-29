package com.vova.tessarwebserver.controler;

import com.vova.tessarwebserver.dbmapper.AllInOneMapper;
import com.vova.tessarwebserver.domain.Player;
import com.vova.tessarwebserver.domain.newadd.NewAddDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author: Vova
 * @create: date 14:10 2017/12/29
 */


@RestController
@SpringBootApplication
public class CTest {

    @Autowired
    private AllInOneMapper allInOneMapper;
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    @GetMapping("/get")
    @ResponseBody Object fun(@RequestParam String app,@RequestParam String cid,@RequestParam String gid,
               @RequestParam String sid,@RequestParam String sDate,@RequestParam String eDate) throws ParseException {

        List<NewAddDay> nadList= allInOneMapper.findCGSListByTimes(app,cid,gid,sid,sdf.parse(sDate),sdf.parse(eDate));
        for (NewAddDay newAddDay : nadList) {
            System.out.println(newAddDay.getDateID());
        }

        return nadList;
    }

    @GetMapping("/getall")
    @ResponseBody Object fun(@RequestParam String app,@RequestParam String sDate,@RequestParam String eDate) throws ParseException {

        List<NewAddDay> nadList= allInOneMapper.findAllListByTimes(app,sdf.parse(sDate),sdf.parse(eDate));
        return nadList;
    }

    @GetMapping("/test")
    @ResponseBody Object fun2(){
        Player p = new Player();

        return p;
    }

}