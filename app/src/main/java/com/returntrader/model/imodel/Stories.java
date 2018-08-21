package com.returntrader.model.imodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Karthikeyan on 18-07-2017
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class Stories implements Parcelable {


    @JsonField(name = "id")
    private String id;

    @JsonField(name = "title")
    private String title;

    @JsonField(name = "description")
    private String body;

    @JsonField(name = "publishedAt")
    private String publishedAt;


    @JsonField(name = "url")
    private String url;

    @JsonField(name = "urlToImage")
    private String urlToImage;

    public Stories() {
    }

    protected Stories(Parcel in) {
        id = in.readString();
        title = in.readString();
        body = in.readString();
        publishedAt = in.readString();
        url = in.readString();
        urlToImage = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(body);
        dest.writeString(publishedAt);
        dest.writeString(url);
        dest.writeString(urlToImage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Stories> CREATOR = new Creator<Stories>() {
        @Override
        public Stories createFromParcel(Parcel in) {
            return new Stories(in);
        }

        @Override
        public Stories[] newArray(int size) {
            return new Stories[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }
}