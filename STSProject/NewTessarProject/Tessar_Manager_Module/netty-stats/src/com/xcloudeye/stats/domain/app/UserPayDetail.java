package com.xcloudeye.stats.domain.app;

public class UserPayDetail {

    private Integer date;
    private Integer new_payer;
    private Integer payer;
    private Integer all_order;
    private Integer payed_order;

    public UserPayDetail() {
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param date
     * @param new_payer
     * @param payer
     * @param all_order
     * @param payed_order
     */
    public UserPayDetail(Integer date, Integer new_payer, Integer payer,
                         Integer all_order, Integer payed_order) {
        this.date = date;
        this.new_payer = new_payer;
        this.payer = payer;
        this.all_order = all_order;
        this.payed_order = payed_order;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getNew_payer() {
        return new_payer;
    }

    public void setNew_payer(Integer new_payer) {
        this.new_payer = new_payer;
    }

    public Integer getPayer() {
        return payer;
    }

    public void setPayer(Integer payer) {
        this.payer = payer;
    }

    public Integer getAll_order() {
        return all_order;
    }

    public void setAll_order(Integer all_order) {
        this.all_order = all_order;
    }

    public Integer getPayed_order() {
        return payed_order;
    }

    public void setPayed_order(Integer payed_order) {
        this.payed_order = payed_order;
    }

}
