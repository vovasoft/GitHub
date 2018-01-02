package com.vova.tessarwebserver.controler;

/**
 * @author: Vova
 * @create: date 9:44 2018/1/2
 */


import com.vova.tessarwebserver.dbmapper.AllInOneMapper;
import com.vova.tessarwebserver.domain.initdata.SelectList;
import com.vova.tessarwebserver.domain.initdata.InitJson;
import com.vova.tessarwebserver.domain.newadd.NewAddDay;
import com.vova.tessarwebserver.domain.newadd.NewJson;
import com.vova.tessarwebserver.domain.stayman.StayJson;
import com.vova.tessarwebserver.domain.stayman.StayParent;
import com.vova.tessarwebserver.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Vova
 * @create: date 14:10 2017/12/29
 */


@RestController
@SpringBootApplication
@RequestMapping("/app")
public class ControlerGame {

    @Autowired
    private AllInOneMapper allInOneMapper;
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    @GetMapping("/getGameT")
    @ResponseBody
    Object fun1(@RequestParam String app, @RequestParam String cid, @RequestParam String gid,
               @RequestParam String sid, @RequestParam String sDate, @RequestParam String eDate) throws ParseException {
        List<NewAddDay> nadList = null;
        nadList = allInOneMapper.findCGSNewAddListByTimes(app, cid, gid, sid, sdf.parse(sDate), sdf.parse(eDate));
        ArrayList<NewJson> nj = new ArrayList<>();
        for (int i=0;i<nadList.size();i++) {
            NewAddDay nad = nadList.get(i);
            nj.add(new NewJson(sdf.format(nad.getDateID()),nad.getNewAddNum(),nad.getActiveNum(),nad.getLoginCount(),nad.getAverageLogin(),nad.getAllPlayerNum()));
        }
        return nj;
    }

    @GetMapping("/getGameAll")
    @ResponseBody
    Object fun2(@RequestParam String app, @RequestParam String sDate, @RequestParam String eDate) throws ParseException {

        List<NewAddDay> nadList = allInOneMapper.findAllNewAddListByTimes(app, sdf.parse(sDate), sdf.parse(eDate));
        return nadList;
    }

    @GetMapping("/getStayT")//留存表的一些处理，其中需要返回json的时候，最好将留存字段改写成数组的形式，如下所示，最长的那行代码。
    @ResponseBody
    Object fun3(@RequestParam String app, @RequestParam String cid, @RequestParam String gid,
                @RequestParam String sid, @RequestParam String sDate, @RequestParam String eDate) throws ParseException {
        List<StayParent> spList =  allInOneMapper.findCGSStayListByTimes(app, cid, gid, sid, sdf.parse(sDate), sdf.parse(eDate));
        ArrayList<StayJson> sj = new ArrayList<>();
        for (int i=0;i<spList.size();i++) {
            StayParent sp = spList.get(i);
            sj.add(new StayJson(sdf.format(sp.getDateID()),sp.getNewAddNum(),Tools.strToNumArray(sp.getStayList(),",")));
        }

        return sj;
    }

    @GetMapping("/getcms")//留存表的一些处理，其中需要返回json的时候，最好将留存字段改写成数组的形式，如下所示，最长的那行代码。
    @ResponseBody
    Object fun4() throws ParseException {
        InitJson ij = new InitJson();
        List<SelectList> cl = allInOneMapper.findCGS("channellist");
        ArrayList<String> str = new ArrayList<>();
        for (SelectList selectList : cl) {
            str.add(selectList.getName());
        }
        ij.setcNames(str);

        str = new ArrayList<>();
        List<SelectList> gl = allInOneMapper.findCGS("gamelist");
        for (SelectList selectList : gl) {
            str.add(selectList.getName());
        }
        ij.setgNames(str);

        List<SelectList> sl = allInOneMapper.findCGS("serverlist");
        str = new ArrayList<>();
        for (SelectList selectList : sl) {
            str.add(selectList.getName());
        }
        ij.setsNames(str);

        return  ij;

    }

}