package com.xcloudeye.stats.domain.app;

public class AppDauChannel {

    private String channel;
    private Integer yes_reg;
    private Integer to_login;
    private float dau;

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     */
    public AppDauChannel() {
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param yes_reg
     * @param to_login
     * @param dau
     */
    public AppDauChannel(String channel, Integer yes_reg, Integer to_login,
                         float dau) {
        this.setChannel(channel);
        this.yes_reg = yes_reg;
        this.to_login = to_login;
        this.dau = dau;
    }

    public Integer getYes_reg() {
        return yes_reg;
    }

    public void setYes_reg(Integer yes_reg) {
        this.yes_reg = yes_reg;
    }

    public Integer getTo_login() {
        return to_login;
    }

    public void setTo_login(Integer to_login) {
        this.to_login = to_login;
    }

    public float getDau() {
        return dau;
    }

    public void setDau(float dau) {
        this.dau = dau;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

}
