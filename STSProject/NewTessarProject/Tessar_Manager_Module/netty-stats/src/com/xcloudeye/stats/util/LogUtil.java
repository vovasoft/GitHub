package com.xcloudeye.stats.util;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.model.JSONValue;
import com.sdicons.json.validator.impl.predicates.Str;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;

import static java.lang.System.*;

/**
 * Created by admin on 2016/3/22.
 */
public class LogUtil {


    public enum WarningLevel {
        info,
        def,
        warning,
        error,
        fatal
    }

    public enum LogStats {
        startup,
        restart,
        halt,
        exit,
        exception
    }

    private static Map<WarningLevel, String> fileMap = null;
    private String rootPath = null;
    private static LogUtil logUtil = null;
    private static Map<WarningLevel, File> fileHandler = null;
    private static Map<LogStats, String> logStats = null;

    private static String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
    private static String KEY = "key";
    private static String KEY_VALUE = "keyVaule";
    private static String TIME = "time";
    private static String TIMESTAMP = "timeStamp";

    //禁止默认构造，必须使用路径参数构造
    private LogUtil() {
    }

    public LogUtil(String rootPath) {
        this.rootPath = rootPath;
    }


    private static synchronized void init() {
        //锁住以后还需要检查变化状态
        if (null == logUtil) {


            String osName = System.getProperties().getProperty("os.name");

            //  /home/tessarlog/MongoLog/mongodb_insert.log

            logUtil = new LogUtil("/home/statslog/");

            fileMap = new HashMap<WarningLevel, String>();
            logStats = new HashMap<LogStats, String>();

            fileMap.put(WarningLevel.def, "def");
            fileMap.put(WarningLevel.warning, "warning");
            fileMap.put(WarningLevel.error, "error");
            fileMap.put(WarningLevel.fatal, "fatal");

            logStats.put(LogStats.restart, "system restart");
            logStats.put(LogStats.startup, "system startup");
            logStats.put(LogStats.halt, "system halt");
            logStats.put(LogStats.exit, "system exit");
            logStats.put(LogStats.exception, "system exception");


            logUtil.initfilelog();
        }
    }

    public static LogUtil getInstance() {
        if (null == logUtil) {
            init();
        }
        return logUtil;
    }

    private void initfilelog() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        fileHandler = new HashMap<WarningLevel, File>();
        try {
            String filepath = new String(rootPath + "/" + year + month + day + "/");

            File mkdir = new File(filepath);
            if (!mkdir.exists()) {
                mkdir.mkdirs();
            }
            for (Map.Entry<WarningLevel, String> entry : fileMap.entrySet()) {
                String filename = new String("LogInfo_" + year + month + day + "_" + entry.getValue() + ".txt");
                File currentFile = new File(filepath + filename);
                if (!currentFile.exists()) {
                    try {
                        currentFile.createNewFile();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        System.exit(0);
                    }
                    fileHandler.put(entry.getKey(), currentFile);
                    writeToFile(logStats.get(LogStats.startup), null, currentFile);
                }
                writeToFile(logStats.get(LogStats.restart), null, currentFile);
            }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(0);
        }
    }

    public boolean LogInfo(String keyName, Object obj, WarningLevel level) {


        try {
            System.out.println(keyName + JSONMapper.toJSON(obj).render(false));
        } catch (MapperException e) {
            e.printStackTrace();
        }

        if (WarningLevel.info != level) {
            return getInstance().writeToFile(keyName, obj, level);
        }
        return false;
    }

    public boolean LogInfo(String keyName, String str, WarningLevel level) {

            System.out.println(keyName + ":"+str);
        if (WarningLevel.info != level) {
            return getInstance().writeToFile(keyName, str, level);
        }
        return false;
    }

    private boolean writeToFile(String keyname, Object obj, File rfile) {
        Map<String, Object> paramsMap = new LinkedHashMap<String, Object>();

        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(rfile, true));
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

            Date date = new Date();
            paramsMap.put(TIME, sdf.format(date));
            paramsMap.put(TIMESTAMP, date.getTime());


            paramsMap.put(KEY_VALUE, obj);
            paramsMap.put(KEY_VALUE, JSONMapper.toJSON(obj).render(false));

            writer.append(paramsMap.toString());
            writer.newLine();//换行
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean writeToFile(String keyname, Object obj, WarningLevel level) {
        File CurrentLogFile = fileHandler.get(level);
        if (null == CurrentLogFile) {
            CurrentLogFile = fileHandler.get(WarningLevel.warning);
        }
        writeToFile(keyname, obj, CurrentLogFile);
        return false;
    }


}
