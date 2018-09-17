package com.ljb.entity;


import java.util.List;

/**
 * Created by longjinbin on 2018/7/21.
 */

public class Article {
    private String id;
    private String title;
    private String url;
    private List<String> imgUrls;
    private String source;
    private Integer commentNum;
    private Integer type;

    public Article(){

    }

    public Article(String  id, String title, String url, List<String>  imgUrls, Integer type, Integer commentNum, String source) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.imgUrls = imgUrls;
        this.type=type;
        this.commentNum=commentNum;
        this.source=source;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getComment() {
        return commentNum;
    }

    public void setComment(Integer comment) {
        this.commentNum = comment;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String  getId() {
        return id;
    }

    public void setId(String  id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }


}
