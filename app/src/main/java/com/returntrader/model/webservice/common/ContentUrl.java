package com.returntrader.model.webservice.common;


import com.returntrader.common.UrlId;

/**
 * Created by Guru karthi on 30/12/14.
 */
public class ContentUrl {

    private UrlId urlId;

    private String url;

    public ContentUrl(UrlId urlId, String url) {
        this.urlId = urlId;
        this.url = url;
    }

    public UrlId getUrlId() {
        return urlId;
    }

    public void setUrlId(UrlId urlId) {
        this.urlId = urlId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}