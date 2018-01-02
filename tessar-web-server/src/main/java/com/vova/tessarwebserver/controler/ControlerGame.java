package com.vova.tessarwebserver.controler;

/**
 * @author: Vova
 * @create: date 9:44 2018/1/2
 */

import com.vova.tessarwebserver.dbmapper.AllInOneMapper;
import com.vova.tessarwebserver.domain.Player;
import com.vova.tessarwebserver.domain.newadd.NewAddDay;
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

        return nadList;
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
            sj.add(new StayJson(sdf.format(spList.get(i).getDateID()),spList.get(i).getNewAddNum(),Tools.strToNumArray(spList.get(i).getStayList(),",")));
        }

        return sj;
    }

}