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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FindFlowerEntity that = (FindFlowerEntity) o;

        if (previewUrl != null ? !previewUrl.equals(that.previewUrl) : that.previewUrl != null)
            return false;
        if (toLabel != null ? !toLabel.equals(that.toLabel) : that.toLabel != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null)
            return false;
        return total != null ? total.equals(that.total) : that.total == null;

    }

    @Override
    public int hashCode() {
        int result = previewUrl != null ? previewUrl.hashCode() : 0;
        result = 31 * result + (toLabel != null ? toLabel.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        return result;
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
