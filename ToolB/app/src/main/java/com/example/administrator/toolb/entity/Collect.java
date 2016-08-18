package com.example.administrator.toolb.entity;

/**
 * Created by Administrator on 2016/7/17.
 */
public class Collect {
    private String title;
    private String url;

    public Collect() {
    }

    @Override
    public String toString() {
        return "Collect{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public Collect(String title, String url) {
        this.title = title;
        this.url = url;
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
}
