package com.xcloudeye.stats.domain.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class PaymentGroupSum {
    @Id
    private String userid;

    @Field
    private int amount_sum;

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param userid
     * @param amount_sum
     */
    public PaymentGroupSum(String userid, int amount_sum) {
        this.userid = userid;
        this.amount_sum = amount_sum;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getAmount_sum() {
        return amount_sum;
    }

    public void setAmount_sum(int amount_sum) {
        this.amount_sum = amount_sum;
    }


}
