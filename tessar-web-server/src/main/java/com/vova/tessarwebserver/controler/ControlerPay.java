package com.vova.tessarwebserver.controler;

import com.vova.tessarwebserver.dbmapper.AllInOneMapper;
import com.vova.tessarwebserver.domain.payment.PayAllShow;
import com.vova.tessarwebserver.domain.payment.PayMentJson;
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
 * @create: date 9:44 2018/1/2
 */

@RestController
//@SpringBootApplication
@RequestMapping("/pay")
public class ControlerPay {
    @Autowired
    private AllInOneMapper allInOneMapper;
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping("/getPayDate")
    @ResponseBody
    Object getPayDate(@RequestParam String app, @RequestParam String cid, @RequestParam String gid,
                       @RequestParam String sid, @RequestParam String sDate, @RequestParam String eDate) throws ParseException {
        List<PayAllShow> payList = null;
        payList = allInOneMapper.findCGSPayAllShowByTimes(app, cid, gid, sid, sdf.parse(sDate), sdf.parse(eDate));
        ArrayList<PayMentJson> nj = new ArrayList<>();
        for (int i=0;i<payList.size();i++) {
            PayAllShow pay = payList.get(i);
            nj.add(new PayMentJson(sdf.format(pay.getDateID()),pay.getNewAddNum(),pay.getNewAddPayPlayerNum(),pay.getNewAddPayAllMoney(),
                    pay.getNewAddPlayerARPPU(),pay.getNewAddPayPlayerARPPU(),pay.getNewPayPlayerNum(),pay.getNewPayAllMoney(),
                    pay.getAverageNewPayMoney(),pay.getActiveNum(),pay.getPayPlayerNum(),pay.getTodayAllPayMoney(),pay.getActiveARPPU(),
                    pay.getPayPlayerARPPU(),pay.getAllMoney()));
        }
        return nj;
    }

    @GetMapping("/getPayStayDate")//留存表的一些处理，其中需要返回json的时候，最好将留存字段改写成数组的形式，如下所示，最长的那行代码。
    @ResponseBody
    Object getPayStayDate(@RequestParam String app, @RequestParam String cid, @RequestParam String gid,
                       @RequestParam String sid, @RequestParam String sDate, @RequestParam String eDate) throws ParseException {
        List<StayParent> spList =  allInOneMapper.findCGSStayListByTimes(app, cid, gid, sid, sdf.parse(sDate), sdf.parse(eDate));
        ArrayList<StayJson> sj = new ArrayList<>();
        for (int i=0;i<spList.size();i++) {
            StayParent sp = spList.get(i);
            sj.add(new StayJson(sdf.format(sp.getDateID()),sp.getNewAddNum(), Tools.strToNumArray(sp.getStayList(),",")));
        }

        return sj;
    }

}
