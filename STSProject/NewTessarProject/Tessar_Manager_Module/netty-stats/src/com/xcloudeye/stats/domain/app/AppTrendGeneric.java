package com.xcloudeye.stats.domain.app;

public class AppTrendGeneric {

    private Integer total;
    private Integer wau;
    private Integer mau;
    private Integer total_payer;
    private double income;
    private Float arpu;
    private Float arppu;

    public AppTrendGeneric() {
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param total
     * @param wau
     * @param mau
     * @param total_payer
     * @param income
     */
    public AppTrendGeneric(Integer total, Integer wau, Integer mau,
                           Integer total_payer, double income, Float arpu, Float arppu) {
        this.total = total;
        this.wau = wau;
        this.mau = mau;
        this.total_payer = total_payer;
        this.income = income;
        this.arpu = arpu;
        this.arppu = arppu;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getWau() {
        return wau;
    }

    public void setWau(Integer wau) {
        this.wau = wau;
    }

    public Integer getMau() {
        return mau;
    }

    public void setMau(Integer mau) {
        this.mau = mau;
    }

    public Integer getTotal_payer() {
        return total_payer;
    }

    public void setTotal_payer(Integer total_payer) {
        this.total_payer = total_payer;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public Float getArpu() {
        return arpu;
    }

    public void setArpu(Float arpu) {
        this.arpu = arpu;
    }

    public Float getArppu() {
        return arppu;
    }

    public void setArppu(Float arppu) {
        this.arppu = arppu;
    }

}
