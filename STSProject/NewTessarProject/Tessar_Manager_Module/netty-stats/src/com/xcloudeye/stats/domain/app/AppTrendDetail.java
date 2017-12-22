package com.xcloudeye.stats.domain.app;

public class AppTrendDetail {

    private Integer date;
    private Integer payer;
    private Integer new_payer;
    private double income;

    public AppTrendDetail() {
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param time
     * @param payer
     * @param new_payer
     * @param income
     */
    public AppTrendDetail(Integer date, Integer payer, Integer new_payer,
                          double income) {
        this.setDate(date);
        this.payer = payer;
        this.new_payer = new_payer;
        this.income = income;
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

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

}
