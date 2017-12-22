package com.xcloudeye.stats.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.xcloudeye.stats.dao.UserInfoDao;
import com.xcloudeye.stats.domain.db.UserInfo;
import com.xcloudeye.stats.single.SingleMongo;

public class UserInfoLogic {
    private UserInfoDao userInfoDao;

    public void initPaymentLogic() {
        SingleMongo singleMongo = SingleMongo.getSinglemongo();
        ClassPathXmlApplicationContext ctx = singleMongo.getDaoContext();
        this.userInfoDao = ctx.getBean("userInfoDao", UserInfoDao.class);
    }

    public List<UserInfo> readMoreByQuery(int startTime, int endTime) {
        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("rcvtime").gte(startTime));
        criterias.add(Criteria.where("rcvtime").lte(endTime));
        Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
        Query query = new Query(criteria);
        return userInfoDao.readMoreByQuery(query);
    }
}
