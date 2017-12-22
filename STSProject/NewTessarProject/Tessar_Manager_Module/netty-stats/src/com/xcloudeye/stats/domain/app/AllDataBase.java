package com.xcloudeye.stats.domain.app;

public class AllDataBase {

    private Integer new_user;
    private Integer dau;
    private Integer payer;
    private Integer new_payer;
    private Integer all_order;
    private Integer payed_order;
    private double income;

    public AllDataBase() {
    }

    public Integer getNew_user() {
        return new_user;
    }

    public void setNew_user(Integer new_user) {
        this.new_user = new_user;
    }


    public Integer getPayer() {
        return payer;
    }

    public void setPayer(Integer payer) {
        this.payer = payer;
    }

    public Integer getNew_payer() {
        return new_payer;
    }

    public void setNew_payer(Integer new_payer) {
        this.new_payer = new_payer;
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

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public Integer getDau() {
        return dau;
    }

    public void setDau(Integer dau) {
        this.dau = dau;
    }

}
