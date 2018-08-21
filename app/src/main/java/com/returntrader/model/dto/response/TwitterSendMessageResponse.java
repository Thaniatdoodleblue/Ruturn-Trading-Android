package com.returntrader.model.dto.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by moorthy on 9/4/2017.
 */


public class TwitterSendMessageResponse {

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("id")
    private String id;

    @SerializedName("recipient_id")
    private String recipientId;


    @SerializedName("sender_id")
    private String senderId;

    @SerializedName("text")
    private String text;

    public String getCreatedAt() {
        return createdAt;
    }

    public String getId() {
        return id;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getText() {
        return text;
    }
}
