package com.xcloudeye.stats.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.xcloudeye.stats.dao.PaymentDao;
import com.xcloudeye.stats.domain.db.Payment;
import com.xcloudeye.stats.domain.db.UserInfo;

public class QueryPaymentTask implements Callable<List<Payment>> {
    private static final Logger logger = LoggerFactory.getLogger(QueryPaymentTask.class);
    private PaymentDao paymentDao;
    private List<UserInfo> list;
    private int startTime;
    private int endTime;

    public QueryPaymentTask(PaymentDao paymentDao, List<UserInfo> list, int startTime,
            int endTime) {
        super();
        this.paymentDao = paymentDao;
        this.list = list;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public List<Payment> call() throws Exception {
        List<Payment> pList = new ArrayList<>();
        try {
            for (UserInfo user : list) {
                List<Criteria> criterias = new ArrayList<Criteria>();
                criterias.add(Criteria.where("userid").is(user.getUid()));
                criterias.add(Criteria.where("status").is("1")); // 1 success
                criterias.add(Criteria.where("date").gte(startTime));
                criterias.add(Criteria.where("date").lte(endTime));
                Criteria criteria = new Criteria()
                        .andOperator(criterias.toArray(new Criteria[criterias.size()]));
                Query query = new Query(criteria);
                List<Payment> paymentList = paymentDao.readMoreByQuery(query);
                if (paymentList != null && !paymentList.isEmpty()) {
                    pList.addAll(paymentList);
                }
            }
        } catch (Exception e) {
            logger.error("QueryPaymentTask error", e);
        }
        return pList;
    }

}
