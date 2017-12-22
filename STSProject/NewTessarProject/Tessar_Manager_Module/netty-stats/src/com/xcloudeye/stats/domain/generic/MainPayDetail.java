package com.xcloudeye.stats.domain.generic;

public class MainPayDetail {

    private Integer date;
    private Integer payer;
    private Integer new_payer;
    private double income;

    public MainPayDetail() {
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param date
     * @param payer
     * @param new_payer
     * @param income
     */
    public MainPayDetail(Integer date, Integer payer, Integer new_payer,
                         double income) {
        this.date = date;
        this.payer = payer;
        this.new_payer = new_payer;
        this.income = income;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
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

}
