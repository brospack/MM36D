package com.aidchow.entity;

/**
 * Created by AicChow 2017/4/1.
 * the FindFlowerEntity entity
 */

public class FindFlowerEntity {
    private String previewUrl;
    private String toLabel;       //category id
    private String category;     //category name
    private String total;       //category total num

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getToLabel() {
        return toLabel;
    }

    public void setToLabel(String toLabel) {
        this.toLabel = toLabel;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "FindFlowerEntity{" +
                "previewUrl='" + previewUrl + '\'' +
                ", toLabel='" + toLabel + '\'' +
                ", category='" + category + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
