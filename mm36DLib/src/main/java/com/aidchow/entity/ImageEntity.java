package com.aidchow.entity;

/**
 * Created by AicChow on 2017/3/30.
 * Image Entity just for the overview list
 */
public class ImageEntity {
    private String title;
    private String labelId;
    private String pushDate;
    private String browseNum;
    private String likeNum;
    private String smallImageUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getPushDate() {
        return pushDate;
    }

    public void setPushDate(String pushDate) {
        this.pushDate = pushDate;
    }

    public String getBrowseNum() {
        return browseNum;
    }

    public void setBrowseNum(String browseNum) {
        this.browseNum = browseNum;
    }

    public String getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(String likeNum) {
        this.likeNum = likeNum;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    @Override
    public String toString() {
        return "ImageEntity{" +
                "title='" + title + '\'' +
                ", labelId='" + labelId + '\'' +
                ", pushDate='" + pushDate + '\'' +
                ", browseNum='" + browseNum + '\'' +
                ", likeNum='" + likeNum + '\'' +
                ", smallImageUrl='" + smallImageUrl + '\'' +
                '}';
    }
}
