package com.xcloudeye.stats.domain.app;

/**
 * Created by admin on 2015/12/3.
 * @author qlyang
 */
public class PaySortChDetail {
    private int sort;
    private String channel;
    private double pay_total;

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     */
    public PaySortChDetail() {
    }
    /**
     * <p>Title: </p>
     * <p>Description: </p>
     * @param sort
     * @param pay_total
     * @param channel
     */
    public PaySortChDetail(int sort, String channel, double pay_total) {
        this.sort = sort;
        this.channel = channel;
        this.pay_total = pay_total;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public double getPay_total() {
        return pay_total;
    }

    public void setPay_total(double pay_total) {
        this.pay_total = pay_total;
    }
}
