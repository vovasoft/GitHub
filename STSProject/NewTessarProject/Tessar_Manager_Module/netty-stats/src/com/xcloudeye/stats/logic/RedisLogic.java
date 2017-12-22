package com.xcloudeye.stats.logic;

import com.xcloudeye.stats.dao.ApolloRedisDao;
import com.xcloudeye.stats.dao.SlaveRedisDao;
import com.xcloudeye.stats.domain.app.*;
import com.xcloudeye.stats.domain.db.PayHobbyType;
import com.xcloudeye.stats.domain.manage.Channel;
import com.xcloudeye.stats.domain.manage.ChannelManageCallback;
import com.xcloudeye.stats.domain.manage.ChannelStatus;
import com.xcloudeye.stats.domain.manage.ChannelStatusChanged;
import com.xcloudeye.stats.domain.user.User;
import com.xcloudeye.stats.util.CharUtil;
import com.xcloudeye.stats.util.StaticValueUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

public class RedisLogic {

    private String key = null;
    private String appname = null;

    private ApolloRedisDao apolloRedisDao;
    private SlaveRedisDao slaveRedisDao;

    private AdvanceDriverLogic advanceDriverLogic;

    public SlaveRedisDao getSlaveRedisDao() {
        return slaveRedisDao;
    }

    public void setSlaveRedisDao(SlaveRedisDao slaveRedisDao) {
        this.slaveRedisDao = slaveRedisDao;
    }

    public ApolloRedisDao getApolloRedisDao() {
        return apolloRedisDao;
    }

    public void setApolloRedisDao(ApolloRedisDao apolloRedisDao) {
        this.apolloRedisDao = apolloRedisDao;
    }

    public void initRedisLogic(int appid) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/redis-conf.xml");
        this.apolloRedisDao = ctx.getBean("redisApolloDao", ApolloRedisDao.class);
        this.slaveRedisDao = ctx.getBean("redisSlaveDao", SlaveRedisDao.class);

        advanceDriverLogic = new AdvanceDriverLogic(appid);
        switch (appid) {
            case StaticValueUtil.BLOODSTRIK_ID:
                key = StaticValueUtil.BS_PAY;
                appname = "bs";
                break;
            case StaticValueUtil.NARUTO_ID:
                key = StaticValueUtil.NARUTO_PAY;
                appname = "won";
                break;
            default:
                break;
        }

    }

    public void initRedisForChPay(int appid) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/redis-conf.xml");
        this.apolloRedisDao = ctx.getBean("redisApolloDao", ApolloRedisDao.class);
        this.slaveRedisDao = ctx.getBean("redisSlaveDao", SlaveRedisDao.class);


        advanceDriverLogic = new AdvanceDriverLogic(appid);
        switch (appid) {
            case StaticValueUtil.BLOODSTRIK_ID:
                key = StaticValueUtil.BS_INCOME;
                appname = "bs";
                break;
            case StaticValueUtil.NARUTO_ID:
                key = StaticValueUtil.NARUTO_INCOME;
                appname = "naruto";
                break;
            default:
                break;
        }

    }


    /**
     * <p>Title: </p>
     * <p>Description: 初始化redis的dao层操作对象</p>
     */
    public RedisLogic() {
    }


    /**
     * <p>Title: SortByIncome</p>
     * <p>Description: 集合比较类</p>
     * <p>Company: LTGames</p>
     *
     * @author xjoker
     * @date 2015年7月2日
     */
    class SortByIncome implements Comparator {
        public int compare(Object o1, Object o2) {
            PaySortDetail s1 = (PaySortDetail) o1;
            PaySortDetail s2 = (PaySortDetail) o2;
            if (s1.getPay_total() > s2.getPay_total()) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    /**
     * <p>Title: getPaySort</p>
     * <p>Description: 获取pay_sort接口数据</p>
     *
     * @return
     */
    public PaySortOut getPaySort() {

        List<PaySortDetail> detail = new ArrayList<PaySortDetail>();
        Set<String> uid = apolloRedisDao.getTopNUid(20, key);
        try {
            Iterator cousor = uid.iterator();
            int count = 0;
            while (cousor.hasNext()) {
                String userid = cousor.next().toString();
                double income = apolloRedisDao.getScoreByMember(key, userid);
                String channel = apolloRedisDao.getChannelByUid("channel", userid);
                count++;
                if (channel == null || channel.equals(null)) {
                    channel = "NA";
                }
                detail.add(new PaySortDetail(count, userid, income, channel));
            }
        } catch (Exception e) {
            System.out.println("error: " + this.getClass() + ".getPaySort()  " + new Date());
            e.printStackTrace();
        }
        //集合排序
//		Collections.sort(detail, new SortByIncome());
        for (int i = 0; i < detail.size(); i++) {
            System.out.println(detail.get(i).getSort() + "   " + detail.get(i).getId() + "    "
                    + detail.get(i).getPay_total() + "   " + detail.get(i).getChannel());
        }
        return new PaySortOut("app", "pay_sort", null, detail);
    }



	/*-------------------------------------pay hobby-----------------------------------------------*/


    /**
     * @return Return all channels'name of pay.
     */

    public List<String> getAllPayChs() {
        Set<String> paychKeys = slaveRedisDao.getAllPayChs(appname);
        List<String> payChs = new ArrayList<String>();
        paychKeys.forEach(key -> {
            payChs.add(CharUtil.cutString(key, ":"));
        });

        return payChs;
    }

    /**
     * @param ch The channel which contains paytypes.
     * @return Return paytypes'names.
     */
    public List<String> getAllPayTypes(String ch) {
        Set<String> paytypeKeys = slaveRedisDao.getAllPayTypes(appname, ch);
        List<String> paytypes = new ArrayList<String>();
        paytypeKeys.forEach(key -> {
            System.out.println("paytype---:" + CharUtil.cut3String(key, ":", 3));
            paytypes.add(CharUtil.cut3String(key, ":", 3));
        });
        return paytypes;
    }

    /**
     * @param ch
     * @param paytype
     * @param start
     * @param end
     * @return Return the infomation of paytype.
     */
    public PayHobbyType getPayType(String ch, String paytype, Integer start, Integer end) {
        Set<String> orderids = slaveRedisDao.getOrderidByTime(appname, ch, paytype, start, end);
        Integer order = orderids.size();
        if (order == 0) {
            return null;
        }
        List<String> uids = new ArrayList<String>();
        final double[] incomes = {0};
        orderids.forEach(orderid -> {
            String uid = slaveRedisDao.getUidByOrderid(appname, orderid);
            if (!uids.contains(uid)) {
                uids.add(uid);
            }
            incomes[0] += slaveRedisDao.getMoneyByOrderid(appname, orderid);
        });
        return new PayHobbyType(paytype, uids.size(), order, incomes[0]);
    }

    /**
     * @param ch
     * @param start
     * @param end
     * @return Return the detail of pay hobby.
     */
    public PayHobbyDetail getPayDetail(String ch, Integer start, Integer end) {
        Set<String> allOrderid = slaveRedisDao.getAllOrderidByCh(appname, ch, start, end);
        Integer all_order = allOrderid.size();
        if (all_order == 0) {
            return null;
        }
        List<String> uids = new ArrayList<String>();
        List<PayHobbyType> types = new ArrayList<PayHobbyType>();

        final double[] all_income = {0};
        allOrderid.forEach(orderid -> {
            String uid = slaveRedisDao.getUidByOrderid(appname, orderid);
            if (!uids.contains(uid)) {
                uids.add(uid);
            }
            all_income[0] += slaveRedisDao.getMoneyByOrderid(appname, orderid);
        });
        List<String> paytypes = getAllPayTypes(ch);
        paytypes.forEach(paytype -> {
            PayHobbyType type = getPayType(ch, paytype, start, end);
            if (type != null && !type.equals(null)) {
                types.add(type);
            }
        });
        String parent = "NA";
        String child = "NA";
        String seq = "NA";
        if (CharUtil.judgeStringMatchFormat(ch)) {
            parent = CharUtil.cut3String(ch, "_", 1);
            child = CharUtil.cut3String(ch, "_", 2);
            seq = CharUtil.cut3String(ch, "_", 3);
        } else {
            String chName = slaveRedisDao.getChNewName(appname, ch);
            if (chName != null && !chName.equals(null)
                    && chName != "" && !"".equals(chName)) {
                parent = CharUtil.cut3String(chName, "_", 1);
                child = CharUtil.cut3String(chName, "_", 2);
                seq = CharUtil.cut3String(chName, "_", 3);
            }
        }
        return new PayHobbyDetail(ch, parent, child, seq, uids.size(), all_order, all_income[0], types);
    }

    /**
     * @param start
     * @param end
     * @return Return the data of pay hobby.
     */
    public PayHobby getPayHobby(Integer start, Integer end) {
        PayHobbyGeneric generic = advanceDriverLogic.getPayHobbyGeneric();
        List<PayHobbyDetail> details = new ArrayList<PayHobbyDetail>();
        List<String> chs = getAllPayChs();
        chs.forEach(ch -> {
            PayHobbyDetail detail = getPayDetail(ch, start, end);
            if (detail != null && !detail.equals(null)) {
                details.add(detail);
            }
        });

        return new PayHobby("pay_hobby", start, end, generic, details);
    }

    /**
     * @return Get all chs'names.
     */
    public List<String> getAllRegChs() {
        Set<String> chKeys = slaveRedisDao.getAllChs(appname);
        Set<String> newCh = slaveRedisDao.getAllChsFromAddCh(appname);
        chKeys.addAll(newCh);
        List<String> chs = new ArrayList<String>();
        chKeys.forEach(key -> {
            chs.add(CharUtil.cutString(key, ":"));
        });

        return chs;
    }

    /**
     * @param oldName The old name of the channel.
     * @param newName The new name of the same channel.
     * @return The callback of the insert work.
     */
    public ChannelManageCallback channgeTheChannelName(String oldName, String newName) {
        ChannelManageCallback callback = new ChannelManageCallback();
        callback.setChid(0);
        callback.setChannel(oldName);
        callback.setFlag(StaticValueUtil.VALUE_NOT_EXIST);

        List<String> chs = getAllRegChs();
        if (chs.contains(oldName)) {
            try {
                slaveRedisDao.insertChName(appname, oldName, newName);
                callback.setFlag(StaticValueUtil.SUCCESS);
            } catch (Exception e) {
                callback.setFlag(StaticValueUtil.INSERT_FAILED);
                e.printStackTrace();
            }
        }
        return callback;
    }


    /**
     * @return Return the data of channels'status.
     */
    public ChannelStatus getChsStatus() {
        List<ChannelStatusChanged> changedChs = new ArrayList<ChannelStatusChanged>();

        Set<String> oldnames = slaveRedisDao.getAllChangedOldName(appname);
        oldnames.forEach(old -> {
            changedChs.add(new ChannelStatusChanged(old, slaveRedisDao.getChNewName(appname, old)));
        });

        List<String> allChs = getAllRegChs();
        allChs.removeAll(oldnames);
        return new ChannelStatus("ch_status", allChs, changedChs);
    }


    /**
     * <p>Title: getPaySortCh</p>
     * <p>Description: 获取pay_sort_ch接口数据</p>
     *
     * @return
     */
    public PaySortChOut getPaySortCh(String username, List<String> channels, int startTime, int endTime) {
        ArrayList<PaySortChDetail> detail = new ArrayList<PaySortChDetail>();
        UserSystemLogic userSysLogic = new UserSystemLogic();
        userSysLogic.initUserLogic();
        UserManagePageLogic userManageLogic = new UserManagePageLogic();
        userManageLogic.initManageLogic();
        User user = userSysLogic.getUser(username);
        String group = user.getGroup();
        Long userid = user.getUid();
        if (group.equals("admin")) {
            int count = 0;
            for (String ch : channels) {
                Set<String> oids = slaveRedisDao.getAllOrderidByCh(appname, ch, startTime, endTime);
                final double[] incomes = {0};
                count++;
                oids.forEach(orderid -> {
                    incomes[0] += slaveRedisDao.getMoneyByOrderid(appname, orderid);
                });
                if (count <= 20) {
                    if (ch == null || ch.equals(null)) {
                        ch = "NA";
                    }
                    detail.add(new PaySortChDetail(count, ch, incomes[0]));
                } else {
                    break;
                }
            }
//			System.out.println("slaveDao"+slaveRedisDao);
//			Set<String> channels = slaveRedisDao.getTopNCh(20, key);
//			try {
//				Iterator cousor = channels.iterator();
//				int count = 0;
//				while (cousor.hasNext()) {
//					String channel = cousor.next().toString();
//					double income = slaveRedisDao.getScoreByMember(key, channel);
//					String channel = apolloRedisDao.getChannelByUid("channel", userid);
//					count++;
//					if ( channel == null || channel.equals(null)) {
//						channel = "NA";
//					}
//					detail.add(new PaySortChDetail(count, channel, income));
//				}
//			} catch (Exception e) {
//				System.out.println("error: "+this.getClass() +".getPaySortCh()  " + new Date());
//				e.printStackTrace();
//			}
        } else {
            List<Channel> channelList = userManageLogic.getUserChs(userid);
            List<String> ownerchannels = new ArrayList<String>();
            if (channelList != null) {
                if (channelList.size() >= 20) {
                    int count = 0;
                    for (Channel ch : channelList) {
                        String chname = ch.getChannel();
                        Set<String> oids = slaveRedisDao.getAllOrderidByCh(appname, chname, startTime, endTime);
                        final double[] incomes = {0};
                        count++;
                        oids.forEach(orderid -> {
                            incomes[0] += slaveRedisDao.getMoneyByOrderid(appname, orderid);
                        });
                        if (count <= 20) {
                            if (chname == null || chname.equals(null)) {
                                chname = "NA";
                            }
                            detail.add(new PaySortChDetail(count, chname, incomes[0]));
                        } else {
                            break;
                        }
                    }
                } else {
                    int count = 0;
                    for (Channel ch : channelList) {
                        String chname = ch.getChannel();
                        Set<String> oids = slaveRedisDao.getAllOrderidByCh(appname, chname, startTime, endTime);
                        final double[] incomes = {0};
                        count++;
                        oids.forEach(orderid -> {
                            incomes[0] += slaveRedisDao.getMoneyByOrderid(appname, orderid);
                        });
                        if (chname == null || chname.equals(null)) {
                            chname = "NA";
                        }
                        detail.add(new PaySortChDetail(count, chname, incomes[0]));
                    }
                }
            }
        }

        //集合排序
//		Collections.sort(detail, new SortByIncome());
//		for (int i = 0; i < detail.size(); i++) {
//			System.out.println(detail.get(i).getSort()+"    "
//					+detail.get(i).getPay_total()+"   " +detail.get(i).getChannel());
//		}
        return new PaySortChOut("app", "pay_sort_ch", null, detail);
    }


}
