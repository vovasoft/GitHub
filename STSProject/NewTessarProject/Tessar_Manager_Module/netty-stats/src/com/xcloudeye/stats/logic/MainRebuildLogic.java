package com.xcloudeye.stats.logic;

import com.xcloudeye.stats.dao.AdvanceDao;
import com.xcloudeye.stats.domain.app.AppTrendGeneric;
import com.xcloudeye.stats.domain.db.DailyData;
import com.xcloudeye.stats.domain.generic.*;
import com.xcloudeye.stats.single.SingleApps;
import com.xcloudeye.stats.single.SingleMongo;
import com.xcloudeye.stats.util.AllAppId;
import com.xcloudeye.stats.util.CharUtil;
import com.xcloudeye.stats.util.StaticValueUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainRebuildLogic {

    private static final int BLOODSTRIK_ID = 1001;
    private static final int WON_ID = 2001;
    private static final int ALL_APPS_ID = 7001;

    private AdvanceDao advanceBsDao;
    private AdvanceDao advanceWonDao;

    public AdvanceDao getAdvanceBsDao() {
        return advanceBsDao;
    }

    public void setAdvanceBsDao(AdvanceDao advanceBsDao) {
        this.advanceBsDao = advanceBsDao;
    }

    public AdvanceDao getAdvanceWonDao() {
        return advanceWonDao;
    }

    public void setAdvanceWonDao(AdvanceDao advanceWonDao) {
        this.advanceWonDao = advanceWonDao;
    }

    /**
     * <p>Title: </p
     * <p>Description: 默认是对bloodstrike进行操作</p>
     */
    public MainRebuildLogic() {
    }

    /**
     * <p>Title: initLogic</p>
     * <p>Description: 初始化数据库</p>
     */
    public void initLogic() {
        SingleMongo singleMongo = SingleMongo.getSinglemongo();
        ClassPathXmlApplicationContext ctx = singleMongo.getDaoContext();
        this.advanceBsDao = ctx.getBean("bsAdvanceDao", AdvanceDao.class);
        this.advanceWonDao = ctx.getBean("narutoAdvanceDao", AdvanceDao.class);
    }

	/*--------------------------------------main pay------------------------------------------------*/


    /**
     * <p>Title: getMainPayDetails</p>
     * <p>Description: 计算相应app detail的情况</p>
     *
     * @param appid
     * @param query
     * @return
     */
    public List<MainPayDetail> getMainPayDetails(int appid, Query query) {
        List<DailyData> daily = new ArrayList<DailyData>();
        switch (appid) {
            case BLOODSTRIK_ID:
                daily = advanceBsDao.getDailyChannels(query);
                break;
            case WON_ID:
                daily = advanceWonDao.getDailyChannels(query);
                break;
            default:
                break;
        }

        List<MainPayDetail> mainDetail = new ArrayList<MainPayDetail>();
        Iterator coucor = daily.iterator();
        while (coucor.hasNext()) {
            DailyData data = (DailyData) coucor.next();
            mainDetail.add(new MainPayDetail(data.getDate(), data.getPayer(), data.getNew_payer(), data.getIncome()));
        }

        return mainDetail;
    }


    /**
     * <p>Title: getMainPay</p>
     * <p>Description: 根据输入的时间乏味，计算所有app的数据</p>
     *
     * @param start
     * @param end
     * @return
     */
    public MainPay getMainPay(String appStr, Integer start, Integer end) {
        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("channel").is("all"));
        criterias.add(Criteria.where("date").gte(start));
        criterias.add(Criteria.where("date").lte(end));
        Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
        Query query = new Query(criteria);

        String[] appStrs = CharUtil.cutStringToArray(appStr, ":");

        List<MainPayApp> apps = new ArrayList<MainPayApp>();

        if (appStrs.length == 1 && Integer.valueOf(appStrs[0]) == ALL_APPS_ID) {
            SingleApps singleRedis = SingleApps.getSingleApps();
            ClassPathXmlApplicationContext ctx = singleRedis.getContext();
            String[] appIds = ctx.getBean("allAppId", AllAppId.class).getApps();

            for (int i = 0; i < appIds.length; i++) {
                apps.add(new MainPayApp(appIds[i], getMainPayDetails(Integer.valueOf(appIds[i]), query)));
            }
        } else {
            for (int i = 0; i < appStrs.length; i++) {
                apps.add(new MainPayApp(appStrs[i], getMainPayDetails(Integer.valueOf(appStrs[i]), query)));
            }
        }

        return new MainPay("main", "pay", apps);
    }

	
	/*--------------------------------------Main channel------------------------------------------------*/

    public List<MainChannelChannel> getMainChChannel(int appid, List<String> parents, Integer start, Integer end) {
//		AppSourceDbLogic sourceLogic = new AppSourceDbLogic();
//		sourceLogic.initLogic(appid);

        AdvanceDao advanceDao = new AdvanceDao();
        switch (appid) {
            case BLOODSTRIK_ID:
                advanceDao = advanceBsDao;
                break;
            case WON_ID:
                advanceDao = advanceWonDao;
                break;
            default:
                break;
        }

        List<MainChannelChannel> channels = new ArrayList<MainChannelChannel>();

        Iterator coucor = parents.iterator();
        while (coucor.hasNext()) {
            String pa = coucor.next().toString();
            if (pa != null && !pa.equals(null)
                    && pa != "" && !"".equals(pa)) {
                List<Criteria> criterias = new ArrayList<Criteria>();
                criterias.add(Criteria.where("parent").is(pa));
                criterias.add(Criteria.where("date").gte(start));
                criterias.add(Criteria.where("date").lte(end));
                Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
                Query query = new Query(criteria);

                List<DailyData> data = advanceDao.getDailyChannels(query);

                if (data != null && !data.equals(null) && data.size() != 0) {
                    Integer new_user = 0;
                    Integer payed_order = 0;
                    double income = 0;

                    //active_user从源数据库中读取
//					Integer active_user = sourceLogic.getAnyTimeNewUser(ch, start, end);

                    for (int i = 0; i < data.size(); i++) {
                        new_user += data.get(i).getNew_user();
                        payed_order += data.get(i).getPayed_order();
                        income += data.get(i).getIncome();
                    }

                    channels.add(new MainChannelChannel(pa, new_user, payed_order, income));
                }
            }

        }
        return channels;
    }


    public MainChannel getMainChannel(Integer start, Integer end) {
        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("date").gte(start));
        criterias.add(Criteria.where("date").lte(end));
        Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
        Query query = new Query(criteria);

        List<String> chBs = advanceBsDao.readParentDistinctDaily("parent", query.getQueryObject());
        List<String> chWon = advanceWonDao.readParentDistinctDaily("parent", query.getQueryObject());

        List<MainChannelApp> apps = new ArrayList<MainChannelApp>();

        apps.add(new MainChannelApp("1001", getMainChChannel(BLOODSTRIK_ID, chBs, start, end)));
        apps.add(new MainChannelApp("2001", getMainChChannel(WON_ID, chWon, start, end)));

        return new MainChannel("main", "channel", start, end, apps);
    }
	
	/*------------------------------------main generic---------------------------------------------------*/

    private List<MainGnericDetail> getMainGeDetail(double bsIncome, double wonIncome) {
        Integer bsnew_tdy = 0;
        Integer bsactive_tdy = 0;

        Integer wonnew_tdy = 0;
        Integer wonActive_tdy = 0;

        Integer bsnew_ytd = 0;
        Integer bsactive_ytd = 0;

        Integer wonnew_ytd = 0;
        Integer wonActive_ytd = 0;

        List<MainGnericDetail> details = new ArrayList<MainGnericDetail>();

        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("channel").is("all"));
        Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
        Query query = new Query(criteria);

        DailyData bsData = advanceBsDao.getDailyOneChannel(query);
        if (bsData != null && !bsData.equals(null)) {
            bsnew_tdy = bsData.getNew_user();
            bsactive_tdy = bsData.getActive_user();

            int dateBs = bsData.getDate() - StaticValueUtil.ONE_DAY_SECONDS;
            List<Criteria> criterias2 = new ArrayList<Criteria>();
            criterias2.add(Criteria.where("channel").is("all"));
            criterias2.add(Criteria.where("date").is(dateBs));
            Criteria criteria2 = new Criteria().andOperator(criterias2.toArray(new Criteria[criterias2.size()]));
            Query queryBs = new Query(criteria2);

            DailyData bsYData = advanceBsDao.getDailyOneChannel(queryBs);


            if (bsYData != null && !bsYData.equals(null)) {
                bsnew_ytd = bsYData.getNew_user();
                bsactive_ytd = bsYData.getActive_user();
            }

        }

        DailyData wonData = advanceWonDao.getDailyOneChannel(query);

        if (wonData != null && !wonData.equals(null)) {
            wonnew_tdy = wonData.getNew_user();
            wonActive_tdy = wonData.getActive_user();

            int dateWon = wonData.getDate() - StaticValueUtil.ONE_DAY_SECONDS;

            List<Criteria> criteriasW = new ArrayList<Criteria>();
            criteriasW.add(Criteria.where("channel").is("all"));
            criteriasW.add(Criteria.where("date").is(dateWon));
            Criteria criteriaW = new Criteria().andOperator(criteriasW.toArray(new Criteria[criteriasW.size()]));
            Query queryWon = new Query(criteriaW);

            DailyData wonYData = advanceWonDao.getDailyOneChannel(queryWon);

            if (wonYData != null && !wonYData.equals(null)) {
                wonnew_ytd = wonYData.getNew_user();
                wonActive_ytd = wonYData.getActive_user();
            }
        }

        details.add(new MainGnericDetail("1001", bsnew_tdy, bsnew_ytd, bsactive_tdy, bsactive_ytd, bsIncome));
        details.add(new MainGnericDetail("2001", wonnew_tdy, wonnew_ytd, wonActive_tdy, wonActive_ytd, wonIncome));

        return details;
    }

    public MainGeneric getMainGeneric() {
        AppTrendGeneric bsGeneric = advanceBsDao.getGeneric().get(0);
        AppTrendGeneric wonGeneric = advanceWonDao.getGeneric().get(0);
        MainGenericGeneric mainGeneric = new MainGenericGeneric(bsGeneric.getTotal() + wonGeneric.getTotal()
                , bsGeneric.getWau() + wonGeneric.getWau(), bsGeneric.getMau() + wonGeneric.getMau()
                , bsGeneric.getTotal_payer() + wonGeneric.getTotal_payer());

        double bsIncome = bsGeneric.getIncome();
        double wonIncome = wonGeneric.getIncome();

        return new MainGeneric("main", "generic", mainGeneric, getMainGeDetail(bsIncome, wonIncome));
    }
	
	/*------------------------------------main trend---------------------------------------------------*/

    public List<MainTrendDetail> getMainTrendDetails(int appid, Integer start, Integer end) {
        AdvanceDao advanceDao = new AdvanceDao();
        switch (appid) {
            case BLOODSTRIK_ID:
                advanceDao = advanceBsDao;
                break;
            case WON_ID:
                advanceDao = advanceWonDao;
                break;
            default:
                break;
        }

        List<MainTrendDetail> details = new ArrayList<MainTrendDetail>();

        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("channel").is("all"));
        criterias.add(Criteria.where("date").gte(start));
        criterias.add(Criteria.where("date").lte(end));
        Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
        Query query = new Query(criteria);
        List<DailyData> data = advanceDao.getDailyChannels(query);
        for (int i = 0; i < data.size(); i++) {
            details.add(new MainTrendDetail(data.get(i).getDate(), data.get(i).getNew_user(), data.get(i).getActive_user()
                    , data.get(i).getPayer(), data.get(i).getPayed_order(), data.get(i).getIncome()));
        }

        return details;
    }

    public MainTrend getMainTrend(String app, Integer start, Integer end) {
        List<MainTrendApp> apps = new ArrayList<MainTrendApp>();

        String[] appStrs = CharUtil.cutStringToArray(app, ":");

        if (appStrs.length == 1 && Integer.valueOf(appStrs[0]) == ALL_APPS_ID) {
            SingleApps singleRedis = SingleApps.getSingleApps();
            ClassPathXmlApplicationContext ctx = singleRedis.getContext();
            String[] appIds = ctx.getBean("allAppId", AllAppId.class).getApps();

            for (int i = 0; i < appIds.length; i++) {
                apps.add(new MainTrendApp(appIds[i], getMainTrendDetails(Integer.valueOf(appIds[i]), start, end)));
            }
        } else {
            for (int i = 0; i < appStrs.length; i++) {
                apps.add(new MainTrendApp(appStrs[i], getMainTrendDetails(Integer.valueOf(appStrs[i]), start, end)));
            }
        }

        return new MainTrend("main", "trend", apps);
    }


}
