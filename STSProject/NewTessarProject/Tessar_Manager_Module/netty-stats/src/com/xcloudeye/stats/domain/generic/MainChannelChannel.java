package com.xcloudeye.stats.domain.generic;

public class MainChannelChannel {

    private String parent;
    private Integer new_user;
    private Integer payed_order;
    private double income;


    public MainChannelChannel() {
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param parent
     * @param new_user
     * @param payed_order
     * @param income
     * @param active_user
     */
    public MainChannelChannel(String parent, Integer new_user,
                              Integer payed_order, double income) {
        this.parent = parent;
        this.new_user = new_user;
        this.payed_order = payed_order;
        this.income = income;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Integer getNew_user() {
        return new_user;
    }

    public void setNew_user(Integer new_user) {
        this.new_user = new_user;
    }

    public Integer getPayed_order() {
        return payed_order;
    }

    public void setPayed_order(Integer payed_order) {
        this.payed_order = payed_order;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

}
