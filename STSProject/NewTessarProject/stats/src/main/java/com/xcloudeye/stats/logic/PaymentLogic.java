package com.xcloudeye.stats.logic;

import com.xcloudeye.stats.dao.PaymentDao;
import com.xcloudeye.stats.domain.app.ChOrder;
import com.xcloudeye.stats.domain.app.ChOrderDetail;
import com.xcloudeye.stats.domain.app.PaySortChDetail;
import com.xcloudeye.stats.domain.app.PaySortChOut;
import com.xcloudeye.stats.domain.db.Payment;
import com.xcloudeye.stats.domain.db.UserInfo;
import com.xcloudeye.stats.single.SingleMongo;
import com.xcloudeye.stats.task.QueryPaymentTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by admin on 2015/12/4.
 */
public class PaymentLogic {
    private static final Logger logger = LoggerFactory.getLogger(PaymentLogic.class);
    private static ExecutorService executor = Executors.newCachedThreadPool();
    PaymentDao paymentDao;

    /**
     * <p>Title: initPaymentLogic</p>
     * <p>Description: paymentDao���������</p>
     */
    public void initPaymentLogic(){
        SingleMongo singleMongo = SingleMongo.getSinglemongo();
        ClassPathXmlApplicationContext ctx = singleMongo.getDaoContext();
        this.paymentDao = ctx.getBean("paymentDao", PaymentDao.class);
    }

    public List<String> getChannels(int startTime,int endTime){
        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("date").gte(startTime));
        criterias.add(Criteria.where("date").lte(endTime));
        Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
        Query query = new Query(criteria);
        List<String> channelList = paymentDao.readMoreDistinctByQuery("channel", query.getQueryObject());
        return channelList;
    }

    public ChOrder getPaymentByCh(int startTime,int endTime,String channel){
        ChOrder chOrder = new ChOrder("detail_ch_order",startTime,endTime,null);
        ArrayList<ChOrderDetail> detail= new ArrayList<ChOrderDetail>();
        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("channel").is(channel));
        criterias.add(Criteria.where("date").gte(startTime));
        criterias.add(Criteria.where("date").lte(endTime));
        Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
        Query query = new Query(criteria);
        List<Payment> paymentList = paymentDao.readMoreByQuery(query);
        for (Payment payment:paymentList){
            detail.add(new ChOrderDetail(payment.getOrderid(),payment.getUserid(),payment.getServer(),
                    payment.getGood(),payment.getPayment_type(),payment.getCurrency(),payment.getAmount(),payment.getChannel(),
                    payment.getInput_time(),payment.getCheckout_time(),payment.getStatus()));
        }
        chOrder.setDetail(detail);

        return chOrder;
    }
    
    public PaySortChOut getPaySortByNewUser(List<UserInfo> userInfoList, int startTime,
            int endTime) {
        List<PaySortChDetail> detail = new ArrayList<>();
        Map<String, Double> map = new HashMap<>();
        List<Payment> paymentList = new ArrayList<>();
        List<Future<List<Payment>>> fuList = new ArrayList<>();
        List<List<UserInfo>> splInfo = splitList(userInfoList, 10);
        
        for (List<UserInfo> ulist : splInfo) {
            try {
                QueryPaymentTask task = new QueryPaymentTask(paymentDao, ulist, startTime, endTime);
                Future<List<Payment>> future = executor.submit(task);
                fuList.add(future);
            } catch (Exception e) {
                logger.error("task submit error", e);
            }
        }
        
        for (Future<List<Payment>> fu : fuList) {
            try {
                paymentList.addAll(fu.get());
            } catch (Exception e) {
                logger.error("Future get error", e);
            }
        }
        
        for (Payment payment : paymentList) {
            Double amount = map.get(payment.getChannel());
            if (amount == null) {
                map.put(payment.getChannel(), Double.valueOf(payment.getAmount()));
            } else {
                BigDecimal b1 = new BigDecimal(Double.toString(amount));
                BigDecimal b2 = new BigDecimal(payment.getAmount());
                map.put(payment.getChannel(), b1.add(b2).doubleValue());
            }
        }

        for (String key : map.keySet()) {
            PaySortChDetail ps = new PaySortChDetail(0, key, map.get(key));
            detail.add(ps);
        }
        return new PaySortChOut("app", "roi_query", null, detail);
    }
    
    public static <T> List<List<T>> splitList(List<T> list, int pageNum) {
        List<List<T>> spList = new ArrayList<>();
        if (list == null) {
            return spList;
        }
        if (list.size() < pageNum) {
            spList.add(list);
            return spList;
        }

        int pageSize = (list.size() % pageNum) == 0 ? (list.size() / pageNum) : (list.size() / pageNum) + 1;

        for (int i = 0; i < pageNum; i++) {
            if (i == pageNum - 1) {
                List<T> nlist = list.subList(i * pageSize, list.size());
                spList.add(nlist);
            } else {
                List<T> nlist = list.subList(i * pageSize, (i + 1) * pageSize);
                spList.add(nlist);
            }
        }
        return spList;
    }
}
