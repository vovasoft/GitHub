package com.xcloudeye.stats.domain.db;

public class PayHobbyType {

    private String paytype;
    private Integer payer;
    private Integer order;
    private double income;

    public PayHobbyType() {
    }

    public PayHobbyType(String paytype, Integer payer, Integer order,
                        double income) {
        super();
        this.paytype = paytype;
        this.payer = payer;
        this.order = order;
        this.income = income;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public Integer getPayer() {
        return payer;
    }

    public void setPayer(Integer payer) {
        this.payer = payer;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

}
