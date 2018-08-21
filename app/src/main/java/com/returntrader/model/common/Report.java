package com.returntrader.model.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.returntrader.model.dto.response.BaseResponse;

/**
 * Created by moorthy on 11/17/2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class Report extends BaseResponse implements Parcelable ,Comparable<Report> {

    @JsonField(name = "id")
    private String id;

    @JsonField(name = "chat_id")
    private String chatId;

    @JsonField(name = "content")
    private String content;

    @JsonField(name = "updated_at")
    private String updatedAt;

    @JsonField(name = "created_at")
    private String createdAt;

    public Report() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    protected Report(Parcel in) {
        id = in.readString();
        chatId = in.readString();
        content = in.readString();
        updatedAt = in.readString();
        createdAt = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(chatId);
        dest.writeString(content);
        dest.writeString(updatedAt);
        dest.writeString(createdAt);
    }

    @SuppressWarnings("unused")
    public static final Creator<Report> CREATOR = new Creator<Report>() {
        @Override
        public Report createFromParcel(Parcel in) {
            return new Report(in);
        }

        @Override
        public Report[] newArray(int size) {
            return new Report[size];
        }
    };

    @Override
    public int compareTo(@NonNull Report o) {
        if (getCreatedAt() == null || o.getCreatedAt() == null)
            return 0;
        return getCreatedAt().compareTo(o.getCreatedAt());
    }
}
