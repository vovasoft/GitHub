package com.xcloudeye.stats.logic;

import com.csvreader.CsvWriter;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.xcloudeye.stats.dao.ApolloRedisDao;
import com.xcloudeye.stats.dao.SlaveRedisDao;
import com.xcloudeye.stats.dao.driverdao.FacebookDbDriverDao;
import com.xcloudeye.stats.dao.driverdao.SourceDbDriverDao;
import com.xcloudeye.stats.util.DateFormatUtil;
import com.xcloudeye.stats.util.StaticValueUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joker on 2015/9/9.
 */
public class QueryCsvLogic {
    private SourceDbDriverDao sourceDbDriverDao;
    private FacebookDbDriverDao facebookDbDriverDao;
    private SlaveRedisDao  slaveRedisDao;
    private String appname;

    public QueryCsvLogic(int appid) {
        sourceDbDriverDao = new SourceDbDriverDao(appid);
        facebookDbDriverDao = new FacebookDbDriverDao();

        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/redis-conf.xml");
        this.slaveRedisDao = ctx.getBean("redisSlaveDao", SlaveRedisDao.class);
        switch (appid) {
            case StaticValueUtil.BLOODSTRIK_ID:
                appname = "bs";
                break;
            case StaticValueUtil.NARUTO_ID:
                appname = "naruto";
                break;
            default:
                break;
        }
    }

    /**
     * @param start
     * @param end
     * @return Retrun a list of distinct users.
     */
    private List<String> getActiveUsers(Integer start, Integer end) {
        BasicDBObject filter = new BasicDBObject("date"
                , new BasicDBObject("$gte", start).append("$lte", end)).append("action", "login");
        String key = "uid";
        return sourceDbDriverDao.getDistinctUser(key, filter);
    }

    /**
     * @param uid
     * @return Return the infomation of the user with uid.
     */
    private DBObject getUserRegInfo(String uid) {
        BasicDBObject filter1 = new BasicDBObject("uid", Integer.valueOf(uid));
        DBObject user = sourceDbDriverDao.getOneUserInfo(filter1);
        System.out.println("getUserRegInfo--:" + user);
        if (user == null) {
            BasicDBObject filter2 = new BasicDBObject("action", "reg").append("uid", uid);
            return sourceDbDriverDao.getOneUserLogInfo(filter2);
        }
        return user;
    }

    /**
     * @param uid
     * @return
     */
    private DBObject getUserLogInfo(String uid) {
        BasicDBObject filter = new BasicDBObject("uid", uid).append("action", "login");
        return sourceDbDriverDao.getOneUserInfo(filter);
    }

    /**
     * @param fbid
     * @return Retrurn the facebook infomation through the facebook id.
     */
    private DBObject getFacebookInfo(String fbid) {
        BasicDBObject filter = new BasicDBObject("facebook_id", fbid);
        return facebookDbDriverDao.getOneFacebookUserInfo(filter);
    }


    /**
     * Create csv,name with start date.
     *
     * @param start
     * @param end
     * @throws ParseException
     */
    public void createActiveCsv(Integer start, Integer end) throws ParseException {
        String filePath = StaticValueUtil.DOWNLOAD_PATH;
        String fileName = DateFormatUtil.intToDate(start) + "_ac.csv";
        CsvWriter wr = new CsvWriter(filePath + fileName, ',', Charset.forName("GBK"));

        String titleArr[] = {"FBID", "UID", "EMAIL", "LOCATION"};
        try {
            wr.writeRecord(titleArr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> activeUsers = getActiveUsers(start, end);
        System.out.println("length--:" + activeUsers.size());
        try {
            activeUsers.forEach(s -> {
                System.out.println("uid--:" + s);
                if ((s != null) && !"".equals(s)) {
                    String fbid = null;
                    String email = null;
                    String location = null;
                    DBObject user = getUserRegInfo(s);
                    if (user != null) {
                        fbid = (user.get("facebook_id") == null) ? null : user.get("facebook_id").toString();
                        email = (user.get("email") == null) ? null : user.get("email").toString();
                        if (fbid != null && !"".equals(fbid)) {
                            System.out.println("fb_id---:" + fbid);
                            DBObject facebook = getFacebookInfo(fbid);
                            location = (facebook == null) ? null : (String) facebook.get("location");
                            System.out.println("location---:" + location);
                        }
                    }
                    String arrs[] = {fbid, s, email, location};
                    try {
                        wr.writeRecord(arrs);
                        System.out.println("created--:" + s + "  success.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } finally {
            wr.close();
        }
        System.out.println(filePath + fileName + "--create end.");
    }

    /**
     * @return Return to remove duplicate uids.
     */
    private List<String> getAllPayedUids() {
        List<String> payUids = new ArrayList<String>();
        List<String> data = slaveRedisDao.getAllPayedUids(appname);
        if (data.size() > 0) {
            data.forEach(d -> {
                if (!payUids.contains(d)) {
                    payUids.add(d);
                }
            });
        }

        return payUids;
    }

    /**
     * @param uid The id of user.
     * @return Return the data througth uid.
     */
    private DBCursor getPaymentUsers(String uid) {
        BasicDBObject filter = new BasicDBObject("userid", uid);
        return sourceDbDriverDao.getPaymentQuery(filter);
    }

    /**
     * Create the csv file of payment.
     */
    public void createPaymentCsv(String filename) {
        List<String> uids = getAllPayedUids();
        if (uids.size() > 0) {
            String filePath = StaticValueUtil.DOWNLOAD_PATH;
            CsvWriter wr = new CsvWriter(filePath + filename, ',', Charset.forName("GBK"));

            String titleArr[] = {"Fbid", "Uid", "IncomeTatol", "PayTimes", "PayFailedTimes", "MaxIncome"
                    , "Email", "Location", "RecentDate", "RegDate"};
            try {
                wr.writeRecord(titleArr);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                uids.forEach(u -> {
                    if ((u != null) && !"".equals(u)) {
                        String fbid = null;
                        String email = null;
                        String location = null;
                        String regTime = null;
                        final double[] income = {0};
                        int paytimes = 0;
                        final int[] failtimes = {0};
                        final double[] maxincome = {0};
                        final int[] recentDate = {0};
                        DBObject user = getUserRegInfo(u);
                        if (user != null) {
                            fbid = (user.get("facebook_id") == null) ? null : user.get("facebook_id").toString();
                            email = (user.get("email") == null) ? null : user.get("email").toString();
                            regTime = ((user.get("date") == null) ? user.get("regdate") : user.get("date")).toString();
                            if (fbid != null && !"".equals(fbid)) {
                                System.out.println("fb_id---:" + fbid);
                                DBObject facebook = getFacebookInfo(fbid);
                                location = (facebook == null) ? null : (String) facebook.get("location");
                                System.out.println("location---:" + location);
                            }
                        }
                        DBCursor payment = getPaymentUsers(u);
                        if (payment.size() > 0) {
                            paytimes = payment.size();
                            payment.forEach(p -> {
                                Integer rDate = Integer.valueOf(p.get("date").toString());
                                if (recentDate[0] <= rDate) {
                                    recentDate[0] = rDate;
                                }
                                if (p.get("status").equals("0")) {
                                    failtimes[0]++;
                                } else {
                                    double amount = Double.valueOf(p.get("amount").toString());
                                    income[0] += amount;
                                    if (maxincome[0] <= amount) {
                                        maxincome[0] = amount;
                                    }
                                }
                            });
                        }
                        createCsvOfThePayment(wr, fbid, u, income[0], paytimes, failtimes[0], maxincome[0]
                                , email, location, recentDate[0], regTime);
                    }
                });
            } finally {
                wr.close();
            }

        }
    }

    /**
     * Create files.
     */
    private void createCsvOfThePayment(CsvWriter wr, String fbid, String uid, double income, int paytimes, int failTimes, double maxIncome
            , String email, String location, Integer recentTime, String regDate) {

        String arrs[] = {fbid, uid, income + "", paytimes + "", failTimes + "", maxIncome + "", email
                , location, recentTime + "", regDate};
        try {
            wr.writeRecord(arrs);
            System.out.println("created--:" + uid + "  success. ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
