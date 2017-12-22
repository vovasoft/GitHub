package com.xcloudeye.stats.NettyServer;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.xcloudeye.stats.*;
import com.xcloudeye.stats.dao.SlaveRedisDao;
import com.xcloudeye.stats.util.EnumMethods;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


public class HttpAction {


    private static SlaveRedisDao slaveRedisDao = null;
    private static Map<String, Object> map = new HashMap<String, Object>();
    private static List<String> urlList = new ArrayList<String>();

    static {

       /*ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/redis-conf.xml");
        slaveRedisDao = ctx.getBean("redisSlaveDao", SlaveRedisDao.class);*/
        map.put("main", new GenericController());
        map.put("app", new AppController());
        map.put("user", new UserController());
        map.put("manage", new ManageController());
        map.put("stats", new AppController());
        map.put("query",new QueryController());
        map.put("csv",new QuerCSVController());


        //动态路径不访问数据库
        urlList.add("/stats/user/");
        urlList.add("/stats/manage");
    }

    //缓存类
    public static LoadingCache<String, String> cahceBuilder = CacheBuilder.newBuilder().expireAfterAccess(1500, TimeUnit.MINUTES).maximumSize(100)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) throws Exception {
                    System.out.println("----经过Action》》load-------");
                    return "null";
                }
            });


    //得到cache 静态资源缓存 动态资源
    public static String getCache(String url, Map<String, Object> paramMap, String key) throws ExecutionException {
        if (istrendsPath(url)) {
            return get(url, paramMap);
        } else {
            String cache = cahceBuilder.get(key);
            if (cache.equals("null")) {
                cache = get(url, paramMap);
                System.out.println("cache:" + cache);
                cahceBuilder.put(key, cache);
            }
            return cache;
        }
    }


    public static boolean istrendsPath(String url) {
        boolean flag = false;
        for (String str : urlList) {
            if (str.equals(url)) {
                flag = true;
            }
            break;
        }
        return flag;
        //Class.forName("");
    }


    public static String get(String url, Map<String, Object> paramMap) {
        System.out.println(url + "-------");
        //if(url!=null&&!"".equals(url)&&PermissionValidation(paramMap,url)){
        String[] strs = url.split("/");
        if (strs[1].equals("stats")) {
            if (map.get(strs[2]) != null) {
                Class<?> controller = map.get(strs[2]).getClass();
                try {
                    //System.out.print(EnumMethods.method.valueOf(strs[3]));
                    String str = EnumMethods.method.valueOf(strs[3]).toString();
                    Method method = controller.getDeclaredMethod(str, Map.class);
                    try {
                        Object obj = method.invoke(map.get(strs[2]), new Object[]{paramMap});
                        if (obj != null) {
                            return obj.toString();
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        //}
        return null;
    }

    public synchronized static boolean PermissionValidation(Map<String, Object> paramMap, String url) {
        if (url.equals("/stats/user/user_login")) {
            return true;
        } else if (paramMap.get("session") != null) {
            String sessionid = paramMap.get("session").toString();
            return true;
        } else {
            return false;
        }
    }
}
