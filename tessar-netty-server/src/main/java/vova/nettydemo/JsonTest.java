package vova.nettydemo;

import com.google.gson.Gson;
import vova.dao.manager.ManagePayInput;
import vova.domain.test.User;
import org.junit.Test;
import vova.util.Tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author vova
 * @version Create in 上午1:28 2017/12/21
 */


public class JsonTest {
    
    @Test
    public void fun(){
        Gson gson = new Gson();
        String jsonstr = gson .toJson(new User(12,"vova",123));
        System.out.println(jsonstr);
    }
    org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ManagePayInput.class);
    @Test
    public void funLog(){
        log.info("aaaaaaaaaaaa");
    }

    @Test
    public void funJsonScript() throws ParseException {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long regInt =0;
        String url = "http://192.168.1.110:9999/tessar/statis/statis?action=game&json=";
        String str = "";
        System.out.println(url);
        //打印注册用户
        for (int i=1;i<100;i++){
            regInt = Tools.dateToSec(sdf.parse("2017-12-"+(i%30)));
            str = "{\"uid\":00"+i+",\"regdate\":"+regInt+",\"lastdate\":"+regInt+",\"cid\":\"ngBrazil\",\"gid\":\"bloodstrke\",\"sub\":\"lp-fb\",\"sid\":\"0\"},";
            System.out.println(str);
        }

        Random random = new Random(165461212);
        System.out.println();
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        //打印登录情况
        System.out.println(url);
        for (int i=1;i<100;i++){
            int r = (int) (Math.random()*100);
            regInt = Tools.dateToSec(sdf.parse("2017-12-"+(i%30)));
            str = "{\"uid\":00"+i+",\"regdate\":"+regInt+",\"lastdate\":"+(regInt+(r*80000))+",\"cid\":\"ngBrazil\",\"gid\":\"bloodstrke\",\"sub\":\"lp-fb\",\"sid\":\"0\"},";
            System.out.println(str);
        }


    }

    @Test
    public void funHTTP(){
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString ="http://192.168.1.110:9999/tessar/statis/statis?action=game&json={\"uid\":001,\"regdate\":1512057600,\"lastdate\":1512057600,\"cid\":\"ngBrazil\",\"gid\":\"bloodstrke\",\"sub\":\"lp-fb\",\"sid\":\"0\"}";
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
