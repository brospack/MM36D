package com.aidchow.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AidChow on 2017/4/2.
 * ImageDetail Entity
 */

public class ImageDetailEntity implements Serializable {
    private static final long serialVersionUID = 8556655454654665431L;
    private String title;
    private List<String> picUrls;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(List<String> picUrls) {
        this.picUrls = picUrls;
    }
}
