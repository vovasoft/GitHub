package com.xcloudeye.stats.domain.generic;

public class MainGenericGeneric {

    private long total_user;
    private Integer wau;
    private Integer mau;
    private Integer payer;

    public MainGenericGeneric() {
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param total_user
     * @param wau
     * @param mau
     * @param payer
     */
    public MainGenericGeneric(long total_user, Integer wau, Integer mau,
                              Integer payer) {
        this.total_user = total_user;
        this.wau = wau;
        this.mau = mau;
        this.payer = payer;
    }

    public long getTotal_user() {
        return total_user;
    }

    public void setTotal_user(long total_user) {
        this.total_user = total_user;
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

    public Integer getPayer() {
        return payer;
    }

    public void setPayer(Integer payer) {
        this.payer = payer;
    }

}
