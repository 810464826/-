package com.example.administrator.toolb.entity;

/**
 * Created by Administrator on 2016/7/16.
 */
public class News {
    private String title;
    private String date;
    private String author_name;
    private String thumbnail_pic_s;
    private String url;

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", author_name='" + author_name + '\'' +
                ", thumbnail_pic_s='" + thumbnail_pic_s + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getThumbnail_pic_s() {
        return thumbnail_pic_s;
    }

    public void setThumbnail_pic_s(String thumbnail_pic_s) {
        this.thumbnail_pic_s = thumbnail_pic_s;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public News(String title, String date, String author_name, String thumbnail_pic_s, String url) {

        this.title = title;
        this.date = date;
        this.author_name = author_name;
        this.thumbnail_pic_s = thumbnail_pic_s;
        this.url = url;
    }
}
