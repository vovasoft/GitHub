package com.xcloudeye.stats.domain.manage;

import java.util.ArrayList;
import java.util.List;

public class ChannelManageOut {
    private String set;
    private String api;
    private Integer page;
    private List<ChannelManageDetail> detail = new ArrayList<ChannelManageDetail>();

    public ChannelManageOut() {
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param set
     * @param api
     * @param page
     * @param detail
     */
    public ChannelManageOut(String set, String api, Integer page,
                            List<ChannelManageDetail> detail) {
        this.set = set;
        this.api = api;
        this.page = page;
        this.detail = detail;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<ChannelManageDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<ChannelManageDetail> detail) {
        this.detail = detail;
    }

}
