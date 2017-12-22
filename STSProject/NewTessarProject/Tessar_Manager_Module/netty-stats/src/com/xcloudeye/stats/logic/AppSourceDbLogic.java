package com.xcloudeye.stats.logic;

import com.xcloudeye.stats.dao.AdvanceDao;
import com.xcloudeye.stats.dao.SourceDbDao;
import com.xcloudeye.stats.domain.app.*;
import com.xcloudeye.stats.domain.db.DailyData;
import com.xcloudeye.stats.domain.db.Payment;
import com.xcloudeye.stats.single.SingleMongo;
import com.xcloudeye.stats.util.StaticValueUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jca.cci.core.InteractionCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class AppSourceDbLogic {

    private static final int ONE_DAY_MILLS = 86400;
    private static final String RET_TITLE = "ret:";
    private static final String ROI_TITLE = "roi:";

    private static final int BLOODSTRIK_ID = 1001;
    private static final int PLATFORM_ID = 7001;
    private static final int NARUTO_ID = 2001;
    private static final int HEROCRAFT_ID=3001;
    private static final int LEAGUEOFANGELS_ID = 4001;
    private static final int BLEACH_ID = 5001;

    private SourceDbDao sourceDbDao;
    private AdvanceDao advanceDao;


    public SourceDbDao getSourceDbDao() {
        return sourceDbDao;
    }

    public void setSourceDbDao(SourceDbDao sourceDbDao) {
        this.sourceDbDao = sourceDbDao;
    }

    public AdvanceDao getAdvanceDao() {
        return advanceDao;
    }

    public void setAdvanceDao(AdvanceDao advanceDao) {
        this.advanceDao = advanceDao;
    }

    /**
     * <p>Title: </p
     * <p>Description: 默认是对bloodstrike进行操作</p>
     */
    public AppSourceDbLogic() {
    }

    /**
     * <p>Title: initLogic</p>
     * <p>Description: 初始化数据库</p>
     *
     * @param appid
     */
    public void initLogic(int appid) {
        SingleMongo singleMongo = SingleMongo.getSinglemongo();
        ClassPathXmlApplicationContext ctx = singleMongo.getDaoContext();
        switch (appid) {
            case BLOODSTRIK_ID:
                this.sourceDbDao = ctx.getBean("bsSourceDao", SourceDbDao.class);
                this.advanceDao = ctx.getBean("bsAdvanceDao", AdvanceDao.class);
                break;
            case NARUTO_ID:
                this.sourceDbDao = ctx.getBean("naturoSourceDao", SourceDbDao.class);
                this.advanceDao = ctx.getBean("narutoAdvanceDao", AdvanceDao.class);
                break;
            case HEROCRAFT_ID:
                this.sourceDbDao = ctx.getBean("herocraftSourceDao", SourceDbDao.class);
                this.advanceDao = ctx.getBean("herocraftAdvanceDao", AdvanceDao.class);
                break;
            case LEAGUEOFANGELS_ID:
                this.sourceDbDao = ctx.getBean("leagueofangelsSourceDao", SourceDbDao.class);
                this.advanceDao = ctx.getBean("leagueofangelsAdvanceDao", AdvanceDao.class);
                break;
            case BLEACH_ID:
                this.sourceDbDao = ctx.getBean("bleachSourceDao", SourceDbDao.class);
                this.advanceDao = ctx.getBean("bleachAdvanceDao", AdvanceDao.class);
                break;
            default:
                break;
        }
    }

    /**
     * <p>Title: getChannelsByTime</p>
     * <p>Description: 查询指定时间段的注册人数</p>
     *
     * @param start 起始时间
     * @param end   结束时间
     * @return 查询出的渠道list
     */
    public List<String> getChannelsByTime(int start, int end) {
        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("action").is("reg"));
        criterias.add(Criteria.where("date").gte(start));
        criterias.add(Criteria.where("date").lte(end));
        Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
        Query query = new Query(criteria);
        query.fields().include("channel");

        List<String> ch = new ArrayList<String>();
        ch = sourceDbDao.readDistinctByKey("channel", query.getQueryObject());

        return ch;
    }

    /**
     * <p>Title: getChannelsByTimeInUserInfo</p>
     * <p>Description: 查询指定时间段的注册人数</p>
     *
     * @param start 起始时间
     * @param end   结束时间
     * @return 查询出的渠道list
     */
    public List<String> getChannelsByTimeInUserInfo(int start, int end) {
        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("rcvtime").gte(start));
        criterias.add(Criteria.where("rcvtime").lte(end));
        Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
        Query query = new Query(criteria);
        query.fields().include("channel");

        List<String> ch = new ArrayList<String>();
        ch = sourceDbDao.readDistinctByKeyInUserInfo("channel", query.getQueryObject());

        return ch;
    }




    /**
     * @return 获取所有的渠道列表
     */
    public List<String> getChannels() {
        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("action").is("reg"));
        Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
        Query query = new Query(criteria);
        query.fields().include("channel");

        List<String> ch = new ArrayList<String>();
        ch = sourceDbDao.readDistinctByKey("channel", query.getQueryObject());

        return ch;
    }

    /**
     * <p>Title: getChannelsByTimeLogin</p>
     * <p>Description: 某段时间内，有用户登录的渠道</p>
     *
     * @param start
     * @param end
     * @return
     */
    public List<String> getChannelsByTimeLogin(int start, int end) {
        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("action").is("login"));
        criterias.add(Criteria.where("date").gte(start));
        criterias.add(Criteria.where("date").lte(end));
        Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
        Query query = new Query(criteria);
        query.fields().include("channel");

        List<String> ch = new ArrayList<String>();
        ch = sourceDbDao.readDistinctByKey("channel", query.getQueryObject());

        return ch;
    }

    /**
     * <p>Title: getChannelAnyTimeNew</p>
     * <p>Description: 根据指定的时间范围，计算总数</p>
     *
     * @param channel 指定渠道名
     * @param start   起始时间
     * @param end     结束时间
     * @return 满足查询条件的总数
     */
    public long getChannelAnyTimeNew(String channel, int start, int end) {
        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("action").is("reg"));
        criterias.add(Criteria.where("channel").is(channel));
        criterias.add(Criteria.where("date").gte(start));
        criterias.add(Criteria.where("date").lte(end));
        Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
        Query query = new Query(criteria);
        query.fields().include("uid");

        long sum = sourceDbDao.sum(query);

        return sum;
    }

    /**
     * <p>Title: getChannelAnyTimeNewInUserInfo</p>
     * <p>Description: 根据指定的时间范围，计算总数</p>
     *
     * @param channel 指定渠道名
     * @param start   起始时间
     * @param end     结束时间
     * @return 满足查询条件的总数
     */
    public long getChannelAnyTimeNewInUserInfo(String channel, int start, int end) {
        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("channel").is(channel));
        criterias.add(Criteria.where("rcvtime").gte(start));
        criterias.add(Criteria.where("rcvtime").lte(end));
        Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
        Query query = new Query(criteria);
        query.fields().include("uid");

        long sum = sourceDbDao.sumUserInfo(query);

        return sum;
    }





    /**
     * <p>Title: getChannelAnyTimePay</p>
     * <p>Description: 根据指定的时间范围，计算总数</p>
     *
     * @param channel 指定渠道名
     * @param start   起始时间
     * @param end     结束时间
     * @return 满足查询条件的总数
     */
    public List<String> getChannelAnyTimePay(String channel, int start, int end) {
        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("action").is("payment"));
        criterias.add(Criteria.where("channel").is(channel));
        criterias.add(Criteria.where("date").gte(start));
        criterias.add(Criteria.where("date").lte(end));
        Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
        Query query = new Query(criteria);
        query.fields().include("uid");
        List<String> ch = new ArrayList<String>();
        ch = sourceDbDao.sumPay("userid",query.getQueryObject());
        return ch;
    }

    /**
     * <p>Title: getAnyTimeRegUid</p>
     * <p>Description: 查询时间范围内的注册uid</p>
     *
     * @param channel
     * @param start
     * @param end
     * @return
     */
    public List<String> getAnyTimeRegUid(String channel, int start, int end) {
        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("action").is("reg"));
        criterias.add(Criteria.where("channel").is(channel));
        criterias.add(Criteria.where("date").gte(start));
        criterias.add(Criteria.where("date").lte(end));
        Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
        Query query = new Query(criteria);
        query.fields().include("uid");

        List<String> uid = new ArrayList<String>();
        uid = sourceDbDao.readDistinctByKey("uid", query.getQueryObject());

        return uid;
    }

    /**
     * <p>Title: getAnyTimeRegUid</p>
     * <p>Description: 查询时间范围内的注册uid</p>
     *
     * @param channel
     * @param start
     * @param end
     * @return
     */
    public List<String> getAnyTimeRegUidInUserInfo(String channel, int start, int end) {
        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("channel").is(channel));
        criterias.add(Criteria.where("rcvtime").gte(start));
        criterias.add(Criteria.where("rcvtime").lte(end));
        Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
        Query query = new Query(criteria);
        query.fields().include("uid");

        List<String> uid = new ArrayList<String>();
        uid = sourceDbDao.readDistinctByKeyInUserInfo("uid", query.getQueryObject());

        return uid;
    }


    /**
     * <p>Title: getChAnyTimeRetLogin</p>
     * <p>Description: 计算指定注册和登陆时间的留存人数</p>
     *
     * @param channel  指定渠道
     * @param regStart 注册起始时间
     * @param regEnd   注册结束时间
     * @param start    登录起始时间
     * @param end      登陆结束时间
     * @return 留存人数
     */
    public Integer getChAnyTimeRetLogin(String channel, int regStart, int regEnd, int start, int end) {
        List<String> regUid = new ArrayList<String>();
        regUid = getAnyTimeRegUid(channel, regStart, regEnd);

        System.out.println("regUid----:" + regUid.size());

        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("action").is("login"));
        criterias.add(Criteria.where("channel").is(channel));
        criterias.add(Criteria.where("date").gte(start));
        criterias.add(Criteria.where("date").lte(end));
        Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
        Query query = new Query(criteria);
        query.fields().include("uid");

        List<String> uid = new ArrayList<String>();
        uid = sourceDbDao.readDistinctByKey("uid", query.getQueryObject());
        System.out.println("login uid----:" + uid.size());

        Iterator<String> it = regUid.iterator();
        int sum = 0;
        while (it.hasNext()) {
            String a = it.next();
            if (uid.contains(a)) {
                Double income = getAnyTimeIncome(a,start,end);
                sum++;
            }
        }

        System.out.println("sum----:" + sum);

        return sum;
    }

    /**
     * <p>Title: getChAnyTimeRetPay</p>
     * <p>Description: 计算指定注册和登陆时间的留存人数</p>
     *
     * @param channel  指定渠道
     * @param regStart 注册起始时间
     * @param regEnd   注册结束时间
     * @param start    登录起始时间
     * @param end      登陆结束时间
     * @return 留存人数
     */
    public String getChAnyTimeRetPay(String channel, int regStart, int regEnd, int start, int end) {
        List<String> regUid = new ArrayList<String>();
        regUid = getAnyTimeRegUidInUserInfo(channel, regStart, regEnd);

        System.out.println("regUid----:" + regUid.size());

        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("action").is("payment"));
        criterias.add(Criteria.where("channel").is(channel));
        criterias.add(Criteria.where("date").gte(start));
        criterias.add(Criteria.where("date").lte(end));
        criterias.add(Criteria.where("status").in("1","2"));
        Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
        Query query = new Query(criteria);
        query.fields().include("userid");

        List<String> uid = new ArrayList<String>();
        uid = sourceDbDao.readPaymentDistinctByKey("userid", query.getQueryObject());
        System.out.println("login uid----:" + uid.size());

        Iterator<String> it = regUid.iterator();
        int sum = 0;
        double income = 0;
        while (it.hasNext()) {
            String a = it.next();
            if (uid.contains(a)) {
                income += getAnyTimeIncomeWithUid(a,start,end);
                sum++;
            }
        }
        System.out.println("sum----:" + sum);
        String value = sum+"_"+income;

        return value;
    }



    /**
     * <p>Title: getRetQuery</p>
     * <p>Description: 按输入的任意时间范围计算用户 7日留存数据，超过6天的只计算6天，</p>
     *
     * @param start
     * @param end
     * @param current 接口被访问的时间
     * @return
     */
    public RetQuery getRetQuery(int start, int end, int current) {
        List<String> chName = new ArrayList<String>();
        int calDate = start / ONE_DAY_MILLS * ONE_DAY_MILLS;
        //确认显示几天的留存数据
        int numDay = (current - calDate) / ONE_DAY_MILLS >= 6 ? 6 : (current - calDate) / ONE_DAY_MILLS;

        chName = getChannelsByTime(start, end);

        System.out.print("ch.length--:" + chName.size());

        Iterator<String> it = chName.iterator();
        List<RetQueryChannel> channels = new ArrayList<RetQueryChannel>();

        while (it.hasNext()) {
            String ch = it.next();
            long yes_reg = getChannelAnyTimeNew(ch, start, end);
            System.out.println("yes_reg---:" + yes_reg);

            if (yes_reg != 0) {
                RetQueryChannel channel = new RetQueryChannel();
                channel.setChannel(ch);
                channel.setNew_user((int) yes_reg);

                List<HashMap<String, Integer>> ret = new ArrayList<HashMap<String, Integer>>();
                for (int i = 1; i <= numDay; i++) {
                    HashMap<String, Integer> retChannel = new HashMap<String, Integer>();
                    String key = RET_TITLE + (calDate + i * ONE_DAY_MILLS);
                    Integer value = getChAnyTimeRetLogin(ch, start, end, calDate + i * ONE_DAY_MILLS, calDate + (i + 1) * ONE_DAY_MILLS);
                    retChannel.put(key, value);

                    ret.add(retChannel);
                }
                channel.setRet(ret);

                channels.add(channel);
            } else {
                channels = null;
            }
        }

        return new RetQuery("ret_query", start, end, channels);
    }

    /**
     * <p>Title: getRetQuery</p>
     * <p>Description: 按天计算用户 7日支付金额以及人数数据，超过14天的只计算14天，</p>
     *
     * @param start
     * @param end
     * @param current 接口被访问的时间
     * @return
     */
    public RoiQuery getRoiQuery(int start, int end, int current) {
        List<String> chName = new ArrayList<String>();
        int calDate = start /ONE_DAY_MILLS * ONE_DAY_MILLS + StaticValueUtil.THREE_HOURS_SECONDS;
        //确认显示几天的roi
        int numDay = (current - calDate) / ONE_DAY_MILLS >= 14 ? 14 : (current - calDate) / ONE_DAY_MILLS;

        chName = getChannelsByTimeInUserInfo(start, end);

        System.out.print("ch.length--:" + chName.size());

        Iterator<String> it = chName.iterator();
        List<RoiQueryChannel> channels = new ArrayList<RoiQueryChannel>();

        while (it.hasNext()) {
            String ch = it.next();
            long yes_user = getChannelAnyTimeNewInUserInfo(ch, start, end);
            System.out.println("yes_user---:" + yes_user);

            if (yes_user != 0) {
                RoiQueryChannel channel = new RoiQueryChannel();
                channel.setChannel(ch);
                channel.setNew_user((int) yes_user);
                List<HashMap<String, String>> roi = new ArrayList<HashMap<String, String>>();
                for (int i = 0; i <= numDay; i++) {
                    HashMap<String, String> roiChannel = new HashMap<String, String>();
                    String key = ROI_TITLE + (calDate + i * ONE_DAY_MILLS);
                    String value = getChAnyTimeRetPay(ch, start, end, calDate + i * ONE_DAY_MILLS, calDate + (i + 1) * ONE_DAY_MILLS);
                    roiChannel.put(key, value);
                    roi.add(roiChannel);
                }
                channel.setRoi(roi);

                channels.add(channel);
            } else {
                channels = null;
            }
        }
        return new RoiQuery("roi_query",start, channels);
    }



	
	/*-------------------------------------------ch track---------------------------------------------------------------*/

    /**
     * <p>Title: getAnyTimeNewUser</p>
     * <p>Description: 指定时间范围内，注册的用户</p>
     *
     * @param start
     * @param end
     * @return
     */
    public Integer getAnyTimeNewUser(String ch, int start, int end) {
        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("action").is("reg"));
        criterias.add(Criteria.where("channel").is(ch));
        criterias.add(Criteria.where("date").gte(start));
        criterias.add(Criteria.where("date").lte(end));
        Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
        Query query = new Query(criteria);
        query.fields().include("uid");

        Integer uid = sourceDbDao.readDistinctByKey("uid", query.getQueryObject()).size();

        return uid;
    }


    /**
     * <p>Title: getAnyTimePayer</p>
     * <p>Description: 根据输入的时间范围计算payer</p>
     *
     * @param ch
     * @param start
     * @param end
     * @return
     */
    public Integer getAnyTimePayer(String ch, int start, int end) {
        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("channel").is(ch));
        criterias.add(Criteria.where("date").gte(start));
        criterias.add(Criteria.where("date").lte(end));
        Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
        Query query = new Query(criteria);
        query.fields().include("userid");

        Integer uid = sourceDbDao.readPaymentDistinctByKey("userid", query.getQueryObject()).size();

        return uid;
    }

    /**
     * <p>Title: getAnyTimeIncome</p>
     * <p>Description: 计算时间范围内的income总和</p>
     *
     * @param ch
     * @param start
     * @param end
     * @return
     */
    public double getAnyTimeIncome(String ch, int start, int end) {
        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("channel").is(ch));
        criterias.add(Criteria.where("date").gte(start));
        criterias.add(Criteria.where("date").lte(end));
        Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
        Query query = new Query(criteria);
        query.fields().include("amount");

        List<Payment> payment = new ArrayList<Payment>();
        payment = sourceDbDao.readPayment(query);

        Iterator<Payment> it = payment.iterator();
        double income = 0;
        while (it.hasNext()) {
            Payment pm = it.next();
            income = income + Double.valueOf(pm.getAmount());
        }

        return income;
    }

    /**
     * <p>Title: getAnyTimeIncome</p>
     * <p>Description: 计算时间范围内的income总和</p>
     *
     * @param uid
     * @param start
     * @param end
     * @return
     */
    public double getAnyTimeIncomeWithUid(String uid, int start, int end) {
        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("userid").is(uid));
        criterias.add(Criteria.where("date").gte(start));
        criterias.add(Criteria.where("date").lte(end));
        Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
        Query query = new Query(criteria);
        query.fields().include("amount");

        List<Payment> payment = new ArrayList<Payment>();
        payment = sourceDbDao.readPayment(query);

        Iterator<Payment> it = payment.iterator();
        double income = 0;
        while (it.hasNext()) {
            Payment pm = it.next();
            income = income + Double.valueOf(pm.getAmount());
        }

        return income;
    }



    /**
     * <p>Title: getChTrack</p>
     * <p>Description: </p>
     *
     * @param start
     * @param end
     * @return
     */
    public ChannelTrack getChTrack(int start, int end) {
        List<String> chName = new ArrayList<String>();
        chName = getChannels();

        System.out.println("chName.size()---:" + chName.size());
        List<ChannelTrackChannel> channels = new ArrayList<ChannelTrackChannel>();

        Iterator<String> it = chName.iterator();
        while (it.hasNext()) {
            String ch = it.next();

            DailyData dailyOne = advanceDao.getDailyOneChannel(new Query(Criteria.where("channel").is(ch)));
            if (dailyOne != null && !dailyOne.equals(null)) {
                Integer new_user = getAnyTimeNewUser(ch, start, end);

                Integer payer = getAnyTimePayer(ch, start, end);
                System.out.println("payer---:" + payer);

                double income = getAnyTimeIncome(ch, start, end);

                System.out.println("income---:" + income);
                float arppu = 0;
                if (payer == 0) {
                    arppu = 0;
                } else {
                    arppu = (float) income / payer;
                }
                channels.add(new ChannelTrackChannel(ch, dailyOne.getParent()
                        , dailyOne.getChild(), dailyOne.getSeq(), income, new_user, payer, arppu));
            }
        }

        return new ChannelTrack("app", "ch_track", null, channels);
    }

}
