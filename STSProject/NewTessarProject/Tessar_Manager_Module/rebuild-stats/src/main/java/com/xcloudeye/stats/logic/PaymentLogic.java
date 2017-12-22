package com.xcloudeye.stats.logic;

import com.xcloudeye.stats.dao.PaymentDao;
import com.xcloudeye.stats.domain.app.ChOrder;
import com.xcloudeye.stats.domain.app.ChOrderDetail;
import com.xcloudeye.stats.domain.db.Payment;
import com.xcloudeye.stats.domain.query.PayOrderDetail;
import com.xcloudeye.stats.single.SingleMongo;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/12/4.
 */
public class PaymentLogic {
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



}
