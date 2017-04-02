package com.aidchow.entity;

/**
 * Created by AidChow on 2017/4/2.
 * ImageDetail Entity
 */

public class ImageDetailEntity {
    private String picUrl;
    private String title;
    private int totalPage;
    private int currentPage;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public String toString() {
        return "ImageDetailEntity{" +
                "picUrl='" + picUrl + '\'' +
                ", title='" + title + '\'' +
                ", totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                '}';
    }
}
