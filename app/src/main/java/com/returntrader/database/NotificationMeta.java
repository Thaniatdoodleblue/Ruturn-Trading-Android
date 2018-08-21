package com.returntrader.database;

import com.bluelinelabs.logansquare.annotation.JsonField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by moorthy on 11/23/2017.
 */

@Entity
public class NotificationMeta {

    @Id(autoincrement = true)
    private Long id;

    private String title;

    private String content;

    private String webLink;

    private String imageLink;

    private String updatedAt;




    @Generated(hash = 374052032)
    public NotificationMeta(Long id, String title, String content, String webLink,
            String imageLink, String updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.webLink = webLink;
        this.imageLink = imageLink;
        this.updatedAt = updatedAt;
    }

    @Generated(hash = 292135088)
    public NotificationMeta() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }
}
