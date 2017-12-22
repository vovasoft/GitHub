package com.xcloudeye.stats.domain.generic;

import java.util.ArrayList;
import java.util.List;

public class MainPayApp {

    private String app;
    private List<MainPayDetail> details = new ArrayList<MainPayDetail>();

    public MainPayApp() {
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param app
     * @param details
     */
    public MainPayApp(String app, List<MainPayDetail> details) {
        this.app = app;
        this.details = details;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public List<MainPayDetail> getDetails() {
        return details;
    }

    public void setDetails(List<MainPayDetail> details) {
        this.details = details;
    }

}
