package com.xcloudeye.stats.domain.generic;

import java.util.ArrayList;
import java.util.List;

public class MainGeneric {
    private String set;
    private String api;
    private MainGenericGeneric generic = new MainGenericGeneric();
    private List<MainGnericDetail> details = new ArrayList<MainGnericDetail>();

    public MainGeneric() {
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param set
     * @param api
     * @param generic
     * @param details
     */
    public MainGeneric(String set, String api, MainGenericGeneric generic,
                       List<MainGnericDetail> details) {
        this.set = set;
        this.api = api;
        this.generic = generic;
        this.details = details;
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

    public MainGenericGeneric getGeneric() {
        return generic;
    }

    public void setGeneric(MainGenericGeneric generic) {
        this.generic = generic;
    }

    public List<MainGnericDetail> getDetails() {
        return details;
    }

    public void setDetails(List<MainGnericDetail> details) {
        this.details = details;
    }

}
