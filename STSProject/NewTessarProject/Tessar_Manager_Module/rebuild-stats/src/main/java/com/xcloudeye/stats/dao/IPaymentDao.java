package com.xcloudeye.stats.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.DBObject;
import com.xcloudeye.stats.domain.db.Payment;


/**
 * @author 金润
 *
 */
public interface IPaymentDao {
	
	public Payment readByQuery(Query query);
	
	public List<Payment> readMoreByQuery(Query query);
	
	public <E>List<E> readMoreDistinctByQuery(String key, DBObject query);
	
}
