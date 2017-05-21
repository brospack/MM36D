package com.aidchow.util;

import com.aidchow.entity.FindFlowerEntity;
import com.aidchow.entity.ImageDetailEntity;
import com.aidchow.entity.ImageEntity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by AidChow on 2017/3/31.
 * parse utils  for parse html source
 */

public class ParseUtil {
    /**
     * parse the html date
     *
     * @param response the html source
     * @return a list about the ImageEntity
     */
    public static List<ImageEntity> parseImageList(String response) {
        List<ImageEntity> imageEntites = new ArrayList<>();
        Document document = Jsoup.parse(response);
        Elements elements = document.getElementsByClass("col-md-3 re-size1");
        for (Element element : elements) {
            ImageEntity imageEntity = new ImageEntity();
            String patternID = "(\\d.*\\d)";
            Pattern pattern = Pattern.compile(patternID);
            Element a = element.select("a").first();
            String labelId = a.attr("onclick");
            Matcher matcher = pattern.matcher(labelId);
            if (matcher.find()) {
                labelId = matcher.group();
            }
            String title = a.text();
            String imageUrl = element.select("img.lazy").attr("data-original");
            imageEntity.setSmallImageUrl(imageUrl);
            Element span = element.select("span.girl-heart").first();
            String spanText = span.text().replaceAll("\\u00a0", "");
            String strs = spanText.replaceAll("\\u53d1\\u5e03", " ");
            strs = strs.replaceAll("\\u6d4f\\u89c8", "");
            strs = strs.replaceAll("\\u70b9\\u8d5e", " ");
            strs = strs.replaceAll("\\(", "");
            strs = strs.replaceAll("\\)", "");
            String[] reult = strs.split(" ");
            imageEntity.setTitle(title);
            imageEntity.setLabelId(labelId);
            imageEntity.setPushDate(reult[0]);
            imageEntity.setBrowseNum(reult[1]);
            imageEntity.setLikeNum(reult[2]);
            imageEntites.add(imageEntity);
        }
        return imageEntites;
    }

    /**
     * parse the category data from the html
     *
     * @param response the html source
     * @return List<FindFlowerEntity>
     */
    public static List<FindFlowerEntity> parseFindFlowerList(String response) {
        List<FindFlowerEntity> findFlowerEntities = new ArrayList<>();
        Document document = Jsoup.parse(response);
        Elements elements = document.getElementsByClass("col-md-3 re-size1");
        for (Element element : elements) {
            FindFlowerEntity findFlowerEntity = new FindFlowerEntity();
            Element img = element.select("img").first();
            String preUrl = img.attr("data-original");
            String patternID = "(\\d.*\\d)";
            Pattern pattern = Pattern.compile(patternID);
            String labelId = img.attr("onclick");
            Matcher matcher = pattern.matcher(labelId);
            if (matcher.find()) {
                labelId = matcher.group();
            }
            String catepory = element.select("div.grid-txt").select("span").text();
            String total = element.select("div.grid-txt-small").select("span").text();
            findFlowerEntity.setCategory(catepory);
            findFlowerEntity.setPreviewUrl(preUrl);
            findFlowerEntity.setToLabel(labelId);
            findFlowerEntity.setTotal(total);
            findFlowerEntities.add(findFlowerEntity);
        }

        return findFlowerEntities;
    }

    /**
     * parse the image details from the html
     *
     * @param response the html source
     * @return ImageDetailEntity {@see ImageDetailEntity}
     */
    public static ImageDetailEntity parseImageDetailEntity(String response) {
        ImageDetailEntity imageDetailEntity = new ImageDetailEntity();
        List<String> urlList = new ArrayList<>();
        try {
            Document document = Jsoup.parse(response);
            Element element = document.select("div.grid_v").first();
            String title = element.select("span.girl-txt").text();
            Elements es = document.select("li.re-sizemm");
            imageDetailEntity.setTitle(title);
            for (Element e : es) {
                String picUrl = e.select("img.lazy").attr("data-original");
                urlList.add(picUrl);
            }
            imageDetailEntity.setPicUrls(urlList);
        } catch (Exception ignored) {

        }

        return imageDetailEntity;
    }
}
